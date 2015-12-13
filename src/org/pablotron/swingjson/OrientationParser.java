package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static javax.swing.SwingConstants.*;;

public final class OrientationParser {
  private OrientationParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("horizontal", HORIZONTAL);
    put("vertical", VERTICAL);
  }};

  protected static int parse(final String s) throws Exception {
    int r = HORIZONTAL;

    if (s != null) {
      if (!lut.containsKey(s))
        throw new Exception("unknown orientation: " + s);

      r = lut.get(s);
    }

    // return result
    return r;
  }
};
