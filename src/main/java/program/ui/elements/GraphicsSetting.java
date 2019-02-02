package program.ui.elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import program.Program;

import java.util.Iterator;

/**
 * A panel of settings controlling an ImageLayers algorithms visual parameters
 * <p>
 *   A panel with elements to control the stroke width, color and colormode of an image.
 * </p>
 * @author Robin Buhlmann
 * @version 0.5
 */
public class GraphicsSetting extends BorderPane {
  /**
   * Enum holding the different color modes and their displayed names
   */
  public enum Type
  {
    SOLID
      {
        public String toString() { return "Solid"; }
      },
    GRADIENT_LINEAR
      {
        public String toString() { return "Linear Gradient"; }
      },
    ALTERNATING
      {
        public String toString() { return "Alternating"; }
      };

    /**
     * A toString method mandatory for all Types so our ChoiceBox can have some nicer names
     * @return name The name of the type
     */
    public abstract String toString();
  }

  /**
   * Current color mode
   */
  private Type current;

  /**
   * The currently used colors. Type.SOLID only used colors[0], the others use both
   */
  private Color[] colors;

  /**
   * The stroke width
   */
  private double strokeWidth;

  /**
   * Panel holding the stroke setting ui elements
   */
  BorderPane strokeSetting;

  /**
   * Slider that controls the thickness
   */
  private Slider strokeSlider;

  /**
   * Text field to manually enter the thickness
   */
  private TextField strokeTextField;

  /**
   * Choice box for selecting the color mode
   */
  private ChoiceBox<Type> choiceBox;

  /**
   * A vertical box.
   */
  private VBox vBox;

  /**
   * Both used color pickers
   */
  ColorPicker cp1, cp2;

  /**
   * Returns the current color mode
   * @return Color mode
   */
  public Type getMode()
  {
    return current;
  }

  /**
   * Returns an array with both current colors
   * @return A color array
   */
  public Color[] getColors()
  {
    return colors;
  }

  /**
   * Sets both colors
   * @param c1 Color1
   * @param c2 Color2
   */
  public void setColors(Color c1, Color c2)
  {
    colors[0] = c1;
    colors[1] = c2;

    if (cp1 != null && cp2 != null)
    {
      cp1.setValue(c1);
      cp2.setValue(c2);
    }
  }

  /**
   * Resets both colors
   */
  private void reset()
  {
    colors[0] = Color.BLACK;
    colors[1] = Color.BLACK;
  }

  /**
   * Sets a new stroke width
   * @param width The stroke width
   */
  public void setStrokeWidth(double width)
  {
    strokeWidth = width;
    strokeSlider.setValue(width);
    strokeTextField.setText(String.valueOf(width));
  }

  /**
   * Returns the current stroke width
   * @return
   */
  public double getStrokeWidth()
  {
    return strokeWidth;
  }

  /**
   * (Re)sets the color mode of a GraphicsSetting
   * @param type The color mode to be used
   */
  private void setColorSettingMode(Type type)
  {
    reset();

    current = type;

    vBox.getChildren().clear();

    vBox.getChildren().add(new Label("Color Mode:"));
    vBox.getChildren().add(choiceBox);

    BorderPane bp1 = new BorderPane();

    Label l1 = new Label();
    l1.setText("Color: ");

    cp1 = new ColorPicker();
    cp1.setValue(Color.BLACK);

    cp1.setOnAction(event -> {
      colors[0] = cp1.getValue();
      Program.ui.getSelectedLayer().redraw();
    });

    bp1.setLeft(l1);
    bp1.setCenter(cp1);

    vBox.getChildren().add(bp1);

    // Add another ColorPicker if we have a different color mode and adjust the first one accordingly
    if (type != Type.SOLID)
    {
      vBox.getChildren().add(new Separator());

      BorderPane bp2 = new BorderPane();

      Label l2 = new Label();

      cp2 = new ColorPicker();
      cp2.setValue(Color.BLACK);

      cp2.setOnAction(event -> {
        colors[1] = cp2.getValue();
        Program.ui.getSelectedLayer().redraw();
      });

      bp2.setLeft(l2);
      bp2.setCenter(cp2);

      if (type == Type.GRADIENT_LINEAR)
      {
        l1.setText("Start Color:");
        l2.setText("End Color:  ");
      }
      else if (type == Type.ALTERNATING)
      {
        l1.setText("Color #1:");
        l2.setText("Color #2:");
      }

      vBox.getChildren().add(bp2);
    }
  }

