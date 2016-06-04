package com.example.fire.wuziqi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.fire.wuziqi.Util.checkFiveInLine;
import static java.lang.Math.min;

/**
 * Created by Lenovo on 2016/6/3.
 */
public class WuziqiPanel extends View {
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private Paint paint = new Paint();
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private float radioPieceofLineHeight = 3* 1.0f / 4;

    private boolean isWhite=true;
    private ArrayList<Point> whiteArray = new ArrayList<>();
    private ArrayList<Point> blackArray = new ArrayList<>();

    private boolean isGameOver = false;
    private boolean isWhiteWinner = false;
    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x220000ff);
        init();
    }
    private void init() {
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = min(widthSize,heightSize);
        //如果宽度未指定,则依赖高度
        if(widthSize == MeasureSpec.UNSPECIFIED)
        {
            width = heightSize;
        }else if(heightSize == MeasureSpec.UNSPECIFIED)
        {
            width = widthSize;
        }
        //设置当前View大小
        setMeasuredDimension(width, width);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;
        int pieceWidth = (int) (mLineHeight * radioPieceofLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPiece(canvas);
        checkGameOver();
    }
    private void checkGameOver() {
        boolean isWhiteWin = checkFiveInLine(whiteArray);
        boolean isBlackWin = checkFiveInLine(blackArray);
        if(isWhiteWin || isBlackWin)
        {
            isGameOver=true;
            isWhiteWinner = isWhiteWin;
            String text = isWhiteWinner?"白棋胜利":"黑棋胜利";
            Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
        }
    }
    //画棋子
    private void drawPiece(Canvas canvas) {

        for(int i=0,len=whiteArray.size();i<len;i++)
        {
            Point p = whiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (p.x + (1-radioPieceofLineHeight) / 2)* mLineHeight,
                    (p.y + (1-radioPieceofLineHeight) / 2)* mLineHeight,null);
            Log.d("White Point X:",String.valueOf( p.x +(int)(1-radioPieceofLineHeight / 2)* mLineHeight));
            Log.d("White Point Y:", String.valueOf(p.y +(int)(1-radioPieceofLineHeight / 2)* mLineHeight));
        }
        for(int i=0,len=blackArray.size();i<len;i++)
        {
            Point p = blackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (p.x + (1-radioPieceofLineHeight) / 2)* mLineHeight,
                    (p.y + (1-radioPieceofLineHeight) / 2)* mLineHeight,null);
            Log.d("Black Point X:",String.valueOf(p.x));
            Log.d("Black Point Y:", String.valueOf(p.y));
        }
    }
    //画棋盘
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for(int i=0;i<MAX_LINE;i++)
        {
            int startX = (int)(lineHeight/2);
            int endX = (int)(w - lineHeight / 2);
            int y = (int) ((0.5 + i)*lineHeight);
            //画横线
            canvas.drawLine(startX,y,endX,y,paint);
            //画竖线
            canvas.drawLine(y,startX,y,endX,paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isGameOver) return false;
        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point point = getValidPoint(x,y);
            if(whiteArray.contains(point) || blackArray.contains(point))
            {
                return false;
            }
            if(isWhite)
            {
                whiteArray.add(point);
            }else
            {
                blackArray.add(point);
            }
            invalidate();
            isWhite = !isWhite;
        }
        return true;
    }
    private Point getValidPoint(int x, int y) {
        Point p = new Point((int)(x / mLineHeight),(int)(y / mLineHeight));
        return p;
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    //保存View状态
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, isGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, whiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,blackArray);
        return bundle;
    }
    //恢复View状态
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            isGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            whiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            blackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
    public void reStart()
    {
        whiteArray.clear();
        blackArray.clear();
        isGameOver=false;
        isWhiteWinner = false;
        invalidate();
    }
}
