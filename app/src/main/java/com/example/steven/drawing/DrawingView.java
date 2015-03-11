package com.example.steven.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by steven on 2/19/15.
 */
public class DrawingView extends View {

    //drawing path
    private CustomPath drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private Context context;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.context = context;
    }

    private void setupDrawing(){
        drawPath = new CustomPath();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(35);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                //Log.d("xTag", Float.toString(touchX));
                //Log.d("yTag", Float.toString(touchY));
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                //drawPaint.setColor(0xEE330000);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
//        CustomPath customPath = new CustomPath();
//        try
//        {
//            String FILENAME = "customPath_file";
//            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(fos);
//            out.writeObject(drawPath);
//            out.close();
//            fos.close();
//            System.out.printf("Serialized data is saved in customPath_file");
//        }catch(IOException i)
//        {
//            i.printStackTrace();
//        }
//
//        //reading
//        try {
//            FileInputStream fis = context.openFileInput("customPath_file");
//            ObjectInputStream is = new ObjectInputStream(fis);
//            try {
//                customPath = (CustomPath)is.readObject();
//            }catch(ClassNotFoundException c){
//
//            }
//                is.close();
//                fis.close();
//
//        }catch(IOException i)
//        {
//            i.printStackTrace();
//        }

        invalidate();
        return true;

    }

}
