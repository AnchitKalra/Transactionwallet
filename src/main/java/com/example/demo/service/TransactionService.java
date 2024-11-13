package com.example.demo.service;

import com.example.demo.service.resource.TransactionRequest;

public interface TransactionService {

    public Boolean performTransaction(TransactionRequest transactionRequest);
}
