package jakeli.com.customview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "JJJ";
    /**
     * Vertices of pentagon
     */
    Point centerPoint;

    Point[] pentagonVertices;

    Button[] buttons;

    FloatingActionButton fab;

    /**
     * Buttons to be animated
     */
    Button button;

    int height, width;

    int radius;

    int ANIMATION_DURATION = 300;

    /**
     * Coordination of button
     */
    int startPositionX = 0;
    int startPositionY = 0;

    /**
     * To check which animation is to be played
     * O for enter animation
     * 1 for exit animation
     */
    int whichAnimation = 0;

    //Polygon
//    int NUM_OF_SIDES = 5;
//    int POSITION_CORRECTION = 11;

//    int[] enterDelay = {80, 120, 160, 40, 0};
//    int[] exitDelay = {80, 40, 0, 120, 160};

    final int NUM_OF_SIDES = 5;

    final int CORRECTION = 11;

    int[] enterDelay = {0, 40, 80, 120, 160};
    int[] exitDelay = {160, 120, 80, 40, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        height = (int) getResources().getDimension(R.dimen.button_height);
        width = (int) getResources().getDimension(R.dimen.button_width);
        radius = (int) getResources().getDimension(R.dimen.radius);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        calculatePentagonVertices(radius);

//        calculatePentagonVertices(radius, POSITION_CORRECTION);
        Display display = getWindowManager().getDefaultDisplay();
        int centerX = display.getWidth() / 2;
        int centerY = display.getHeight() / 2;

        centerPoint = new Point(centerX, centerY);
    }

    private void calculatePentagonVertices(int radius) {

        pentagonVertices = new Point[NUM_OF_SIDES];

        /**
         * Calculating the center of pentagon
         */
        Display display = getWindowManager().getDefaultDisplay();
        int centerX = display.getWidth() / 2;
        int centerY = display.getHeight() / 2;

        for(int i = 0; i < NUM_OF_SIDES; i++){
            pentagonVertices[i] = new Point(50,200*i+30);
        }

        buttons = new Button[pentagonVertices.length];

        for(int i = 0; i < buttons.length; i++){

            buttons[i] = new Button(MainActivity.this);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(5, 5));
           buttons[i].setBackgroundResource(R.drawable.circlebackground);

            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setText(String.valueOf(i+1));
            buttons[i].setTextSize(20);
            buttons[i].setVisibility(View.INVISIBLE);
            ((RelativeLayout) findViewById(R.id.activity_main2)).addView(buttons[i]);
        }

    }

    @Override
    public void onClick(View view) {

        boolean isFabClicked = false;

        switch (view.getId()) {
            case R.id.fab:
                isFabClicked = true;
                if (whichAnimation == 0) {
                    /**
                     * Getting the center point of floating action button
                     *  to set start point of buttons
                     */
                    Log.i(TAG, "onClick: 11111111111  =  " + view.getX());

                    startPositionX = (int) view.getX() + 50;
                    startPositionY = (int) view.getY() + 50;

                    for(Button button : buttons){
                        button.setX(startPositionX);
                        button.setY(startPositionY);
                        button.setVisibility(View.VISIBLE);
                    }

                    for(int i = 0; i <  buttons.length; i++){
                        playEnterAnimation(buttons[i], i);
                    }
                     whichAnimation = 1;
                } else {
                    for(int i = 0; i <  buttons.length; i++){
                        playExitAnimation(buttons[i], i);
                    }
                    whichAnimation = 0;
                }
        }

        if (!isFabClicked) {
            switch ((int) view.getTag()) {
                case 0:
                    Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this, "Button 5 clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void playEnterAnimation(final Button button, int pos) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * 更新按钮的x位置
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(startPositionX + button.getLayoutParams().width / 2,
                pentagonVertices[pos].x);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);
        /**
         * ValueAnimator to update y position of a button
         * 更新按钮的y位置
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(startPositionY,
                pentagonVertices[pos].y );
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });

        /**
         * This will increase the size of button
         * 这将增加按钮的大小
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(5, width);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        button.setBackgroundResource(R.drawable.circlebackground1);
        /**
         * This will decrease the size of button
         * 这将按钮的变长
         */
        ValueAnimator buttonLengthAnimator = ValueAnimator.ofInt(width, 700);
        buttonLengthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);
        buttonAnimatorY.setDuration(ANIMATION_DURATION);
        buttonLengthAnimator.setDuration(100*pos+200);
        //我添加的
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.play(buttonLengthAnimator).after(buttonSizeAnimator);
        buttonAnimator.setStartDelay(enterDelay[pos]);
        buttonAnimator.start();

    }

    private void playExitAnimation(final Button button, int pos) {
        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();
        /**
         * ValueAnimator to update x position of a button
         * 更新按钮的x位置
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(pentagonVertices[pos].x - button.getLayoutParams().width / 2,
                startPositionX);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        /**
         * ValueAnimator to update y position of a button
         * 更新按钮的y位置
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(pentagonVertices[pos].y,
                startPositionY);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });

        /**
         * This will decrease the size of button
         * 这将减少按钮的大小
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(width, 5);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        /**
         * This will decrease the size of button
         * 这将按钮的变短
         */
        ValueAnimator buttonLengthAnimator = ValueAnimator.ofInt(700, 160);
        buttonLengthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);
        buttonAnimatorY.setDuration(ANIMATION_DURATION);
        long a=200/(pos+1)+ANIMATION_DURATION;
        buttonLengthAnimator.setDuration(a);
        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonLengthAnimator);
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator).after(buttonLengthAnimator);
        buttonAnimator.setStartDelay(exitDelay[pos]);
        buttonAnimator.start();
    }

}
