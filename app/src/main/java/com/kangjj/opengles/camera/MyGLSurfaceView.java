package com.kangjj.opengles.camera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context) {
        this(context,null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //1设置EGL版本，必须设置
        setEGLContextClientVersion(2);
        //2 设置渲染器
        setRenderer(new MyGLRenderer(this));
        //3 设置渲染器模式：按需渲染
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
