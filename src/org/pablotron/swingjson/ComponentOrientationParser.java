package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.ComponentOrientation;
import static java.awt.ComponentOrientation.*;

public final class ComponentOrientationParser {
  private ComponentOrientationParser() {}

  private static Map<String, ComponentOrientation> lut = new HashMap<String, ComponentOrientation>() {{
    put("left-to-right", LEFT_TO_RIGHT);
    put("right-to-left", RIGHT_TO_LEFT);
  }};

  public static ComponentOrientation parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown component orientation: " + s);

    return lut.get(s);
  }
};
