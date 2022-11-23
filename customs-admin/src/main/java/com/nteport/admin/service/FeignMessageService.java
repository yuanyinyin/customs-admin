package com.nteport.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shaoyf
 * @since 2021-07-29
 */
@Service
@FeignClient(name = "message")
public interface FeignMessageService {

    @RequestMapping("/message/send")
    Map sendMessage(@RequestParam("json") String json);

}