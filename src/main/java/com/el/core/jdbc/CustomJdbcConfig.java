package com.el.core.jdbc;

import com.el.core.jdbc.coutomTypeHandler.BooleanTypeHandler;
import com.el.core.jdbc.coutomTypeHandler.PurifyStringTypeHandler;
import com.el.core.jdbc.dialect.MysqlDialect;
import com.el.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author danfeng
 * @since 2018/1/25.
 */
@Slf4j
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
@MapperScan({"com.el.app.*.mapper","com.el.core.security.mapper"})
public class CustomJdbcConfig {

    static {
        SqlUtil.SQL_DIALECT = new MysqlDialect();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        // 设置自动扫描包,可以使用分号或逗号作为分隔符来设置多个包。
        mapperScannerConfigurer.setBasePackage("com.el.app.*.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean
    public PurifyStringTypeHandler purifyStringTypeHandler() {
        log.info("[CUST-JDBC] purifyStringTypeHandler");
        return new PurifyStringTypeHandler();
    }

    @Bean
    public BooleanTypeHandler booleanTypeHandler() {
        log.info("[CUST-JDBC] booleanTypeHandler");
        return new BooleanTypeHandler();
    }

}
