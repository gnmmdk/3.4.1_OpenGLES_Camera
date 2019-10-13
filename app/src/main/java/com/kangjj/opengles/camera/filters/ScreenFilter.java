package com.kangjj.opengles.camera.filters;

import android.content.Context;
import android.opengl.GLES20;

import com.kangjj.opengles.camera.R;
import com.kangjj.opengles.camera.utils.TextResourceReader;

public class ScreenFilter {
    private final int mProgrameId;
    private int mWidth;
    private int mHeight;

    public ScreenFilter(Context context) {
        //顶点着色器代码
        String vertextSource = TextResourceReader.readTextFileFromResource(context, R.raw.camera_vertex);
        //片元着色器代码
        String framentSource = TextResourceReader.readTextFileFromResource(context,R.raw.camera_fragment);

        /**
         * 1.配置顶点着色器
         */
        // 1.1创建着色器，返回着色器id
        int vShaderId = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        //1.2绑定代码到着色器上面（加载着色器代码）
        GLES20.glShaderSource(vShaderId,vertextSource);
        //1.3 编译着色器代码
        GLES20.glCompileShader(vShaderId);
        //1.4 主动获取着色器的编译状态
        int[] status = new int[1];
        GLES20.glGetShaderiv(vShaderId,GLES20.GL_COMPILE_STATUS,status,0);//offset表示从数组的哪个位置开始
        if(status[0] != GLES20.GL_TRUE){
            throw new IllegalStateException("顶点着色器配置失败");
        }

        /**
         * 2 配置片元着色器
         */
        // 2.1 创建着色器，返回着色器id
        int fShaderId = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        // 2.2 绑定代码到着色器上（加载着色器代码）
        GLES20.glShaderSource(fShaderId,framentSource);
        // 2.3 编译着色器代码
        GLES20.glCompileShader(fShaderId);
        GLES20.glGetShaderiv(fShaderId,GLES20.GL_COMPILE_STATUS,status,0);
        if(status[0] != GLES20.GL_TRUE){
            throw new IllegalStateException("片元着色器配置失败");
        }

        /**
         * 3 配置着色器程序
         */
        //3.1 创建一个新的OpenGL程序
        mProgrameId = GLES20.glCreateProgram();
        //3.2将着色器附加到程序
        GLES20.glAttachShader(mProgrameId,vShaderId);
        GLES20.glAttachShader(mProgrameId,fShaderId);
        //3.3 链接程序
        GLES20.glLinkProgram(mProgrameId);
        GLES20.glGetShaderiv(fShaderId,GLES20.GL_LINK_STATUS,status,0);

        if(status[0] != GLES20.GL_TRUE){
            throw new IllegalStateException("着色器程序配置失败");
        }
        /**
         * 4.释放、删除着色器 TODO
         */
    }

    public void onReady(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void onDrawFrame(int textureID, float[] mtx) {
    }
}
