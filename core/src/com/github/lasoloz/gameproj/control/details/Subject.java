package com.github.lasoloz.gameproj.control.details;

import java.util.LinkedList;

/**
 * Subject base class - game controlling class with attachable observers
 */
public abstract class Subject {
    private LinkedList<Observer> observers;
    protected GameState gameState;

    /**
     * Construct a new subject
     * @param gameState Game state to be attached.
     */
    public Subject(GameState gameState) {
        observers = new LinkedList<Observer>();
        this.gameState = gameState;
    }


    /**
     * Attach an observer to the subject.
     * @param observer New observer to be attached.
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Detach the specific observer from the subject
     * @param observer Observer to be detached
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Update game state and notify observers.
     */
    public void update() {
        for (Observer o : observers) {
            o.update(gameState);
        }
    }
}
