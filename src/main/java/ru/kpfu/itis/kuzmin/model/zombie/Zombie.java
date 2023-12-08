package ru.kpfu.itis.kuzmin.model.zombie;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.defense.Turret;
import ru.kpfu.itis.kuzmin.model.defense.Wall;

public abstract class Zombie {
    public static final byte SHAMBLING_CITIZEN = 1;
    private int id;
    private int type;
    private int damage;
    private float speed;
    private ImageView image;
    private ProgressBar hpProgressBar;
    private double startHp;
    private double hp;
    private double speedDamage;
    private double intervalDamage;


    public Zombie(int id, int type, int hp, int damage, float speed, double speedDamage, double positionX, double positionY) {
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.speed = speed;
        this.speedDamage = speedDamage;
        this.intervalDamage = speedDamage;

        this.image = new ImageView("/images/shambling_citizen.png");
        image.setFitWidth(53);
        image.setFitHeight(70);

        this.hp = hp;
        startHp = hp;
        hpProgressBar = new ProgressBar(1);
        hpProgressBar.setPrefWidth(53);
        hpProgressBar.setPrefHeight(15);
        LevelController.addHpBar(hpProgressBar);

        setPositionX(positionX);
        setPositionY(positionY);

    }

    public double getIntervalDamage() {
        return intervalDamage;
    }

    public void resetIntervalDamage() {
        intervalDamage = speedDamage;
    }

    public void reduceIntervalDamage(double elapsedTime) {
        intervalDamage -= elapsedTime;
    }

    public double getPositionX() {
        return image.getLayoutX();
    }

    public void setPositionX(double positionX) {
        image.setLayoutX(positionX);
        hpProgressBar.setLayoutX(positionX);
    }

    public double getPositionY() {
        return image.getLayoutY();
    }

    public void setPositionY(double positionY) {
        image.setLayoutY(positionY);
        hpProgressBar.setLayoutY(positionY-20);
    }

    public double getCenterX() {
        return getPositionX() + 26.5;
    }

    public double getCenterY() {
        return getPositionY() + 35;
    }

    public double damage(Player player, Teammate teammate) {
        if (player.getRole().getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
            player.setHp(player.getHp() - getDamage());
            resetIntervalDamage();
        }
        if (teammate.getRole().getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
            teammate.setHp(teammate.getHp() - getDamage());
            resetIntervalDamage();
        }
        return player.getHp();
    }

    //TODO: продумать обход препятствий

    public void move(Player player, Teammate teammate, double elapsedTime, World world) {
        double dx1 = player.getCenterX() - getCenterX();
        double dy1 = player.getCenterY() - getCenterY();

        double dx2 = teammate.getCenterX() - getCenterX();
        double dy2 = teammate.getCenterY() - getCenterY();

        double vectorX, vectorY, distance;

        double playerDistance = Math.sqrt(dx1*dx1 + dy1*dy1);
        double teammateDistance = Math.sqrt(dx2*dx2 + dy2*dy2);

        if (playerDistance < teammateDistance) {
            distance = playerDistance;
            vectorX = dx1/playerDistance;
            vectorY = dy1/playerDistance;
        } else {
            distance = teammateDistance;
            vectorX = dx2/teammateDistance;
            vectorY = dy2/teammateDistance;
        }

        for (Turret turret: world.getTurrets()) {
            double dxTurret = turret.getCenterX() - getCenterX();
            double dyTurret = turret.getCenterY() - getCenterY();

            double sqrt = Math.sqrt(dxTurret*dxTurret + dyTurret*dyTurret);

            if (sqrt < distance) {
                distance = sqrt;
                vectorX = dxTurret/distance;
                vectorY = dyTurret/distance;
            }
        }

        double oldPositionX = getPositionX();
        double oldPositionY = getPositionY();

        setPositionX(getPositionX() + vectorX * speed * elapsedTime);
        setPositionY(getPositionY() + vectorY * speed * elapsedTime);

        for (Wall wall: world.getWalls()) {
            if (image.getBoundsInParent().intersects(wall.getImage().getBoundsInParent())) {
                setPositionX(oldPositionX);
                setPositionY(oldPositionY);
                damageWall(wall, world);

                return;
            }
        }

        for (Turret turret: world.getTurrets()) {
            if (image.getBoundsInParent().intersects(turret.getImage().getBoundsInParent())) {
                setPositionX(oldPositionX);
                setPositionY(oldPositionY);

                damageTurret(turret, world);
                return;
            }
        }
    }

    private void damageWall(Wall wall, World world) {
        if (getIntervalDamage() <= 0) {
            wall.setHp(wall.getHp() - damage);
            resetIntervalDamage();
            if (wall.getHp() <= 0) {
                world.deleteWall(wall);
            }
        }
    }

    private void damageTurret(Turret turret, World world) {
        if (getIntervalDamage() <= 0) {
            turret.setHp(turret.getHp() - damage);
            resetIntervalDamage();
            if (turret.getHp() <= 0) {
                world.deleteTurret(turret);
            }
        }
    }


    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
        this.hpProgressBar.setProgress(hp/this.startHp);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ProgressBar getHpProgressBar() {
        return hpProgressBar;
    }
}
