package com.seuittrv.dancingrobot3d;

public class Matrix {
    public static double[] getIdentityMatrix() {//return an 4x4 identity matrix
        return new double[]{
                1, 0, 0, 0
                , 0, 1, 0, 0
                , 0, 0, 1, 0
                , 0, 0, 0, 1
        };
    }
}
