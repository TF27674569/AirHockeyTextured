package com.tf.airhockey.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class TextureHelper {
    private static final String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resId) {
        final int[] textureObjId = new int[1];

        // open gl 创建纹理
        glGenTextures(1, textureObjId, 0);

        if (textureObjId[0] == 0) {
            Log.e(TAG, "创建OpenGL Texture 失败！");
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);

        if (bitmap == null) {
            glDeleteTextures(1, textureObjId, 0);
            throw new IllegalArgumentException("resId to bitmap error !");
        }
        glBindTexture(GL_TEXTURE_2D, textureObjId[0]);

        // GL_TEXTURE_MIN_FILTER 指缩小情况  GL_LINEAR_MIPMAP_LINEAR 三线性过滤
        // GL_TEXTURE_MAG_FILTER 放大情况 GL_LINEAR 双线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // 将bitmap绑定到纹理对象
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        // 释放bitmap资源
        bitmap.recycle();

        glGenerateMipmap(GL_TEXTURE_2D);

        // 解除绑定
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureObjId[0];
    }
}
