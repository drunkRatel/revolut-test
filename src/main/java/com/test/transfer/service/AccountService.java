package com.test.transfer.service;

import com.test.transfer.database.TransferDao;
import com.test.transfer.domain.Account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("accounts")
public class AccountService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAccount(@PathParam("id") long accountId) {
        Account account = TransferDao.getAccount(accountId);
        if(Objects.isNull(account))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(account).build();
    }
}
