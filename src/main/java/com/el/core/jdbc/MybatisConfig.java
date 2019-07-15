package com.el.core.jdbc;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.util.List;


/**
 * EnableTransactionManagement 实现事务管理
 *
 * @author danfeng
 * @since 2018/1/24.
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({JdbcProperties.class})
public class MybatisConfig implements TransactionManagementConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private JdbcProperties jdbcProperties;

    @Bean
    public DataSource dataSource() {
        log.info("[CORE-JDBC] dataSource: {}", this.jdbcProperties);
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(this.jdbcProperties);
        return dataSource;
    }

    /**
     * 配置事务注解支持
     *
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Autowired(required = false) List<TypeHandler> typeHandlers) throws Exception {
        log.info("[CORE-JDBC] sqlSessionFactory");
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        if (typeHandlers != null && !typeHandlers.isEmpty()) {
            sessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler[typeHandlers.size()]));
        }
        sessionFactoryBean.setDataSource(this.dataSource());
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        log.info("[CORE-JDBC] sqlSessionTemplate");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
