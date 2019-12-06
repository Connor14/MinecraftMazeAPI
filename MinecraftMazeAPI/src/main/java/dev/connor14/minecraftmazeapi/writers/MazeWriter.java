package dev.connor14.minecraftmazeapi.writers;

import dev.connor14.minecraftmazeapi.utility.CardinalDirection;
import dev.connor14.minecraftmazeapi.utility.PlacementPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public abstract class MazeWriter implements IMazeWriter {

    protected World world;
    protected int locationX;
    protected int locationY;
    protected int locationZ;

    //private Vector direction;

    protected double rotationOffset;

    protected PlacementPosition mazePlacement;

    protected int xOffset;
    protected int zOffset;

    public MazeWriter(Location location, CardinalDirection facing, PlacementPosition placementPosition){
        world = location.getWorld();
        locationX = location.getBlockX();
        locationY = location.getBlockY();
        locationZ = location.getBlockZ();

        this.mazePlacement = placementPosition;

        // negative degrees are clockwise
        // 0 degree rotation is to SOUTH EAST of player
        // we want the maze to be to the front left of the player
        switch(facing) {
            case NORTH:
                rotationOffset = Math.toRadians(180);
                break;
            case EAST:
                rotationOffset = Math.toRadians(90);
                break;
            case SOUTH:
                rotationOffset = Math.toRadians(0);
                break;
            case WEST:
                rotationOffset = Math.toRadians(-90);
                break;
            default:
                rotationOffset = 0;
                break;
        }

        //direction = location.getDirection();

        // Calculate rotation angle:
        // Source:
        // https://answers.unity.com/questions/181867/is-there-way-to-find-a-negative-angle.html
        //Vector mazeDirection = new Vector(0, 0, 1);
        //double angleRadians = mazeDirection.angle(direction);
        //double angleDegrees = Math.toDegrees(angleRadians);

        // Source: https://stackoverflow.com/a/29865963/1984712
        //double roundedDegrees = Math.round(angleDegrees / 90.0) * 90.0;

        //Vector cross = mazeDirection.crossProduct(direction);
        //if (cross.getY() < 0) {
        //	roundedDegrees = -roundedDegrees;
        //}

        //double adjustedDegrees = roundedDegrees - 90; // rotate counter-clockwise
        //rotationOffset = Math.toRadians(adjustedDegrees);

        //Bukkit.broadcastMessage(angleDegrees + " " + adjustedDegrees);
    }

    public abstract void write(Material[][][] blocks);

    protected void placeSingleBlock(int x, int y, int z, Material material) {
        // original maze generation placed blocks to the south and east of the player
        // now we rotate each block's position using vectors (alternatively we could
        // rotate the array)
        // this rotation works to put the maze on the front-right of the player

        // use this to adjust the positioning
        // put the maze 1 block in front of the player and adjust for the floor (-1)
        Vector vector = new Vector(x, y - 1, z);
        vector.rotateAroundAxis(new Vector(0, 1, 0), rotationOffset); // rotate around Y axis

        world.getBlockAt(new Location(world, locationX, locationY, locationZ).add(vector)).setType(material);
    }
}
