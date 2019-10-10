package com.kangjj.opengles.camera;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.kangjj.opengles.camera.utils.CameraHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 核心类 渲染器
 */
class MyGLRenderer implements GLSurfaceView.Renderer {
    private final MyGLSurfaceView mGLSurfaceView;
    private final int mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT ;
    private CameraHelper mCameraHelper;
    private int[] mTextureID;
    private SurfaceTexture mSurfaceTexture;

    public MyGLRenderer(MyGLSurfaceView mGLSurfaceView) {
        this.mGLSurfaceView = mGLSurfaceView;
    }

    /**
     * Surface创建时回调
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraHelper = new CameraHelper((Activity) mGLSurfaceView.getContext(),mCameraID,480,800);
        //准备画布， 全局 onDrawFrame会用到
        mTextureID = new int[1];
        //通过opengl创建一个纹理的id
        GLES20.glGenTextures(mTextureID.length,mTextureID,0);
        mSurfaceTexture = new SurfaceTexture(mTextureID[0]);
        mSurfaceTexture.setOnFrameAvailableListener(mOnFrameAvailableListener);
        //TODO filter  new ScreenFilter
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mCameraHelper.startPreview(mSurfaceTexture);
        //TODO filter onReady
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //设置清理屏幕的颜色 看效果？
        GLES20.glClearColor(255,0,0,0);
        //GL_COLOR_BUFFER_BIT 颜色缓冲区
        //GL_DEPTH_WRITEMASK 深度缓冲区
        //GL_STENCIL_BUFFER_BIT 模型缓冲区
        GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);
        //输出摄像头的数据
        //更新纹理
        mSurfaceTexture.updateTexImage();
        float[] mtx = new float[16];
        mSurfaceTexture.getTransformMatrix(mtx);
        //TODO filter onDrawFrame

    }

    /**
     * 有可用数据时回调
     */
    private SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener(){
        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            mGLSurfaceView.requestRender();
        }
    };
}
