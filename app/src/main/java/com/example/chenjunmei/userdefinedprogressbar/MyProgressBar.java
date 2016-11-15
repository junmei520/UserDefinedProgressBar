package com.example.chenjunmei.userdefinedprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by junmei
 * 自定义圆形ProgressBar
 */
public class MyProgressBar extends View {
    private int width;
    private Paint paint;

    //属性
    private int roundColor;  //圆环颜色
    private int roundProgressColor;  //圆弧的颜色
    private int textColor;  //文本颜色

    private int roundWidth;//圆环的宽度
    private int textSize; //字体的大小

    //提供当前的进度和最大值
    private int progress;
    private int max;

    private Context context;
    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

        //1.得到所有自定义属性的数组
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.JMRoundProgressBar);

        //2.获取自定义属性的值, 如果没有指定取默认值
        roundColor=typedArray.getColor(R.styleable.JMRoundProgressBar_roundColor, Color.GRAY);
        roundProgressColor=typedArray.getColor(R.styleable.JMRoundProgressBar_roundProgressColor,Color.RED);
        textColor=typedArray.getColor(R.styleable.JMRoundProgressBar_textColor,Color.RED);

        roundWidth= (int) typedArray.getDimension(R.styleable.JMRoundProgressBar_roundWidth, 10);
        textSize = (int) typedArray.getDimension(R.styleable.JMRoundProgressBar_textSize, 20);

        max = typedArray.getInteger(R.styleable.JMRoundProgressBar_max, 100);
        progress = typedArray.getInteger(R.styleable.JMRoundProgressBar_progress, 60);

        //3.释放资源数据
        typedArray.recycle();

        paint=new Paint();  //创建画笔
        paint.setAntiAlias(true);  //设置抗锯齿

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=this.getMeasuredWidth();  //先获取当前视图的宽度
    }

    //绘制圆环，圆弧，文本
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.绘制圆环
        //设置圆环的中心点
        int dx=width/2;
        int dy=width/2;
        //设置半径
        int radius=width/2-roundWidth/2;
        //设置画笔
        paint.setColor(roundColor);  //设置圆环颜色
        paint.setStyle(Paint.Style.STROKE); //设置为圆环
        paint.setStrokeWidth(roundWidth); //设置圆环宽度
        canvas.drawCircle(dx,dy,radius,paint);


        //2.绘制圆弧
        //理解为包裹圆环中心线的圆的矩形
        RectF rectF=new RectF(roundWidth/2,roundWidth/2,width-roundWidth/2,width-roundWidth/2);
        paint.setColor(roundProgressColor); //设置圆弧的画笔颜色
        canvas.drawArc(rectF, 0, progress * 360 / max, false, paint);


        //3.绘制文本(注意：设置字体大小的操作要放在设置包裹的rect之前）
        paint.setColor(textColor);//设置文本颜色
        paint.setTextSize(textSize); //设置字体大小
        paint.setStrokeWidth(0);
        String text=progress*100/max+"%";
        Rect bound=new Rect();  //此时包裹文本的矩形框还没有宽度和高度
        paint.getTextBounds(text,0,text.length(),bound);//此时bound有了宽度和高度
        //提供文本区域的左下定点
        int left=width/2-bound.width()/2;
        int bottom=width/2+bound.width()/2;

        canvas.drawText(text,left,bottom,paint);
    }

    //将dp-->px
    public  int dp2px(int dp){
        //先获取手机的密度
        float desity=context.getResources().getDisplayMetrics().density;
        //通过+0.5来实现四舍五入
        return (int) (dp*desity+0.5);
    }

}
