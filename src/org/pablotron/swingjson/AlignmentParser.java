package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonPrimitive;

public final class AlignmentParser {
  private AlignmentParser() {}

  private static final Map<String, Float> lut = new HashMap<String, Float>() {{
    put("left", 0.0f);
    put("right", 1.0f);
    put("center", 0.5f);
    put("top", 0.0f);
    put("bottom", 1.0f);
  }};

  public static float parse(final JsonPrimitive el) throws Exception {
    if (el.isString()) {
      final String key = el.getAsString();

      if (!lut.containsKey(key))
        throw new Exception("invalid alignment string: " + key);

      return lut.get(key);
    } else if (el.isNumber()) {
      return el.getAsFloat();
    } else {
      // invalid alignment type
      throw new Exception("invalid alignment: " + el);
    }
  }
};
