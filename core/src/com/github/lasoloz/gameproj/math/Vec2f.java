package com.github.lasoloz.gameproj.math;

public class Vec2f {
    private float x;
    private float y;


    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public Vec2f copy() {
        return new Vec2f(x, y);
    }


    public void set(Vec2f other) {
        this.x = other.x;
        this.y = other.y;
    }


    public void addTo(Vec2f other) {
        this.x += other.x;
        this.y += other.y;
    }


    public Vec2f add(Vec2f other) {
        Vec2f t = this.copy();
        t.addTo(other);
        return t;
    }


    public void addToX(float val) {
        this.x += val;
    }


    public void addToY(float val) {
        this.y += val;
    }

    public void setX(float val) {
        x = val;
    }

    public void setY(float val) {
        y = val;
    }


    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }


    public float distSq(Vec2f other) {
        float dx = x - other.x;
        float dy = y - other.y;

        return dx * dx + dy * dy;
    }


    public float dist(Vec2f other) {
        return (float) Math.sqrt(distSq(other));
    }


    public float absSq() {
        return x * x + y * y;
    }

    public float abs() {
        return (float) Math.sqrt(absSq());
    }


    public void scale(float ratio) {
        x *= ratio;
        y *= ratio;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", " + y + "}";
    }
}
