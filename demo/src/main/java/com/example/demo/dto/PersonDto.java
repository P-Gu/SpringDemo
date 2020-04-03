package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonDto {

    private UUID id;

    private String n;

    private String bankName;
}
