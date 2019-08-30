package com.tf.airhockey.objects;

import com.tf.airhockey.data.VertexArray;
import com.tf.airhockey.programs.ColorShaderProgram;
import com.tf.airhockey.util.Geometry;

import java.util.List;

/**
 * create by TIAN FENG on 2019/8/30
 */
public class Puck {
    // 冰球表现的事三维空间 需要有z
    private static final int POSITION_COMPONENT_COUNT = 3;// x y z
    public final float radius, height;
    private VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0, 0, 0), radius, height),
                numPointsAroundPuck);
        this.radius = radius;
        this.height = height;
        this.vertexArray = new VertexArray(generatedData.vertexData);
        this.drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram program) {
        vertexArray.setVertexAttribPointer(0, program.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
