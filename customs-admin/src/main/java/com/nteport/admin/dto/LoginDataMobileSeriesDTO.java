package com.nteport.admin.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LoginDataMobileSeriesDTO {
    private String name;
    private ArrayList<Long> data = new ArrayList<>();
}
