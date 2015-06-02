/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/Test.java $
 */

/*
 * Copyright (c) 2010 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.interview.google;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class MuseumFloor
{
    public static void main(String[] args)
    {
        char floor[][] = { 
            {'.', '#', '.', 'G', '.'},  
            {'.', '.', '#', '.', '.'},  
            {'G', '.', '.', '.', '.'},  
            {'.', '.', '#', '.', '.'},  
        };
        
        System.out.println("Floor plan");
        for (int r = 0; r < floor.length; r++) {
            for (int c = 0; c < floor[0].length; c++) {
                System.out.print(floor[r][c]);
            }
            System.out.println();
        }
        
        for (int r = 0; r < floor.length; r++) {
            for (int c = 0; c < floor[0].length; c++) {
                if (floor[r][c] == 'G') {
                    bfsVisit(floor, new Point(r, c));
                }
            }
        }
        
        System.out.println("Floor markings after inspection");
        for (int r = 0; r < floor.length; r++) {
            for (int c = 0; c < floor[0].length; c++) {
                System.out.print(floor[r][c]);
            }
            System.out.println();
        }
    }

    /**
     * 
     * CareerCup http://www.careercup.com/question?id=5630807089610752
     * 
     * Imagine a museum floor that looks like this:
     * 
     * .#.G. 
     * ..#.. 
     * G.... 
     * ..#..
     * 
     * G == Museum Guard 
     * # == obstruction
     * . == empty space
     * 
     * Write a piece of code that will find the nearest guard for each open
     * floor space. Diagonal moves are not allowed. The output should convey
     * this information:
     * 
     * 2#1G1 
     * 12#12 
     * G1223 
     * 12#34
     * 
     * You may choose how you want to receive the input and output. For example,
     * you may use a 2-d array, as depicted here, or you may use a list of
     * points with features, if you deem that easier to work with, as long as
     * the same information is conveyed.
     */
    public static void bfsVisit(final char[][] floor, final Point startPoint)
    {
        if (floor == null || floor.length == 0) {
            return;
        }
        Queue<Point> q = new LinkedList<Point>();
        q.add(startPoint);
        Set<Point> visited = new HashSet<Point>();
        while (!q.isEmpty()) {
            Point current = q.remove();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            char currentMark = floor[current.x][current.y];
            int level = currentMark == 'G' ? 0 : currentMark - '0';
            level++;
            for (Point p : adjacent(floor, current)) {
                if (floor[p.x][p.y] != 'G' && floor[p.x][p.y] != '#') {
                    int minLevel = level;
                    if (floor[p.x][p.y] != '.') { // already marked by previous bfs
                        minLevel = Math.min(floor[p.x][p.y] - '0', level);
                    }
                    char levelChar = (char)('0' + minLevel);
                    floor[p.x][p.y] = levelChar;
                    q.add(p);
                }
            }
        }
    }
    
    private static List<Point> adjacent(final char[][] floor, final Point p) {
        List<Point> result = new ArrayList<Point>();
        if (p.x - 1 >= 0) { // top
            result.add(new Point(p.x - 1, p.y));
        }
        if (p.x + 1 < floor.length) { // bottom
            result.add(new Point(p.x + 1, p.y));
        }
        if (p.y - 1 >= 0) { // left
            result.add(new Point(p.x, p.y - 1));
        }
        if (p.y + 1 < floor[0].length) { // right
            result.add(new Point(p.x, p.y + 1));
        }
        return result;
    }
}
