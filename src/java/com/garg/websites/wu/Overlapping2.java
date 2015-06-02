package com.garg.websites.wu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Finding all overlapping events. There are number of events each having ...
 * startTime, endTime and flag as hasConflict. Events have conflict if they
 * overlap. Initially all events have hasConflict as false, you have to write a
 * method to turn this flag as true for events which overlap. Also, what will be
 * the complexity if given events are already sorted from say, start Time or
 * EndTime.
 */
public class Overlapping2
{
	static class Event
	{
		int start;
		int end;
		boolean hasConflict;

		Event(int s, int e)
		{
			// spans at least 2 days
			assert e - s >= 1;
			start = s;
			end = e;
			hasConflict = false;
		}

		@Override
		public String toString()
		{
			return String.format("{%d,%d}", start, end);
		}

		/**
		 * Returns true if the given day falls in the event duration.
		 */
		boolean includes(int i)
		{
			return i >= start && i <= end;
		}

		static List<Event> createEvents(int[] starts, int[] ends)
		{
			List<Event> list = new ArrayList<Event>();
			for (int i = 0; i < starts.length; i++) {
				list.add(new Event(starts[i], ends[i]));
			}
			return list;
		}
	}

	public static void main(String[] args)
	{
		int[] starts = new int[] { 1, 8, 7, 9, 1, 2,  16, 21 };
		int[] ends = new int[] { 4, 9, 12, 14, 14, 6, 20, 21,};
		// event(i): {start[i] - end[i]}
		List<Event> events = Event.createEvents(starts, ends);
		// Sorted by start times
		// 1,4 1,14 2,6 7,12 8,9 9,14
		Collections.sort(events, new Comparator<Event>()
		{
			@Override
			public int compare(Event o1, Event o2)
			{
				return o1.start > o2.start ? 1 : (o1.start < o2.start ? -1 : 0);
			}
		});

		// Count of max events that are active simultaneously.
		int count = 0;
		// A Min heap ordered by end times of the event.
		// This will contain only the active events at any given time.
		PriorityQueue<Event> activeEvents = new PriorityQueue<Event>(3,
				new Comparator<Event>()
				{
					@Override
					public int compare(Event o1, Event o2)
					{
						return o1.end > o2.end ? 1 : (o1.end < o2.end ? -1 : 0);
					}
				});

		// Sweep across the events.
		for (Event e : events) {
			// Time: e.start
			// Remove events which may have ended by now (e.start).
			// <=  imp.
			while (!activeEvents.isEmpty() && activeEvents.peek().end <= e.start) {
				activeEvents.poll();
				count--;
			}
			activeEvents.add(e);
			count++;
			if (activeEvents.size() > 1) {
				// more than 1 element in heap. Mark all as conflicting.
				Iterator<Event> itr = activeEvents.iterator();
				while (itr.hasNext()) {
					itr.next().hasConflict = true;
				}
			}
			System.out.println(String.format("%7s -> %2d  active: %s", e,
					count, activeEvents));
		}

		System.out.println();
		for (Event e : events) {
			System.out.println(e + " -> " + e.hasConflict);
			// reset hasconflict for later use.
			e.hasConflict = false;
		}
		System.out.println();
		
		
		// If problem is just to mark hasConflict:
		// and if the events are already sorted by start time, you can
		// just keep track of the previous event with last end time and get an
		// O(n) algorithm. Using a heap is unnecessary unless you want to know
		// which events overlap (and in that case it'd be O(n2) in the
		// worst-case anyway).
		
		Event eventWithLatestEndEtime = null;
		for (Event e : events) {
			if (eventWithLatestEndEtime != null && eventWithLatestEndEtime.end > e.start) {
				e.hasConflict = true;
				eventWithLatestEndEtime.hasConflict = true;
			}
			if (eventWithLatestEndEtime == null || e.end > eventWithLatestEndEtime.end) {
				eventWithLatestEndEtime = e;
			}
		}
		for (Event e : events) {
			System.out.println(e + " -> " + e.hasConflict);
			// reset hasconflict for later use.
			e.hasConflict = false;
		}
		
		// if the problem was just to find the max not of active events at any time:
		// Sort all times, irrespective of whether it is start time or end time.
		// start iterating: for every start time add 1, for every end time subtract 1.
		// keep a max count.
	}

}
