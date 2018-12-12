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
    //protected - enables accessing from within a package
    protected ArrayList<Lane> showLaneInformation(int lane) {
        laneList.stream().filter(e -> e.getLaneNumber() == lane).collect(Collectors.toList()).forEach(Lane::modifiedPrint);
        return laneList;
    }
    // creation of Lines available for handling

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

    protected String getMinAndMax() {
        Lane laneMax = laneList.stream().max(Comparator.comparing(Lane::getSumOfTransitTimes)).get();
        Lane laneMin = laneList.stream().min(Comparator.comparing(Lane::getSumOfTransitTimes)).get();//czemu to nie smiga
        System.out.println("Max: " + laneMax);
        System.out.println("Min: " + laneMin);
        String sMinMax = laneMax.toString() + laneMin.toString();
        return(sMinMax);
    }

    protected ArrayList<Lane> getAllLanesBetweenStops(Stop stop1, Stop stop2) {
        ArrayList<Lane> lanes = new ArrayList<>();
        for (Lane lane : laneList) {
            if (lane.getHandledStops().contains(stop1) && lane.getHandledStops().contains(stop2)) {
                lanes.add(lane);
            }
        }
        System.out.println(lanes);
        return(lanes);
    }

    protected ArrayList<Lane> getAllLanesDeparturingFromParticularStopInTimeRange(Stop stop, Time from, Time to) {
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
        System.out.println(lanes);
        return(lanes);
    }

    public static void main(String[] args) {

        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(Stop.BOROWSKA);
        stops.add(Stop.BRZOZOWA);
        stops.add(Stop.KOMANDORSKA);

        ArrayList<Stop> stops2 = new ArrayList<>();
        stops2.add(Stop.BOROWSKA);
        stops2.add(Stop.KSIECIA_WITOLDA);
        stops2.add(Stop.KAMIENNA);

        ArrayList<Time> timesOfDeparture = new ArrayList<>();
        timesOfDeparture.add(new Time(12, 15));
        timesOfDeparture.add(new Time(15, 00));
        timesOfDeparture.add(new Time(16, 30));

        ArrayList<Time> timesOfTransit = new ArrayList<>(); // incrementally modeled time
        timesOfTransit.add(new Time(00, 00));
        timesOfTransit.add(new Time(00, 15));
        timesOfTransit.add(new Time(00, 25));

        ArrayList<Time> timesOfTransit2 = new ArrayList<>(); // incrementally modeled time
        timesOfTransit2.add(new Time(00, 00));
        timesOfTransit2.add(new Time(00, 05));
        timesOfTransit2.add(new Time(00, 10));

        ArrayList<Time> transitKomandorska = new ArrayList<>();
        transitKomandorska.add(new Time(10, 00));
        transitKomandorska.add(new Time(16, 00));
        transitKomandorska.add(new Time(18, 00));

        JakDojade jakDojade = new JakDojade();

        Lane linia1 = new Lane(1, stops, timesOfDeparture, timesOfTransit);
        Lane linia2 = new Lane(2, stops2, timesOfDeparture, timesOfTransit2);   // obiekty nie wystepuja poza struktura danych - laneList

        jakDojade.addLane(linia1);
        jakDojade.addLane(linia2);

        jakDojade.showLaneInformation(1);
        jakDojade.getStopWithMaxNumberOfLanes();
        jakDojade.getTimeOfTransitBetweenStops(linia1, Stop.BRZOZOWA, Stop.KOMANDORSKA);
        jakDojade.getMinAndMax();
        jakDojade.getAllLanesBetweenStops(Stop.BOROWSKA, Stop.KAMIENNA);
        jakDojade.getAllLanesDeparturingFromParticularStopInTimeRange(Stop.BOROWSKA, new Time(00,00), new Time(24,00));

    }
}
