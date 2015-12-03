package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;

import java.awt.Component;
import javax.swing.JComponent;

import com.google.gson.JsonObject;

public final class LayoutParsers {
  private LayoutParsers() {}

  private static Map<String, LayoutParser> lut = new HashMap<String, LayoutParser>() {{
    put("null", new NullLayoutParser());
    put("flow", new FlowLayoutParser());
    put("border", new BorderLayoutParser());
  }};

  protected static LayoutParser get(final String key) throws Exception {
    if (key == null)
      return lut.get("null");

    if (!lut.containsKey(key))
      throw new Exception("unknown type: " + key);

    return lut.get(key);
  }
};
