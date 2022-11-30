package com.seuittrv.dancingrobot3d.robot;

import android.graphics.Color;

import com.seuittrv.dancingrobot3d.Cube;

public class Body extends Part{
    public Body() {
        Cube head = new Cube(1.5,1.5,1.5,0.5,0,0.5, Color.BLUE);
        head.doTranslate(0, 0.25, 0);
        this.addCube(head);

        Cube neck = new Cube(0.5, 0.25, 0.5, 0.5, 0, 0.5, Color.MAGENTA);
        this.addCube(neck);

        Cube body = new Cube(3, 3, 1.5, 0.5,1,0.5, Color.RED);;
        this.addCube(body);

        Cube stomach = new Cube(3, 0.5, 1.5, 0.5, 1, 0.5, Color.MAGENTA);
        stomach.doTranslate(0, -3, 0);
        this.addCube(stomach);
    }
}
