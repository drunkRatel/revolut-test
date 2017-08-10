package com.test.transfer.service;

import com.test.transfer.database.TransferDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("transfer")
public class TransferService {
    private static final int LOCK_POOL_SIZE = 100;
    private static final List<Lock> lockPool = IntStream.range(0, LOCK_POOL_SIZE)
            .mapToObj(x -> new ReentrantLock()).collect(Collectors.toList());

    @POST
    public Response transfer(@QueryParam("from") Long accountFrom, @QueryParam("to") Long accountTo,
                             @QueryParam("amount") BigDecimal amount) {
        System.out.println(accountFrom);
        System.out.println(accountTo);
        if (accountFrom.equals(accountTo))
            return Response.status(Response.Status.BAD_REQUEST).build();

        lockAccounts(accountFrom, accountTo);
        try {
            TransferDao.transfer(accountFrom, accountTo, amount);
        } finally {
            unlockAccounts(accountFrom, accountTo);
        }

        return Response.ok().build();
    }

    private void lockAccounts(long from, long to) {
        int indexFrom = getLockIndex(from);
        int indexTo = getLockIndex(to);
        lockPool.get(Math.min(indexFrom, indexTo)).lock();
        lockPool.get(Math.max(indexFrom, indexTo)).lock();
    }

    private void unlockAccounts(long from, long to) {
        int indexFrom = getLockIndex(from);
        int indexTo = getLockIndex(to);
        lockPool.get(Math.min(indexFrom, indexTo)).unlock();
        lockPool.get(Math.max(indexFrom, indexTo)).unlock();
    }

    private int getLockIndex(long accountId) {
        return (int) (accountId % LOCK_POOL_SIZE);
    }


}
