package program.system;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.Program;
import program.ui.elements.ImageLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * A class for parsing config files<br>
 * <p>
 *  This class parses a config file at a given file path and returns its values as a variable to be used by the program
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 *
 * @see ImageLayer
 * <br>
 */

public class FileTask extends Task<Boolean>
{
    JSONObject config;
    String path;
    List<ImageLayer> layers;
    int mode;

    // 'extremely bad practice but not my beer :-)' - Ron Leonard Pudwitz, a.d. 2019
    final int layerSize = 1000;

    public void addConfig(JSONObject config)
    {
        this.config = config;
    }

    public JSONObject getConfig()
    {
        return config;
    }

    /**
     * Constructor to read from a config file
     * @param path
     */
    public FileTask(String path) {
        this.path = path;
        this.mode = 0;
        Program.debug("MODE=" + mode);
    }

    /**
     * Constructor to serialize layers and write a config to a file
     * @param path
     * @param layers
     */
    public FileTask(String path, List<ImageLayer> layers)
    {
        this.mode = 1;
        this.path = path;
        this.layers = layers;
    }

    /**
     * Constructor to save one image layer to a file
     * @param path
     * @param layer
     */
    public FileTask(String path, ImageLayer layer)
    {
        this.mode = 2;
        this.path = path;

        JSONObject c = new JSONObject();

        // We only have one layer, serializing it in a thread is unnecessary
        c.put("0", ImageLayer.toJSON(layer));

        this.addConfig(c);
    }

    /**
     * Invokes the writing procedure
     * @return success of writing or not
     */
    boolean writeToFile() {
        Program.debug("Writing to " + path);
        assert path != null;    //make sure we have a path
        assert config != null;  //and a config file to write

        try (FileWriter file = new FileWriter(path)) {
            file.write(config.toJSONString());
            file.flush();
            Platform.runLater(() -> Program.ui.setStatus("Saved as " + path));

            return true;
        } catch (IOException e) {
            Platform.runLater(() -> Program.ui.setStatus("Could nto save as " + path));
            return false;
        }
    }

    /**
     * Invokes the reading procedure
     * @return success of reading or not
     */
    boolean readFromFile() {
        assert path != null;

        JSONParser parser = new JSONParser();

        try {
            File file = new File(path);
            FileInputStream stream = new FileInputStream(file);
            byte[] raw = new byte[(int) file.length()];
            stream.read(raw);
            stream.close();

            this.config = (JSONObject) parser.parse(new String(raw, StandardCharsets.UTF_8));
            return true;
        } catch (IOException | ParseException e) {
            Program.debug(e);
            return false;
        }
    }

    @Override
    protected Boolean call() throws Exception
    {
        // Loading JSON
        if (mode == 0)
        {
            readFromFile();
            JSONObject config = getConfig();

            for (Object key : config.keySet())
            {
                JSONObject l = (JSONObject) config.get(key);
                // Make sure to run import task with Platform.runLater, as it calls methods from the JavaFX Application thread and interacts with the scene graph
                Platform.runLater(() -> Program.ui.addLayer(ImageLayer.fromJSON(l)));
            }
            return true;
        }
        // Saving all layers to JSON
        else if (mode == 1)
        {
            JSONObject config = new JSONObject();
            int num = 0;

            for (ImageLayer layer : layers)
            {
                // save as "index" = "layer"
                config.put(String.valueOf(num), ImageLayer.toJSON(layer));
                num++;
            }

            this.addConfig(config);
            this.writeToFile();

            return true;
        }
        // Saving one layer to JSON
        else if (mode == 2)
        {
            // Simply write to file as we added the config in the constructor
            this.writeToFile();
            return true;
        }

        return false;
    }
}