package com.seuittrv.dancingrobot3d.robot;

import android.graphics.Color;

import com.seuittrv.dancingrobot3d.Cube;

import java.util.Random;

public class Arm extends Part {
    final long TIME_ALL = DanceConstant.DANCE_TIME;

    final long TIME_SINGLE_FULL = DanceConstant.DANCE_TIME / 4;
    final long TIME_SINGLE_HALF = DanceConstant.DANCE_TIME / 8;

    final long TIME_BOTH_FULL = DanceConstant.DANCE_TIME / 4;
    final long TIME_BOTH_HALF = DanceConstant.DANCE_TIME / 8;
    long counter = 0;

    long tickStartDance = 0;
    long tickEndDance = 0;

    long tickBothStart = 0;
    long tickBothEnd = 0;

    boolean handX = true;
    int sideWaveY = 1;

    final long ANGLE_CHANGE_PER_FRAME = -2;
    Cube upperArm;
    Cube lowerArm;
    Cube mark;

    public Arm(double offsetX, double offsetY) {
        this.upperArm = new Cube(1, 2, 1, 0.5, 1, 0.5, Color.BLUE);
        this.addCube(this.upperArm);

        this.lowerArm = new Cube(1, 2, 1, 0.5, 1, 0.5, Color.GREEN);
        this.upperArm.addChild(this.lowerArm);

//        this.mark = new Cube(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, Color.DKGRAY);
//        this.upperArm.addChild(this.mark);

        Cube hand = new Cube(1, 0.5, 1.5, 0.5, 1, 0.3, Color.YELLOW);
        this.lowerArm.addChild(hand);

        this.lowerArm.addDrawTransition(0, -2, 0, 0);
        hand.addDrawTransition(0, -2, 0, 0);
//        this.mark.addDrawTransition(0, -2, 0, 0);

        this.setHandPosition(offsetX, offsetY);
    }

    private void setHandPosition(double x, double y) {
        for (Cube cube : this.cubes) {
            cube.addDrawTransition(x, y, 0, 0);
        }

        if (x > 0) {
            this.tickStartDance = 0;
            this.tickEndDance = this.TIME_ALL / 4;
            this.sideWaveY = -1;
        } else {
            this.tickStartDance = this.TIME_ALL / 4;
            this.tickEndDance = this.TIME_ALL / 2;
            this.sideWaveY = 1;
        }

        this.tickBothStart = this.TIME_ALL / 2;
        this.tickBothEnd = this.TIME_ALL;
    }

    @Override
    public void dance() {
        if (this.counter >= TIME_ALL) {
            this.counter -= this.TIME_ALL;
            int randNumber = new Random().nextInt(2);
            this.handX = randNumber == 0;
        }

        if (this.counter >= this.tickStartDance && this.counter < this.tickEndDance) {
            double direction = counter >= this.tickStartDance + this.TIME_SINGLE_HALF ? -1 : 1;
            if (this.handX) this.waveHand(direction, 'x', 1);
            else this.waveHand(direction, 'y', this.sideWaveY);
        } else if (this.counter >= this.tickBothStart && this.counter < this.tickBothEnd) {
            long raiseCounter = this.counter - this.tickBothStart;
            double section = Math.floor((double) 4 * raiseCounter / (this.tickBothEnd - this.tickBothStart));
            double directionY = (section == 0 || section == 3) ? 1 : -1;
            double directionX = (section == 0 || section == 2) ? 1 : -1;
            this.waveHand(directionY, 'y', 1);
            this.waveHand(directionX, 'x', 1);
        }

        this.counter += DanceConstant.TICK;
    }

    private void waveHand(double direction, char axis, int sideWaveY) {
        this.lowerArm.addDrawRotation(direction * sideWaveY * this.ANGLE_CHANGE_PER_FRAME, axis, 0);
        this.upperArm.addDrawRotation(direction * sideWaveY * this.ANGLE_CHANGE_PER_FRAME, axis, 0);
//        this.mark.addDrawRotation(direction * this.ANGLE_CHANGE_PER_FRAME, axis, 0);
    }
}
