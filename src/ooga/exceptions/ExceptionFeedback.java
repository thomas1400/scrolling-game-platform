package ooga.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExceptionFeedback {

  public static final int MESSAGE_LINE_LENGTH = 200;

  /**
   * Throws an exception dialog to the user with a given exception and message.
   * Throws the exception, halting program execution with non-zero exit code.
   *
   * @param e       Exception to throw
   * @param message message to include
   */
  public static void throwBreakingException(Exception e, String message) {
    throwHandledException(e, message);
    throw new FeedbackRuntimeException(e);
  }

  /**
   * Throws an exception dialog to the user with a given exception and message.
   *
   * @param e Exception to throw
   * @param message message to include
   */
  public static void throwHandledException(Exception e, String message) {
    JOptionPane.showConfirmDialog(new JFrame(),
        message,
        e.getClass().getSimpleName(), JOptionPane.DEFAULT_OPTION);
  }

  @Deprecated
  public static void throwException() {

  }

}
