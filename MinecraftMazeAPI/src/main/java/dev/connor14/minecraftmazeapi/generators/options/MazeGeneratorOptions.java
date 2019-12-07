package dev.connor14.minecraftmazeapi.generators.options;

public class MazeGeneratorOptions {

    private int pathWidth = 1;
    private int wallThickness = 1;
    private int wallHeight = 2;
    private int layerCount = 1;
    private int stairwellCount = 1;

    public MazeGeneratorOptions() {

    }

    public int getWallHeight() {
        return wallHeight;
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public int getLayerCount(){
        return layerCount;
    }

    public int getStairwellCount() {
        return stairwellCount;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
    }

    public void setWallThickness(int wallThickness) {
        this.wallThickness = wallThickness;
    }

    public void setWallHeight(int wallHeight) {
        this.wallHeight = wallHeight;
    }

    public void setLayerCount(int layerCount) {
        this.layerCount = layerCount;
    }

    public void setStairwellCount(int stairwellCount) {
        this.stairwellCount = stairwellCount;
    }
}
