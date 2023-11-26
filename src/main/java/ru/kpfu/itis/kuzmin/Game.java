package ru.kpfu.itis.kuzmin;

import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.Teammate;

public class Game {
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
    public void prepareOneFrame() {
        player.move();
        world.moveBullets();
        player.shoot(world);
        System.out.println(player.getPositionX() + " " + player.getPositionY());
        System.out.println(teammate.getPositionX() + " " + teammate.getPositionY());
    }



    public World getWorld() {
        return world;
    }

    public Teammate getTeammate() {
        return teammate;
    }
}
