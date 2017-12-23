package com.zlcm.zlgg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zlcm.zlgg.R;

public class ClipView extends View {

	private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
			| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
			| Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.ALL_SAVE_FLAG;
	//圆形
	public static final int CLIP_ROUND = 1;
	//矩形
	public static final int CLIP_RECTANGLE = 2;
	private float mRadius = 200;
	private float radiusWidthRatio  = 3f/9;//裁剪圆框的半径占view的宽度的比

	private int clipType;
	private Paint mPaint;
	private RectF shelterR;

	public ClipView(Context context) {
		super(context);
	}

	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClipView);
		clipType = ta.getInt(R.styleable.ClipView_type,1);
		init();
	}

	private void init(){
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(2);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#a8000000"));
	}

	public ClipView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}

	public int getClipType() {
		return clipType;
	}

	public void setClipType(int clipType) {
		this.clipType = clipType;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();
		mPaint = new Paint();
		mPaint.setColor(0xA6000000);
		switch (clipType){
			case CLIP_ROUND:
				int sc = canvas.saveLayer(shelterR, null, LAYER_FLAGS);
				canvas.drawRect(shelterR, mPaint);
				// DST_OUT取差集，显示图层上最下面如图片背景不相交的部分，抠掉中间裁剪区域
				mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
				mRadius = getWidth() * radiusWidthRatio;
				canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
				canvas.restoreToCount(sc);
				mPaint.setXfermode(null);
				break;
			case CLIP_RECTANGLE:
				//top
				canvas.drawRect(0, 0, width, (height-width/2)/2, mPaint);
				//bottom
				canvas.drawRect(0, (height+width/2)/2, width, height, mPaint);
				mPaint.setXfermode(null);
				break;
		}
	}



}
