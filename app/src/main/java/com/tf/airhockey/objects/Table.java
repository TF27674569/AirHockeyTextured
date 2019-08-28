package com.tf.airhockey.objects;


import com.tf.airhockey.data.VertexArray;
import com.tf.airhockey.programs.TextureShaderProgram;

import static android.opengl.GLES20.*;
import static com.tf.airhockey.util.Constants.*;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;//2 x y
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;//  S T
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PRE_FLOAT;

    // 桌子 循环画三角形 draw 4 个点与 中心点（0，0）  后面是纹理
    private static final float[] VERTEX_DATA = {
            // x,y ,S,T
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f,
    };

    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }


}
