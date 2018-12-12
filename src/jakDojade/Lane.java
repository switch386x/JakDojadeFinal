package jakDojade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */

public class Lane {

    private final int laneNumber;
    private final List<Stop> handledStops; // jesli jej nie przyrownam do niczego, czyli w tym przypadku do obietku ArrayList to
                                           // jest to zmienna o referencji null
    private final List<Time> timesOfDeparture;
    private final List<Time> timesOfDepartureReverse;
    private final List<Time> timesOfTransit;
    private final List<Time> timesOfTransitReverse;
    private final Map<Stop, ArrayList<Time>> departureTimesForStops = new HashMap<Stop, ArrayList<Time>>();
    private final Map<Stop, ArrayList<Time>> departureTimesForStopsReverse = new HashMap<Stop, ArrayList<Time>>();
    
    // single responsibility principle

    public List<Time> getTimesOfTransit() {
        return timesOfTransit;
    }

    public List<Time> getTimesOfDeparture() {
        return timesOfDeparture;
    }

    // constructor - powrot do porzedniej koncepcji
    public Lane(int laneNumber, ArrayList<Stop> handledStops, ArrayList<Time> timesOfDeparture, ArrayList<Time> timesOfDepartureReverse, ArrayList<Time> timesOfTransit) {
        this.laneNumber = laneNumber;
        this.handledStops = handledStops;
        this.timesOfTransit = timesOfTransit;
        timesOfTransitReverse = timesOfTransit.stream().map(e -> e.getTimeDifference(getSumOfTransitTimes())).collect(Collectors.toList());
        this.timesOfDeparture = timesOfDeparture;
        this.timesOfDepartureReverse = timesOfDepartureReverse;
        prepareDepartureTimesForStops();
    }

    private void prepareDepartureTimesForStops() { // tworzenie listy odjazdow - lista odjazdow
        for (int i = 0; i < handledStops.size(); i++) {
            ArrayList<Time> temp = new ArrayList<>();
            for (Time t : timesOfDeparture) {
                temp.add(Time.sum(t, timesOfTransit.get(i))); // dla kazdego z czasow odjazdu dodawanie czasu przejazdu
            }
            departureTimesForStops.put(handledStops.get(i), temp);
        }
        for (int i = handledStops.size() - 1; i >= 0; i--) { // size = 5, indexMax = 4
            ArrayList<Time> temp = new ArrayList<>();
            for (Time t : timesOfDepartureReverse) {
                temp.add(Time.sum(t, timesOfTransitReverse.get(i)));
            }
            departureTimesForStopsReverse.put(handledStops.get(i), temp);
        }
    }

    public Map<Stop, ArrayList<Time>> getDepartureTimesForStops() {
        return departureTimesForStops;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public List<Stop> getHandledStops() {
        return handledStops;
    }

    public Time getSumOfTransitTimes() {
        return timesOfTransit.get(timesOfTransit.size() - 1);
    }

    public void modifiedPrint() {
        ArrayList<Time> timesInBetween = new ArrayList<>();
        for (int i = 1; i < timesOfTransit.size(); i++) {
            timesInBetween.add(timesOfTransit.get(i).getTimeDifference(timesOfTransit.get(i - 1)));
        }
        System.out.println("Numer Linii: " + laneNumber + "\n" + "Obslugiwane przystanki: " + handledStops + "\n" + "Czasy pomiedzy przystankami: " + timesInBetween);
    }

    @Override
    public String toString() {
        return "" + laneNumber;
    }

    public Map<Stop, ArrayList<Time>> getDepartureTimesForStopsReverse() {
        return departureTimesForStopsReverse;
    }

}
