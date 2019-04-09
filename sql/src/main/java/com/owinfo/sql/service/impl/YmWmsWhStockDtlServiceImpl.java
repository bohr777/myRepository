package com.owinfo.sql.service.impl;

import com.owinfo.sql.dao.YmWmsWhStockDtlMapper;
import com.owinfo.sql.service.YmWmsWhStockDtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: qiyong
 * 2018/11/12 16:20
 */
@Service
public class YmWmsWhStockDtlServiceImpl implements YmWmsWhStockDtlService {

    @Autowired
    private YmWmsWhStockDtlMapper ymWmsWhStockDtlMapper;

    public List selectAll() {
        return ymWmsWhStockDtlMapper.selectAll();
    }

    @Override
    public List selectAllByPage(Integer page) {
        return ymWmsWhStockDtlMapper.selectAllByPage(page);
    }

}
