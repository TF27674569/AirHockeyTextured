package com.tf.airhockey;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tf.airhockey.renderer.AirHockeyRenderer;
import com.tf.airhockey.renderer.AirHockeyRenderer1;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView = findViewById(R.id.glSurfaceView);

        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.setRenderer(new AirHockeyRenderer1(this));
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        // RENDERMODE_WHEN_DIRTY 和 RENDERMODE_CONTINUOUSLY，前者是懒惰渲染，需要手动调用 glSurfaceView.requestRender() 才会进行更新，而后者则是不停渲染。
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

}
