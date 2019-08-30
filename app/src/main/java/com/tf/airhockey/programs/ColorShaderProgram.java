package com.tf.airhockey.programs;

import android.content.Context;

import com.tf.airhockey.R;

import static android.opengl.GLES20.*;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class ColorShaderProgram extends ShaderProgram {

    // uniform location
    private final int uMatrixLocation;

    // attribute location
    private final int aPositionLocation;
    private final int uColorLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uColorLocation = glGetUniformLocation(program, U_COLOR);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }

    /**
     * 传递 矩阵和纹理 给 对应 uniform
     */
    public void setUniforms(float[] matrix, float r, float g, float b) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform4f(uColorLocation, r, g, b, 1f);
    }


    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aColorLocation;
    }
}
