package com.ledger.api_customerService.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionInfoDTO {


    private String id;

    private String user_id;

    private String username;

    private String message;

    private String custom_service;

    private String formatDt;

    private String user_icon;



}
