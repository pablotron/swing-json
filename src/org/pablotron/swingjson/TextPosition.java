package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static javax.swing.SwingConstants.*;

public final class TextPosition {
  private TextPosition() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("bottom", BOTTOM);
    put("center", CENTER);
    put("east",  EAST);
    put("horizontal", HORIZONTAL);
    put("leading", LEADING);
    put("left", LEFT);
    put("next", NEXT);
    put("north", NORTH);
    put("north-east", NORTH_EAST);
    put("north-west", NORTH_WEST);
    put("previous", PREVIOUS);
    put("right", RIGHT);
    put("south", SOUTH);
    put("south-east", SOUTH_EAST);
    put("south-west", SOUTH_WEST);
    put("top", TOP);
    put("trailing", TRAILING);
    put("vertical", VERTICAL);
    put("west", WEST);
  }};

  public static int parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown position: " + s);
    return lut.get(s);
  }
};
