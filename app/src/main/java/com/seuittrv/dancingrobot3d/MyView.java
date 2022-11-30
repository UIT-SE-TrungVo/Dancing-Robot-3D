package com.seuittrv.dancingrobot3d;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.seuittrv.dancingrobot3d.robot.DanceConstant;
import com.seuittrv.dancingrobot3d.robot.Robot;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    private final Robot robot = new Robot();

    public MyView(Context context) {
        super(context);
        final MyView thisView = this;
        CubeDrawer.clearAllPaints();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                robot.rotateY(2);
                thisView.invalidate();
            }
        };
        timer.scheduleAtFixedRate(task, 5, 50);
//        robot.rotateY(0);

        Timer danceTimer = new Timer();
        TimerTask danceTask = new TimerTask() {
            @Override
            public void run() {
                robot.dance();
                thisView.invalidate();
            }
        };
        danceTimer.scheduleAtFixedRate(danceTask, 10, DanceConstant.TICK * 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        System.out.println("Draw robot !");
        robot.draw(canvas);
    }
}