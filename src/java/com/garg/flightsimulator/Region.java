package com.garg.flightsimulator;

import com.garg.flightsimulator.Constants.WeatherCondition;

import java.awt.Point;
import java.util.List;

/**
 * Region. Maybe a state.
 * @author gargb
 *
 */
public class Region 
{
	private final List<Point> myRegion;
	private final Point myCheckPoint;
	private WeatherCondition myWeather = WeatherCondition.NORMAL;
	
	public Region(List<Point> region, Point checkPoint)
	{
		myRegion = region;
		myCheckPoint = checkPoint;
	}
	
	public final List<Point> getRegion()
	{
		return myRegion;
	}
	
	public final Point getCheckPoint()
	{
		return myCheckPoint;
	}
	
	public final synchronized WeatherCondition getWeather()
	{
		return myWeather;
	}
	
	public final synchronized void setWeather(WeatherCondition weather)
	{
		myWeather = weather;
	}

}
