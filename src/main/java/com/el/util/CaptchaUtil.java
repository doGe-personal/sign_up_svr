package com.el.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
public abstract class CaptchaUtil {
    private static final Logger log = LoggerFactory.getLogger(CaptchaUtil.class);
    private static final String S_CAPTCHA = CaptchaUtil.class.getName();
    private static final String CAPTCHA_IMAGE_FMT = "jpg";
    private static Producer producer;

    public static byte[] generate(HttpServletRequest request) throws IOException {
        String captchaText = producer.createText();
        log.trace("[CORE-SEC] captcha was created: {}", captchaText);
        byte[] captchaImageData = ImageUtil.toImageData(producer.createImage(captchaText), "jpg");
        request.getSession().setAttribute(S_CAPTCHA, captchaText);
        return captchaImageData;
    }

    public static String text2jpeg(String text) throws IOException {
        return Base64.getEncoder().encodeToString(ImageUtil.toImageData(producer.createImage(text), "jpg"));
    }

    public static String captchaOf(HttpSession session) {
        return session == null ? null : (String) session.getAttribute(S_CAPTCHA);
    }

    static {
        Properties props = new Properties();

        try {
            InputStream in = CaptchaUtil.class.getClassLoader().getResourceAsStream("captcha.properties");
            Throwable var2 = null;

            try {
                props.load(in);
            } catch (Throwable var12) {
                var2 = var12;
                throw var12;
            } finally {
                if (in != null) {
                    if (var2 != null) {
                        try {
                            in.close();
                        } catch (Throwable var11) {
                            var2.addSuppressed(var11);
                        }
                    } else {
                        in.close();
                    }
                }

            }
        } catch (IOException var14) {
            throw new OpsError("Load properties file failed: captcha.properties", var14);
        }

        producer = (new Config(props)).getProducerImpl();
    }
}
