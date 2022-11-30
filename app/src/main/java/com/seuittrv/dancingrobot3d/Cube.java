package com.seuittrv.dancingrobot3d;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    public final static int[][] SIDE_INDEXES = {
            {0, 1, 3, 2}
            , {4, 5, 7, 6}
            , {0, 1, 5, 4}
            , {2, 3, 7, 6}
            , {0, 2, 6, 4}
            , {1, 3, 7, 5}
    };

    Coordinate[] coordinates;
    Coordinate position = new Coordinate();
    Coordinate rotation = new Coordinate();

    List<Coordinate> drawTransition = new ArrayList<>();
    List<Coordinate> drawRotation = new ArrayList<>();

    List<Cube> children = new ArrayList<>();
    int color;

    public Cube() {
    }

    public Cube(double sizeX, double sizeY, double sizeZ, double anchorX, double anchorY, double anchorZ, int color) {
        double lx, rx, ly, ry, lz, rz;
        lx = -sizeX * anchorX * 0.95;
        rx = sizeX * (1 - anchorX) * 0.95;
        ly = -sizeY * anchorY * 0.95;
        ry = sizeY * (1 - anchorY) * 0.95;
        lz = -sizeZ * anchorZ * 0.95;
        rz = sizeZ * (1 - anchorZ) * 0.95;

        this.coordinates = new Coordinate[]{
                new Coordinate(lx, ly, lz, 1)
                , new Coordinate(lx, ly, rz, 1)
                , new Coordinate(lx, ry, lz, 1)
                , new Coordinate(lx, ry, rz, 1)
                , new Coordinate(rx, ly, lz, 1)
                , new Coordinate(rx, ly, rz, 1)
                , new Coordinate(rx, ry, lz, 1)
                , new Coordinate(rx, ry, rz, 1)
        };
        this.color = color;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinate(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinate[] translate(double tx, double ty, double tz) {
        Coordinate[] vertices = this.getCoordinates();
        double[] matrix = Matrix.getIdentityMatrix();
        matrix[3] = tx;
        matrix[7] = ty;
        matrix[11] = tz;
        return transformation(vertices, matrix);
    }

    public Coordinate[] scale(double sx, double sy, double sz) {
        Coordinate[] vertices = this.getCoordinates();
        double[] matrix = Matrix.getIdentityMatrix();
        matrix[0] = sx;
        matrix[5] = sy;
        matrix[10] = sz;
        return transformation(vertices, matrix);
    }

    public Coordinate[] rotate(double deg, char axis) {
        Coordinate[] vertices = this.getCoordinates();
        if (deg == 0 || (axis != 'z' && axis != 'y' && axis != 'x')) return vertices;
        double rad = Math.PI * deg / 180;
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        double[] matrix = Matrix.getIdentityMatrix();
        switch (axis) {
            case 'x':
                matrix[5] = cos;
                matrix[6] = -sin;
                matrix[9] = sin;
                matrix[10] = cos;
                break;
            case 'y':
                matrix[0] = cos;
                matrix[2] = sin;
                matrix[8] = -sin;
                matrix[10] = cos;
                break;
            case 'z':
                matrix[0] = cos;
                matrix[1] = -sin;
                matrix[4] = sin;
                matrix[5] = cos;
                break;
            default:
                return vertices;
        }
        return this.transformation(vertices, matrix);
    }

    public Coordinate transformation(Coordinate vertex, double[] matrix) {
        Coordinate result = new Coordinate();
        result.x = matrix[0] * vertex.x + matrix[1] * vertex.y + matrix[2] * vertex.z + matrix[3];
        result.y = matrix[4] * vertex.x + matrix[5] * vertex.y + matrix[6] * vertex.z + matrix[7];
        result.z = matrix[8] * vertex.x + matrix[9] * vertex.y + matrix[10] * vertex.z + matrix[11];
        result.w = matrix[12] * vertex.x + matrix[13] * vertex.y + matrix[14] * vertex.z + matrix[15];
        return result;
    }

    public Coordinate[] transformation(Coordinate[] vertices, double[] matrix) {
        Coordinate[] result = new Coordinate[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            result[i] = transformation(vertices[i], matrix);
            result[i].Normalise();
        }
        return result;
    }

    public Cube clone() {
        Cube newCube = new Cube();
        newCube.setCoordinate(this.getCoordinates());
        newCube.setColor(this.getColor());
        newCube.setDrawTransition(this.getDrawTransition());
        newCube.setDrawRotation(this.getDrawRotation());

        for (Cube child : this.children) {
            Cube cloneChild = child.clone();
            newCube.addChild(cloneChild);
        }
        return newCube;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }

    public double getZ() {
        Coordinate[] coordinates = this.getCoordinates();
        int numCoordinate = coordinates.length;
        double z = 0;
        for (Coordinate coordinate : coordinates) {
            z += coordinate.z;
        }
        return z / numCoordinate;
    }

    public void addChild(Cube child) {
        this.children.add(child);
    }

    public List<Cube> getAllChildren() {
        return this.children;
    }

    public void doTranslate(double tx, double ty, double tz) {
//        for (Cube child : this.children) {
//            child.doTranslate(tx, ty, tz);
//        }
        this.setCoordinate(this.translate(tx, ty, tz));
        this.position.x = this.position.x + tx;
        this.position.y = this.position.y + ty;
        this.position.z = this.position.z + tz;
    }

    public void doScale(double sx, double sy, double sz) {
        for (Cube child : this.children) {
            child.doScale(sx, sy, sz);
        }
        this.setCoordinate(this.scale(sx, sy, sz));
    }

    public void doRotate(double deg, char axis) {
        this.setCoordinate(this.rotate(deg, axis));
        if (axis == 'x') this.rotation.x += deg;
        if (axis == 'y') this.rotation.y += deg;
        if (axis == 'z') this.rotation.z += deg;

//        for (Cube child : this.children) {
//            child.doRotate(deg, axis);
//        }
    }

    public void doRotateSelf(double deg, char axis) {
        Coordinate oriPosition = this.position.clone();
        Coordinate oriRotation = this.rotation.clone();

        if (axis != 'y') this.doRotate(-oriRotation.y, 'y');
        if (axis != 'z') this.doRotate(-oriRotation.z, 'z');
        if (axis != 'x') this.doRotate(-oriRotation.x, 'x');
        this.doTranslate(-oriPosition.x, -oriPosition.y, -oriPosition.z);

        this.doRotate(deg, axis);

        this.doTranslate(oriPosition.x, oriPosition.y, oriPosition.z);
        if (axis != 'x') this.doRotate(oriRotation.x, 'x');
        if (axis != 'z') this.doRotate(oriRotation.z, 'z');
        if (axis != 'y') this.doRotate(oriRotation.y, 'y');
    }

    public List<Coordinate> getDrawTransition() {
        return this.drawTransition;
    }

    public void setDrawTransition(List<Coordinate> drawTrans) {
        this.drawTransition.clear();
        this.drawTransition.addAll(drawTrans);
    }

    public void addDrawTransition(double x, double y, double z, int depth) {
        while (this.drawTransition.size() <= depth) this.drawTransition.add(new Coordinate());
        this.drawTransition.get(depth).x += x;
        this.drawTransition.get(depth).y += y;
        this.drawTransition.get(depth).z += z;

        for (Cube child : this.children) {
            child.addDrawTransition(x, y, z, depth + 1);
        }
    }

    public List<Coordinate> getDrawRotation() {
        return this.drawRotation;
    }

    public void setDrawRotation(List<Coordinate> drawRot) {
        this.drawRotation.clear();
        this.drawRotation.addAll(drawRot);
    }

    public void addDrawRotation(double deg, char axis, int depth) {
        while (this.drawRotation.size() <= depth) this.drawRotation.add(new Coordinate());
        this.drawRotation.get(depth).x += axis == 'x' ? deg : 0;
        this.drawRotation.get(depth).y += axis == 'y' ? deg : 0;
        this.drawRotation.get(depth).z += axis == 'z' ? deg : 0;

        for (Cube child : this.children) {
            child.addDrawRotation(deg, axis, depth + 1);
        }
    }
}
