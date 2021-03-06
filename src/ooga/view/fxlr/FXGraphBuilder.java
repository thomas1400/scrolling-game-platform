package ooga.view.fxlr;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.screen.Screen;

/**
 * Builds a JavaFX graph, handles children and branching for FXLRParser.
 */
class FXGraphBuilder {

  private Screen root;
  private Node parent;

  void addChild(Node node) {
    try {
      ObservableList<Node> children = (ObservableList<Node>) parent.getClass().getMethod("getChildren").invoke(parent);
      children.add(node);
    } catch (Throwable e) {
      ExceptionFeedback.throwBreakingException((Exception) e, "Child " + node.toString() + " unable to be "
          + "correctly added");
    }
  }

  void branchOn(Node node) {
    parent = node;
  }

  void closeBranch() {
    parent = parent.getParent();
  }

  Screen getRoot() {
    return root;
  }

  void setRoot(Screen root) {
    this.root = root;
    this.parent = root;
  }

}
