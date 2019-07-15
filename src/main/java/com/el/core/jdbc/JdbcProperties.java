package com.el.core.jdbc;

import com.el.util.SqlUtil;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author danfeng
 * @since 2018/1/24.
 */
@ConfigurationProperties("jdbc")
public class JdbcProperties extends PoolProperties {
    private static final int MAX_WAIT = 1000;
    private static final int VALIDATION_INTERVAL = 30000;
    private static final int TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000;

    /**
     * maxActive 池中 工作连接的最大个数，此值为非正数是表述不限制
     * MaxIdle 池中允许的最大连接数，值为非正数时表示不限制
     * MinIdle 池中最小空闲连接数，当连接数少于此值时，池会创建连接来补充到该值的数量
     * InitialSize 以毫秒表示的当连接池中没有可用连接时等待可用连接返回的时间，超时则抛出异常，值为-1时无限期等待。
     *
     * @param maxActive
     */
    public void setMaxActive(int maxActive) {
        super.setMaxActive(maxActive);
        this.setInitialSize(maxActive / 10);
        this.setMinIdle(this.getInitialSize());
        this.setMaxIdle(maxActive / 2);
    }

    public JdbcProperties() {
        // 连接池是否暴露一个可被注册的MBean,tomcat 本身注册了MBean服务器的DataSource，所以设置成false
        this.setJmxEnabled(false);
        // 数据库事务自动提交 设置成false
        this.setDefaultAutoCommit(Boolean.valueOf(false));
        // 以毫秒表示的当连接池中没有可用连接时等待可用连接返回的时间，超时则抛出异常，值为-1时无限期等待。
        this.setMaxWait(10000);
        // 初始化校验 查询之前校验此连接是否可用，否则抛出SQLException
        this.setValidationQuery(SqlUtil.validationQuery());
        // 记录来自校验的错误
        this.setLogValidationErrors(true);
        // 以毫秒为单位验证时间间隔。
        this.setValidationInterval(30000L);
        // 通常这总是设置为false，除非想使用validationQuery作为初始化查询。
        this.setTestOnConnect(true);
        // 指定连接被调用时是否经过校验。如果校验未通过，则该连接被连接池断掉，并由连接池尝试调用另一个连接。(validationQuery 非空)
        this.setTestOnBorrow(true);
        // 会使用测试线程，测试池中连接是否能够正常使用。
        this.setTestWhileIdle(true);
        // 以毫秒表示的空闲对象驱逐进程由运行状态进入休眠状态的数值。值为非正数时表示不运行任何空闲对象驱逐进程。
        this.setTimeBetweenEvictionRunsMillis('\uea60');
        // 以秒表示的清除无效连接的时限 默认为60
        this.setRemoveAbandonedTimeout(40);
        // 是否清除已经超过“removeAbandonedTimout”设置的无效连接
        this.setRemoveAbandoned(true);
        // 当清除无效连接时是否在日志中记录清除信息的标志。
        this.setLogAbandoned(true);
        // 设置 tomcat jdbc 连接池的拦截器。
        this.setJdbcInterceptors("ConnectionState;StatementFinalizer");
    }

}
