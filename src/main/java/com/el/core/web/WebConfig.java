package com.el.core.web;

import com.el.common.SecurityProperties;
import com.el.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author danfeng
 * @since 2018/5/8
 * 配置跨域支持(CORS_跨域资源共享)
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * DelegatingFilterProxy: Proxy for a standard Servlet Filter, delegating to a Spring-managed bean that
     * implements the Filter interface.
     * DelegatingFilterProxy 表示这是一个代理filter，它会将实际的工作，交给spring中 id="shiroFilterFactoryBean" 的bean来处理
     * @return
     */
    @ConditionalOnProperty("security.cors")
    @Bean
    public FilterRegistrationBean corsfilterRegistration(SecurityProperties securityProperties) {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean(new CorsFilter(this.corsConfigSource(securityProperties.allowedOrigin())), new ServletRegistrationBean[0]);
        filterRegistration.setOrder(0);
        return filterRegistration;
    }

    private UrlBasedCorsConfigurationSource corsConfigSource(String corsOrigins) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 表示允许...域名发起跨域请求。
        config.addAllowedOrigin(corsOrigins);
        // 表示允许跨域请求包含的头部信息。
        config.addAllowedHeader("*");
        //  表示允许跨域请求的方法。
        config.addAllowedMethod("*");
        config.addExposedHeader("el-result-code");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("[CORE-WEB] static resource mappings: {} -> {}", "/**", "classpath:/static/");
        registry.addResourceHandler(new String[]{"/**"}).addResourceLocations(new String[]{"classpath:/static/"});
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("[CORE-WEB] treat error view (/error) as E404");
        registry.addViewController("/error").setStatusCode(HttpStatus.NOT_FOUND);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("[CORE-WEB] JSON converter is registered");
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(JsonUtil.JSON_MAPPER);
        converters.add(jsonConverter);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        log.info("[CORE-WEB] enable parameters validation by annotation `@Validated` for all bean methods");
        return new MethodValidationPostProcessor();
    }

}
