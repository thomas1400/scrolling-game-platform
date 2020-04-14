package ooga.view.fxlr;

import java.lang.reflect.InvocationTargetException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

class FXGraphBuilder {

  private Node root;
  private Node parent;

  FXGraphBuilder(Node root) {
    this.root = root;
    parent = root;
  }

  void addChild(Node node) {
    try {
      ObservableList<Node> children = (ObservableList<Node>) parent.getClass().getDeclaredMethod("getChildren").invoke(parent);
      children.add(node);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void branch(Node node) {
    parent = node;
  }

  void closeBranch() {
    parent = parent.getParent();
  }

  Node getRoot() {
    return root;
  }

}
