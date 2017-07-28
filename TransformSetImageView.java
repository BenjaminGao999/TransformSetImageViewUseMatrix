package com.gaos.imageviewmatrix.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import java.util.Arrays;


/**
 * Author:　Created by benjamin
 * DATE :  2017/7/3 17:43
 * versionCode:　v2.2
 */

public class TransformSetImageView extends ImageView {
    public GestureDetector gestureDetector;
    public ScaleGestureDetector scaleGestureDetector;
    private static final String TAG = "TransformSetImageView";
    private Matrix mImageMatrix;
    private boolean isInit;
    private float originScaleX;
    //    private float originTransX;
////    private float originTransY;
//    /**
//     * 缩放模式
//     */
//    private int MODE_ZOOM = 0;
//    /**
//     * 拖拽模式
//     */
//    private int MODE_DRAG = 1;
//    /**
//     * 普通模式
//     */
//    private int MODE_NON = 2;
//    /**
//     * 当前模式
//     */
//    private int currentMode;
    private float[] values;
    private int position;

    public TransformSetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        setScaleType(ScaleType.FIT_CENTER);
        isInit = true;
//        currentMode = MODE_NON;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * [1.2189616, 0.0, 0.0, 0.0, 1.2189616, 212.59369, 0.0, 0.0, 1.0]
         */
        mImageMatrix = getImageMatrix();
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        Log.e(TAG, "TransformSetImageView: image view  =" + Arrays.toString(values));
        Log.e(TAG, "onDraw: scaleX = " + values[Matrix.MSCALE_X]);
        Log.e(TAG, "onDraw: scaleY = " + values[Matrix.MSCALE_Y]);
        Log.e(TAG, "onDraw: translate Y = " + values[Matrix.MTRANS_Y]);
        Log.e(TAG, "onDraw: translate X = " + values[Matrix.MTRANS_X]);
        if (isInit) {
            isInit = false;
            originScaleX = values[Matrix.MSCALE_X];
//            originTransX = values[Matrix.MTRANS_X];
//            originTransY = values[Matrix.MTRANS_Y];
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        scaleGestureDetector.onTouchEvent(event);


        int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:


                values = new float[9];

                mImageMatrix.getValues(values);

                if (values[Matrix.MSCALE_X] > originScaleX) {

                    getParent().requestDisallowInterceptTouchEvent(true);


                    float scaleX = values[Matrix.MSCALE_X];
                    float currentFrontImgWidth = getDrawable().getIntrinsicWidth() * scaleX;
//                    Log.e(TAG, "onTouchEvent: currentFrontImgWidth = " + currentFrontImgWidth);
                    int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
                    float leftMax = widthPixels - currentFrontImgWidth;
                    float leftTopX = values[Matrix.MTRANS_X];
                    if (leftMax > leftTopX) {


                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                } else {


                }

                break;


            case MotionEvent.ACTION_MOVE:


                values = new float[9];

                mImageMatrix.getValues(values);

                if (values[Matrix.MSCALE_X] > originScaleX) {

                    getParent().requestDisallowInterceptTouchEvent(true);


                    float scaleX = values[Matrix.MSCALE_X];
                    float currentFrontImgWidth = getDrawable().getIntrinsicWidth() * scaleX;
//                    Log.e(TAG, "onTouchEvent: currentFrontImgWidth = " + currentFrontImgWidth);
                    int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
                    float leftMax = widthPixels - currentFrontImgWidth;
                    float leftTopX = values[Matrix.MTRANS_X];
                    if (leftMax > leftTopX) {


                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                } else {


                }

                break;


            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:


                float[] values = new float[9];
                mImageMatrix.getValues(values);
                if (values[Matrix.MSCALE_X] >= originScaleX) {


                } else {


                    RectF src = new RectF(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
                    RectF dst = new RectF(0, 0, getWidth(), getHeight());
                    mImageMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

                    setImageMatrix(mImageMatrix);

                    invalidate();
                }
                break;
            default:
                break;
        }

        return true;
    }


    /**
     * 重置
     */
    public void reset() {


        RectF src = new RectF(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        RectF dst = new RectF(0, 0, getWidth(), getHeight());
        mImageMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        setImageMatrix(mImageMatrix);

        invalidate();
    }

    /**
     * 当前view所处位置。
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

//        private float px;
//        private float py;


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

//            Log.e(TAG, "onScroll: distanceX = " + distanceX + " ; distanceY = " + distanceY);
            /**
             * dis = start - end
             */


            if (values[Matrix.MSCALE_X] == originScaleX) {

                return true;

            } else {


                float[] values = new float[9];
                mImageMatrix.getValues(values);
//            Log.e(TAG, "onScroll: tanslateX = " + values[Matrix.MTRANS_X]);
//            Log.e(TAG, "onScroll: tanslateY = " + values[Matrix.MTRANS_Y]);
                mImageMatrix.postTranslate(-distanceX, -distanceY);
                setImageMatrix(mImageMatrix);
                invalidate();
            }


            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            float[] values = new float[9];
            mImageMatrix.getValues(values);

            if (values[Matrix.MSCALE_X] < 2.0f) {

//                px = e.getX();
//                py = e.getY();
                mImageMatrix.postScale(2.0f, 2.0f, e.getX(), e.getY());

                setImageMatrix(mImageMatrix);


            } else {


                float factor = originScaleX / values[Matrix.MSCALE_X];
                Log.e(TAG, "onDoubleTap: factor = " + factor);
//                mImageMatrix.postScale(factor, factor, px, py);

                RectF src = new RectF(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
                RectF dst = new RectF(0, 0, getWidth(), getHeight());
                mImageMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
            }

            setImageMatrix(mImageMatrix);


            invalidate();

            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float factor = 1.0f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            factor *= detector.getScaleFactor();

//            Log.e(TAG, "onScale: factor = " + factor);

            mImageMatrix.postScale(detector.getScaleFactor(), detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY());


            setImageMatrix(mImageMatrix);

            invalidate();

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            factor = 1.0f;
        }
    }

}
