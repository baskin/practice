/*
 * Constants.java
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
import java.util.ArrayList;
import java.util.List;

public class Constants {
	public static final Airport INDIRA_GANDHI_INTERNATIONAL = new Airport(
			"Indira Gandhi International, New Delhi", 500, 500);
	public static final Airport RAJIV_GANDHI_INTERNATIONAL = new Airport(
			"Rajiv Gandhi International, Hyderabad", 900, 4000);
	public static final Airport CHANDIGARH_DOMESTIC_AIRPASS = new Airport(
			"Chandigarh Domestic Airpass, Chandigarh", 200, 100);

	public static final List<Airport> INDIAN_AIRPORTS = new ArrayList<Airport>();

	public static final RegionWithAirport DECCAN_REGION = new RegionWithAirport(
			new ArrayList<Point>(), RAJIV_GANDHI_INTERNATIONAL);
	public static final RegionWithAirport NCR_REGION = new RegionWithAirport(
			new ArrayList<Point>(), INDIRA_GANDHI_INTERNATIONAL);
	public static final RegionWithAirport PUNJAB_REGION = new RegionWithAirport(
			new ArrayList<Point>(), CHANDIGARH_DOMESTIC_AIRPASS);

	public static final Route HYD_ND_1 = new Route(DECCAN_REGION, NCR_REGION);
	public static final Route ND_HYD_1 = HYD_ND_1.reverse();
	public static final Route HYD_CHD_1 = new Route(DECCAN_REGION, PUNJAB_REGION);
	public static final Route CHD_HYD_1 = HYD_CHD_1.reverse();

	public static enum WeatherCondition {
		NORMAL(1), ROUGH(1.2), TOUGH(1.5), MAELSTORM(2.2);

		private final double myDelayFactor;

		private WeatherCondition(double delayFactor) {
			myDelayFactor = delayFactor;
		}

		public double getDelayFactor() {
			return myDelayFactor;
		}
	}

	static {
		INDIAN_AIRPORTS.add(INDIRA_GANDHI_INTERNATIONAL);
		INDIAN_AIRPORTS.add(RAJIV_GANDHI_INTERNATIONAL);
		INDIAN_AIRPORTS.add(CHANDIGARH_DOMESTIC_AIRPASS);
	}
}
