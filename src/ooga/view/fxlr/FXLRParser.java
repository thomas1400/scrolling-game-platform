package ooga.view.fxlr;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.exceptions.ExceptionFeedback;
import ooga.view.screen.Screen;

/**
 * Parses .FXLR (FX Layout Representation) files and creates JavaFX node hierarchies.
 *
 * Parsing Syntax:
 * () enclose node type and attributes: (NodeType attr=val val val val, attr=val)
 * {} enclose children (Parent) { (Child); }
 * Lines that do not end with { or } end with semicolons;
 * Attribute values for size can be set absolutely (px, default) or relatively (%);
 *
 * Note: FXLR does not represent styling. FXLR nodes should be styled with external CSS.
 *
 */
public class FXLRParser {

  private static final String COMMENT_STRING = "###";
  private static final char NODE_OPEN_CHAR = '(';
  private static final char NODE_CLOSE_CHAR = ')';
  private static final char CHILDREN_OPEN_CHAR = '{';
  private static final char CHILDREN_CLOSE_CHAR = '}';
  private static final int ATTR_EQUALITY_CHAR = '=';
  private static final char PACKAGE_OPEN_CHAR = '<';
  private static final char PACKAGE_CLOSE_CHAR = '>';
  private static final String STYLE_CLASS_ATTR_TAG = "style";
  private static final String ACTION_EVENT_TAG = "actionTag";
  private static final char RELATIVE_SIZE_CHAR = '%';
  private static final String WIDTH_STRING = "width";
  private static final String HEIGHT_STRING = "height";
  private static final String DYNAMIC_UI_PACKAGE = "ooga.view.dynamicUI";


  private FXGraphBuilder gb;
  private List<String> packages;

  /**
   * Initialize an FXLR parser.
   */
  public FXLRParser() {
    gb = new FXGraphBuilder();
    packages = new ArrayList<>();
  }

  /**
   * Load layout to a provided Screen from an FXLR file.
   *
   * @param root Screen for which to set layout
   * @param file FXLR file
   * @throws FileNotFoundException if FXLR not found
   */
  public void loadFXLRLayout(Screen root, File file) throws FileNotFoundException {
    packages.clear();
    gb.setRoot(root);

    Scanner s = new Scanner(file);

    while (s.hasNextLine()) {
      String line = s.nextLine().strip().trim();

      // skip empty lines and comments
      if (line.length() <= 0 ||
          (line.length() >= 3 && line.substring(0, 3).equals(COMMENT_STRING))) {
        continue;
      }

      // load packages
      if (line.charAt(0) == PACKAGE_OPEN_CHAR) {
        loadPackage(line);
        continue;
      }

      // instantiate nodes
      if (line.charAt(0) == NODE_OPEN_CHAR) {
        instantiateNode(line);
      }

      if (line.charAt(0) == CHILDREN_CLOSE_CHAR) {
        gb.closeBranch();
      }
    }

    s.close();
  }

  private void loadPackage(String line) {
    packages.add(line.split("\\s+")[1].replace(Character.toString(PACKAGE_CLOSE_CHAR), ""));
  }

  private void instantiateNode(String line) {
    String className = line.split("\\s+")[0]
        .replace(Character.toString(NODE_CLOSE_CHAR), "")
        .replace(Character.toString(NODE_OPEN_CHAR), "");

    for (String packageName : packages) {
      try {
        Node node = getNodeForPackage(className, packageName);

        if (node == null) {
          continue;
        }

        // set attributes
        if (line.indexOf(' ') != -1 && line.indexOf(' ') < line.indexOf(NODE_CLOSE_CHAR)) {
          String attrLine = line.substring(line.indexOf(' ') + 1, line.indexOf(NODE_CLOSE_CHAR));
          setAttributes(node, attrLine);
        }

        gb.addChild(node);

        if (line.charAt(line.length() - 1) == CHILDREN_OPEN_CHAR) {
          gb.branchOn(node);
        }

        return;
      } catch (InvocationTargetException e) {
        ExceptionFeedback.throwHandledException(new FXLRException(), "Unknown exception in parsing FXLR.");
      } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {}
    }

    (new ClassNotFoundException("Class not found for classname " + className)).printStackTrace();
  }

