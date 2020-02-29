package net.comorevi.plugin.worldgenerator;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import net.comorevi.plugin.cosseworldgenerator.CoSSeWorldGenerator;
import org.iq80.leveldb.util.FileUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class WorldGenerator extends PluginBase {

    @Override
    public void onEnable() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        File dir = new File(this.getServer().getFilePath() + File.separator + "worlds" + File.separator + "resource");
        if (calendar.get(Calendar.HOUR_OF_DAY) != 0 && dir.exists()) {
            loadLevel();
            return;
        }

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_FOREST);
                break;
            case Calendar.MONDAY:
            case Calendar.THURSDAY:
                generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_DESERT);
                break;
            case Calendar.TUESDAY:
                generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_BIRCH_FOREST);
                break;
            case Calendar.WEDNESDAY:
            case Calendar.SATURDAY:
                generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_MINE);
                break;
            case Calendar.FRIDAY:
                generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_TAIGA_FOREST);
                break;

                default:
                    generateLevel(CoSSeWorldGenerator.GENERATE_TYPE_FOREST);
                    break;
        }
    }

    private void generateLevel(int type) {
        Long seed = new Random().nextLong();
        Class<? extends Generator> generator = Generator.getGenerator(type);
        getServer().generateLevel("resource", seed, generator);
    }

    private void loadLevel() {
        getServer().loadLevel("resource");
    }
}
