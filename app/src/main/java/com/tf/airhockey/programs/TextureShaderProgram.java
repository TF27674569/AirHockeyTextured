package com.tf.airhockey.programs;

import android.content.Context;

import com.tf.airhockey.R;

import static android.opengl.GLES20.*;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class TextureShaderProgram extends ShaderProgram {

    // uniform location
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    // attribute location
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    /**
     * 传递 矩阵和纹理 给 对应 uniform
     */
    public void setUniforms(float[] matrix, int textureId) {
        //  传递矩阵 给 对应的uniform texture unit 使用纹理单元  应为GPU 绘制的纹理数量有限
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        // 把活动的纹理单元设置为0
        glActiveTexture(GL_TEXTURE0);

        // 把纹理绑定到这个单元
        glBindTexture(GL_TEXTURE_2D,textureId);

        //把绑定的纹理单元 传递给片段着色器  u_TextureUnit
        glUniform1i(uTextureUnitLocation,0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }
}
