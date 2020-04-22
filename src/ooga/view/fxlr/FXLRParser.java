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
 * TODO : clean up code
 * TODO : add package checking to class instantiation
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
  private static final String STYLECLASS_ATTR_TAG = "style";
  private static final String ACTION_EVENT_TAG = "actionTag";
  private static final char RELATIVE_SIZE_CHAR = '%';
  private static final String WIDTH_STRING = "width";
  private static final String HEIGHT_STRING = "height";
  private static final String DYNAMIC_UI_PACKAGE = "ooga.view.dynamicUI";


  private FXGraphBuilder gb;
  private List<String> packages;

  public FXLRParser() { }

  public Screen loadFXLRLayout(Screen root, File file) throws FileNotFoundException {
    packages = new ArrayList<>();
    gb = new FXGraphBuilder();
    gb.setRoot(root);

    Scanner s = new Scanner(file);

    while (s.hasNextLine()) {
      String line = s.nextLine().strip().trim();
      String[] words = line.split("\\s+");

      // skip empty lines
      if (line.length() <= 0) {
        continue;
      }

      // Skip comments
      if (line.length() >= 3 && line.substring(0, 3).equals(COMMENT_STRING)) {
        continue;
      }

      // load packages
      if (line.charAt(0) == PACKAGE_OPEN_CHAR) {
        loadPackage(line);
        continue;
      }

      // instantiate nodes
      if (line.charAt(0) == NODE_OPEN_CHAR) {
        // make new node
        instantiateNode(line);
      }

      if (line.charAt(0) == CHILDREN_CLOSE_CHAR) {
        gb.closeBranch();
      }
    }

    s.close();
    return gb.getRoot();
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
        Node node;
        if (packageName.equals(DYNAMIC_UI_PACKAGE)) {
          node = gb.getRoot()
              .getDynamicUIElement(className);
          if (node == null) {
            continue;
          }
        } else {
          node = (Node) Class.forName(packageName + "." + className).getDeclaredConstructor()
              .newInstance();
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
        //FIXME
        e.printStackTrace();
      } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {}
    }
    //FIXME TAKE OUT PRINTING THE STACK TRACE
    (new ClassNotFoundException("Class not found for classname " + className)).printStackTrace();
  }

  private void setAttributes(Node node, String attrLine) {
    // set each attribute
    for (String item : attrLine.split(",")) {
      // find the setter method
      String[] split = item.split(Character.toString(ATTR_EQUALITY_CHAR));
      String attr = split[0].replace(" ", "");
      String[] argStrings = split[1].split("\\s+");

      // special case for setting style class
      if (attr.equals(STYLECLASS_ATTR_TAG)) {
        node.getStyleClass().add(argStrings[0]);
        return;
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
      }

      try {
        String setterName =
            "set" + attr.toUpperCase().substring(0, 1) + attr.substring(1);
        Method[] methods = node.getClass().getMethods();
        for (Method method : methods) {
          if (method.getName().equals(setterName)) {
            Object[] args = getArgsFromStrings(node, attr, argStrings, method.getParameterTypes());
            method.invoke(node, args);
          }
        }
      } catch (Exception e) {
        //FIXME
        e.printStackTrace();
        System.out.println("Couldn't set attribute " + attr);
      }
    }
  }

  private Object[] getArgsFromStrings(Node node, String attr, String[] argStrings,
      Class[] parameterTypes) {
    Object[] args = new Object[argStrings.length];

    // Possible values of args include:
    // Integer, Double, String, Pos.ALIGNMENT, Insets (need to create from Integer/Double if needed)

    for (int i = 0; i < args.length; i++) {
      String argString = argStrings[i];
      Object arg = null;
      if (parameterTypes[i].equals(double.class)) {
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
      }
      if (parameterTypes[i].equals(int.class)) {
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
      }
      if (parameterTypes[i].equals(Pos.class)) {
        arg = Pos.valueOf(argString);
      }
      if (parameterTypes[i].equals(String.class)) {
        if (isTextAttribute(node, attr)) {
          arg = gb.getRoot().getResource(argString);
        } else {
          arg = argString;
        }
      }
      if (parameterTypes[i].equals(Insets.class)) {
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
      }
      if (parameterTypes[i].equals(Node.class)) {
        ImageView imageView = new ImageView(new Image(new File(gb.getRoot().getResource(argString)).toURI().toString()));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        arg = imageView;
      }
      args[i] = arg;
    }

    return args;
  }

  private boolean isWidthWiseAttribute(Node node, String attr) {
    return attr.toLowerCase().contains(WIDTH_STRING) ||
        (node.getClass().getSimpleName().equals("HBox") && attr.contains("spacing")) ||
        attr.contains("X");
  }

  private boolean isHeightWiseAttribute(Node node, String attr) {
    return attr.toLowerCase().contains(HEIGHT_STRING) ||
        (node.getClass().getSimpleName().equals("VBox") && attr.contains("spacing")) ||
        attr.contains("Y");
  }

  private boolean isTextAttribute(Node node, String attr) {
    return attr.toLowerCase().contains("text");
  }

}
