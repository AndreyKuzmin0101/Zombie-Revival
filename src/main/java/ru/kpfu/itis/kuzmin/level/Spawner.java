package ru.kpfu.itis.kuzmin.level;

import ru.kpfu.itis.kuzmin.model.zombie.ShamblingCitizen;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.util.*;

public class Spawner {
    private boolean active;
    private Queue<Zombie> zombies;
    private Queue<Integer> intervals;

    public Spawner(Queue<Zombie> zombies, Queue<Integer> intervals) {
        this.zombies = zombies;
        this.intervals = intervals;
        active = true;
    }
    public Zombie getZombie() {
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

    //TODO: фабрика
    public static Spawner getInstance(int lvl) {
        Queue<Zombie> zombies = new PriorityQueue<>();
        Queue<Integer> intervals = new PriorityQueue<>();
        switch (lvl) {
            case 1:
                for (int i = 0; i < 30; i++) {
                    zombies.add(new ShamblingCitizen(i));
                    intervals.add(3000);
                }
                break;
        }

        return new Spawner(zombies, intervals);
    }

    public boolean isActive() {
        return active;
    }
}
