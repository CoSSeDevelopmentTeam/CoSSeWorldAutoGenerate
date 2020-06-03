package net.comorevi.np.cwg;

import cn.nukkit.Server;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.plugin.PluginBase;
import net.comorevi.np.cwg.generator.*;
import org.iq80.leveldb.util.FileUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class Main extends PluginBase {
    public static final int GENERATE_TYPE_FOREST = 10;
    public static final int GENERATE_TYPE_BIRCH_FOREST = 11;
    public static final int GENERATE_TYPE_TAIGA_FOREST = 12;
    public static final int GENERATE_TYPE_DESERT = 13;
    public static final int GENERATE_TYPE_MINE = 14;
    public static final int GENERATE_TYPE_PLAINS = 15;
    public static final int GENERATE_TYPE_JUNGLE = 16;
    public static final int GENERATE_TYPE_FLOWER_FOREST = 17;
    //public static final int GENERATE_TYPE_FUNNY = 30;
    public static final int GENERATE_TYPE_EMPTY = 31;

    @Override
    public void onLoad() {
        //resister generators
        Generator.addGenerator(Forest.class, "Forest", GENERATE_TYPE_FOREST);
        Generator.addGenerator(BirchForest.class, "Birch Forest", GENERATE_TYPE_BIRCH_FOREST);
        Generator.addGenerator(TaigaForest.class, "Taiga Forest", GENERATE_TYPE_TAIGA_FOREST);
        Generator.addGenerator(Desert.class, "Desert", GENERATE_TYPE_DESERT);
        Generator.addGenerator(Mine.class, "Mine", GENERATE_TYPE_MINE);
        Generator.addGenerator(Plains.class, "Plains", GENERATE_TYPE_PLAINS);
        Generator.addGenerator(Jungle.class, "Jungle", GENERATE_TYPE_JUNGLE);
        Generator.addGenerator(FlowerForest.class, "Flower Forest", GENERATE_TYPE_FLOWER_FOREST);
        Generator.addGenerator(Empty.class, "Empty", GENERATE_TYPE_EMPTY);
    }

    @Override
    public void onEnable() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        File dir = new File("./worlds/resource");
        if (calendar.get(Calendar.HOUR_OF_DAY) != 0 && dir.exists()) {
            loadLevel();
            return;
        }

        Server.getInstance().getScheduler().scheduleDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:
                        generateLevel(GENERATE_TYPE_FLOWER_FOREST);
                        break;
                    case Calendar.TUESDAY:
                        generateLevel(GENERATE_TYPE_BIRCH_FOREST);
                        break;
                    case Calendar.SUNDAY:
                    case Calendar.WEDNESDAY:
                        generateLevel(GENERATE_TYPE_FOREST);
                        break;
                    case Calendar.THURSDAY:
                        generateLevel(GENERATE_TYPE_DESERT);
                        break;
                    case Calendar.FRIDAY:
                        generateLevel(GENERATE_TYPE_TAIGA_FOREST);
                        break;
                    case Calendar.SATURDAY:
                        generateLevel(GENERATE_TYPE_JUNGLE);
                        break;
                }
            }
        }, 20, true);
    }

    private void generateLevel(int type) {
        if (new File("./worlds/resource").exists()) {
            if (getServer().getLevelByName("resource") != null) getServer().getLevelByName("resource").unload(true);
            FileUtils.deleteDirectoryContents(new File("./worlds/resource"));
        }
        Class<? extends Generator> generator = Generator.getGenerator(type);
        getServer().generateLevel("resource", new Random().nextLong(), generator);
    }

    private void loadLevel() {
        getServer().loadLevel("resource");
    }
}
