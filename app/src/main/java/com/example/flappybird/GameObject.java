package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameObject extends View {

    private final Bitmap ball;
    private double positionX;
    private double positionY;
    private Bitmap background;
    private final Bitmap tmp;
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / MainActivity.MAX_UPS;
    private double velocityX=MAX_SPEED;
    private double velocityY=MAX_SPEED;

    public GameObject(Context context, double positionX, double positionY) {

        super(context);
        this.positionX = positionX;
        this.positionY = positionY;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.football);
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.staduim_background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        background = Bitmap.createScaledBitmap(tmp, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(ball, (float) positionX, (float) positionY, null);
        update(canvas);
    }

    public void update(Canvas canvas) {
        if(positionY<0.0 || positionY+ball.getHeight()>canvas.getHeight()) {
            velocityY = -velocityY;
        }
        if(positionX<0.0 || positionX+ball.getWidth()>canvas.getWidth()) {
            velocityX = -velocityX;
//            Log.e("positionX  =  ", "" + positionX);
//            Log.e("positionX+b.W  =  ", "" + (positionX+ball.getWidth()));
//            Log.e("b.W  =  ", "" + (ball.getWidth()));
//            Log.e("width  =  ", "" + canvas.getWidth());
        }

        positionX += velocityX;
        positionY += velocityY;
    }

    private double getDistanceBetweenObjects(double ballX, double ballY, double handX, double handY) {
        return Math.sqrt(Math.pow(ballX-handX, 2) + Math.pow(ballY-handY, 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //****************************************************************************************
                //Update the velocity of the ball so that the velocity is in the direction of the the touch
                //****************************************************************************************
                //calculate vector from the ball to player(in x and y)
                double distanceToPlayerX = event.getX() - positionX;
                double distanceToPlayerY = event.getY() - positionY;

                //calculate (absolute) distance between ball (this) and player
                double distanceToPlayer = getDistanceBetweenObjects(positionX, positionY, event.getX(), event.getY());

                //calculate direction from ball to player
                double directionX = distanceToPlayerX/distanceToPlayer;
                double directionY = distanceToPlayerY/distanceToPlayer;

                //set velocity in the direction to the player
                Log.e("directionX  =  ", "" + directionX);
                Log.e("directionY  =  ", "" + directionY);
                velocityX = directionX*MAX_SPEED;
                velocityY = directionY*MAX_SPEED;
        }

        return super.onTouchEvent(event);
    }
}
