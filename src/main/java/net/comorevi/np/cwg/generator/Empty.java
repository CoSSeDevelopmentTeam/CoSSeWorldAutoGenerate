package net.comorevi.np.cwg.generator;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import net.comorevi.np.cwg.Main;

import java.util.HashMap;
import java.util.Map;

public class Empty extends Generator {
    private ChunkManager level;

    public Empty(Map<String, Object> options) {
        //
    }

    @Override
    public int getId() {
        return Main.GENERATE_TYPE_EMPTY;
    }

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.level = chunkManager;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        //https://github.com/kake26s/EmptyWorld/blob/master/src/kake26s/emptyworld/EmptyGenerator.php
        if (chunkX != getSpawn().getFloorX() >> 4 || chunkZ != getSpawn().getFloorZ() >> 4) {
            return;
        } else {
            level.getChunk(chunkX, chunkZ).setBlock(0, getSpawn().getFloorY() - 1, 0, Block.STONE);
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        //
    }

    @Override
    public Map<String, Object> getSettings() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return "Empty";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 256, 0.5);
    }

    @Override
    public ChunkManager getChunkManager() {
        return level;
    }
}