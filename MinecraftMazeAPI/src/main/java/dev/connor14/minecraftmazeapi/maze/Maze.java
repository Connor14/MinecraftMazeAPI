package dev.connor14.minecraftmazeapi.maze;

import java.util.ArrayList;

import dev.connor14.minecraftmazeapi.utility.Cell;
import dev.connor14.minecraftmazeapi.utility.CellDirection;

// Based on: http://weblog.jamisbuck.org/2011/1/27/maze-generation-growing-tree-algorithm
public class Maze {

    private int rows;
    private int columns;

    private int[][] grid;
    private ArrayList<Cell> cells;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        grid = new int[rows][columns];
        cells = new ArrayList<Cell>();

        generateCellGrid();
    }

    private void generateCellGrid() {
        // select a random grid cell
        // subtract 1 so we don't go out of bounds

        // x is columns, z is rows
        cells.add(new Cell(getRandomColumn(), getRandomRow()));

        while (cells.size() > 0) {
            Cell sourceCell = chooseSourceCell();

            for (CellDirection cellDirection : CellDirection.shuffle()) {
                int nextX = sourceCell.x + CellDirection.changeX(cellDirection);
                int nextZ = sourceCell.z + CellDirection.changeZ(cellDirection);

                if (isValidCoordinate(nextX, nextZ) && grid[nextZ][nextX] == 0) {
                    grid[sourceCell.z][sourceCell.x] |= CellDirection.valueOf(cellDirection);
                    grid[nextZ][nextX] |= CellDirection.oppositeValue(cellDirection);

                    Cell destinationCell = new Cell(nextX, nextZ);
                    cells.add(destinationCell);
                    sourceCell = null;
                    break;
                }
            }

            if (sourceCell != null) {
                cells.remove(sourceCell); // cannot do anything more with this cell
            }
        }
    }

    // this is how we change maze generation schemes
    private Cell chooseSourceCell() {
        int method = (int) (Math.random() * 2);

        int index = 0;

        switch (method) {
            case 0: // random
                index = (int) (Math.random() * cells.size());
                break;
            case 1: // last
                index = cells.size() - 1;
        }

        return cells.get(index); // choose the last cell
    }

    public int getCellValue(int x, int z) {
        return grid[z][x];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getRandomRow() {
        return (int)(Math.random() * (rows - 1));
    }

    public int getRandomColumn() {
        return (int)(Math.random() * (columns - 1));
    }

    public boolean cellPointsTo(int x, int z, CellDirection cellDirection) {
        return (grid[z][x] & CellDirection.valueOf(cellDirection)) != 0;
    }

    public boolean isValidCoordinate(int x, int z) {
        return x >= 0 && z >= 0 && x < columns && z < rows;
    }
}
