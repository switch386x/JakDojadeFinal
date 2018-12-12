package jakDojade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */

public class JakDojade {

    ArrayList<Lane> laneList = new ArrayList<>();

    // protected - enables accessing from within a package
    // creation of Lines available for handling
    protected ArrayList<Lane> showLaneInformation(int lane) {
        laneList.stream().filter(e -> e.getLaneNumber() == lane).collect(Collectors.toList()).forEach(Lane::modifiedPrint);
        return laneList;
    }

    protected Stop getStopWithMaxNumberOfLanes() {
        Map<Stop, Integer> stopCounter = new HashMap<>();
        for (Lane lane : laneList) {
            for (Stop stop : lane.getHandledStops()) {
                if (stopCounter.get(stop) != null) {
                    int c = stopCounter.remove(stop);
                    stopCounter.put(stop, c + 1);
                }
                else {
                    stopCounter.put(stop, 1);
                }
            }
        }
        Stop s = stopCounter.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        System.out.println(s);
        return s;
    }

    protected void addLane(Lane lane) {
        laneList.add(lane);
    }

    protected Time getTimeOfTransitBetweenStops(Lane lane, Stop stop1, Stop stop2) {
        int i = lane.getHandledStops().indexOf(stop1);
        int j = lane.getHandledStops().indexOf(stop2);
        Time time = lane.getTimesOfTransit().get(j).getTimeDifference(lane.getTimesOfTransit().get(i));
        System.out.println(time); // syso ctrl space
        return time;
    }

    protected String getMinAndMaxTransitTimeLane() {
        Lane laneMax = laneList.stream().max(Comparator.comparing(Lane::getSumOfTransitTimes)).get();
        Lane laneMin = laneList.stream().min(Comparator.comparing(Lane::getSumOfTransitTimes)).get();
        System.out.println("Max: " + laneMax);
        System.out.println("Min: " + laneMin);
        String sMinMax = laneMax.toString() + laneMin.toString();
        return sMinMax;
    }

    protected ArrayList<Lane> getAllLanesBetweenStops(Stop stop1, Stop stop2) {
        ArrayList<Lane> lanes = new ArrayList<>();
        for (Lane lane : laneList) {
            if (lane.getHandledStops().contains(stop1) && lane.getHandledStops().contains(stop2)) {
                lanes.add(lane);
            }
        }
        System.out.println(lanes);
        return lanes;
    }

    protected Map<String, ArrayList<Lane>> getAllLanesDeparturingFromParticularStopInTimeRange(Stop stop, Time from, Time to) {
        ArrayList<Lane> lanes = new ArrayList<>();
        for (Lane lane : laneList) {
            if (lane.getHandledStops().contains(stop)) {
                ArrayList<Time> departureTimes = lane.getDepartureTimesForStops().get(stop);
                for (Time time : departureTimes) {
                    if (time.compareTo(from) >= 0 && time.compareTo(to) <= 0) {
                        lanes.add(lane);
                        break;
                    }
                }

            }
        }

        ArrayList<Lane> lanesReverse = new ArrayList<>();
        for (Lane lane : laneList) {
            if (lane.getHandledStops().contains(stop)) {
                ArrayList<Time> departureTimes = lane.getDepartureTimesForStopsReverse().get(stop);
                for (Time time : departureTimes) {
                    if (time.compareTo(from) >= 0 && time.compareTo(to) <= 0) {
                        lanesReverse.add(lane);
                        break;
                    }
                }

            }
        }

        Map<String, ArrayList<Lane>> result = new HashMap<String, ArrayList<Lane>>();
        result.put("Forward: ", lanes);
        result.put("Reverse: ", lanesReverse);
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {

    }
}
