/*
* AllMighty.java
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
