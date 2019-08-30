package com.tf.airhockey.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.tf.airhockey.util.ShaderHelper;
import com.tf.airhockey.util.TextResourcesReader;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class ShaderProgram {

    protected static final String U_COLOR = "u_Color";

    // uniform
    protected static final String U_MATRIX = "u_Matrix";// 正交投影矩阵
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // attribute
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // shader program
    protected final int program;


    public ShaderProgram(Context context, int vertexShaderSourceId, int fragmentShaderSourceId) {
        String vertexShaderSource = TextResourcesReader.readTextFileFromResources(context, vertexShaderSourceId);
        String fragmentShaderSource = TextResourcesReader.readTextFileFromResources(context, fragmentShaderSourceId);
        program = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
    }


    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
