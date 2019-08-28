package com.tf.airhockey.programs;

import android.content.Context;

import com.tf.airhockey.R;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class ColorShaderProgram extends ShaderProgram {

    // uniform location
    private final int uMatrixLocation;

    // attribute location
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }

    /**
     * 传递 矩阵和纹理 给 对应 uniform
     */
    public void setUnifroms(float[] matrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }


    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aColorLocation;
    }
}
