package com.ikizamini.backenddash.service;

import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import twitter4j.JSONObject;

import java.util.Date;

@Service
public class PytHelper {

    public String getGwToken()
    {
        /* authorization and authentication */
        String accountId = "db62e116-c0b5-480e-9ed2-8d051bf0b668";
        String secret = "b7e739d6-eb3f-48a5-b9bd-2c01e5c88b74";
        Client client = ClientBuilder.newClient();

        Entity authPayload = Entity.json("{  \"appId\": \"" + accountId +
                "\",  \"secret\": \"" + secret +
                "\"}");

        Response response1 = client.target("https://payments-api.fdibiz.com/v2/auth")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Accept", "application/json")
                .post(authPayload);

        String authResponse = response1.readEntity(String.class);

        JSONObject payloadObject = new JSONObject(authResponse);

        JSONObject tokenObject = new JSONObject(payloadObject.getString("data"));
        return tokenObject.getString("token");

        /* *****/
    }

    public String createTrxId(String trxType, String msisdn)
    {
        long time=new Date().getTime();
        return  "TRX-tpe"+trxType+"-"+time+"-"+msisdn.substring(4);
    }

}
