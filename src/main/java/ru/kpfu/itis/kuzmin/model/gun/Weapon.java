package ru.kpfu.itis.kuzmin.model.gun;

public class Weapon {
    private final int rateOfFire;
    private int bulletVelocity;
    private double damage;
    private int interval;

    public Weapon(double damage, int rateOfFire, int bulletVelocity) {
        this.damage = damage;
        this.rateOfFire = rateOfFire;
        this.bulletVelocity = bulletVelocity;
        interval = 0;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
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

    public int getBulletVelocity() {
        return bulletVelocity;
    }

    public void setBulletVelocity(int bulletVelocity) {
        this.bulletVelocity = bulletVelocity;
    }
}
