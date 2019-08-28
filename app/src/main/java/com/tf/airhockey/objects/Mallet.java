package com.tf.airhockey.objects;

import com.tf.airhockey.data.VertexArray;
import com.tf.airhockey.programs.ColorShaderProgram;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static com.tf.airhockey.util.Constants.BYTES_PRE_FLOAT;

/**
 * create by TIAN FENG on 2019/8/28
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;//2 x y 0 1  4 x y  z w
    private static final int COLOR_COMPONENT_COUNT = 3;// opengl 是本地系统直接运行在硬件上的  如果使用的是java的API那么就需要将java的内存拷贝到c里面
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PRE_FLOAT;

    // 锤  两个点  后面是颜色rgb
    private static final float[] VERTEX_DATA = {
            // x y  r g b
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };

    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.getTextureCoordinatesAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, 2);
    }


}
