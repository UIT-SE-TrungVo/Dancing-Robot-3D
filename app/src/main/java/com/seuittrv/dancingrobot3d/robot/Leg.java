package com.seuittrv.dancingrobot3d.robot;

import android.graphics.Color;

import com.seuittrv.dancingrobot3d.Cube;

public class Leg extends Part {
    final long TIME_2_LEG = DanceConstant.DANCE_TIME;
    final long TIME_LEG_HALF = DanceConstant.DANCE_TIME / 4;
    long counter = 0;

    long tickStartDance = 0;
    long tickEndDance = 0;

    final double ANGLE_CHANGE_PER_FRAME = -1;
    Cube upperLeg;
    Cube lowerLeg;
    Cube mark;

    public Leg(double offsetX, double offsetY) {
        this.upperLeg = new Cube(1, 2, 1, 0.5, 1, 0.5, Color.BLUE);
        this.addCube(this.upperLeg);

        this.lowerLeg = new Cube(1, 2.5, 1, 0.5, 1, 0.5, Color.GREEN);
        this.upperLeg.addChild(this.lowerLeg);

//        this.mark = new Cube(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, Color.DKGRAY);
//        this.upperLeg.addChild(this.mark);

        Cube foot = new Cube(1, 0.5, 2, 0.5, 1, 0.3, Color.RED);
        this.lowerLeg.addChild(foot);

        this.lowerLeg.addDrawTransition(0, -2, 0, 0);
        foot.addDrawTransition(0, -2.5, 0, 0);
//        this.mark.addDrawTransition(0, -2, 0, 0);

        this.setLegPosition(offsetX, offsetY);
    }

    private void setLegPosition(double x, double y) {
        for (Cube cube : this.cubes) {
            cube.addDrawTransition(x, y, 0, 0);
        }
        if (x < 0) {
            this.tickStartDance = 0;
            this.tickEndDance = this.TIME_2_LEG / 2;
        } else {
            this.tickStartDance = this.TIME_2_LEG / 2;
            this.tickEndDance = this.TIME_2_LEG;
        }
    }

    @Override
    public void dance() {
        if (this.counter >= this.TIME_2_LEG) this.counter -= this.TIME_2_LEG;
        if (this.counter >= this.tickStartDance && this.counter < this.tickEndDance) {
            long direction = counter >= this.tickStartDance + this.TIME_LEG_HALF ? -1 : 1;
            this.lowerLeg.addDrawRotation(-direction * this.ANGLE_CHANGE_PER_FRAME, 'x', 0);
//            this.mark.addDrawRotation(-direction * this.ANGLE_CHANGE_PER_FRAME, 'x', 0);
            this.upperLeg.addDrawRotation(direction * this.ANGLE_CHANGE_PER_FRAME, 'x', 0);
        }
        this.counter += DanceConstant.TICK;
    }
}
