package dev.connor14.minecraftmazeapi.generators;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.connor14.minecraftmazeapi.generators.options.MazeGeneratorOptions;
import dev.connor14.minecraftmazeapi.maze.IMaze;
import dev.connor14.minecraftmazeapi.utility.Cell;
import org.bukkit.Material;

import dev.connor14.minecraftmazeapi.utility.CellDirection;

// todo need to cache the position of the player so that the location doesn't
// change during long running actions?

public class DefaultMazeGenerator implements IMazeGenerator {

    private IMaze maze;
    private MazeGeneratorOptions options;

    // Additional Maze Settings
    private final int pathWidth;
    private final int wallThickness;
    private final int wallHeight;
    private final int layerCount;
    private final int stairwellCount;

    // Calculated Values
    private final int cellToBlockOffset;

    private final int layerHeight;
    private final int blockHeight;
    private final int blockRows;
    private final int blockColumns;

    // Generator Output
    private final Material[][][] blockGrid;

    public DefaultMazeGenerator(IMaze maze, MazeGeneratorOptions options) {
        this.maze = maze;
        this.options = options;

        this.pathWidth = options.getPathWidth();
        this.wallThickness = options.getWallThickness();
        this.wallHeight = options.getWallHeight();
        this.layerCount = options.getLayerCount();
        this.stairwellCount = options.getStairwellCount();

        cellToBlockOffset = pathWidth + wallThickness;

        layerHeight = (1 + wallHeight); // (floor + wallHeight)
        blockHeight = layerHeight * layerCount; // layerHeight * layerCount
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

        for (int layer = 0; layer < layerCount; layer++) {

            maze.generate(); // generate a maze for this layer

            // generate stairwell cells
            List<Cell> stairwellCells = new ArrayList<Cell>();
            for (int stairwell = 0; stairwell < stairwellCount; stairwell++) {
                stairwellCells.add(new Cell(maze.getRandomColumn(), maze.getRandomRow()));
            }

            Material fillMaterial = Material.OAK_WOOD;
            for (int y = layer * layerHeight; y < (layer * layerHeight) + layerHeight; y++) {
                // floor
                if (y == layer * layerHeight) {
                    fillMaterial = Material.OAK_WOOD;
                } else {// passages
                    fillMaterial = Material.AIR;
                }

                for (int z = 0; z < maze.getRows(); z++) {
                    for (int x = 0; x < maze.getColumns(); x++) {

                        // create the stair in the floor


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

            // put holes in the floor based on the cells. Put at layer * layerHeight
            for (int stairwell = 0; stairwell < stairwellCount; stairwell++) {
                Cell cell = stairwellCells.get(stairwell);
                populateGridCell(cell.x, layer * layerHeight, cell.z, Material.AIR);
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