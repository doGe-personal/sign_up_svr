package com.el.core.security.entity;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.config.Ini;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author danfeng
 * @since 2018/5/9
 */
@Slf4j
public class SecurityFilterChainsBuilder {
    private static final String INI_PATH = "classpath:shiro.ini";
    private static final String INI_PRIOR = "prior";
    private static final String INI_BASIC = "basic";

    public Map<String, String> build() {
        val ini = Ini.fromResourcePath(INI_PATH);
        val chains = new LinkedHashMap<String, String>(ini.getSection(INI_PRIOR));
        chains.putAll(ini.getSection(INI_BASIC));
        log.trace("[CORE-SEC] Filter chains: {}", chains);
        return chains;
    }
}
