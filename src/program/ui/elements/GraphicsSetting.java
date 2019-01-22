package program.ui.elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import program.Program;

public class GraphicsSetting extends BorderPane {
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

  private Type current;

  private Color[] colors;
  private double strokeWidth;

  private BorderPane strokeSetting;
  private Slider strokeSlider;
  private TextField strokeTextField;

  private ChoiceBox<Type> choiceBox;
  private VBox vBox;

  public Type getMode()
  {
    return current;
  }

  public Color[] getColors()
  {
    return colors;
  }

  public void setColors(Color c1, Color c2) {
    colors[0] = c1;
    colors[1] = c2;
  }

  private void reset()
  {
    colors[0] = Color.BLACK;
    colors[1] = Color.BLACK;
  }

  public double getStrokeWidth()
  {
    return strokeWidth;
  }

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

    ColorPicker cp1 = new ColorPicker();
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

      ColorPicker cp2 = new ColorPicker();
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
    choiceBox = new ChoiceBox<Type>();
    choiceBox.setPrefWidth(Double.MAX_VALUE); // Cheeky workaround to use instead of fitting in a ScrollPane

    // Populate the choiceBox
    ObservableList<Type> types = FXCollections.observableArrayList();
    types.addAll(Type.values());
    choiceBox.setItems(types);

    // Select 'SOLID' by default
    choiceBox.getSelectionModel().select(0);

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
