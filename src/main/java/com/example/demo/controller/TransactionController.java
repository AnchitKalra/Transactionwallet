package com.example.demo.controller;


import com.example.demo.service.TransactionService;
import com.example.demo.service.resource.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TransactionController {


    @Autowired
    TransactionService transactionService;


    @PostMapping("/transaction/wallet")
    public ResponseEntity<?> performTransaction(@RequestBody(required = false) TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionService.performTransaction(transactionRequest), HttpStatus.OK);
    }

}
