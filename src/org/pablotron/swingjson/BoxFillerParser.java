package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JComponent;
import com.google.gson.JsonObject;

public final class BoxFillerParser implements ComponentParser {
  protected BoxFillerParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JsonObject size = el.getAsJsonObject("size");
    final JComponent r = new Box.Filler(
      SizeParser.parse(size.getAsJsonArray("minimum")),
      SizeParser.parse(size.getAsJsonArray("preferred")),
      SizeParser.parse(size.getAsJsonArray("maximum"))
    );

    ComponentParsers.initJComponent(context, el, r);

    return r;
  }
};
