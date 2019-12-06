package dev.connor14.minecraftmazeapi.generators;

import org.bukkit.Material;

import dev.connor14.minecraftmazeapi.maze.Maze;

public abstract class MazeGenerator implements IMazeGenerator {

    protected final Maze maze;

    public MazeGenerator(int rows, int columns) {
        this.maze = new Maze(rows, columns);
    }

    public abstract Material[][][] getBlocks();
}
