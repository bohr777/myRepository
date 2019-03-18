package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liyue on 2017/10/17.
 h)*/
@FeignClient(value="owinfo-common-service")
public interface MaterialService {
    @RequestMapping(value = "/material/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
    @RequestMapping(value = "/material/getById1",method = RequestMethod.POST)
    JSONObject getById(JSONObject jsonObject);
    @RequestMapping(value = "/material/deleteByIds",method = RequestMethod.POST)
    boolean delete(JSONObject jsonObject);
    @RequestMapping(value = "/material/isLocal",method = RequestMethod.POST)
    boolean isLocal(JSONObject jsonObject);
/*    class MultipartSupportConfig{
        @Bean
        public Encoder feignFormEncoder(){
            return new SpringFormEncoder();
        }
    }*/
}
