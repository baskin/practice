
package com.garg.projects.flightsimulator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * FlightSimulator.
 * @author gargb
 *
 */
public final class FlightSimulator
{
    public static void main(String... arguments)
    {
        System.out.println("Running Flight Simulator.\n");

        // Start the airports!.
        for (Airport airport : Constants.INDIAN_AIRPORTS) {
            airport.start();
        }

        TimerTask goFlyNdHyd =
            new TimerTask() {
                @Override
                public void run()
                {
                    Airplane airForceOne =
                        new Airplane(Constants.ND_HYD_1,
                                     "Flight 2345SU");
                    airForceOne.start();
                }
            };
        Timer scheduler = new Timer("Bhupi's ND-HYD Flight Scheduler", true);
        scheduler.schedule(goFlyNdHyd, 0, 60000);

        TimerTask goFlyHydChd =
            new TimerTask() {
                @Override
                public void run()
                {
                    Airplane airForceOne =
                        new Airplane(Constants.HYD_CHD_1,
                                     "Flight 5590DLX");
                    airForceOne.start();
                }
            };
        Timer scheduler2 = new Timer("Bhupi's HYD-CHD Flight Scheduler", true);
        scheduler2.schedule(goFlyHydChd, 10000, 80000);

        System.out.println("Terminating the original user thread..");
    }
}