  private Node getNodeForPackage(String className, String packageName)
      throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
    Node node;
    if (packageName.equals(DYNAMIC_UI_PACKAGE)) {
      node = gb.getRoot()
          .getDynamicUIElement(className);
    } else {
      node = (Node) Class.forName(packageName + "." + className).getDeclaredConstructor()
          .newInstance();
    }
    return node;
  }

  private void setAttributes(Node node, String attrLine) {
    // set each attribute
    for (String item : attrLine.split(",")) {
      // find the setter method
      String[] split = item.split(Character.toString(ATTR_EQUALITY_CHAR));
      String attr = split[0].replace(" ", "");
      String[] argStrings = split[1].split("\\s+");

      if (specialCaseHandled(node, attr, argStrings)) {
        return;
      }

      try {
        Method setter = getSetterForAttribute(node, attr);
        if (setter != null) {
          Object[] args = getArgsFromStrings(node, attr, argStrings, setter.getParameterTypes());
          setter.invoke(node, args);
        }
      } catch (Exception e) {
        ExceptionFeedback.throwHandledException(new FXLRException(),
            "Couldn't set attribute " + attr + " in " +
                gb.getRoot().getClass().getSimpleName() + " layout.");
      }
    }
  }

  private Method getSetterForAttribute(Node node, String attr) {
    String setterName =
        "set" + attr.toUpperCase().substring(0, 1) + attr.substring(1);
    Method[] methods = node.getClass().getMethods();
    for (Method method : methods) {
      if (method.getName().equals(setterName)) {
        return method;
      }
    }
    ExceptionFeedback.throwHandledException(new FXLRException(),
        "Couldn't find setter for attribute " + attr);
    return null;
  }

  private boolean specialCaseHandled(Node node, String attr, String[] argStrings) {
    // special case for setting style class
    if (attr.equals(STYLE_CLASS_ATTR_TAG)) {
      node.getStyleClass().add(argStrings[0]);
      return true;
    }

    // special case for setting control action tags
    if (attr.equals(ACTION_EVENT_TAG)) {
      try {
        node.addEventHandler(
            ActionEvent.ACTION,
            e -> gb.getRoot().handleButtonPress(argStrings[0])
        );
      } catch (Exception e) {
        System.out.println("Node " + node.toString() + " has no event handler.");
      }
      return true;
    }
    return false;
  }

  private Object[] getArgsFromStrings(Node node, String attr, String[] argStrings,
      Class[] parameterTypes) {
    Object[] args = new Object[argStrings.length];

    // Possible values of args include:
    // Integer, Double, String, Pos.ALIGNMENT, Insets

    for (int i = 0; i < args.length; i++) {
      String argString = argStrings[i];
      if (parameterTypes[i].equals(double.class)) {
        args[i] = parseDoubleArgument(node, attr, argString);
      }
      if (parameterTypes[i].equals(int.class)) {
        args[i] = parseIntegerArgument(node, attr, argString);
      }
      if (parameterTypes[i].equals(Pos.class)) {
        args[i] = Pos.valueOf(argString);
      }
      if (parameterTypes[i].equals(String.class)) {
        args[i] = parseStringArgument(node, attr, argString);
      }
      if (parameterTypes[i].equals(Insets.class)) {
        args[i] = parseInsetsArgument(argStrings);
      }
      if (parameterTypes[i].equals(Node.class)) {
        args[i] = parseNodeArgument(argString);
      }
    }

    return args;
  }

  private Object parseNodeArgument(String argString) {
    Object arg;
    ImageView imageView = new ImageView(new Image(new File(gb.getRoot().getResource(argString)).toURI().toString()));
    imageView.setFitWidth(20);
    imageView.setFitHeight(20);
    arg = imageView;
    return arg;
  }

  private Object parseInsetsArgument(String[] argStrings) {
    Object arg = null;
    if (argStrings.length == 4) {
      arg = new Insets(
          Double.parseDouble(argStrings[0]),
          Double.parseDouble(argStrings[1]),
          Double.parseDouble(argStrings[2]),
          Double.parseDouble(argStrings[3])
      );
    } else if (argStrings.length == 1) {
      arg = new Insets(
          Double.parseDouble(argStrings[0]));
    }
    return arg;
  }

  private Object parseStringArgument(Node node, String attr, String argString) {
    Object arg;
    if (isTextAttribute(node, attr)) {
      arg = gb.getRoot().getResource(argString);
    } else {
      arg = argString;
    }
    return arg;
  }

  private Object parseIntegerArgument(Node node, String attr, String argString) {
    Object arg = null;
    if (argString.charAt(argString.length()-1) == RELATIVE_SIZE_CHAR) {
      int temp = Integer.parseInt(argString.substring(0, argString.length()-1));
      if (isWidthWiseAttribute(node, attr)) {
        arg = (int) (temp / 100.0 * gb.getRoot().getPrefWidth());
      } else if (isHeightWiseAttribute(node, attr)) {
        arg = (int) (temp / 100.0 * gb.getRoot().getPrefHeight());
      }
    } else {
      arg = Integer.parseInt(argString);
    }
    return arg;
  }

  private Object parseDoubleArgument(Node node, String attr, String argString) {
    Object arg = null;
    if (argString.charAt(argString.length()-1) == RELATIVE_SIZE_CHAR) {
      double temp = Double.parseDouble(argString.substring(0, argString.length()-1));
      if (isWidthWiseAttribute(node, attr)) {
        arg = temp / 100.0 * gb.getRoot().getPrefWidth();
      } else if (isHeightWiseAttribute(node, attr)) {
        arg = temp / 100.0 * gb.getRoot().getPrefHeight();
      }
    } else {
      arg = Double.parseDouble(argString);
    }
    return arg;
  }

  private boolean isWidthWiseAttribute(Node node, String attr) {
    return attr.toLowerCase().contains(FXLRParser.WIDTH_STRING) ||
        (node.getClass().getSimpleName().equals("HBox") && attr.contains("spacing")) ||
        attr.contains("X");
  }

  private boolean isHeightWiseAttribute(Node node, String attr) {
    return attr.toLowerCase().contains(FXLRParser.HEIGHT_STRING) ||
        (node.getClass().getSimpleName().equals("VBox") && attr.contains("spacing")) ||
        attr.contains("Y");
  }

  private boolean isTextAttribute(Node node, String attr) {
    return attr.toLowerCase().contains("text");
  }

}
