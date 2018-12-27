package jakeli.com.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

public class CustomView extends View {
    private String TAG = "JJJ";

    private boolean isTouched = false;

    private int lastX;
    private int lastY;

    private int lastFrameX;
    private int lastFrameY;

    private int offsetX = 0;
    private int offsetY = 0;

    Paint mPaint;
    Rect mRect;

    static int mSquareColor;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();

        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.CustomView);
        mSquareColor = typedArray.getColor(R.styleable.CustomView_square_color, Color.GREEN);
        mPaint.setColor(mSquareColor);
        typedArray.recycle();

    }

    public void setX(){

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //todo Task1
        mRect.left = 250    ;
        mRect.right = getWidth();
        mRect.top = 250;
        mRect.bottom = getHeight();

        canvas.drawRect(mRect, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        ////todo Task1
        Log.i(TAG, "onTouchEvent: FDFDFDFDF");
//        Log.i(TAG, "onTouchEvent: getX   =  " + event.getX());
//        Log.i(TAG, "onTouchEvent: getY   =  " + event.getY());
//        Log.i(TAG, "onTouchEvent: getrawX = " + event.getRawX());
//        Log.i(TAG, "onTouchEvent: getrawY = " + event.getRawY());

        //todo Task1
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: view x viewx  = " + View.X);
                Log.i(TAG, "onTouchEvent: view tx view tx = " + View.TRANSLATION_X);
                Log.i(TAG, "onTouchEvent: get x get x = " + getX());
                Log.i(TAG, "onTouchEvent: get tx  get tx = " + getTranslationX());

                lastX = x;
                lastY = y;
//                clearAnimation();

                startAnime3();
                Log.i(TAG, "onTouchEvent: 1111111111111111111 " );

                Log.i(TAG, "onTouchEvent: get xx= " + getLeft());
                Log.i(TAG, "onTouchEvent: get yy = " + getTop());
                isTouched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isTouched = false;
                offsetX = x - lastX;
                offsetY = y - lastY;

                lastFrameX = x;
                lastFrameY = y;

//                move(offsetX, offsetY);


                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: 2222222222222222222 " );
                isTouched = false;
//                startAnime(offsetX, offsetY);
                offsetX = lastFrameX - x;
                offsetY = lastFrameY - y;
                Log.i(TAG, "onTouchEvent: offset xxxxxxxxx = " + offsetX);
                Log.i(TAG, "onTouchEvent: offset yyyyyyyyy = " + offsetY);
//                startAnime(x, offsetX, y, offsetY);
//                startAnime2(offsetX, offsetY);
                offsetX = 0;
                offsetY = 0;
                lastX = 0;
                lastY = 0;

                break;
        }

        return true;
    }

    private void move(int offsetX, int offsetY){
        layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
    }

    private void startAnime2(int offsetX, int offsetY){
        offsetX = offsetX * 2;
        offsetY = offsetY * 2;
//        final ObjectAnimator oax = ObjectAnimator.ofFloat(this, View.X, getX() + offsetX);
//        final ObjectAnimator oay = ObjectAnimator.ofFloat(this, View.Y, getY() + offsetY);
        final ObjectAnimator oax = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, offsetX);
        final ObjectAnimator oay = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y,  offsetY);
        DecelerateInterpolator di = new DecelerateInterpolator(2);

        final AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(di);
        set.playTogether(oax, oay);

        oax.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate: xxxxxxx=  " + getX());
                Log.i(TAG, "onAnimationUpdate: yyyyyy=  " + getY());
                if(isTouched){
                    oax.cancel();
                    oay.cancel();
                    set.cancel();
                    oax.removeAllUpdateListeners();
                    oay.removeAllUpdateListeners();

                }
            }
        });

        set.start();
    }

    private void startAnime(final int offsetX, final int offsetY){
        Log.i(TAG, "startAnime: offset x 111111111 = " + offsetX);
        Log.i(TAG, "startAnime: offset y = 1111111111 = " + offsetY);
        final int newOffsetX = offsetX * 4 ;
        final int newOffsetY = offsetY * 4;
        TranslateAnimation ta = new TranslateAnimation(0, newOffsetX , 0, newOffsetY );
        ta.setDuration(1000);
        Interpolator i = new DecelerateInterpolator(2);
        ta.setInterpolator(i);
        ta.setRepeatCount(0);
        ta.setFillAfter(true);
        setVisibility(View.VISIBLE);

        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                setVisibility(View.GONE);
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)getLayoutParams();
//                params.leftMargin += newOffsetX;
//                params.topMargin += newOffsetY;
//                setLayoutParams(params);
                layout(getLeft() + newOffsetX, getTop() + newOffsetY, getRight() + newOffsetX, getBottom() + newOffsetY);
                clearAnimation();
                Log.i(TAG, "onAnimationEnd: get lft = " +getLeft());
                Log.i(TAG, "onAnimationEnd: get top = " + getTop());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        startAnimation(ta);
    }

    private void startAnime3(){
        Log.i(TAG, "startAnime3: ______________-    =   ");
        Log.i(TAG, "startAnime3: HHHHHHHHHHHHHHh = " + getLayoutParams().width);

        AnimatorSet set = new AnimatorSet();

        ValueAnimator widthAnimator1 = ValueAnimator.ofInt(getLayoutParams().width, getLayoutParams().width + 700);
        widthAnimator1.setDuration(1000);
        widthAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate: IIIIIIIIIIIIIIIIIII  ");
                int animatedValue = (int)animation.getAnimatedValue();
                getLayoutParams().width = animatedValue;
                requestLayout();
            }
        });

        ValueAnimator widthAnimator2 = ValueAnimator.ofInt(getLayoutParams().height, getLayoutParams().height + 1000);
        widthAnimator2.setDuration(1000);
        widthAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate: IIIIIIIIIIIIIIIIIII  ");
                int animatedValue = (int)animation.getAnimatedValue();
                getLayoutParams().height = animatedValue;
                requestLayout();
            }
        });

        set.play(widthAnimator1).before(widthAnimator2);

        set.start();
//        widthAnimator1.start();
    }


}
