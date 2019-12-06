package dev.connor14.minecraftmazeapi.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

// Modified from Source:
// https://www.spigotmc.org/threads/checking-if-the-player-direction-is-north.316037/#post-2979625
public enum CardinalDirection {
    SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST, WEST, SOUTHWEST;

    private static final CardinalDirection[] directions = {SOUTH, EAST, NORTH, WEST};
    private static final CardinalDirection[] doubleDirection = {SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST, WEST, SOUTHWEST};

    public static CardinalDirection getDirection(Location location) {
        return getDirection(location.getYaw());
    }

    public static CardinalDirection getDoubleDirection(Location location) {
        return getDoubleDirection(location.getYaw());
    }

    // yaw:
    // south = 0
    // east = -90
    // north = -180
    // west = -270
    public static CardinalDirection getDirection(float yaw) {
        return directions[Math.abs(((int) (yaw - 45F) % 360) / 90)];
    }

    public static CardinalDirection getDoubleDirection(float yaw) {
        return doubleDirection[Math.abs(((int) (yaw - 22.5F) % 360) / 45)];
    }

    public static CardinalDirection getDirection(BlockFace face) {
        CardinalDirection direction = CardinalDirection.NORTH;

        switch (face) {
            case NORTH:
                direction = CardinalDirection.NORTH;
                break;
            case EAST:
                direction = CardinalDirection.EAST;
                break;
            case SOUTH:
                direction = CardinalDirection.SOUTH;
                break;
            case WEST:
                direction = CardinalDirection.WEST;
                break;
        }

        return direction;
    }
}

