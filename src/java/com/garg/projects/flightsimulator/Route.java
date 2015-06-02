package com.garg.projects.flightsimulator;

import java.io.DataInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Air-Route.
 * @author gargb
 *
 */
public class Route implements Serializable 
{
	private final List<Region> myRouteRegions;
	
	public Route(RegionWithAirport start, RegionWithAirport end)
	{
		myRouteRegions = new ArrayList<Region>();
		myRouteRegions.add(start);
		myRouteRegions.add(end);
	}
	
	public RegionWithAirport getSourceRegion()
	{
		return (RegionWithAirport) myRouteRegions.get(0);
	}
	
	public RegionWithAirport getDestinationRegion()
	{
		return (RegionWithAirport) myRouteRegions.get(myRouteRegions.size() - 1);
	}
	
	public List<Region> getRouteRegions()
	{
		return myRouteRegions;
	}
	
	public Region getRegion(int i)
	{
		return myRouteRegions.get(i);
	}
	
	public void deserialize(DataInputStream in)
	{
		
	}
	
	public Route reverse()
	{
		return new Route(getDestinationRegion(), getSourceRegion());
	}
	
	
}
