package ooga.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


/**
 * Class that parses all of the commands given to it
 * and returns its match from the 'language', aka properties file
 * @author Dana Mulligan
 */

public class ResourceParser {

  private String resourcesPackage;
  private List<Entry<String, Pattern>> mySymbols; // note, it is a list because order matters (some patterns may be more generic)
  ResourceBundle resources;

  /**
   * Create a parser that initializes the specific resource package and a new arrayList mySymbols
   * @param resourcesPackageName the specific resource package being parsed
   */
  public ResourceParser (String resourcesPackageName, String filename) {
    resourcesPackage = resourcesPackageName;
    mySymbols = new ArrayList<>();
    resources = ResourceBundle.getBundle(resourcesPackage + filename);
    addPatterns();
  }

  /**
   * Adds the matched pairs
   */
  private void addPatterns() {
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  public List<String> getKeys(){
    return Collections.list(resources.getKeys());
  }

  /**
   * Returns language's type associated with the given text if one exists
   * @param text each specific string to determine what type of syntax it is
   * @return the language type or no match
   */
  public String getSymbol (String text) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    return ERROR;
  }

  /** Returns true if the given text matches the given regular expression pattern
   * @param text text we're searching for
   * @param regex list of choices to match to
   * @return if the text is in the file
   */
  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
