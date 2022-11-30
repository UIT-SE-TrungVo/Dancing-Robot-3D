package com.seuittrv.dancingrobot3d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.HashMap;

public class CubeDrawer {
    static HashMap<Integer, Paint> mapPaint = new HashMap<>();

    private static void drawLinePair(Canvas canvas, Coordinate[] vertices, int start, int end, Paint paint) {//draw a line connecting 2 points
        canvas.drawLine((int) vertices[start].x, (int) vertices[start].y, (int) vertices[end].x, (int) vertices[end].y, paint);
    }

    public static void drawOutlinedCube(Cube cube, Canvas canvas) {
        Paint paint = getPaint(cube.getColor());
        Coordinate[] coordinates = cube.getCoordinates();
        drawLinePair(canvas, coordinates, 0, 1, paint);
        drawLinePair(canvas, coordinates, 1, 3, paint);
        drawLinePair(canvas, coordinates, 3, 2, paint);
        drawLinePair(canvas, coordinates, 2, 0, paint);
        drawLinePair(canvas, coordinates, 4, 5, paint);
        drawLinePair(canvas, coordinates, 5, 7, paint);
        drawLinePair(canvas, coordinates, 7, 6, paint);
        drawLinePair(canvas, coordinates, 6, 4, paint);
        drawLinePair(canvas, coordinates, 0, 4, paint);
        drawLinePair(canvas, coordinates, 1, 5, paint);
        drawLinePair(canvas, coordinates, 2, 6, paint);
        drawLinePair(canvas, coordinates, 3, 7, paint);
    }

    public static void drawFilledCube(Cube cube, Canvas canvas) {
        Paint paint = getPaint(cube.getColor());
        Coordinate[] coordinates = cube.getCoordinates();

        Path path;
        boolean firstDraw;

        for (int[] sideIndex : Cube.SIDE_INDEXES) {
            path = new Path();
            firstDraw = true;
            for (int index : sideIndex) {
                Coordinate point = coordinates[index];
                if (firstDraw) path.moveTo((int) point.x, (int) point.y);
                else path.lineTo((int) point.x, (int) point.y);
                firstDraw = false;
            }
            canvas.drawPath(path, paint);
        }
    }

    public static Paint getPaint(int color) {
        if (!mapPaint.containsKey(color)) {
            Paint newPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            newPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            newPaint.setColor(color);
            newPaint.setStrokeWidth(5);
            mapPaint.put(color, newPaint);
        }
        return mapPaint.get(color);
    }

    public static void clearAllPaints() {
        mapPaint.clear();
    }
}
