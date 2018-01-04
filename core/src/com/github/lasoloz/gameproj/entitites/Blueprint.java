package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;
import com.github.lasoloz.gameproj.math.Vec2f;

import java.util.ArrayList;

public class Blueprint {
    private SpriteWrapper indexImage;

    private ArrayMap<Property, Integer> integerProperties;
    private ArrayMap<Property, Float> floatProperties;
    private ArrayMap<Property, Drawable> drawables;
    private ArrayList<Property> inheritedProperties;

    private String unitName;


    public Blueprint(
            String unitName,
            SpriteWrapper indexImage
    ) {
        this.unitName = unitName;
        this.indexImage = indexImage;
        integerProperties = new ArrayMap<Property, Integer>();
        floatProperties = new ArrayMap<Property, Float>();
        drawables = new ArrayMap<Property, Drawable>();
        inheritedProperties = new ArrayList<Property>();
    }

    public void addIntegerProperty(
            Property property,
            Integer value,
            boolean isInherited
    ) {
        integerProperties.put(property, value);
        if (isInherited) {
            inheritedProperties.add(property);
        }
    }

    public Integer getIntegerProperty(Property property) {
        return integerProperties.get(property);
    }


    public void addFloatProperty(
            Property property,
            Float value,
            boolean isInherited
    ) {
        floatProperties.put(property, value);
        if (isInherited) {
            inheritedProperties.add(property);
        }
    }

    public Float getFloatProperty(Property property) {
        return floatProperties.get(property);
    }


    public void addDrawableProperty(Property property, Drawable drawable) {
        drawables.put(property, drawable);
    }

    public Drawable getDrawableProperty(Property property) {
        return drawables.get(property);
    }


    public ArrayList<Property> getInheritedProperties() {
        return inheritedProperties;
    }


    public SpriteWrapper getIndexImage() {
        return this.indexImage;
    }


    public String getUnitName() {
        return unitName;
    }
}
