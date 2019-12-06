package dev.connor14.minecraftmazeapi.writers;

import dev.connor14.minecraftmazeapi.utility.CardinalDirection;
import dev.connor14.minecraftmazeapi.utility.PlacementPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DefaultMazeWriter extends MazeWriter {

    public DefaultMazeWriter(Location location, CardinalDirection facing, PlacementPosition placementPosition) {
        super(location, facing, placementPosition);
    }

    public void write(Material[][][] blocks) {
        // todo should this move to the constructor of MazeWriter?
        switch(mazePlacement) {
            case LEFT: // shift along x axis
                xOffset = 0;
                zOffset = 1; //  + 1 to place in front of player
                break;
            case RIGHT: // shift along x axis
                xOffset = -blocks[0][0].length + 1; // shift left 1 to account for 0 index
                zOffset = 1;
                break;
            case MIDDLE: // shift along x axis
                xOffset = (-blocks[0][0].length + 1) / 2;
                zOffset = 1;
                break;
            case CENTER: // x and z axis
                xOffset = Math.floorDiv(-blocks[0][0].length + 1, 2);
                zOffset = Math.floorDiv(-blocks[0].length, 2) + 1;
                break;
        }

        for (int y = 0; y < blocks.length; y++) {
            for (int z = 0; z < blocks[0].length; z++) {
                for (int x = 0; x < blocks[0][0].length; x++) {

                    Material material = blocks[y][z][x];

                    if (material != null) {
                        placeSingleBlock(x + xOffset, y, z + zOffset, material);
                    }

                }
            }
        }
    }
}

