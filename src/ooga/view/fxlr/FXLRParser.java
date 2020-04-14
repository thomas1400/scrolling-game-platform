package ooga.view.fxlr;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import ooga.controller.ScreenController;
import ooga.view.Screen;

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
  private static final String ROOT_NAME = "root";
  private static final int ATTR_EQUALITY_CHAR = '=';
  private static final char PACKAGE_OPEN_CHAR = '<';
  private static final char PACKAGE_CLOSE_CHAR = '>';
  private static final String FONT_FAMILY = "Cambria";
  //private static final int LINE_DELIMITER = ';';

  private FXGraphBuilder gb;
  private List<String> packages;
  private ScreenController controller;

  public FXLRParser(ScreenController controller) {
    this.controller = controller;
  }

  public Screen loadFXLRLayout(File file) throws FileNotFoundException {
    packages = new ArrayList<>();
    gb = new FXGraphBuilder();

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
        try {
          Node node = (Node) Class.forName(packageName + "." + className).getDeclaredConstructor()
              .newInstance();

          // set attributes
          if (line.indexOf(' ') != -1 && line.indexOf(' ') < line.indexOf(NODE_CLOSE_CHAR)) {
            String attrLine = line.substring(line.indexOf(' ') + 1, line.indexOf(NODE_CLOSE_CHAR));
            setAttributes(node, attrLine);
          }

          gb.addChild(node);

          if (line.charAt(line.length() - 1) == CHILDREN_OPEN_CHAR) {
            gb.branchOn(node);
          }
        } catch (NoSuchMethodException e) {
          Screen root = (Screen) Class.forName(packageName + "." + className).getDeclaredConstructor(ScreenController.class).newInstance(controller);
          gb.setRoot(root);
        }

        return;
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {}
    }
    (new ClassNotFoundException("Class not found for classname " + className)).printStackTrace();
  }

  private void setAttributes(Node node, String attrLine) {
    System.out.println(Arrays.toString(attrLine.split(",")));

    // set each attribute
    for (String item : attrLine.split(",")) {
      // find the setter method
      String[] split = item.split(Character.toString(ATTR_EQUALITY_CHAR));
      String attr = split[0].replace(" ", "");
      String[] argStrings = split[1].split("\\s+");


      try {
        String setterName =
            "set" + attr.toUpperCase().substring(0, 1) + attr.substring(1);
        Method[] methods = node.getClass().getMethods();
        for (Method method : methods) {
          if (method.getName().equals(setterName)) {
            Object[] args = getArgsFromStrings(argStrings, method.getParameterTypes());
            method.invoke(node, args);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Couldn't set attribute " + attr);
      }
    }

  }

  private Object[] getArgsFromStrings(String[] argStrings, Class[] parameterTypes) {
    Object[] args = new Object[argStrings.length];
    System.out.println(Arrays.toString(parameterTypes));

    // Possible values of args include:
    // Integer, Double, String, Pos.ALIGNMENT, Insets (need to create from Integer/Double if needed)

    for (int i = 0; i < args.length; i++) {
      String argString = argStrings[i];
      Object arg = null;
      if (parameterTypes[i].equals(double.class)) {
        arg = Double.parseDouble(argString);
      }
      if (parameterTypes[i].equals(int.class)) {
        arg = Integer.parseInt(argString);
      }
      if (parameterTypes[i].equals(Pos.class)) {
        arg = Pos.valueOf(argString);
      }
      if (parameterTypes[i].equals(String.class)) {
        arg = argString;
      }
      if (parameterTypes[i].equals(Font.class)) {
        arg = new Font(FONT_FAMILY, Integer.parseInt(argString));
      }
      if (parameterTypes[i].equals(Paint.class)) {
        arg = Color.valueOf(argString);
      }
      args[i] = arg;
    }

    System.out.println(Arrays.toString(args));
    return args;
  }

}
