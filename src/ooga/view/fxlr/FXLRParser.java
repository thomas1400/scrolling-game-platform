package ooga.view.fxlr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

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
  private static final int LINE_DELIMITER = ';';

  private Pane root;
  private FXGraphBuilder gb;

  public FXLRParser() {
    root = new Pane();
    gb = new FXGraphBuilder(root);
  }

  public Node parse(File file) throws FileNotFoundException {
    Parent root = null;
    Parent parent = null;
    Scanner s = new Scanner(file);

    while (s.hasNext()) {
      String word = s.next();
      if (word.equals(COMMENT_STRING)) {
        do {
          word = s.next();
        } while (!word.equals(COMMENT_STRING));
        word = s.next();
      }
      System.out.println(word);

      if (word.charAt(0) == NODE_OPEN_CHAR) {
        // create new Node
        String className = word
            .replace(Character.toString(NODE_CLOSE_CHAR), "")
            .replace(Character.toString(LINE_DELIMITER), "")
            .replace(Character.toString(NODE_OPEN_CHAR), "");

        if (!className.equals(ROOT_NAME)) {
          try {
            Node node = (Node) Class.forName(className).getDeclaredConstructor().newInstance();
            gb.addChild(node);

            while (word.charAt(word.length()-1) != NODE_CLOSE_CHAR &&
                !word.substring(word.length() - 2).equals("" + NODE_CLOSE_CHAR + LINE_DELIMITER)) {
              word = s.next();
              if (word.indexOf(ATTR_EQUALITY_CHAR) >= 0) {
                String attr = word.substring(0, word.indexOf(ATTR_EQUALITY_CHAR));
                String val = word.substring(word.lastIndexOf(ATTR_EQUALITY_CHAR) + 1);
                System.out.println(attr + " = " + val);
              }
            }

          } catch (Exception e) {
            // TODO : robust error handling
            e.printStackTrace();
          }
        }
      }
    }

    s.close();
    return root;
  }

}
