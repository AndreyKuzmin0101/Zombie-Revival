package ru.kpfu.itis.kuzmin.level;

import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.protocol.ZombieModel;

import java.util.*;

public class Spawner {
    private boolean active;
    private Queue<ZombieModel> zombies;
    private Queue<Integer> intervals;

    private Spawner(Queue<ZombieModel> zombies, Queue<Integer> intervals) {
        this.zombies = zombies;
        this.intervals = intervals;
        active = true;
    }
    public ZombieModel getZombie() {
        if (zombies.size() == 0) {
            active = false;
            return null;
        }

        try {
            Thread.sleep(intervals.remove());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return zombies.remove();
    }

    public static Spawner getInstance(int lvl) {
        Queue<ZombieModel> zombies = new LinkedList<>();
        Queue<Integer> intervals = new LinkedList<>();
        switch (lvl) {
            case 1:
                for (int i = 0; i < 15; i++) {
                    zombies.add(new ZombieModel(i, (byte) 1, (float) World.getZombieStartPositionX(), (float) World.getZombieStartPositionY()));
                    intervals.add(5000);
                }
                break;
        }

        return new Spawner(zombies, intervals);
    }

    public boolean isActive() {
        return active;
    }

    public Queue<ZombieModel> getZombies() {
        return zombies;
    }
}
