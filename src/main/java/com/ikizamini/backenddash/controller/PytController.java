package com.ikizamini.backenddash.controller;

import com.ikizamini.backenddash.entity.PytTrx;
import com.ikizamini.backenddash.models.AuthenticationRequest;
import com.ikizamini.backenddash.models.AuthenticationResponse;
import com.ikizamini.backenddash.models.SendMoneyDto;
import com.ikizamini.backenddash.models.StatusDto;
import com.ikizamini.backenddash.service.PytHelper;
import com.ikizamini.backenddash.service.PytTrxService;
import com.ikizamini.backenddash.service.User2Service;
import com.ikizamini.backenddash.util.Msg;
import com.ikizamini.backenddash.util.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PytController {

    @Autowired
    PytTrxService pytTrxService;
    @Autowired
    PytHelper pytHelper;
    @Autowired
    User2Service user2Service;



    @RequestMapping(value = "/send-money",method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> sendMoney(@RequestBody SendMoneyDto sendMoneyDto) throws Exception{
        ResponseType response=new ResponseType();
        try {
            String accountId = "db62e116-c0b5-480e-9ed2-8d051bf0b668";

            String msisdn = sendMoneyDto.getRecipient().replace(" ", "");
            String token = pytHelper.getGwToken();
            String callback = "https://ikizamini.herokuapp.com/payment-callback";
            String trxId=pytHelper.createTrxId("send",msisdn);
            String pytMethod=sendMoneyDto.getPytMethod();

            //send to fdi
            Entity payload = Entity.json("{  \"trxRef\": \"" + trxId +
                    "\",  \"channelId\": \"" + pytMethod +
                    "\",  \"accountId\": \"" + accountId + "\"," +
                    "\"msisdn\": \"" + msisdn + "\"," +
                    "\"amount\":" + 100 + "," +
                    "\"callback\": \"" + callback + "\"}");
            Client client = ClientBuilder.newClient();
            Response res = client.target("https://payments-api.fdibiz.com/v2/momo/push")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .post(payload);

            //create transaction

            PytTrx pytTrx=new PytTrx();
            pytTrx.setAmount(Double.parseDouble(sendMoneyDto.getAmount()));
            pytTrx.setInitiator(user2Service.findById(1)); //change this to a dynamic user
            pytTrx.setReason(sendMoneyDto.getReason());
            pytTrx.setRecipient(sendMoneyDto.getRecipient());
            pytTrx.setType(0); //send
            pytTrx.setTrxId(trxId);
            pytTrx.setTrxTime(new Date().getTime());

            JSONObject payloadObject = new JSONObject(new JSONObject(res.readEntity(String.class)).getString("data"));

            if (res.getStatus() == 200)
            {
                pytTrx.setStatus(1); //pending
                pytTrx.setGwRef(payloadObject.getString("gwRef"));
                response.setCode(Msg.SUCCESS_CODE);
                response.setDescription("transaction is pending");


            }
            else
            {
                pytTrx.setStatus(3); //failed
                response.setCode(Msg.ERROR_CODE);
                response.setDescription("transaction failed");
            }

            response.setObject(pytTrxService.save(pytTrx));

        }catch (RuntimeException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("Something is wrong with the system");

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-trx-status",method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public  ResponseEntity<?> getTrxStatus(@RequestBody StatusDto statusDto){
        ResponseType response= new ResponseType();

        try{
            String token = pytHelper.getGwToken();
            Client client = ClientBuilder.newClient();
            String link="https://payments-api.fdibiz.com/v2/momo/trx/"+statusDto.getRef()+"/info";
            Response res = client.target(link)
                            .request(MediaType.APPLICATION_JSON_TYPE)
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + token)
                            .get();
           // JSONObject payloadObject = new JSONObject(new JSONObject(res.readEntity(String.class)).getString("data"));
            if(res.getStatus()==200){
               // JSONObject payloadObject = new JSONObject(new JSONObject(res.readEntity(String.class)).getString("data"));
                response.setCode(Msg.SUCCESS_CODE);
                response.setDescription("transaction status fetched");
                response.setObject(res.readEntity(String.class));
            }else{
                response.setCode(Msg.ERROR_CODE);
                response.setDescription("an error occurred");
            }


        }
        catch (RuntimeException e){
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to fetch transaction status");
            response.setObject(null);
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @RequestMapping(value = "/get-transactions",method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public  ResponseEntity<?> getAllTrx(){
        ResponseType response= new ResponseType();
        try{
            List<PytTrx> pytTrxList=pytTrxService.findAll();
            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("transactions fetched successfully");
            response.setObject(pytTrxList);
        }
        catch (RuntimeException e){
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to fetch transactions");
            response.setObject(null);
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
