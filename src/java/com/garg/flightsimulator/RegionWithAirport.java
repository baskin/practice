package com.garg.flightsimulator;

import java.awt.Point;
import java.util.List;

public class RegionWithAirport extends Region
{
	private final Airport myAirport;
	public RegionWithAirport(List<Point> region, Airport airport) 
	{
		super(region, airport.getLocation());
		myAirport = airport;
	}


	public Airport getAirort() 
	{
		return myAirport;
	}

}
