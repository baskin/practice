
package com.garg.projects.flightsimulator;

import java.awt.Point;

public class Utils
{
	public static long getFlightTime(Point point1, Point point2)
    {
        return (long) point1.distance(point2);
    }
	
    public static long getFlightTime(Region r1, Region r2, boolean weatherEffect)
    {
    	long time = getFlightTime(r1.getCheckPoint(), r2.getCheckPoint());
    	if (weatherEffect) {
    		time *= r1.getWeather().getDelayFactor() * r2.getWeather().getDelayFactor();
    	}
    	return time;
    }
}
