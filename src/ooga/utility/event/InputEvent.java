package ooga.utility.event;

public class InputEvent {

  private javafx.scene.input.InputEvent ie;

  public InputEvent(javafx.scene.input.InputEvent ie) {
    this.ie = ie;
  }

  public javafx.scene.input.InputEvent event() {
    return this.ie;
  }

}
