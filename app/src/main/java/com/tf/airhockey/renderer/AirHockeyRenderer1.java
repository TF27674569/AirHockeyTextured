package com.tf.airhockey.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tf.airhockey.R;
import com.tf.airhockey.objects.Mallet;
import com.tf.airhockey.objects.Table;
import com.tf.airhockey.programs.ColorShaderProgram;
import com.tf.airhockey.programs.TextureShaderProgram;
import com.tf.airhockey.util.MatrixHelper;
import com.tf.airhockey.util.TextureHelper;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * create by TIAN FENG on 2019/8/27
 */
public class AirHockeyRenderer1 implements GLSurfaceView.Renderer {
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    public AirHockeyRenderer1(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 0f, 0f, 0f);

        table = new Table();
        mallet = new Mallet();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.opengl);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
//        final float aspectTadio = width > height ? width / (float) height : height / (float) width;
        // 防止横竖屏变换的时候 图像变形
        // 通过矩阵处理正交投影，拓展坐标系空间
//        if (width > height) {
//            // 横屏下 扩展宽度的取值 让其不是在[-1 1] 而是[ -aspectTadio, aspectTadio]
//            Matrix.orthoM(projectionMatrix, 0, -aspectTadio, aspectTadio, -1f, 1f, -1f, 1f);
//        } else {
//            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectTadio, aspectTadio, -1f, 1f);
//        }

        // 图像默认的z值为0
        // 视锥体是从45度视野 近处1 ，远处10 观看
        // 观看位置为 -1  -10
        MatrixHelper.perspectiveM(projectionMatrix, 45f, width / (float) height, 1f, 10f);

        // 将模型矩阵 初始化单位举证然后z轴移动-2个单位
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);// a 角度  x轴 y轴 z轴（此处是延x轴旋转-60）

        final float[] temp = new float[16];
        //矩阵相乘的函数
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        // 将结果copy到 projectionMatrix
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        // draw table
        textureProgram.useProgram();
        textureProgram.setUnifroms(projectionMatrix,texture);
        table.bindData(textureProgram);
        table.draw();

        // draw mallet
        colorProgram.useProgram();
        colorProgram.setUnifroms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();

    }
}
