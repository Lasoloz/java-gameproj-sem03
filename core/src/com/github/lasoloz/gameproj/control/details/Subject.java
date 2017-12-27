package com.github.lasoloz.gameproj.control.details;

import java.util.LinkedList;

public abstract class Subject {
    LinkedList<Observer> observers;
    GameState gameState;

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

    public void notify(Observer observer) {
        for (Observer o : observers) {
            o.update(gameState);
        }
    }
}
