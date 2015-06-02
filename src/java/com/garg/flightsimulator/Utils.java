/*
 * FlightSimulatorUtils.java
 *
 * $Header: /proj/infra/repository/desmake/etc/deshaw_jalopy.xml,v 1.4 2007/09/12 10:24:53 lalam Exp $
 */

/*
 * Copyright (c) 2008 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.flightsimulator;

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
