package org.pablotron.swingjson;

import java.awt.Font;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class FontParser {
  private FontParser() {}

  protected static Font parse(
    final JsonElement el
  ) throws Exception {
    // TODO: add hash support
    return Font.decode(el.getAsString());
  }
};
