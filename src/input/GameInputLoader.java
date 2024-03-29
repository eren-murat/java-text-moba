package input;

import champion.Champion;
import fileio.FileSystem;

import java.util.ArrayList;
import java.util.List;

public class GameInputLoader {
    private final String inputPath;
    private final String outputPath;

    public GameInputLoader(final String inputPath, final String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public final GameInput load() {
        int roundsNum = 0;
        int playersNum = 0;
        int mapLength = 0;
        int mapWidth = 0;
        List<String> terrainTypes = new ArrayList<String>();
        ArrayList<ArrayList<String>> championsOrder = new ArrayList<ArrayList<String>>();
        List<String> roundsOrder = new ArrayList<String>();

        try {
                FileSystem fs = new FileSystem(inputPath, outputPath);
                mapLength = fs.nextInt();
                mapWidth = fs.nextInt();
                for (int i = 0; i < mapLength; ++i) {
                    terrainTypes.add(fs.nextWord());
                }

                playersNum = fs.nextInt();

                for (int i = 0; i < playersNum; ++i) {
                    ArrayList<String> currChampion = new ArrayList<String>();
                    currChampion.add(fs.nextWord());
                    currChampion.add(fs.nextWord());
                    currChampion.add(fs.nextWord());
                    championsOrder.add(currChampion);
                }

                roundsNum = fs.nextInt();

                for (int i = 0; i < roundsNum; ++i) {
                    roundsOrder.add(fs.nextWord());
                }

                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return new GameInput(terrainTypes, championsOrder, roundsOrder);
    }

    public final void write(final ArrayList<Champion> champions) {
        try {
            FileSystem fout = new FileSystem(inputPath, outputPath);

            for (Champion currChampion : champions) {
                fout.writeWord(currChampion.printFinalStats());
                fout.writeNewLine();
            }

            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
