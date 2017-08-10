package com.test.transfer.database;

public interface Queries {
    String SELECT_ACCOUNT = "select id, amount from ACCOUNT where id=?";

    String UPDATE_AMOUNT = "update ACCOUNT set amount = ? where id = ?";
}
