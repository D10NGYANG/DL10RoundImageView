package com.dlong.rep.dlroundimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.support.annotation.Nullable;

/**
 * 圆形imageview带边框和阴影
 * Round imageview with borders and shadows
 * 边框宽度可变
 * Border width can be modified
 * 边框颜色、阴影颜色可调
 * Border color, shadow color can also be modified
 * @author  dlong
 * created at 2019/3/16 10:44 AM
 */
public class DLRoundImageView extends AppCompatImageView {
    /** 边框宽度 默认值 */
    private int mBorderWidth = 0;
    /** 边框颜色 默认值 */
    private int mBorderColor = Color.WHITE;
    /** 是否有阴影 默认值 */
    private boolean mHasShadow = false;
    /** 阴影颜色 默认值 */
    private int mShadowColor = Color.BLACK;
    /** 阴影模糊半径 默认值 */
    private float mShadowRadius = 4.0f;

    /** 图片直径 */
    private float mBitmapDiameter = 120f;
    /** 需要绘制的图片 */
    private Bitmap mBitmap;
    /** 图片的画笔 */
    private Paint mBitmapPaint;
    /** 边框的画笔 */
    private Paint mBorderPaint;
    /** 图片的渲染器 */
    private BitmapShader mBitmapShader;
    /** 控件宽度 */
    private float widthOrHeight;
    /** 控件初始设置宽度 */
    private float widthSpecSize;
    /** 控件初始设置宽度 */
    private float heightSpecSize;

    public DLRoundImageView(Context context) {
        super(context);
        initData(context, null);
    }

    public DLRoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public DLRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    /**
     * 带参数初始化
     * Initialization with parameters
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        if (null != attrs){
            // 如果用户有设置参数
            // If the user has set parameters
            @SuppressLint("Recycle")
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DLRoundImageView);
            if (null != typedArray){
                // 读取边框宽度
                // Read the border width
                mBorderWidth = typedArray.getInt(R.styleable.DLRoundImageView_borderWidth, mBorderWidth);
                // 读取边框颜色
                // Read the border color
                mBorderColor = typedArray.getColor(R.styleable.DLRoundImageView_borderColor, mBorderColor);
                // 读取是否有阴影
                // Read has shadow
                mHasShadow = typedArray.getBoolean(R.styleable.DLRoundImageView_hasShadow, mHasShadow);
                // 读取阴影颜色
                // Read the shadow color
                mShadowColor = typedArray.getColor(R.styleable.DLRoundImageView_shadowColor, mShadowColor);
                // 读取阴影模糊半径
                // Read the shadow radius
                mShadowRadius = typedArray.getFloat(R.styleable.DLRoundImageView_shadowRadius, mShadowRadius);
            }
        }
        // 实例化图片的画笔
        mBitmapPaint = new Paint();
        // 设置打开抗锯齿功能
        // Turn on anti-aliasing
        mBitmapPaint.setAntiAlias(true);
        // 实例化边框的画笔
        mBorderPaint = new Paint();
        // 设置边框颜色
        // Set the border color
        mBorderPaint.setColor(mBorderColor);
        // 设置打开抗锯齿功能
        // Turn on anti-aliasing
        mBorderPaint.setAntiAlias(true);
        // 设置硬件加速
        // Set hardware acceleration
        this.setLayerType(LAYER_TYPE_SOFTWARE, mBorderPaint);
        // 设置阴影参数
        // Set shadow parameters
        if (mHasShadow){
            mBorderPaint.setShadowLayer(mShadowRadius, 0, 0, mShadowColor);
        }
    }

    /**
     * 测量
     * MeasureSpec值由specMode和specSize共同组成
     * specMode的值有三个，MeasureSpec.EXACTLY、MeasureSpec.AT_MOST、MeasureSpec.UNSPECIFIED
     * MeasureSpec.EXACTLY：父视图希望子视图的大小应该是specSize中指定的。
     * MeasureSpec.AT_MOST：子视图的大小最多是specSize中指定的值，也就是说不建议子视图的大小超过specSize中给定的值。
     * MeasureSpec.UNSPECIFIED：我们可以随意指定视图的大小。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 得到宽度数据模式
        // Get width data mode
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        // 得到宽度数据
        // Get width data
        widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        // 得到高度数据模式
        // Get height data mode
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        // 得到高度数据
        // Get height data
        heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 初始化控件高度为 图片直径 + 两个边框宽度（左右边框）
        widthOrHeight = mBitmapDiameter + (mBorderWidth * 2);
        // 判断宽高数据格式
        if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY){
            // 如果宽高都给定了数据
            if (widthSpecSize > heightSpecSize){
                // 如果高度小，图片直径等于高度减两个边框宽度
                mBitmapDiameter = heightSpecSize - (mBorderWidth * 2);
                // 控件宽度为高度
                widthOrHeight = heightSpecSize;
            } else {
                // 如果宽度小，图片直径等于宽度减两个边框宽度
                mBitmapDiameter = widthSpecSize - (mBorderWidth * 2);
                // 控件宽度为宽度
                widthOrHeight = widthSpecSize;
            }
        } else if (widthSpecMode == MeasureSpec.EXACTLY){
            // 如果只给了宽度，图片直径等于宽度减两个边框宽度
            mBitmapDiameter = widthSpecSize - (mBorderWidth * 2);
            // 控件宽度为宽度
            widthOrHeight = widthSpecSize;
        } else if (heightSpecMode == MeasureSpec.EXACTLY){
            // 如果只给了高度，图片直径等于高度减两个边框宽度
            mBitmapDiameter = heightSpecSize - (mBorderWidth * 2);
            // 控件宽度为高度
            widthOrHeight = heightSpecSize;
        }
        // 保存测量好的宽高，向上取整再加2为了保证画布足够画下边框和阴影
        // Save the measured width and height
        setMeasuredDimension((int) Math.ceil((double) widthOrHeight + 2), (int) Math.ceil((double) widthOrHeight + 2));
    }

    /**
     * 绘制
     * @param canvas
     */
    @SuppressLint({"DrawAllocation", "CanvasSize"})
    @Override
    protected void onDraw(Canvas canvas) {
        // 加载图片
        // load the bitmap
        loadBitmap();
        // 剪裁图片获得中间正方形
        // Crop the picture to get the middle square
        mBitmap = centerSquareScaleBitmap(mBitmap, (int) Math.ceil((double) mBitmapDiameter + 1));
        // 确保拿到图片
        if (mBitmap != null) {
            // 初始化渲染器
            // init shader
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // 配置渲染器
            // Configuring the renderer
            mBitmapPaint.setShader(mBitmapShader);
            // 判断是否有阴影
            // Determine if there is a shadow
            if (mHasShadow){
                // 配置阴影
                mBorderPaint.setShadowLayer(mShadowRadius, 0, 0, mShadowColor);
                // 画边框
                canvas.drawCircle(widthOrHeight / 2, widthOrHeight / 2,
                        mBitmapDiameter / 2 + mBorderWidth - mShadowRadius, mBorderPaint);
                // 画图片
                canvas.drawCircle(widthOrHeight / 2, widthOrHeight / 2,
                        mBitmapDiameter / 2 - mShadowRadius, mBitmapPaint);
            } else {
                // 配置阴影
                mBorderPaint.setShadowLayer(0, 0, 0, mShadowColor);
                // 画边框
                canvas.drawCircle(widthOrHeight / 2, widthOrHeight / 2,
                        mBitmapDiameter / 2 + mBorderWidth, mBorderPaint);
                // 画图片
                canvas.drawCircle(widthOrHeight / 2, widthOrHeight / 2,
                        mBitmapDiameter / 2, mBitmapPaint);
            }
        }
    }

