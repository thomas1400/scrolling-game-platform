package ooga.view.factory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ControlFactory {

  private static final String FONT_FAMILY = "Cambria";

  private double padding;

  public ControlFactory(double padding) {
    this.padding = padding;
  }

  public Button button(String text, int fontSize, EventHandler<ActionEvent> onAction,
      double prefWidth, double prefHeight) {

    Button button = new Button();

    button.setText(text);
    button.setFont(Font.font(FONT_FAMILY, fontSize));
    button.setOnAction(onAction);
    button.setPrefSize(prefWidth, prefHeight);

    return button;
  }

  public Label label(String text, int fontSize) {
    Label label = new Label(text);
    label.setFont(Font.font(FONT_FAMILY, fontSize));

    return label;
  }

  public void setMargin(Node node) {
    Insets margin = new Insets(padding);
    HBox.setMargin(node, margin);
    VBox.setMargin(node, margin);
  }

}
