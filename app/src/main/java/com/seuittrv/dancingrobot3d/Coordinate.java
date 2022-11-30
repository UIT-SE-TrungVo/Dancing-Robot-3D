package com.seuittrv.dancingrobot3d;
//*********************************************
//* Homogeneous coordinate in 3D space

public class Coordinate {
    public double x, y, z, w;

    public Coordinate() {//create a coordinate with 0,0,0
        x = 0;
        y = 0;
        z = 0;
        w = 1;
    }

    public Coordinate(double x, double y, double z, double w) {//create a Coordinate object
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void Normalise() {
        if (w != 0) {
            x /= w;
            y /= w;
            z /= w;
            w = 1;
        } else w = 1;
    }

    public Coordinate clone() {
        Coordinate newCoordinate = new Coordinate();
        newCoordinate.x = x;
        newCoordinate.y = y;
        newCoordinate.z = z;
        return newCoordinate;
    }
}
