package jakDojade;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */

public class Time implements Comparable<Time> {

    public int hour;
    public int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return new String() + hour + " " + "godzin" + " " + minute + " " + "minut";
    }

    public static Time sum(Time time1, Time time2) {
        return new Time(time1.hour + time2.hour, time1.minute + time2.minute);
    }

    public Time getTimeDifference(Time time2) {
        int firstTimeInMinutes = hour * 60 + minute; // jak usunac stad this i zastapic drugim parametrem w sygnaturze metody - po prostu usuniete 12.12.2018
        int secondTimeInMinutes = time2.hour * 60 + time2.minute;
        int timeDifference = Math.abs(firstTimeInMinutes - secondTimeInMinutes);
        return new Time(timeDifference / 60, timeDifference % 60);
    }

    @Override
    public int compareTo(Time time2) {
        int firstTimeInMinutes = this.hour * 60 + this.minute;
        int secondTimeInMinutes = time2.hour * 60 + time2.minute;
        return firstTimeInMinutes - secondTimeInMinutes;
    }
    //menu kontekstowe, source, generate hashcode and equals

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Time other = (Time) obj;
        if (hour != other.hour)
            return false;
        if (minute != other.minute)
            return false;
        return true;
    }
    
    
}

