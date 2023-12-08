package ru.kpfu.itis.kuzmin.model.role;

import ru.kpfu.itis.kuzmin.model.gun.Revolver;

public class Engineer extends Role{
    private double wallTimer;
    private final double wallInterval;

    private double turretTimer;
    private final double turretInterval;

    private int lvlWall;
    public Engineer() {
        super(150, Role.ENGINEER);
        setDefaultWeapon(new Revolver());
        this.lvlWall = 1;
        wallInterval = 5;
        wallTimer = wallInterval;
        turretInterval = 10;
        turretTimer = turretInterval;
    }

    public double getWallTimer() {
        return wallTimer;
    }
    public void resetWallTimer() {
        wallTimer = wallInterval;
    }

    public void reduceWallTimer(double elapsedTime) {
        wallTimer -= elapsedTime;
    }

    public double getTurretTimer() {
        return turretTimer;
    }
    public void resetTurretTimer() {
        turretTimer = turretInterval;
    }

    public void reduceTurretTimer(double elapsedTime) {
        turretTimer -= elapsedTime;
    }

    public int getLvlWall() {
        return lvlWall;
    }

    public void setLvlWall(int lvlWall) {
        this.lvlWall = lvlWall;
    }
}
