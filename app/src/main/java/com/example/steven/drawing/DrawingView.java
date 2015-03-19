package com.example.steven.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by steven on 2/19/15.
 */
public class DrawingView extends View {

    //drawing path
    private CustomPath drawPath;

    // for storing
    private CustomPath customPath;

    //drawing and canvas paint
    private Paint drawPaint, canvasPaint, blurPaint;
    //initial color
    private int paintColor = 0xDD0044DE;
    //canvas
    private Canvas drawCanvas;
    private Canvas backupCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private Bitmap backupBitmap;

    private Context context;
    //for animation??
    int fps = 60;
    long duration = 10000;
    Matrix matrix = new Matrix();
    long startTime;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.context = context;
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }

    private void setupDrawing(){
        drawPath = new CustomPath();
        customPath = new CustomPath();

        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(17);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        blurPaint = new Paint();
        blurPaint.set(drawPaint);
        blurPaint.setColor(0xDD00FFFF);
        blurPaint.setStrokeWidth(35);
        blurPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.OUTER));

        canvasPaint = new Paint(Paint.DITHER_FLAG);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        backupBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        drawCanvas = new Canvas(canvasBitmap);
//        backupCanvas = new Canvas(backupBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long elapsedTime = System.currentTimeMillis() - startTime;
//    Log.d("first matrix", matrix.toString());
////        matrix.postRotate(5);        // rotate 30° every second
////        matrix.postTranslate(100 * elapsedTime/1000, 0); // move 100 pixels to the right
//        matrix.postScale(1.001f,.999f);
//        canvas.concat(matrix);        // call this before drawing on the canvas!!
//        Log.d("second matrix", matrix.toString());
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
//        canvas.drawBitmap(backupBitmap, 0, 0, canvasPaint);
//
//        canvas.save(); //Save the position of the canvas.
//        canvas.rotate(-5);
//
//
////draw view
        canvas.drawPath(drawPath, drawPaint);
//        canvas.restore();
//        canvas.drawPath(drawPath, drawPaint);
//
//        if(elapsedTime < duration)
//            this.postInvalidateDelayed( 1000 / fps);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        byte[] buf = null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
//                drawPaint.setColor(0x4400FF00);

                //redraws WITH blur
                drawCanvas.drawPath(drawPath, drawPaint);

//                drawPaint.setColor(0x4400FF00);
//                drawPath.reset();
                try
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutput out = new ObjectOutputStream(baos);
                    out.writeObject(drawPath);
                    out.close();
                    buf = baos.toByteArray();
                }catch(IOException i) {
                    i.printStackTrace();
                }
                break;
            default:
                return false;
        }
//
//        if (buf != null) {
//
//            Path recover_path = null;
//            try {
//                ByteArrayInputStream bis = new ByteArrayInputStream(buf);
//                ObjectInputStream in = new ObjectInputStream(bis);
//                recover_path = (Path) in.readObject();
//                bis.close();
//                in.close();
//            } catch (IOException | ClassNotFoundException c) {
//                c.printStackTrace();
//            }
//
//         if (recover_path != null) {
//             drawPaint.setColor(0xFF0000FF);
//             System.out.println("Reached the recover path drawing");
//             drawCanvas.drawPath(recover_path, blurPaint);
//         }
//        }

        invalidate();

        return true;

    }

}
