package jakDojade;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */
// mozliwosc tworzenia nowych przystankow z poziomu klasy jakDojade, w enumie trzeba grzebac

public class Stop {
    
    private final String name;

    Stop(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }  

    @Override
    public String toString() {
        return name;
    }
}

