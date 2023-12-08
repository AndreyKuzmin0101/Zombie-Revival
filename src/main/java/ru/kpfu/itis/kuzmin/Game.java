package ru.kpfu.itis.kuzmin;

import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Game {
    private long lastTime = System.nanoTime();
    private int lvl;
    private World world;
    private Player player;
    private Teammate teammate;

    public Game(int lvl, World world, Player player, Teammate teammate) {
        this.lvl = lvl;
        this.world = world;
        this.player = player;
        this.teammate = teammate;
    }

    public void initController() {
        LevelController.player = this.player;
        LevelController.world = this.world;
        LevelController.teammate = this.teammate;
        LevelController.game = this;
    }

    public void prepareOneFrame(long currentTime) {
        double elapsedTime = (currentTime - lastTime) / 1e9;
        if (player.getRole().getRoleCode() == Role.ENGINEER) {
            ((Engineer) player.getRole()).reduceWallTimer(elapsedTime);
            ((Engineer) player.getRole()).reduceTurretTimer(elapsedTime);
        }
        player.move(world, elapsedTime);
        player.shoot(world, elapsedTime);
        world.moveBullets(elapsedTime);
        world.deleteOldBullets();
        world.shootTurrets(elapsedTime);
        world.checkIntersectBulletsAndZombies();
        world.moveZombies(player, teammate, elapsedTime);
        world.checkIntersectPlayersAndZombies(player, teammate, elapsedTime);
        lastTime = currentTime;
    }


    public World getWorld() {
        return world;
    }

    public Teammate getTeammate() {
        return teammate;
    }
}
