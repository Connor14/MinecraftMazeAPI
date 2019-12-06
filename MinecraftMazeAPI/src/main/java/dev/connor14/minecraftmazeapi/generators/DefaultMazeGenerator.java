package dev.connor14.minecraftmazeapi.generators;

import java.util.Arrays;

import org.bukkit.Material;

import dev.connor14.minecraftmazeapi.utility.CellDirection;

// todo need to cache the position of the player so that the location doesn't
// change during long running actions?

public class DefaultMazeGenerator extends MazeGenerator {

    // Additional Maze Settings
    private final int pathWidth;
    private final int wallThickness;
    private final int wallHeight;

    // Calculated Values
    private final int cellToBlockOffset;

    private final int blockHeight;
    private final int blockRows;
    private final int blockColumns;

    // Generator Output
    private final Material[][][] blockGrid;

    public DefaultMazeGenerator(int rows, int columns, int pathWidth, int wallThickness, int wallHeight) {
        super(rows, columns);

        this.pathWidth = pathWidth;
        this.wallThickness = wallThickness;
        this.wallHeight = wallHeight;

        cellToBlockOffset = pathWidth + wallThickness;

        blockHeight = 1 + wallHeight; // floor + wallHeight
        blockRows = (maze.getRows() * cellToBlockOffset) + wallThickness;
        blockColumns = (maze.getColumns() * cellToBlockOffset) + wallThickness;

        blockGrid = new Material[blockHeight][blockRows][blockColumns];
    }

    public Material[][][] getBlocks() {
        convertMazeToBlocks();

        return blockGrid;
    }

    private void convertMazeToBlocks() {
        // populate the blockGrid with OBSIDIAN
        for (Material[][] level : blockGrid) {
            for (Material[] row : level) {
                Arrays.fill(row, Material.OBSIDIAN);
            }
        }

        Material fillMaterial = Material.OAK_WOOD;
        for (int y = 0; y < blockHeight; y++) {

            // floor
            if (y == 0) {
                fillMaterial = Material.OAK_WOOD;

            } else {// passages
                fillMaterial = Material.AIR;
            }

            for (int z = 0; z < maze.getRows(); z++) {
                for (int x = 0; x < maze.getColumns(); x++) {
                    // generate the intersection
                    if (maze.getCellValue(x, z) != 0) {
                        populateGridCell(x, y, z, fillMaterial);
                    }

                    // todo do I need to populate all cells again here just in case they are 0?
                    if (maze.cellPointsTo(x, z, CellDirection.SOUTH)) {
                        removeSouthWall(x, y, z, fillMaterial);
                    }

                    if (maze.cellPointsTo(x, z, CellDirection.EAST)) {
                        removeEastWall(x, y, z, fillMaterial);
                    }
                }
            }
        }
    }

    // Helper functions

    // populate a specific cell in the grid
    private void populateGridCell(int cellX, int blockY, int cellZ, Material material) {
        for (int row = 0; row < pathWidth; row++) {
            for (int col = 0; col < pathWidth; col++) {
                blockGrid[blockY][(cellZ * cellToBlockOffset + wallThickness) + row][(cellX * cellToBlockOffset)
                        + wallThickness + col] = material;
            }
        }
    }

    private void removeEastWall(int cellX, int blockY, int cellZ, Material material) {
        for (int row = 0; row < pathWidth; row++) {
            for (int col = 0; col < wallThickness; col++) {
                // x axis is EAST
                blockGrid[blockY][(cellZ * cellToBlockOffset + wallThickness)
                        + row][(cellX * cellToBlockOffset + wallThickness) + pathWidth + col] = material;
            }
        }
    }

    private void removeSouthWall(int cellX, int blockY, int cellZ, Material material) {
        for (int row = 0; row < wallThickness; row++) {
            for (int col = 0; col < pathWidth; col++) {
                // z axis is SOUTH
                blockGrid[blockY][(cellZ * cellToBlockOffset + wallThickness) + pathWidth
                        + row][(cellX * cellToBlockOffset + wallThickness) + col] = material;
            }
        }
    }
}