package com.example.demo.service.resource;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequest {


    private Long senderId;

    private Long receiverId;

    private Double amount;

    private String userType;

    private String transactionStatus;

    private String senderEmail;

    private String receiverEmail;


}
