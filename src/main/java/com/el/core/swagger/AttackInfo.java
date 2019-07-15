package com.el.core.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

/**
 * @author Danfeng
 * @since 2018/9/16
 */
@Data
@Component
@ConfigurationProperties("swagger")
public class AttackInfo {
    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String contactName;
    private String license;
    private String licenseUrl;

    ApiInfo build() {
        return new ApiInfoBuilder()
            .title(this.title)
            .description(this.description)
            .termsOfServiceUrl(this.termsOfServiceUrl)
            .license(this.license)
            .licenseUrl(this.licenseUrl)
            .version(this.version)
            .build();
    }
}
