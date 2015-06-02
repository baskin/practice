/*
* Nature.java
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

package com.garg.projects.flightsimulator;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A class which controls all the natural phenomenon which may affect an air
 * flight.
 *
 * @author Bhupinder Garg (gargb)
 * @version $Id: Nature.java 4 2011-02-18 10:00:02Z bhupi.iit@gmail.com $
 */
public class Nature extends Thread
{    
    private static final Logger LOG = Logger.getLogger("garg.flightsimulator");
    
    /**
     * 
     * @param regions the {@link Region}s under the control of 
     */
    public Nature()
    {
        super("Nature");
        setDaemon(true);
    }
    
    @Override
    public void run()
    {
        while(true) {
            Random random = new Random();
            // Nobody knows when she wakes up to unleash her fury.
            try {
                sleep(random.nextInt());
            }
            catch (InterruptedException e) {
                // Humans really don't care about her!
                // One of the reasons why we're in deep shit.
                // May want to revisit our attitude.
                LOG.fine(e.getMessage());
            }
            
            // Chooses a few random regions to start with.
            
            
            // If say nature decides to start a maelstorm (at present at a 
            // random place, should ideally be from coastal region) it will 
            // propagate it inland for some time growing and getting intense
            // (in terms of visibility, wind etc) before it starts subsiding.
        }
    }
    
    private List<Region> getRandomRegions()
    {
        List<Region> allRegions = AllMighty.getRegions();
		return allRegions;
        
    }
}
