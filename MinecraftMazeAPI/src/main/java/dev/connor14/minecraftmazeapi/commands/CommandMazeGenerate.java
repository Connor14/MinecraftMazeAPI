package dev.connor14.minecraftmazeapi.commands;

import dev.connor14.minecraftmazeapi.generators.DefaultMazeGenerator;
import dev.connor14.minecraftmazeapi.generators.IMazeGenerator;
import dev.connor14.minecraftmazeapi.generators.options.MazeGeneratorOptions;
import dev.connor14.minecraftmazeapi.maze.RectangularMaze;
import dev.connor14.minecraftmazeapi.utility.CardinalDirection;
import dev.connor14.minecraftmazeapi.utility.PlacementPosition;
import dev.connor14.minecraftmazeapi.writers.DefaultMazeWriter;
import dev.connor14.minecraftmazeapi.writers.MazeWriter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandMazeGenerate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 8) {
            return false;
        }

        // Maze type
        // Generic Maze settings
        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);

        // Generator settings
        int pathWidth = Integer.parseInt(args[2]);
        int wallThickness = Integer.parseInt(args[3]);
        int wallHeight = Integer.parseInt(args[4]);
        int layerCount = Integer.parseInt(args[5]);
        int stairCount = Integer.parseInt(args[6]);

        PlacementPosition placement = PlacementPosition.valueOf(args[7]);

        MazeGeneratorOptions options = new MazeGeneratorOptions();
        options.setPathWidth(pathWidth);
        options.setWallThickness(wallThickness);
        options.setWallHeight(wallHeight);
        options.setLayerCount(layerCount);
        options.setStairwellCount(stairCount);

        IMazeGenerator generator = new DefaultMazeGenerator(new RectangularMaze(rows, columns), options);

        MazeWriter writer = null;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            writer = new DefaultMazeWriter(player.getLocation(), CardinalDirection.getDirection(player.getLocation()), placement);
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender commandBlock = (BlockCommandSender) sender;

            Block block = commandBlock.getBlock();
            if (block.getType() == Material.COMMAND_BLOCK) {
                BlockFace facing = ((org.bukkit.block.data.Directional) block.getBlockData()).getFacing();
                writer = new DefaultMazeWriter(block.getLocation(), CardinalDirection.getDirection(facing), placement);
            } else {
                sender.sendMessage("This BlockCommandSender is not implemented yet");
                return false;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            sender.sendMessage("ConsoleCommandSender is not implemented yet");
            return false;
        } else {
            sender.sendMessage("Invalid sender");
            return false;
        }

        writer.write(generator.getBlocks());

        return true;
    }
}