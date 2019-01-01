package com.example.karthik.opengles_tutorial;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context) {

        super(context);

        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {

            case MotionEvent.ACTION_MOVE:

                float dx = -(x - mPreviousX);
                float dy = y - mPreviousY;

                //reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }
                //reverse direction of rotation to the left of mid-line
                if(x > getWidth() / 2){
                    dy = dy * -1;
                }

                mRenderer.setAngle(mRenderer.getAngle() + ((dx+dy)*TOUCH_SCALE_FACTOR));
                requestRender();

        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
