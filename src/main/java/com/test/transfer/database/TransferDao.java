package com.test.transfer.database;

import com.test.transfer.domain.Account;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class TransferDao {
    public static void transfer(long from, long to, BigDecimal amount){
        Account accountFrom = getAccount(from);
        Account accountTo = getAccount(to);
        if(Objects.isNull(accountFrom) || Objects.isNull(accountTo))
            throw new NotFoundException();

        try(Connection connection = HSQLDataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_AMOUNT)) {
                preparedStatement.setBigDecimal(1, accountFrom.getAmount().add(amount.negate()));
                preparedStatement.setLong(2, from);
                preparedStatement.execute();

                preparedStatement.setBigDecimal(1, accountTo.getAmount().add(amount));
                preparedStatement.setLong(2, to);
                preparedStatement.execute();
                connection.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Account getAccount(long id){
        try(Connection connection = HSQLDataSource.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(Queries.SELECT_ACCOUNT)){
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.next())
                    return null;
                return new Account(resultSet.getLong(1), resultSet.getBigDecimal(2));
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
