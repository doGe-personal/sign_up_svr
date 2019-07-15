package com.el.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
public abstract class ImageUtil {


    public static BufferedImage fromImageData(byte[] imageData) throws IOException {
        ByteArrayInputStream buf = new ByteArrayInputStream(imageData);
        Throwable var2 = null;

        BufferedImage var3;
        try {
            var3 = ImageIO.read(buf);
        } catch (Throwable var12) {
            var2 = var12;
            throw var12;
        } finally {
            if (buf != null) {
                if (var2 != null) {
                    try {
                        buf.close();
                    } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                    }
                } else {
                    buf.close();
                }
            }

        }

        return var3;
    }

    public static byte[] toImageData(BufferedImage image, String type) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        Throwable var3 = null;

        byte[] var4;
        try {
            ImageIO.write(image, type, buf);
            var4 = buf.toByteArray();
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (buf != null) {
                if (var3 != null) {
                    try {
                        buf.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    buf.close();
                }
            }

        }

        return var4;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver)null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver)null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }
}
