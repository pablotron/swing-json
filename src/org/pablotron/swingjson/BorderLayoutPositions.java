package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.BorderLayout;
import com.google.gson.JsonObject;

public final class BorderLayoutPositions {
  private static Map<String, String> lut = new HashMap<String, String>() {{
    put("center", BorderLayout.CENTER);
    put("line-start", BorderLayout.LINE_START);
    put("line-end", BorderLayout.LINE_END);
    put("page-start", BorderLayout.PAGE_START);
    put("page-end", BorderLayout.PAGE_END);
  }};

  protected static String get(final JsonObject config) throws Exception {
    if (!config.has("position"))
      return BorderLayout.CENTER;

    final String key = config.get("position").getAsString();
    if (!lut.containsKey(key))
      throw new Exception("unknown border layout position: " + key);

    return lut.get(key);
  }
};