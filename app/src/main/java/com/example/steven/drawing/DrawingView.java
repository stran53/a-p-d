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

    // for storing
    private CustomPath customPath;

    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFFDE0000;
    //canvas
    private Canvas drawCanvas;
    private Canvas backupCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private Bitmap backupBitmap;

    private Context context;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.context = context;
    }

    private void setupDrawing(){
        drawPath = new CustomPath();
        customPath = new CustomPath();

        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
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

//        backupBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
//        backupCanvas = new Canvas(backupBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

        //backupCanvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        //backupCanvas.drawPath(drawPath, canvasPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //backupCanvas.drawBitmap(canvasBitmap, 0, 0, null);
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
//                drawPaint.setColor(0x4400FF00);
//                drawCanvas.drawPath(drawPath, drawPaint);

//                try
//                {
//                    String FILENAME = "customPath_file";
//                    FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//                    ObjectOutputStream out = new ObjectOutputStream(fos);
//                    out.writeObject(drawPath);
//                    out.close();
//                    fos.close();
//                    System.out.printf("Serialized data is saved in customPath_file");
//                }catch(IOException i)
//                {
//                    i.printStackTrace();
//                }
                drawPath.reset();
                drawPaint.setColor(0x4400FF00);

                break;
            default:
                return false;
        }
        //reading
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
            //drawPath contains OLD path which should have been reset
            //shouldn't do anything

        invalidate();
        return true;

    }

}
