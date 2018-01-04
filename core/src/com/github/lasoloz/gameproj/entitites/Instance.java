package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.utils.ArrayMap;

public class Instance {
    private Blueprint blueprint;
    private ArrayMap<Property, Integer> integerProperties;
    private ArrayMap<Property, Float> floatProperties;

//    private Property currentAction;


    public Instance(Blueprint blueprint) {
        this.blueprint = blueprint;
        integerProperties = new ArrayMap<Property, Integer>();
    }


    public Blueprint getBlueprint() {
        return blueprint;
    }


    public void addIntegerProperty(Property property, Integer value) {
        this.integerProperties.put(property, value);
    }


    public Integer getIntegerProperty(Property property) {
        if (property.isInstanceSpecific()) {
            return integerProperties.get(property);
        } else {
            return blueprint.getIntegerProperty(property);
        }
    }


    public void addFloatProperty(Property property, Float value) {
        this.floatProperties.put(property, value);
    }

    public Float getFloatProperty(Property property) {
        if (property.isInstanceSpecific()) {
            return floatProperties.get(property);
        } else {
            return blueprint.getFloatProperty(property);
        }
    }
}
