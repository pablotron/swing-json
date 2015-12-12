package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static java.awt.GridBagConstraints.*;

public final class GridBagFillParser {
  private GridBagFillParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("none", NONE);
    put("horizontal", HORIZONTAL);
    put("vertical", VERTICAL);
    put("both", BOTH);
  }};

  protected static int parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown fill constraint: " + s);

    return lut.get(s);
  }
};
