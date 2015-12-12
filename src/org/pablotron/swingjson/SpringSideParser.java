package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static javax.swing.SpringLayout.*;

public final class SpringSideParser {
  private SpringSideParser() {}

  private static final Map<String, String> lut = new HashMap<String, String>() {{
    put("baseline", BASELINE);
    put("east", EAST);
    put("height", HEIGHT);
    put("horizontal-center", HORIZONTAL_CENTER);
    put("north", NORTH);
    put("south", SOUTH);
    put("vertical-center", VERTICAL_CENTER);
    put("west", WEST);
    put("width", WIDTH);
  }};

  protected static String parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown spring side constraint: " + s);

    return lut.get(s);
  }
};
