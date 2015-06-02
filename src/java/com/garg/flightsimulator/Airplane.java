/*
 * Airplane.java
 *
 * $Header$
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

import java.util.logging.Logger;

/**
 * Airplane.
 * @author gargb
 *
 */
public class Airplane extends Thread
{
    private static final Logger LOG = Logger.getLogger("garg.flightsimulator");

    private Route myRoute;
    private Airport mySourceAirport;
    private Airport myDestinationAirport;
    private String myFlightId;

    private long myExpectedFlightTime;
    private long myTakeOffTime;
    private long myLandingTime;

    public Airplane(Route route, String flightId)
    {
        init(route, flightId);
    }

    public void init(Route route, String flightId)
    {
        if (isAlive()) {
            LOG.severe(
                "Trying to divert the airplane during an ongoing flight! " + 
                "Either ur crazy or ur a terrorist!!");
            hijackAlert();
            return;
        }
        
        myRoute = route;
        mySourceAirport = route.getSourceRegion().getAirort();
        myDestinationAirport = route.getDestinationRegion().getAirort();
        myFlightId = flightId;
        setName(flightId);
    }

    private void hijackAlert() 
    {
    	// Gosh! Don't do this the American way.
	}

	@Override
    public void run()
    {
        takeoff();
        fly();
        land();
        dumpStats();
    }

    private void dumpStats()
    {
        String summary =
            "\n========================"
            + "\nFlight Summary.\n" + myFlightId + " ("
            + mySourceAirport.getAirportName()
            + " -> " + myDestinationAirport.getAirportName() + ")"
            + "\nEFT: " + myExpectedFlightTime
            + "\nAFT: " + (myLandingTime - myTakeOffTime)
            + "\n========================";
        LOG.info(summary);
    }

    private void takeoff()
    {
        myTakeOffTime = System.currentTimeMillis();

        synchronized (mySourceAirport) {
            while (! mySourceAirport.isRunwayAvailable()) {
                LOG.info(myFlightId
                         + ": Waiting for takeoff on runaway of "
                         + mySourceAirport.getAirportName());

                try {
                    mySourceAirport.wait();
                }
                catch (InterruptedException e) {
                    LOG.severe(e.getMessage());
                }
            }

            LOG.info(myFlightId + ": Taking off now from "
                     + mySourceAirport.getAirportName());
        }
    }

    private void fly()
    {
        LOG.info(myFlightId + ": Flying now ...");

        try {
        	long flightTime = 0;
        	for (int i = 0; i < myRoute.getRouteRegions().size() - 1; i++) {
        		flightTime += Utils.getFlightTime(myRoute.getRegion(i), myRoute.getRegion(i + 1), true);
        		myExpectedFlightTime += Utils.getFlightTime(myRoute.getRegion(i), myRoute.getRegion(i + 1), false);
        	}
            Thread.sleep(flightTime);
        }
        catch (InterruptedException e) {
            LOG.severe(e.getMessage());
        }
    }

    private void land()
    {
        synchronized (myDestinationAirport) {
            while (! myDestinationAirport.isRunwayAvailable()) {
                LOG.info(myFlightId
                         + ": Waiting for landing on runway of "
                         + myDestinationAirport.getAirportName());

                try {
                    myDestinationAirport.wait();
                }
                catch (InterruptedException e) {
                    LOG.severe(e.getMessage());
                }
            }

            LOG.info(myFlightId + ": Landing now on "
                     + myDestinationAirport.getAirportName());
        }

        myLandingTime = System.currentTimeMillis();
    }

}
