
package com.garg.projects.flightsimulator;

import java.util.ArrayList;
import java.util.List;

/**
 * The ONE.
 * Has to be a singleton.
 *
 * @author Bhupinder Garg (gargb)
 * @version $Id: AllMighty.java 4 2011-02-18 10:00:02Z bhupi.iit@gmail.com $
 */
public class AllMighty
{
    static {
        // Read/create regions.
    }
    
    public static List<Region> getRegions()
    {
        // HE's harsh. Could have easily returned defensive copies.
        // Likes playing around with us.
        return new ArrayList<Region>(); 
    }
}
