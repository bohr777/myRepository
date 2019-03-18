package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.dao.STMOgusMapper;
import com.owinfo.dao.STMSystemInfoMapper;
import com.owinfo.dao.SystemsMapper;
import com.owinfo.entity.STMSystemInfo;
import com.owinfo.entity.Systems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: qiyong
 * 2018/8/21 15:30
 */
@RestController
@RequestMapping("/STMSystemInfo")
public class STMSystemInfoServiceImpl extends AbstractService<STMSystemInfo> {

    private static final Logger logger = LoggerFactory.getLogger(STMSystemInfoServiceImpl.class);


    @Autowired
    private SystemsMapper systemsMapper;
    @Autowired
    private STMSystemInfoMapper stmSystemInfoMapper;
    @Autowired
    private STMOgusMapper stmOgusMapper;


}
