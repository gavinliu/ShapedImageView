package cn.gavinliu.android.lib.shapedimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Arrays;

public class ShapedImageView extends ImageView {

    public static final int SHAPE_MODE_ROUND_RECT = 1;
    public static final int SHAPE_MODE_CIRCLE = 2;

    private int mShapeMode = SHAPE_MODE_ROUND_RECT;
    private float mRadius = 0;
    private float mInnerRadius = 0;
    private int mStrokeColor = 0x26000000;
    private float mStrokeWidth = 0;
    private boolean mShapeChanged;

    private Path mPath;
    private Shape mShape, mStrokeShape;
    private Paint mPaint, mStrokePaint, mPathPaint;
    private Bitmap mShapeBitmap, mStrokeBitmap;

    private PathExtension mExtension;

    private PorterDuffXfermode DST_IN = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private PorterDuffXfermode DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    public ShapedImageView(Context context) {
        super(context);
        init(null);
    }

    public ShapedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShapedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShapedImageView);
            mShapeMode = a.getInt(R.styleable.ShapedImageView_shape_mode, SHAPE_MODE_ROUND_RECT);
            mRadius = a.getDimension(R.styleable.ShapedImageView_round_radius, 0);
            mInnerRadius = a.getDimension(R.styleable.ShapedImageView_inner_radius, 0);

            mStrokeWidth = a.getDimension(R.styleable.ShapedImageView_stroke_width, 0);
            mStrokeColor = a.getColor(R.styleable.ShapedImageView_stroke_color, mStrokeColor);
            a.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(DST_IN);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setFilterBitmap(true);
        mStrokePaint.setColor(Color.BLACK);

        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setFilterBitmap(true);
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setXfermode(DST_OUT);

        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed || mShapeChanged) {
            mShapeChanged = false;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            switch (mShapeMode) {
                case SHAPE_MODE_ROUND_RECT:
                    break;
                case SHAPE_MODE_CIRCLE:
                    int min = Math.min(width, height);
                    mRadius = (float) min / 2;
                    mInnerRadius = (float) min / 2;
                    break;
            }

            if (mShape == null || mRadius != 0) {
                float[] radius = new float[8];
                float[] innerRadius = new float[8];

                Arrays.fill(radius, mRadius);
                mShape = new RoundRectShape(radius, null, null);

                if (mInnerRadius != 0) {
                    Arrays.fill(innerRadius, mInnerRadius);
                } else {
                    innerRadius = radius;
                }

                mStrokeShape = new RoundRectShape(innerRadius, null, null);
            }
            mShape.resize(width, height);
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            makeStrokeBitmap();
            makeShapeBitmap();

            if (mExtension != null) {
                mExtension.onLayout(mPath, width, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveLayers = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);

        try {
            super.onDraw(canvas);
        } catch (Throwable throwable) {

        }

        if (mStrokeWidth > 0 && mStrokeShape != null) {
            if (mStrokeBitmap == null || mStrokeBitmap.isRecycled()) {
                makeStrokeBitmap();
            }
            int i = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
            mStrokePaint.setXfermode(null);
            if (mStrokeBitmap != null && !mStrokeBitmap.isRecycled()) {
                canvas.drawBitmap(mStrokeBitmap, 0, 0, mStrokePaint);
            }
            canvas.translate(mStrokeWidth, mStrokeWidth);
            mStrokePaint.setXfermode(DST_OUT);
            mStrokeShape.draw(canvas, mStrokePaint);
            canvas.restoreToCount(i);
        }

        if (mExtension != null) {
            canvas.drawPath(mPath, mPathPaint);
        }

        switch (mShapeMode) {
            case SHAPE_MODE_ROUND_RECT:
            case SHAPE_MODE_CIRCLE:
                if (mShapeBitmap == null || mShapeBitmap.isRecycled()) {
                    makeShapeBitmap();
                }

                if (mShapeBitmap != null && !mShapeBitmap.isRecycled()) {
                    canvas.drawBitmap(mShapeBitmap, 0, 0, mPaint);
                }
                break;
        }
        canvas.restoreToCount(saveLayers);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseBitmap(mShapeBitmap);
        releaseBitmap(mStrokeBitmap);
    }

    private void makeStrokeBitmap() {
        if (mStrokeWidth <= 0) return;

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return;

        releaseBitmap(mStrokeBitmap);

        mStrokeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mStrokeBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(mStrokeColor);
        c.drawRect(new RectF(0, 0, w, h), p);
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void makeShapeBitmap() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return;

        releaseBitmap(mShapeBitmap);

        mShapeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mShapeBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        mShape.draw(c, p);
    }

    public void setExtension(PathExtension extension) {
        mExtension = extension;
        requestLayout();
    }

    public void setStroke(int strokeColor, float strokeWidth) {
        if (mStrokeWidth <= 0) return;

        if (mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            postInvalidate();
        }

        if (mStrokeColor != strokeColor) {
            mStrokeColor = strokeColor;

            makeStrokeBitmap();
            postInvalidate();
        }
    }

    public void setStrokeColor(int strokeColor) {
        setStroke(strokeColor, mStrokeWidth);
    }

    public void setStrokeWidth(float strokeWidth) {
        setStroke(mStrokeColor, strokeWidth);
    }

    public void setShape(int shapeMode, float radius, float innerRadius) {
        mShapeChanged = mShapeMode != shapeMode || mRadius != radius || mInnerRadius != innerRadius;

        if (mShapeChanged) {
            mShapeMode = shapeMode;
            mRadius = radius;
            mInnerRadius = innerRadius;

            mShape = null;
            mStrokeShape = null;
            requestLayout();
        }
    }

    public void setShapeMode(int shapeMode) {
        setShape(shapeMode, mRadius, mInnerRadius);
    }

    public void setShapeRadius(float radius) {
        setShape(mShapeMode, radius, mInnerRadius);
    }

    public void setInnerRadius(float innerRadius) {
        setShape(mShapeMode, mRadius, innerRadius);
    }

    public interface PathExtension {
        void onLayout(Path path, int width, int height);
    }

}
