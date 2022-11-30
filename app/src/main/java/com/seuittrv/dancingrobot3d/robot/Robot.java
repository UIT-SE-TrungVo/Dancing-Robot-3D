package com.seuittrv.dancingrobot3d.robot;

import android.graphics.Canvas;

import com.seuittrv.dancingrobot3d.Coordinate;
import com.seuittrv.dancingrobot3d.Cube;
import com.seuittrv.dancingrobot3d.CubeDrawer;

import java.util.ArrayList;
import java.util.List;

public class Robot extends Part implements IDraw {
    List<Part> parts = new ArrayList<>();
    double rotateY = 0;

    public Robot() {
        this.addPart(new Body());

        Leg legL = new Leg(-1, -3.5);
        this.addPart(legL);

        Leg legR = new Leg(1, -3.5);
        this.addPart(legR);


        Arm armL = new Arm(-2, 0);
        this.addPart(armL);

        Arm armR = new Arm(2, 0);
        this.addPart(armR);
    }

    private void addPart(Part part) {
        this.parts.add(part);
        List<Cube> partCubes = part.getCubes();
        for (Cube partCube : partCubes) {
            List<Cube> scanCube = this.getAllDecendant(partCube);
            for (Cube cube : scanCube) {
                this.addCube(cube);
            }
        }
    }

    private List<Cube> getAllDecendant(Cube cube) {
        List<Cube> foundCube = new ArrayList<>();
        foundCube.add(cube);
        List<Cube> children = cube.getAllChildren();
        for (Cube child : children) {
            List<Cube> foundInChild = this.getAllDecendant(child);
            foundCube.addAll(foundInChild);
        }
        return foundCube;
    }

    @Override
    public void draw(Canvas canvas) {
        List<Cube> drawCubes = new ArrayList<>();
        for (Cube cube : this.cubes) {
            drawCubes.add(this.getDrawCube(cube));
        }
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < drawCubes.size(); i++) order.add(i);
        for (int i = 0; i < order.size(); i++) {
            for (int j = i + 1; j < order.size(); j++) {
                int ic1 = order.get(i);
                int ic2 = order.get(j);
                Cube cube1 = drawCubes.get(ic1);
                Cube cube2 = drawCubes.get(ic2);
                double z1 = cube1.getZ();
                double z2 = cube2.getZ();
                if (z1 > z2) {
                    order.set(i, ic2);
                    order.set(j, ic1);
                }
            }
        }

        for (Integer index : order) {
            Cube cube = drawCubes.get(index);
//            CubeDrawer.drawOutlinedCube(cube, canvas);
            CubeDrawer.drawFilledCube(cube, canvas);
        }
    }

    @Override
    public void dance() {
//        System.out.println("Robot dance !");
        for (Part part : parts) {
            part.dance();
        }
    }

    public void rotateY(double y) {
        this.rotateY += y;
        if (this.rotateY >= 360) this.rotateY -= 360;
    }

    private Cube getDrawCube(Cube cube) {
        final double OFFSET = 300;
        final double SCALE = -50;

        Cube drawCube = cube.clone();
        List<Coordinate> drawRot = drawCube.getDrawRotation();
        List<Coordinate> drawTrans = drawCube.getDrawTransition();

        int maxDepth = Math.max(drawRot.size(), drawTrans.size());
        for (int i = 0; i < maxDepth; i++) {
            if (i < drawRot.size()) {
                drawCube.doRotate(drawRot.get(i).x, 'x');
//                drawCube.doRotate(drawRot.get(i).y, 'y');
                drawCube.doRotate(drawRot.get(i).y, 'z');
            }
            if (i < drawTrans.size()) drawCube.doTranslate(
                    drawTrans.get(i).x,
                    drawTrans.get(i).y,
                    drawTrans.get(i).z
            );
        }

        drawCube.doRotate(this.rotateY, 'y');
        drawCube.doScale(SCALE, SCALE, SCALE);
        drawCube.doTranslate(OFFSET, OFFSET, OFFSET);
        return drawCube;
    }
}
