package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.Box;
import com.google.gson.JsonObject;

public final class GlueParser implements ComponentParser {
  private GlueType type;

  protected GlueParser(final GlueType type) {
    this.type = type;
  }

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    return type.create();
  }
};
