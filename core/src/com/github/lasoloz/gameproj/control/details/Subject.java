package com.github.lasoloz.gameproj.control.details;

import java.util.LinkedList;

public abstract class Subject {
    private LinkedList<Observer> observers;
    protected GameState gameState;

    public Subject(GameState gameState) {
        observers = new LinkedList<Observer>();
        this.gameState = gameState;
    }


    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void update() {
        for (Observer o : observers) {
            o.update(gameState);
        }
    }
}
