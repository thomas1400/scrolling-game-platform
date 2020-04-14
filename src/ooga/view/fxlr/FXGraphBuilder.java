package ooga.view.fxlr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import ooga.view.Screen;

class FXGraphBuilder {

  private Screen root;
  private Node parent;

  FXGraphBuilder() { }

  void addChild(Node node) {
    try {
      ObservableList<Node> children = (ObservableList<Node>) parent.getClass().getMethod("getChildren").invoke(parent);
      children.add(node);
    } catch (Throwable e) {
      e.printStackTrace();
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
