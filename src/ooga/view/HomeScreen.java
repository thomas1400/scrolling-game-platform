package ooga.view;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class HomeScreen extends Screen {

  public HomeScreen(ScreenController controller) {
    super(controller);
    loadLayout();
  }

}
