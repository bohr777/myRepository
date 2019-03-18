//package com.owinfo.service.config.activiti;
//
//import com.owinfo.activity.config.druid.DruidConfig;
//import org.activiti.spring.SpringAsyncExecutor;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
///**
// * Created by liyue on 2017/12/25.
// */
//@Configuration
//@AutoConfigureAfter(DruidConfig.class)
//public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {
//
//    @Bean
//    @Primary
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
//            DataSource dataSource,
//            PlatformTransactionManager transactionManager,
//            SpringAsyncExecutor springAsyncExecutor) throws IOException {
//        SpringProcessEngineConfiguration processEngineConfiguration = this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
//        processEngineConfiguration.setXmlEncoding("UTF-8");
//        processEngineConfiguration.setActivityFontName("宋体");
//        processEngineConfiguration.setLabelFontName("宋体");
//        processEngineConfiguration.setDatabaseType("mysql");
//        processEngineConfiguration.setDatabaseSchemaUpdate("true");
//        return processEngineConfiguration;
//    }
//}
