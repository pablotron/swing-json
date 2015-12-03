package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import com.google.gson.JsonObject;

public final class RigidAreaParser implements ComponentParser {
  protected RigidAreaParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    return Box.createRigidArea(SizeParser.parse(el.getAsJsonArray("size")));
  }
};
