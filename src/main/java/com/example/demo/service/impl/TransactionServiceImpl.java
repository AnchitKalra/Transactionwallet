package com.example.demo.service.impl;

import com.example.demo.domain.TransactionType;
import com.example.demo.service.TransactionService;
import com.example.demo.service.resource.NotificationRequest;
import com.example.demo.service.resource.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    RestTemplate restTemplate;

    NotificationRequest request = new NotificationRequest();

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    ObjectMapper mapper = new ObjectMapper();
    @Override
    public Boolean performTransaction(TransactionRequest transactionRequest) {
        String url = "http://localhost:8081/wallet/transaction";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, transactionRequest, Boolean.class);

        try {

            if (response.getBody().equals(true)) {
                request.setTransactionStatus("SUCCESS");
                request.setAmount(transactionRequest.getAmount());
                request.setSenderId(transactionRequest.getSenderId());
                request.setReceiverId(transactionRequest.getReceiverId());
                request.setUserType(transactionRequest.getType().toString());
                request.setSenderEmail(transactionRequest.getSenderEmail());
                request.setReceiverEmail(transactionRequest.getReceiverEmail());
            } else {
                request.setTransactionStatus("FAILURE");
                request.setAmount(transactionRequest.getAmount());
                request.setSenderId(transactionRequest.getSenderId());
                request.setReceiverId(transactionRequest.getReceiverId());
                request.setUserType(transactionRequest.getType().toString());
                request.setReceiverEmail(transactionRequest.getSenderEmail());
            }
            kafkaTemplate.send("notifications", mapper.writeValueAsString(request));
        }catch (Exception e) {
            System.out.println(e);
        }
        return response.getBody();
    }
}
