package com.test.transfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.transfer.Main;
import com.test.transfer.domain.Account;
import org.apache.catalina.LifecycleException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public class TransferServiceTest {
    private static Main main;
    public static final String BASE_URI = "http://localhost:8080/api/";
    @BeforeClass
    public static void setUpTomcat() throws Exception {
        main = new Main();
        main.start();
    }

    @AfterClass
    public static void tearDownTomcat() throws LifecycleException {
        main.stop();
    }

    @Test
    public void testAccount() throws Exception {
        Account accountInfo = getAccountInfo(1L);
        Assert.assertTrue(accountInfo.getAmount().compareTo(new BigDecimal("100.00")) == 0);
        Assert.assertEquals(new Long(1L), accountInfo.getId() );
    }

    @Test
    public void testTransferIncorrectAccountId() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target =  client.target(BASE_URI).path("transfer")
                .queryParam("from", "foo")
                .queryParam("to", "goo")
                .queryParam("amount", "1.002");

        Response response = target.request().post(Entity.json(null));
        Assert.assertTrue(response.getStatus() == Response.Status.NOT_FOUND.getStatusCode());
    }


    @Test
    public void testTransferPositive() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target =  client.target(BASE_URI).path("transfer")
                .queryParam("from", "2")
                .queryParam("to", "3")
                .queryParam("amount", "0.02");

        Response response = target.request().post(Entity.json(null));
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        Account from = getAccountInfo(2L);
        Account to = getAccountInfo(3L);
        Assert.assertTrue(new BigDecimal("1213.00").compareTo(from.getAmount()) == 0);
        Assert.assertTrue(new BigDecimal("11231.02").compareTo(to.getAmount()) == 0);
    }

    @Test
    public void testTransferNegative() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target =  client.target(BASE_URI).path("transfer")
                .queryParam("from", "4")
                .queryParam("to", "5")
                .queryParam("amount", "-20000.02");


        Response response = target.request().post(Entity.json(null));
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        Account from = getAccountInfo(4L);
        Account to = getAccountInfo(5L);
        Assert.assertTrue(new BigDecimal("32312.143").compareTo(from.getAmount()) == 0);
        Assert.assertTrue(new BigDecimal("-4547.790").compareTo(to.getAmount()) == 0);
    }

    private Account getAccountInfo(long id) throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target =  client.target(BASE_URI).path("accounts/" + Long.toString(id));
        Response response = target.request().get();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.readEntity(String.class), Account.class);
    }
}
