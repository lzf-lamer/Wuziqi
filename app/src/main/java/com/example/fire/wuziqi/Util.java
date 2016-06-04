package com.example.fire.wuziqi;

import android.graphics.Point;

import java.util.List;

/**
 * Created by Lenovo on 2016/6/4.
 */
public class Util
{
    public static int MAX_COUNT_IN_LINE = 5;
    public static boolean checkFiveInLine(List<Point> points) {
        for(Point p:points)
        {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizontal(x,y,points);
            if(win) return true;
            win = checkVertical(x,y,points);
            if(win) return true;
            win = checkLeftDiagonal(x,y,points);
            if(win) return true;
            win = checkRightDiagonal(x,y,points);
            if(win) return true;
        }
        return false;
    }

    public static boolean checkHorizontal(int x, int y,List<Point> points) {
        int count = 1;
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x-i,y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x+i,y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    public static boolean checkVertical(int x, int y,List<Point> points) {
        int count = 1;
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    public static boolean checkLeftDiagonal(int x, int y,List<Point> points) {
        int count = 1;
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x+i,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x-i,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    public static boolean checkRightDiagonal(int x, int y,List<Point> points) {
        int count = 1;
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x-i,y-i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        for(int i=1;i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x+i,y+i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
}
