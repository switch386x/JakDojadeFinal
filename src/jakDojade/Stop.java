package jakDojade;

/*
 * Copyright (c) 2018. Schenker AG
 * All rights reserved.
 */
// mozliwosc tworzenia nowych przystankow z poziomu klasy jakDojade, w enumie trzeba grzebac

public class Stop {
    
    public static final Stop BOROWSKA = new Stop("Borowska");
    public static final Stop KAMIENNA = new Stop("Kamienna");
    public static final Stop KOMANDORSKA = new Stop("Komandorska");
    public static final Stop BRZOZOWA = new Stop("Brzozowa");
    public static final Stop KSIECIA_WITOLDA = new Stop("Ksiecia Witolda");
    
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

