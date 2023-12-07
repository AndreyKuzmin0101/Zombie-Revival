package ru.kpfu.itis.kuzmin.model.role;

import ru.kpfu.itis.kuzmin.model.gun.Revolver;

public class Engineer extends Role{
    private double timer;
    private final double interval;
    private int lvlWall;
    public Engineer() {
        super(150, Role.ENGINEER);
        setDefaultWeapon(new Revolver());
        this.lvlWall = 1;
        interval = 5;
        timer = interval;
    }

    public double getTimer() {
        return timer;
    }
    public void resetTimer() {
        timer = interval;
    }

    public void reduceTimer(double elapsedTime) {
        timer -= elapsedTime;
    }

    public int getLvlWall() {
        return lvlWall;
    }

    public void setLvlWall(int lvlWall) {
        this.lvlWall = lvlWall;
    }
}
