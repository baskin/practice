package com.garg.prep.wu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * You are given N ranges of date offsets when N employees are present in an
 * organization. Something like 1-4 (i.e. employee will come on 1st, 2nd, 3rd
 * and 4th day ) 2-6 8-9 .. 1-14 You have to organize an event on minimum number
 * of days such that each employee can attend the event at least twice. Any idea
 * how to do this ?
 * <p>
 * <a href=
 * "http://www.ocf.berkeley.edu/~wwu/cgi-bin/yabb/YaBB.cgi?board=riddles_cs;action=display;num=1302206054"
 * >link</a>
 */
public class Overlapping
{
    public static class Range implements Comparable {
        int start;
        int end;
        
        Range(int s, int e) {
            // spans at least 2 days
            assert e - s >= 1;
            start = s;
            end = e;
        }
        
        /**
         * Returns true if the given day falls in the event duration.
         */
        boolean includes(int i) {
            return i >= start && i <= end;
        }
        
        static List<Range> createEvents(int []starts, int []ends) {
            List<Range> list = new ArrayList<Overlapping.Range>();
            for (int i = 0; i < starts.length; i++) {
                list.add(new Range(starts[i], ends[i]));
            }
            return list;
        }

        @Override
        public int compareTo(Object o)
        {
            Range e = (Range) o;
            return end > e.end ? 1 : (end < e.end ? -1 : 0);  
        }
    }
    
    /**
     * (You can ignore all employees such that their range covers completely
     * another employee's range. We won't directly trim the input using this
     * array though).
     * <p>
     * Begin by ordering the employees by end date. 
     * Remember the dates of the last 2 event days and
     * scan the employees in order:
     * <ul>
     * <li>if present on the last 2 event days, he's done.
     * <li>if present on the last event day only, add a new event day on the
     * last day of that employee (and you can forget about the oldest event
     * day).
     * <li>if not present on either event day, add 2 event days on the last
     * 2 days of the employee (and forget the previous event days).
     * </ul>
     * 
     * PS: One additional test is that if you need to add a day, it might be the
     * next to last, if the last day is already scheduled as an event day.
     * 
     * For example, if employees are present on: 1-2, 2-5, 3-5 For the 1st
     * employee we schedule days 1,2, for the 2nd employee we add day 5, for the
     * 3rd employee, we need to add one event day, but day 5, the last days, is
     * used already.
     * 
     * So, whenever we add an event day, we need to schedule it on the last
     * event-free day where the employee is there, not the last day.
     * 
     * Once you have sorted the employee by end date, you just take each one in
     * turn and add 1 or 2 days at the end as necessary. You only need to
     * compare an employee's range with the last 2 days in the schedule so far.
     * 
     * The sorting is O(n log n), the processing is O(n).
     * 
     * s1,e1   s2,e2   (e1 < e2, sorted by end times)
     * 
     * 
     */
    public static void main(String[] args)
    {
        int []starts = new int[] {1,8,7,9,1,2};
        int []ends   = new int[] {4,9,12,14,14,6};
        // Range emp(i) = {start[i] - end[i]}
        List<Range> empRanges = Range.createEvents(starts, ends);
        // Sorted by end times
        // 1,4   2,6   8,9   7,12   9,14   1,14 
        Collections.sort(empRanges);
        
        List<Integer> events = new ArrayList<Integer>();
        events.add(-1);
        events.add(-1);
        // scan over each employee
        for (Range e : empRanges) {
        	int lastEvent = events.get(events.size() - 1);
        	int secondLastEvent = events.get(events.size() - 2);
        	if (!e.includes(lastEvent) && !e.includes(secondLastEvent)) {
        		events.add(e.end - 1);
        		events.add(e.end);
        	}
        	else if (!e.includes(secondLastEvent)) {
        		events.add(e.end == secondLastEvent ? e.end - 1 : e.end);
        	}
        	else if (!e.includes(lastEvent)) {
        		events.add(e.end == lastEvent ? e.end - 1 : e.end);
        	}
        }
        
        for (int i = 2; i < events.size(); i++) {
        	System.out.print(events.get(i) + " ");
        }
    }
}
