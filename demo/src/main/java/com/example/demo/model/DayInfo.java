package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DayInfo {

    private String day;
    @NotBlank
    private double count;

    public DayInfo(String day, @NotBlank int count) {
        this.day = day;
        this.count = count;
    }
}
