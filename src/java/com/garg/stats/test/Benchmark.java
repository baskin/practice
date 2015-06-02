package com.garg.stats.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

import com.garg.stats.Statistics;
import com.garg.stats.impl.SimpleStatistics;
import com.garg.stats.impl.safe.AtomicStatistics;
import com.garg.stats.impl.safe.RWLockedStatistics;
import com.garg.stats.impl.safe.SynchronizedStatistics;
import com.garg.stats.impl.safe.VolatileStatistics;
import com.garg.stats.impl.safe.periodic.EventualStatistics;
import com.garg.stats.impl.safe.periodic.StripedLockedStatistics;

/**
 * Benchmark com.garg.terracota.stats.
 * <p>
 * Sample run output:
 * 
 * <pre>
 * Expected value of statistic
 *        0 ms -- stat:{0, 131071, 65506.14, 1428763904.00}
 * Type: SIMPLE wThreads: 10 rThreads: 100 rounds: 5 sampleSize: 1000000
 *       1222 ms -- stat:{0, 131071, 65532.18, 1803499392.00}
 *       1375 ms -- stat:{0, 131071, 65514.34, 1769191040.00}
 *       1253 ms -- stat:{0, 131071, 65496.80, 1620423936.00}
 *       1120 ms -- stat:{0, 131071, 65530.83, 1714668928.00}
 * Type: SYNC wThreads: 10 rThreads: 100 rounds: 5 sampleSize: 1000000
 *        839 ms -- stat:{0, 131071, 65505.56, 1428775808.00}
 *        880 ms -- stat:{0, 131071, 65505.19, 1428778240.00}
 *        840 ms -- stat:{0, 131071, 65506.08, 1428790912.00}
 *        777 ms -- stat:{0, 131071, 65505.24, 1428794240.00}
 * Type: Volatile wThreads: 10 rThreads: 100 rounds: 5 sampleSize: 1000000
 *        709 ms -- stat:{0, 131071, 65505.28, 1428785792.00}
 *        824 ms -- stat:{0, 131071, 65505.78, 1428784384.00}
 *        811 ms -- stat:{0, 131071, 65505.44, 1428785664.00}
 *        856 ms -- stat:{0, 131071, 65505.13, 1428760832.00}
 * Type: Striped wThreads: 10 rThreads: 100 rounds: 5 sampleSize: 1000000
 *        110 ms -- stat:{0, 131071, 65504.20, 1444308480.00}
 *         92 ms -- stat:{0, 131071, 65502.61, 1426041472.00}
 *        123 ms -- stat:{0, 131071, 65504.11, 1419660544.00}
 *        125 ms -- stat:{0, 131071, 65503.76, 1428644352.00}
 * Type: Eventual wThreads: 10 rThreads: 100 rounds: 5 sampleSize: 1000000
 *         80 ms -- stat:{0, 131071, 65508.25, 1387286016.00}
 *         84 ms -- stat:{0, 131071, 65508.28, 1387286016.00}
 *         63 ms -- stat:{0, 131071, 65508.23, 1387286016.00}
 *         64 ms -- stat:{0, 131071, 65508.24, 1387286016.00}
 * </pre>
 * 
 * @author bgarg
 * 
 */
public class Benchmark
{
    // Vary these over test runs.
    private static int SAMPLE_SIZE = 10000000;
    public static int WRITER_THREADS = 10;
    public static int READER_THREADS = 10;
    public static int ROUNDS = 5;

    private static int SAMPLE_SIZE_PER_WRITER = SAMPLE_SIZE / WRITER_THREADS;
    private static final int SAMPLE_UPPER_BOUND = 2 << 16;

    private static final Statistics ACTUAL_STATISTIC = new SimpleStatistics();

    /**
     * We want to use the same sample space for all stats for correctness check.
     */
    private static int[][] SAMPLES = new int[WRITER_THREADS][SAMPLE_SIZE_PER_WRITER];

    static {
        // TODOO try with biased data (and other real life data patterns)
        Random r = new Random();
        for (int j = 0; j < WRITER_THREADS; j++) {
            for (int i = 0; i < SAMPLE_SIZE_PER_WRITER; i++) {
                int value = r.nextInt(SAMPLE_UPPER_BOUND);
                SAMPLES[j][i] = value;
                ACTUAL_STATISTIC.event(value);
            }
        }
    }

    public static class Reader implements Runnable
    {
        private final Statistics stats;
        private int garbage;

        public Reader(Statistics stats)
        {
            this.stats = stats;
        }

        public int getGarbage()
        {
            return garbage;
        }

        @Override
        public void run()
        {
            int c = 0;
            while (true) {
                if (Thread.interrupted()) {
                    break;
                }

                c++;
                // TODOO random types of reads, not just min()
                int min = stats.minimum();
                
                // Do sth with the result so that jit doesn't just mess up with
                // its optimization.
                garbage += min;
                if (c % 100 == 0) {
                    LockSupport.parkNanos(100);
                }
            }
        }
    }

    public static class Writer implements Runnable
    {
        private final Statistics stats;
        private final int number;

        public Writer(Statistics stats, int number)
        {
            this.stats = stats;
            this.number = number;
        }

        @Override
        public void run()
        {
            int c = 0;
            for (int event : SAMPLES[number]) {
                if (Thread.interrupted()) {
                    break;
                }
                c++;
                stats.event(event);
                if (c % 100 == 0) {
                    LockSupport.parkNanos(10);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Expected value of statistic");
        System.out.println(pretty(ACTUAL_STATISTIC, 0));

        String[] statsType =
            new String[] { "SIMPLE", "SYNC", "Atomic", /* "RW", */"Volatile", "Striped", "Eventual" };
        for (String s : statsType) {
            System.out.println("Type: " + s + " wThreads: " + WRITER_THREADS
                + " rThreads: " + READER_THREADS + " rounds: " + ROUNDS + " sampleSize: "
                + SAMPLE_SIZE);

            for (int round = 0; round < ROUNDS; round++) {
                List<Thread> writers = new ArrayList<Thread>();
                List<Thread> readers = new ArrayList<Thread>();
                Statistics stats = getNewStatObject(s);

                long bt = System.currentTimeMillis();
                for (int j = 0; j < WRITER_THREADS; j++) {
                    Thread t = new Thread(new Writer(stats, j), "Writer" + j);
                    writers.add(t);
                    t.start();
                }
                for (int j = 0; j < READER_THREADS; j++) {
                    Thread t = new Thread(new Reader(stats), "Reader" + j);
                    readers.add(t);
                    t.start();
                }
                for (Thread w : writers) {
                    w.join();
                }
                for (Thread r : readers) {
                    r.interrupt();
                }

                System.out.println(pretty(stats, System.currentTimeMillis() - bt));
            }
        }
    }

    private static String pretty(Statistics s, long elapsed)
    {
        return String.format(
            "%10d ms -- stat:{%d, %d, %.2f, %.2f}", elapsed, s.minimum(), s.maximum(),
            s.mean(), s.variance());
    }

    private static Statistics getNewStatObject(String s)
    {
        switch (s) {
        case "SIMPLE": // Dirty (will give incorrect results -- keeping for
                       // comparison)
            return new SimpleStatistics();
        case "SYNC":
            return new SynchronizedStatistics(new SimpleStatistics());
        case "RW":
            return new RWLockedStatistics();
        case "Atomic":
            return new AtomicStatistics();
        case "Volatile":
            return new VolatileStatistics();
        case "Striped":
            return new StripedLockedStatistics(WRITER_THREADS);
        case "Eventual":
            return new EventualStatistics();
        }

        return null;
    }
}
