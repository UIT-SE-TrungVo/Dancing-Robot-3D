package com.seuittrv.dancingrobot3d.robot;

import com.seuittrv.dancingrobot3d.Cube;

import java.util.ArrayList;
import java.util.List;

public class Part implements IDance {
    List<Cube> cubes = new ArrayList<>();

    protected void addCube(Cube cube) {
        this.cubes.add(cube);
    }

    public List<Cube> getCubes() {
        return this.cubes;
    }

    @Override
    public void dance() {

    }
}
