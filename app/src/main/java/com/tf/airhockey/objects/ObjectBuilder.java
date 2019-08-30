package com.tf.airhockey.objects;

import android.opengl.GLES20;

import com.tf.airhockey.util.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * create by TIAN FENG on 2019/8/29
 */
public class ObjectBuilder {
    // 一个顶点需要多少个浮点数
    private static final int FLOATS_PRE_VERTEX = 3;

    private final float[] vertexData;

    // 画圆形三角形扇 需要的绘画指令
    private final List<DrawCommand> drawList = new ArrayList<>();

    // 记录下一个顶点的位置
    private int offset = 0;


    // 冰球 为一个圆柱
    static GeneratedData createPuck(Geometry.Cylinder puck, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);
        ObjectBuilder builder = new ObjectBuilder(size);
        Geometry.Circle puckTop = new Geometry.Circle(puck.center, puck.radius);
        // 圆柱顶部的圆
        builder.appendCircle(puckTop, numPoints);
        // 圆柱的侧面
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }

    // 木槌
    static GeneratedData createMallet(Geometry.Point center, float radius, float height, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2;
        ObjectBuilder builder = new ObjectBuilder(size);

        // 木槌底部的圆柱  总共站0.25 个高
        float baseHeight = height * 0.25f;
        //木槌底部的圆柱 顶部的圆
        Geometry.Circle baseCircle = new Geometry.Circle(center.translateY(-baseHeight), radius);
        //木槌底部的圆柱 侧面
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(baseCircle.center.translateY(-baseHeight / 2f), radius, baseHeight);
        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);


        // 木槌首部握把
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3; // 大小为整个的1/3


        //木槌握把的圆柱 顶部的圆
        Geometry.Circle handleCircle = new Geometry.Circle(center.translateY(height / 2f), handleRadius);
        //木槌握把的圆柱 侧面
        Geometry.Cylinder handleCylinder = new Geometry.Cylinder(baseCircle.center.translateY(handleHeight / 2f), handleRadius, handleHeight);
        builder.appendCircle(handleCircle, numPoints);
        builder.appendOpenCylinder(handleCylinder, numPoints);


        return builder.build();
    }

    private GeneratedData build() {
        return new GeneratedData(vertexData, drawList);
    }

    // 添加圆柱体的侧面  矩阵数组按函数格式三个一组排好
    private void appendOpenCylinder(Geometry.Cylinder cylinder, int numPoints) {
        final int startVertex = offset / FLOATS_PRE_VERTEX;
        final int numVertex = sizeOfOpenCylinderInVertices(numPoints);
        // 中心点往上移动一个 圆柱高的一半
        final float yStart = cylinder.center.y - cylinder.height / 2;
        // 中心点往下移动一个 圆柱高的一半
        final float yEnd = cylinder.center.y + cylinder.height / 2;

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = (float) (i / (float) numPoints * Math.PI * 2);
            float xPosition = (float) (cylinder.center.x + cylinder.radius * Math.cos(angleInRadians));
            float zPosition = (float) (cylinder.center.z + cylinder.radius * Math.sin(angleInRadians));

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;


            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertex);
            }
        });

    }

    // 添加圆柱体顶部的圆
    private void appendCircle(Geometry.Circle circle, int numPoints) {
        final int startVertex = offset / FLOATS_PRE_VERTEX;
        final int numVertex = sizeOfCircleInVertices(numPoints);

        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = (float) ((i / (float) numPoints) * Math.PI * 2f);
            vertexData[offset++] = (float) (circle.center.x + circle.radius * Math.cos(angleInRadians));
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = (float) (circle.center.z + circle.radius * Math.sin(angleInRadians));
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertex);
            }
        });
    }

    private ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOATS_PRE_VERTEX];
    }


    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    private static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }


    static class GeneratedData {
        final float[] vertexData;
        final List<DrawCommand> drawList;

        public GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    static interface DrawCommand {
        void draw();
    }


}
