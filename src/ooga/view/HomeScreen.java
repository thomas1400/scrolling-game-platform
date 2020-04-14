package ooga.view;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.view.factory.ControlFactory;

public class HomeScreen extends Screen {

  private static final List<String> BUTTON_TAGS = List.of(
    "start", "statistics", "help", "user-select"
  );

  public HomeScreen(ScreenController controller) {
    super(controller);
    //initializeLayout();
  }

  private void initializeLayout() {
    ControlFactory cf = new ControlFactory(PADDING);
    VBox layout = new VBox();
    layout.setAlignment(Pos.TOP_CENTER);

    Label title = new Label(resources.getString("title"));
    title.setFont(Font.font(FONT_FAMILY, 60));
    layout.getChildren().add(title);

    Rectangle splash = new Rectangle(780, 400);
    VBox.setMargin(splash, new Insets(10));
    splash.setFill(Color.GREY);
    splash.getStyleClass().add("home-screen-splash");
    layout.getChildren().add(splash);


    HBox menu = new HBox();
    menu.setAlignment(Pos.TOP_CENTER);
    menu.setPadding(new Insets(5));
    menu.setSpacing(5);
    for (String tag : BUTTON_TAGS) {
      Button button = cf.button(resources.getString(tag), BUTTON_FONT_SIZE, e->handleButtonPress(tag), 150, 80);
      menu.getChildren().add(button);
    }
    layout.getChildren().add(menu);

    this.getChildren().add(layout);
  }

}
