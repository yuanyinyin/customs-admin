package com.nteport.admin.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * 移动端 登录情况统计dto
 */
@Data
public class LoginDataMobileDTO {
    private ArrayList<String> categories;
    private ArrayList<LoginDataMobileSeriesDTO> series = new ArrayList<>();

}
