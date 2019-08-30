package com.tf.airhockey.objects;

import com.tf.airhockey.data.VertexArray;
import com.tf.airhockey.programs.ColorShaderProgram;
import com.tf.airhockey.util.Geometry;

import java.util.List;


/**
 * create by TIAN FENG on 2019/8/28
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createMallet(
                new Geometry.Point(0, 0, 0),
                radius, height,
                numPointsAroundMallet);
        this.radius = radius;
        this.height = height;
        this.vertexArray = new VertexArray(generatedData.vertexData);
        this.drawList = generatedData.drawList;

    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }


}
