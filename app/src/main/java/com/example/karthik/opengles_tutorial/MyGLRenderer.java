package com.example.karthik.opengles_tutorial;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Set background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        float[] scratch = new float[16];

        //Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //Create a rotation transformation for the triangle
//        long time = SystemClock.uptimeMillis()% 4000L;
//        float angle = 0.090f*((int)time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);



        //set the camera position(View position)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        //calculate the projection and view transform
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //combine the rotation matrix with the projection and camera view
        //mMVP factor must be first for correct result
        Matrix.multiplyMM(scratch,0, mMVPMatrix,0,mRotationMatrix,0);

        mTriangle.draw(scratch);
    }
}
