package program.system;

import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.GraphicsSetting;
import program.ui.elements.ImageLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class FileTask {
    JSONObject config;
    String path;

    //extremely bad practice but not my beer :-)
    final int layerSize = 1000;

    /**
     * Constructor to read from a config file
     * @param path
     */
    public FileTask(String path) {
        this.path = path;
    }

    /**
     * Constructor to write a config to a file
     * @param path
     * @param layers
     */
    public FileTask(String path, List<ImageLayer> layers) {
        this.path = path;
        this.config = new JSONObject();

        JSONArray confLayers = new JSONArray();

        //iterate over all layers
        for (ImageLayer l : layers) {
            //initialize the single layer that gets added to the list
            JSONObject layer = new JSONObject();

            //self explanatory
            layer.put("name", l.name);

            //add the type of algorithm
            layer.put("algorithm", l.getAlgorithm().name());

            //construct a json object for the color setting class
            JSONObject graphicsSettings = new JSONObject();

            //stuff the color mode inside
            graphicsSettings.put("type", l.getGraphicsSettings().getMode().name());

            JSONArray colors = new JSONArray();

            for (Color c : l.getGraphicsSettings().getColors()) {
                colors.add(c.toString());
            }

            //and add the color list to the colorSettings too
            graphicsSettings.put("colors", colors);

            //add the constructed layer element to the config object
            confLayers.add(layer);

            //add the graphicsettings to the layer object
            layer.put("graphicsettings", graphicsSettings);



            //last but not least also make a new object for the algorithm settings
            JSONObject algorithmSettings = new JSONObject();

            //who doesn't love iterating over hash maps right
            for (Map.Entry<String, AlgorithmSetting> s: l.getSettings().entrySet()) {
                algorithmSettings.put(s.getKey(), s.getValue().toString());
            }

            //and append it to the layer object again
            layer.put("algorithmsettings", algorithmSettings);


            //ok VERY LAST but still important is to add the layer object to the root json object that will get written
            confLayers.add(layer);
            config.put("layers", confLayers);
        }
    }

    /**
     * Invokes the writing procedure
     * @return success of writing or not
     */
    boolean writeToFile() {
        assert path != null;    //make sure we have a path
        assert config != null;  //and a config file to write

        try (FileWriter file = new FileWriter(path)) {
            file.write(config.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
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

            this.config = (JSONObject) parser.parse(new String(raw, "UTF-8"));

            return true;
        } catch (IOException | ParseException e) {
            return false;
        }
    }

    List<ImageLayer> getLayers() {

        JSONArray layers = (JSONArray) config.get("layers");
        List<ImageLayer> result = new ArrayList<>();

        for (Object layer : layers) {
            JSONObject currentLayer = (JSONObject) layer;

            //misc
            String name = (String) currentLayer.get("name");
            Algorithm alg = Algorithm.valueOf((String) currentLayer.get(("algorithm")));

            //construct the image layer
            ImageLayer tempLayer = new ImageLayer(name, layerSize, layerSize, alg);


            //graphics settings
            JSONObject graphicsSettings = (JSONObject) currentLayer.get("graphicsettings");

            GraphicsSetting.Type type = GraphicsSetting.Type.valueOf((String) graphicsSettings.get("type"));

            Color[] colors = {Color.BLACK, Color.BLACK};

            JSONArray configColors = (JSONArray) graphicsSettings.get("colors");
            Iterator<String> iterator = configColors.iterator();

            for (int i = 0; i < 2; i++) {
                colors[i] = Color.valueOf(iterator.next());
            }

            GraphicsSetting graphicsSetting = new GraphicsSetting(type);
            graphicsSetting.setColors(colors[0], colors[1]);

            tempLayer.setGraphicsSettings(graphicsSetting);


            //algorithm settings
            Fractal f = tempLayer.getFractal();

            JSONObject algorithmSettingsJSON = (JSONObject) currentLayer.get("algorithmsettings");

            for (Object o : algorithmSettingsJSON.keySet()) {
                String key = (String) o;
                Number value = (Number) algorithmSettingsJSON.get(key);

                f.updateSetting(key, value);
            }

            result.add(tempLayer);
        }

        return result;
    }

}