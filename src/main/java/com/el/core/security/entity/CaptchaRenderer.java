package com.el.core.security.entity;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
public class CaptchaRenderer extends Configurable implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {
        return bufferedImage;
    }
}
