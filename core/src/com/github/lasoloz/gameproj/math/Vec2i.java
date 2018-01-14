package com.github.lasoloz.gameproj.math;

/**
 * Integer vector
 */
public class Vec2i {
    public int x;
    public int y;


    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Vec2i copy() {
        return new Vec2i(x, y);
    }


    public void set(Vec2i other) {
        this.x = other.x;
        this.y = other.y;
    }


    public void addTo(Vec2i other) {
        this.x += other.x;
        this.y += other.y;
    }


    public Vec2i add(Vec2i other) {
        Vec2i t = this.copy();
        t.addTo(other);
        return t;
    }



    public int distSq(Vec2i other) {
        int dx = x - other.x;
        int dy = y - other.y;

        return dx * dx + dy * dy;
    }


    public float dist(Vec2i other) {
        return (float) Math.sqrt(distSq(other));
    }


    public int absSq() {
        return x * x + y * y;
    }

    public float abs() {
        return (float) Math.sqrt(absSq());
    }


    public void scale(int ratio) {
        x *= ratio;
        y *= ratio;
    }

    @Override
    public String toString() {
        return "(i){x: " + x + ", y: " + y + "}";
    }
}
