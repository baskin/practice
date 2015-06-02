/*
 * Airport.java
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

import java.awt.Point;
import java.util.logging.Logger;

/**
 * Airport.
 * 
 * @author gargb
 *
 */
public class Airport extends Thread
{
    private static final Logger LOG = Logger.getLogger("garg.flightsimulator");
    
    private final String myName;
    private final Point myLocation;
    private final int myNumberOfRunways;
    
    private final Atc myAtc;

    // Be safe. Close runaway by default.
    private volatile boolean myIsRunwayAvailable = false;

    public Airport(String name, int x, int y)
    {
        myName = name;
        myLocation = new Point(x, y);
        myNumberOfRunways = 1;
        setName(name);
        myAtc = new Atc();
    }

    public boolean isRunwayAvailable()
    {
        return myIsRunwayAvailable;
    }

    public String getAirportName()
    {
        return myName;
    }

    Point getLocation()
    {
        return myLocation;
    }

    @Override
    public void run()
    {
        while (true) {
            synchronized (this) {
                myIsRunwayAvailable = ! myIsRunwayAvailable;
                LOG.fine(myName + ": Runway available? "
                         + myIsRunwayAvailable);

                // Notify all waiters of the change of state.
                notifyAll();
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                LOG.severe(e.getMessage());
            }
        }
    }

}
