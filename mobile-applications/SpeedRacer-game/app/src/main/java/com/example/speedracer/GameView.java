package com.example.speedracer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public static int MOVEMENT = 5;
    private final Bitmap heartBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    int score = 0;
    Player player;
    ArrayList<RoadDot> dots = new ArrayList<>();
    ArrayList<RoadLine> lines = new ArrayList<>();
    ArrayList<Competitor> competitors = new ArrayList<>();

    Rect powerUp = null;
    Random random = new Random();

    int lives = 3;
    float ferodoLife = 1;

    boolean isAlive = true;

    Paint paint;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Thread gameThread;

    int screenSizeX;
    int screenSizeY;
    private Bitmap crashBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crash);

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;

        player = new Player(context, screenSizeX, screenSizeY);
        player.speed = 10;

        paint = new Paint();
        surfaceHolder = getHolder();

        for(int i = 0; i < 8000; i++) dots.add(new RoadDot(screenSizeX, screenSizeY));

        Bitmap lineBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.road_line);

        int lineAndSpacing = lineBitmap.getHeight() * 2;
        int numberLines = screenSizeY / lineAndSpacing;

        int laneWidth = screenSizeX / 3;

        for(int i = 0; i < numberLines; i++){

            for(int j = 1; j < 3 ; j++){

                RoadLine line = new RoadLine(getContext(), screenSizeY);
                line.x = laneWidth * j;
                line.y = i * lineAndSpacing;

                lines.add(line);
            }
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (isAlive) {
            update();
            draw();
            refreshRate();
            score++;
        }
    }

    private void refreshRate() {

        try {
            gameThread.sleep(41);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void update() {

        if (score % 200 == 0) {
            player.speed++;
            MOVEMENT = (int)player.speed / 2;
        }

        if (score % 1000 == 0) {
            competitors.add(new Competitor(getContext(), screenSizeX, screenSizeY, (int)player.speed));
        }


        if (powerUp != null) {
            powerUp.top += player.speed;
            powerUp.bottom += player.speed;

            if (Rect.intersects(powerUp, player.collisionDetection)) {
                ferodoLife = 1;
                powerUp.top = screenSizeY + 1;
            }

            if (powerUp.top > screenSizeY)
                powerUp = null;
        }

        if (score > 1 && score % 1000 == 0) {
            int powerUpX = random.nextInt(screenSizeX) - 100;
            powerUp = new Rect(powerUpX, -30 ,powerUpX + 30,0);
        }

        for (RoadLine line: lines) {
            line.update((int)player.speed);
        }

        for (RoadDot dot : dots) {
            dot.update((int)player.speed);
        }

        for (Competitor comp : competitors) {
            comp.update((int)player.speed);

            if (!comp.playerCrashed && Rect.intersects(comp.collisionDetection, player.collisionDetection)) {
                lives--;
                if (lives == 0) {
                    isAlive = false;
                    player.crashed = true;
                }
                comp.crashed();
                comp.playerCrashed = true;
            }
        }

        for (Competitor comp : competitors) {

            for (Competitor secondComp : competitors) {
                if (comp == secondComp) {
                    continue;
                }

                if (Rect.intersects(comp.collisionDetection, secondComp.collisionDetection)) {
                    comp.crashed();
                    secondComp.crashed();
                }
            }
        }


    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.GRAY);
            paint.setColor(Color.BLACK);

            for (RoadDot dot : dots) {
                canvas.drawPoint(dot.x, dot.y, paint);
            }

            for (RoadLine line : lines) {
                canvas.drawBitmap(line.bitmap, line.x, line.y, paint);
            }

            for (Competitor comp : competitors) {
                canvas.drawBitmap(comp.bitmap, comp.x, comp.y, paint);
                if(comp.crashed){
                    canvas.drawBitmap(crashBitmap, comp.x + 2, comp.y + 2, paint);
                }
            }

            paint.setColor(Color.WHITE);
            paint.setTextSize(40);

            canvas.drawText("Score: " + score, 250 ,50 ,paint);

            paint.setColor(Color.YELLOW);

            canvas.drawRect(new Rect(50, 100, 51 + (int)((screenSizeX - 100) * ferodoLife), 150), paint);

            for (int i = 1; i <= lives ; i++) {
                canvas.drawBitmap(
                        heartBitmap,
                        50 * i, 50, paint);
            }


            if (powerUp != null) {
                paint.setColor(Color.BLUE);
                canvas.drawRect(powerUp, paint);
            }

            canvas.drawBitmap(player.bitmap, player.x, player.y, paint);

            if (player.crashed) {
                canvas.drawBitmap(crashBitmap, player.x + 2, player.y + 2, paint );
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionIndex();
        int pointer = event.getPointerId(action);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                if(velocityTracker == null){
                    velocityTracker = VelocityTracker.obtain();
                }else{
                    velocityTracker.clear();
                }

                velocityTracker.addMovement(event);

                break;

            case MotionEvent.ACTION_MOVE:

                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                //Log.wtf("velocity", "X:" + velocityTracker.getXVelocity());
                //Log.wtf("velocity", "Y:" + velocityTracker.getYVelocity());

                int xMovement = 0;
                int yMovement = 0;


                if(velocityTracker.getXVelocity() > 0){
                    xMovement = MOVEMENT;
                }else if(velocityTracker.getXVelocity() < 0){
                    xMovement = -MOVEMENT;
                }

                if(velocityTracker.getYVelocity() > 0 && ferodoLife > 0){
                    ferodoLife -= 0.005;

                    if(player.speed > 5)
                        player.speed -= 0.1;

                    Log.wtf("ferodo", ferodoLife + "");
                }
//                else
//                if(velocityTracker.getYVelocity() < 0){
//                    yMovement = -MOVEMENT;
//                }

                player.update(xMovement, yMovement);

                break;
        }


        return true;
    }
}