  /**
   * Serializes a GraphicsSetting to a JSON string object
   * @param settings GraphicsSettings object to be serialized
   * @return A JSON string
   */
  public static JSONObject toJSON(GraphicsSetting settings)
  {
    JSONObject object = new JSONObject();
    // Add the mode
    object.put("mode", settings.current.name());
    // Add the colors to the array
    JSONArray colors = new JSONArray();
    for (Color color : settings.colors)
    {
      colors.add(color.toString());
    }
    object.put("colors", colors);
    // Do not forget the stroke width!
    object.put("stroke", settings.strokeWidth);

    return object;
  }

  /**
   * Generates a GraphicsSetting from a given JSON string
   * @param config The JSON string
   * @return A GraphicsSetting customized according to the JSON string
   */
  public static GraphicsSetting fromJSON(JSONObject config)
  {
    GraphicsSetting graphics = new GraphicsSetting(Type.valueOf((String)config.get("mode")));

    // Get JSONArray containing colors and attach an iterator
    JSONArray colors = (JSONArray) config.get("colors");
    Iterator<String> iterator = colors.iterator();

    // Set colors and stroke width
    Color c1 = Color.web(iterator.next());
    Color c2 = Color.web(iterator.next());

    graphics.setColors(c1, c2);
    graphics.setStrokeWidth((Double) config.get("stroke"));
    Program.debug(graphics.getStrokeWidth());

    return graphics;
  }

  /**
   * Generates a GraphicsSetting
   * @param type The color type that is to be used
   */
  public GraphicsSetting(Type type)
  {
    // Setup color array, we have at max. 2 colors
    colors = new Color[2];

    // Setup the basics of BorderPane
    this.setPadding(new Insets(5,5,5,5));

    // Setup the stroke setting
    strokeSetting = new BorderPane();
    strokeSlider = new Slider(1,10,1);
    strokeTextField = new TextField();

    strokeSetting.setPadding(new Insets(0,0,5,0));
    BorderPane.setMargin(strokeTextField, new Insets(0,0,5,5));
    BorderPane.setMargin(strokeSlider, new Insets(0,0,5,0));

    strokeSlider.setMinorTickCount(10);
    strokeSlider.setShowTickLabels(true);
    strokeSlider.setShowTickMarks(true);

    strokeTextField.setMaxSize(50,25);
    strokeTextField.setText(String.valueOf(strokeSlider.getValue()));

    strokeSetting.setTop(new Label("Stroke Width:"));
    strokeSetting.setCenter(strokeSlider);
    strokeSetting.setRight(strokeTextField);
    strokeSetting.setBottom(new Separator());

    // Add an event handler to the stroke slider
    strokeSlider.setOnMouseReleased(event -> {
      this.strokeWidth = strokeSlider.getValue();
      strokeTextField.setText(String.valueOf(this.strokeWidth));
      Program.ui.getSelectedLayer().redraw();
    });

    // Add an event handler to the stroke text field
    strokeTextField.setOnAction(event -> {
      double sw = Double.valueOf(strokeTextField.getText());
      if (sw > 10) { sw = 10; }
      else if (sw < 1) { sw = 1; }

      this.strokeWidth = sw;

      strokeSlider.setValue(this.strokeWidth);
      Program.ui.getSelectedLayer().redraw();
    });

    // Setup the ChoiceBox for choosing the color mode
    choiceBox = new ChoiceBox<>();
    choiceBox.setPrefWidth(Double.MAX_VALUE); // Cheeky workaround to use instead of fitting in a ScrollPane

    // Populate the choiceBox
    ObservableList<Type> types = FXCollections.observableArrayList();
    types.addAll(Type.values());
    choiceBox.setItems(types);

    // Select the type
    choiceBox.getSelectionModel().select(type);


    // Add a listener to the choiceBox
    choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      setColorSettingMode(newValue);
      Program.ui.getSelectedLayer().redraw();
    });

    // Setup the VBox which will hold the color pickers
    vBox = new VBox();
    vBox.setPadding(new Insets(0,5,0,0));
    vBox.setSpacing(5.0);

    vBox.getChildren().add(choiceBox);

    // Add the elements to the pane
    this.setTop(strokeSetting);
    this.setCenter(vBox);

    // Populate the color array with initial values
    colors[0] = Color.BLACK;
    colors[1] = Color.BLACK;

    setColorSettingMode(choiceBox.getValue());
  }
}
