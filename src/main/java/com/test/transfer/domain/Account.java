package com.test.transfer.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.transfer.configuration.BigDecimalJsonMapper;

import java.math.BigDecimal;

public class Account {
    @JsonSerialize(using = BigDecimalJsonMapper.class)
    private BigDecimal amount;
    private Long id;

    public Account(){

    }

    public Account(Long id, BigDecimal amount){
        this.id = id;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
