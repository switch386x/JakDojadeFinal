package jakDojade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */

public class Lane {

    private final int laneNumber;
    private final ArrayList<Stop> handledStops;   // jesli jej nie przyrownam do niczego, czyli w tym przypadku do obietku ArrayList to jest to zmienna o referencji null
    private final Map<Stop,ArrayList<Time>> departureTimesForStops = new HashMap<Stop,ArrayList<Time>>();
    private final ArrayList<Time> timesOfDeparture;
    private final ArrayList<Time> timesOfTransit;
      
   // single responsibility principle

    public ArrayList<Time> getTimesOfTransit() {
        return timesOfTransit;
    }
    
    public ArrayList<Time> getTimesOfDeparture() {
        return timesOfDeparture;
    }

    // constructor - powrot do porzedniej koncepcji
    public Lane(int laneNumber, ArrayList<Stop> handledStops, ArrayList<Time> timesOfDeparture, ArrayList<Time> timesOfTransit){  // linia nie ma odjazdu w dwie strony
        this.laneNumber = laneNumber;
        this.handledStops = handledStops;
        this.timesOfTransit = timesOfTransit;
        this.timesOfDeparture = timesOfDeparture;
        prepareDepartureTimesForStops();
    }
    
    private void prepareDepartureTimesForStops(){
        for (int i = 0; i < handledStops.size(); i++){
            ArrayList<Time> temp = new ArrayList<>();
            for (Time t : timesOfDeparture){
                temp.add(Time.sum(t, timesOfTransit.get(i)));
            }
            departureTimesForStops.put(handledStops.get(i), temp);
        }
    }
    public Map<Stop, ArrayList<Time>> getDepartureTimesForStops() {
        return departureTimesForStops;
    }

    public int getLaneNumber() {
        return laneNumber;
    }
    
    public ArrayList<Stop> getHandledStops() {
        return handledStops;
    }
    
    public Time getSumOfTransitTimes(){
        return timesOfTransit.get(timesOfTransit.size() - 1);
    }

    public void modifiedPrint() {
        ArrayList<Time> timesInBetween = new ArrayList<>();
        for (int i = 1; i < timesOfTransit.size(); i++){
            timesInBetween.add(timesOfTransit.get(i).getTimeDifference(timesOfTransit.get(i - 1)));
        }
        System.out.println("Numer Linii: " + laneNumber + "\n" + "Obslugiwane przystanki: " + handledStops + "\n" + "Czasy pomiedzy przystankami: " + timesInBetween);
    }
    
    @Override
    public String toString() {
        return "" + laneNumber;
    }
     
}