    /**
     * 加载图片
     * load the bitmap
     */
    private void loadBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null)
            mBitmap = bitmapDrawable.getBitmap();
    }

    /**
     * 裁切图片
     * Crop picture
     * 得到图片中间正方形的图片
     * Get a picture of the middle square of the picture
     * @param bitmap   原始图片
     * @param edgeLength  要裁切的正方形边长
     * @return Bitmap  图片中间正方形的图片
     */
    private Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength){
        if (null == bitmap || edgeLength <= 0) {
            // 避免参数错误
            // Avoid parameter errors
            return bitmap;
        }
        // 初始化结果
        // Initialization result
        Bitmap result = bitmap;
        // 拿到图片原始宽度
        // Get the original width of the image
        int widthOrg = bitmap.getWidth();
        // 拿到图片原始高度
        // Get the original height of the image
        int heightOrg = bitmap.getHeight();
        // 要保证图片宽高要大于要裁切的正方形边长
        // Make sure that the width of the image is greater than the length of the square to be cropped.
        if (widthOrg >= edgeLength && heightOrg >= edgeLength){
            // 得到对应宽高比例的另一个更长的边的长度
            // Get the length of the other longer side corresponding to the aspect ratio
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            // 分配宽度
            // Distribution width
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            // 分配高度
            // Distribution height
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            // 定义一个新压缩的图片位图
            // Define a new compressed picture bitmap
            Bitmap scaledBitmap;
            try {
                // 压缩图片，以一个新的尺寸创建新的位图
                // Compress the image to create a new bitmap in a new size
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }catch (Exception e) {
                return bitmap;
            }
            // 得到裁切中间位置图形的X轴偏移量
            // Get the X-axis offset of the cut intermediate position graphic
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            // 得到裁切中间位置图形的Y轴偏移量
            // Get the Y-axis offset of the cut intermediate position graphic
            int yTopLeft = (scaledHeight - edgeLength) / 2;
            try {
                // 在指定偏移位置裁切出新的正方形位图
                // Crop a new square bitmap at the specified offset position
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                // 释放内存，回收资源
                // Free up memory, recycle resources
                scaledBitmap.recycle();
            }catch (Exception e) {
                return bitmap;
            }
        }
        return result;
    }

    /**
     * 设置边框宽度
     * Set the border width
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        // 重新绘制
        // repaint
        this.invalidate();
    }

    /**
     * 设置边框颜色
     * Set the border color
     * Exposure method
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        if (mBorderPaint != null)
            mBorderPaint.setColor(borderColor);
        // 重新绘制
        // repaint
        this.invalidate();
    }

    /**
     * 设置是否有阴影
     * Set whether there is a shadow
     * @param hasShadow
     */
    public void setHasShadow(boolean hasShadow) {
        this.mHasShadow = hasShadow;
        // 重新绘制
        // repaint
        this.invalidate();
    }

    /**
     * 设置阴影颜色
     * Set the shadow color
     * @param shadowColor
     */
    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        // 重新绘制
        // repaint
        this.invalidate();
    }

    /**
     * 设置阴影模糊半径
     * Set the shadow blur radius
     * @param shadowRadius
     */
    public void setShadowRadius(float shadowRadius) {
        this.mShadowRadius = shadowRadius;
        // 重新绘制
        // repaint
        this.invalidate();
    }
}
