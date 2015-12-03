package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static javax.swing.BoxLayout.*;

public final class BoxLayoutAxisParser {
  private BoxLayoutAxisParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("line", LINE_AXIS);
    put("page", PAGE_AXIS);
    put("x",  X_AXIS);
    put("y", Y_AXIS);
  }};

  public static int parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown box layout axis: " + s);
    return lut.get(s);
  }
};
