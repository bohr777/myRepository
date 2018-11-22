package com.owinfo.sql.controller;

import com.owinfo.sql.bean.YmWmsWhStockDtl;
import com.owinfo.sql.service.YmWmsWhStockDtlService;
import com.owinfo.sql.util.ExportExcelUtil;
import com.owinfo.sql.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author: qiyong
 * 2018/11/12 16:28
 */
@RestController
public class YmWmsWhStockDtlController {

    @Autowired
    private YmWmsWhStockDtlService ymWmsWhStockDtlService;


    /**
     * SQL数据库导出EXECL
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/sql")
    @ResponseBody
    public Result sql(HttpServletResponse response) throws Exception {
        Result result = null;
        List<YmWmsWhStockDtl> ymWmsWhStockDtlList = ymWmsWhStockDtlService.selectAll();
        String fileName = "sql_data.xls";
        List<Map<String, Object>> sqlList = new ArrayList<>();
        try {
            //列名
            List<String> column = new ArrayList<>();

            column.add("库位条码");
            column.add("品名");
            column.add("批次号");
            column.add("数量");
            column.add("商品条码");
            column.add("生产日期");
            column.add("失效日志");
            column.add("托号");
            column.add("库区");
            column.add("外部系统料号（skuid）");
            //Excel正文数据
            for (YmWmsWhStockDtl ymWmsWhStockDtl : ymWmsWhStockDtlList) {
                Map<String, Object> map = new HashMap<>();

                map.put("库位条码", ymWmsWhStockDtl.getStockpositioncode());
                map.put("品名", ymWmsWhStockDtl.getgName());
                map.put("批次号", ymWmsWhStockDtl.getSn());
                map.put("数量", ymWmsWhStockDtl.getgQty());
                map.put("商品条码", ymWmsWhStockDtl.getPn());
                map.put("生产日期", timeToString(ymWmsWhStockDtl.getProductDate()));
                map.put("失效日志", ymWmsWhStockDtl.getInvalidDate());
                map.put("托号", ymWmsWhStockDtl.getExtend3());
                map.put("库区", ymWmsWhStockDtl.getExtend4());
                map.put("外部系统料号（skuid）", ymWmsWhStockDtl.getDnExternal());
                sqlList.add(map);
            }
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            // 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            String res = ExportExcelUtil.exportExcel3(fileName, column, sqlList, os, "明细", "商品明细表", 10);
            if (res.equals("success")) {
                Result.success("成功");
            }
        } catch (Exception ex) {
            Result.success("失败");
        }
        return result;
    }

    /**
     * 日期格式转换成字符串
     *
     * @param date
     * @return String
     */
    public static String timeToString(Date date) {
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format0.format(date);
    }

}
