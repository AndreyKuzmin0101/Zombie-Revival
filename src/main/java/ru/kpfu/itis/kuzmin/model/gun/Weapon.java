package ru.kpfu.itis.kuzmin.model.gun;

public class Weapon {
    private final int rateOfFire;
    private int damage;
    private int interval;

    public Weapon(int damage, int rateOfFire) {
        this.rateOfFire = rateOfFire;
        interval = 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void reduceInterval() {
        interval -= 1;
    }
    public void resumeInterval() {
        interval = rateOfFire;
    }

    public int getInterval() {
        return interval;
    }
}
