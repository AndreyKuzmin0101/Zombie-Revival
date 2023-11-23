package ru.kpfu.itis.kuzmin.model;

public abstract class Role {
    public static final int SHOOTER = 0;
    private double speed;
    private int roleCode;

    public Role(double speed, int roleCode) {
        this.speed = speed;
        this.roleCode = roleCode;
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }
}
