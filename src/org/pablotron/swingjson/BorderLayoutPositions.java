package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import com.google.gson.JsonObject;

public final class BorderLayoutPositions {
  private BorderLayoutPositions() {}

  private static Map<String, String> lut = new HashMap<String, String>() {{
    put("center", CENTER);
    put("line-start", LINE_START);
    put("line-end", LINE_END);
    put("page-start", PAGE_START);
    put("page-end", PAGE_END);
  }};

  protected static String get(final JsonObject config) throws Exception {
    if (!config.has("position"))
      return CENTER;

    final String key = config.get("position").getAsString();
    if (!lut.containsKey(key))
      throw new Exception("unknown border layout position: " + key);

    return lut.get(key);
  }
};
