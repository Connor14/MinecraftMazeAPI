package dev.connor14.minecraftmazeapi.maze;

import dev.connor14.minecraftmazeapi.utility.CellDirection;

public interface IMaze {
    void generate();

    int getCellValue(int x, int z);
    int getRows();
    int getColumns();
    int getRandomRow();
    int getRandomColumn();
    boolean cellPointsTo(int x, int z, CellDirection cellDirection);
    boolean isValidCoordinate(int x, int z);
}
