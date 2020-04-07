package ooga.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;

public class HomeScreen extends Screen {

  private static final String FONT_FAMILY = "Cambria";
  private static final List<String> BUTTON_TEXTS = List.of(
    "Start", "Statistics", "Help", "Change User"
  );

  private ScreenController controller;

  public HomeScreen(ScreenController controller) {
    this.controller = controller;
    initializeScreen();
    initializeLayout();
  }

  private void initializeLayout() {
    VBox layout = new VBox();
    layout.setAlignment(Pos.TOP_CENTER);

    Label title = new Label("Title");
    title.setFont(Font.font(FONT_FAMILY, 60));
    layout.getChildren().add(title);

    Rectangle filler = new Rectangle(780, 400);
    VBox.setMargin(filler, new Insets(10));
    filler.setFill(Color.GREY);
    layout.getChildren().add(filler);

    HBox menu = new HBox();
    menu.setAlignment(Pos.TOP_CENTER);
    menu.setPadding(new Insets(5));
    menu.setSpacing(5);
    for (String text : BUTTON_TEXTS) {
      Button button = new Button();
      button.setText(text);
      button.setOnAction(e-> controller.handleButtonPress());
      button.setPrefSize(100, 80);
      menu.getChildren().add(button);
    }
    layout.getChildren().add(menu);

    this.getChildren().add(layout);
  }

}
