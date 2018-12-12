package jakDojade;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */

public class JakDojadeTest {
    //trening
    
    public static final Stop BOROWSKA = new Stop("Borowska");
    public static final Stop KAMIENNA = new Stop("Kamienna");
    public static final Stop KOMANDORSKA = new Stop("Komandorska");
    public static final Stop BRZOZOWA = new Stop("Brzozowa");
    public static final Stop KSIECIA_WITOLDA = new Stop("Ksiecia Witolda");
    public static final Stop BYCZA = new Stop("Bycza");
    public static final Stop RAKOWA = new Stop("Rakowa");
    public static final Stop KAWOWA = new Stop("Kawowa");
    public static final Stop DZIABANSKA = new Stop("Dziabanska");
    public static final Stop TANGOWA = new Stop("Tangowa");

    public static final Time testTimeForTransit = new Time(00, 10);   // final = bold , static = kursywa, niebieskie = klasowa metoda

    public static final Time testTimeFrom = new Time(00, 00);
    public static final Time testTimeTo = new Time(24, 00);

    public static Map<String, ArrayList<Lane>> testTimeRangeLanes = new HashMap<String, ArrayList<Lane>>();
    public static ArrayList<Lane> laneListingForTestTimeRange = new ArrayList<>();
    public static ArrayList<Lane> testLanesBetweenStops = new ArrayList<>();

    public static JakDojade jakDojade;
    public static Lane linia1;
    public static Lane linia2;

    public static String sMinMax = "12";
    
    public static ArrayList<Stop> stops3 = new ArrayList<>();
    public static ArrayList<Time> testTimesOfTransit = new ArrayList<>();
    public static ArrayList<Time> testTimesOfDeparture = new ArrayList<>();
    

    @BeforeClass
    public static void prepareTestData() {

        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(BOROWSKA);
        stops.add(BRZOZOWA);
        stops.add(KOMANDORSKA);

        ArrayList<Stop> stops2 = new ArrayList<>();
        stops2.add(BOROWSKA);
        stops2.add(KSIECIA_WITOLDA);
        stops2.add(KAMIENNA);

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
        

        linia1 = new Lane(1, stops, timesOfDeparture, timesOfDeparture, timesOfTransit);
        linia2 = new Lane(2, stops2, timesOfDeparture, timesOfDeparture, timesOfTransit2); // obiekty nie wystepuja poza struktura danych
        // ctrl alt dol kopiuje linie

        jakDojade = new JakDojade();
        jakDojade.addLane(linia1);
        jakDojade.addLane(linia2);

        laneListingForTestTimeRange.add(linia1);
        laneListingForTestTimeRange.add(linia2);
        testTimeRangeLanes.put("Forward: ", laneListingForTestTimeRange);
        testTimeRangeLanes.put("Reverse: ", laneListingForTestTimeRange);

        testLanesBetweenStops.add(linia2);
        
        stops3.add(BOROWSKA);
        stops3.add(BRZOZOWA);
        stops3.add(KOMANDORSKA);
        
        testTimesOfTransit.add(new Time(00,00));
        testTimesOfTransit.add(new Time(00,15));
        testTimesOfTransit.add(new Time(00,25));
        
        testTimesOfDeparture.add(new Time(12, 15));
        testTimesOfDeparture.add(new Time(15, 00));
        testTimesOfDeparture.add(new Time(16, 30));
    }

    @Test
    public void testShowLaneInformation() {
        assertEquals(linia1.getHandledStops(),stops3);
        assertEquals(linia1.getLaneNumber(),1);
        assertEquals(linia1.getTimesOfTransit(),testTimesOfTransit);
        assertEquals(linia1.getTimesOfDeparture(),testTimesOfDeparture);
    }

    @Test
    public void testGetStopWithMaxNumberOfLanes() {
        assertEquals(jakDojade.getStopWithMaxNumberOfLanes(), BOROWSKA);
    }

    @Test
    public void testGetTimeOfTransitBetweenStops() {
        assertEquals(jakDojade.getTimeOfTransitBetweenStops(linia1, BRZOZOWA, KOMANDORSKA), testTimeForTransit);
    }

    @Test
    public void testGetMinAndMax() {
        assertEquals(jakDojade.getMinAndMaxTransitTimeLane(), sMinMax);
    }

    @Test
    public void testGetAllLanesBetweenStops() {
        assertEquals(jakDojade.getAllLanesBetweenStops(BOROWSKA, KAMIENNA), testLanesBetweenStops);
    }

    @Test
    public void testGetAllLanesDeparturingFromParticularStopInTimeRange() {
        assertEquals(jakDojade.getAllLanesDeparturingFromParticularStopInTimeRange(BOROWSKA, testTimeFrom, testTimeTo),testTimeRangeLanes);
    }
}
