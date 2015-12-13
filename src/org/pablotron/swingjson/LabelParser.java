package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JLabel;
import com.google.gson.JsonObject;

public final class LabelParser implements ComponentParser {
  protected LabelParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String text = el.has("text") ? el.get("text").getAsString() : "";
    final JLabel r = new JLabel(context.getText(text));

    ComponentParsers.initJComponent(context, el, r);

    if (el.has("text-position")) {
      final JsonObject o = el.getAsJsonObject("text-position");

      if (o.has("vertical"))
        r.setVerticalTextPosition(get_position(o, "vertical"));
      if (o.has("horizontal"))
        r.setHorizontalTextPosition(get_position(o, "horizontal"));
    }

    if (el.has("label-align")) {
      final JsonObject o = el.getAsJsonObject("label-align");

      if (o.has("vertical"))
        r.setVerticalAlignment(get_position(o, "vertical"));
      if (o.has("horizontal"))
        r.setHorizontalAlignment(get_position(o, "horizontal"));
    }

    return r;
  }

  private static int get_position(
    final JsonObject o,
    final String key
  ) throws Exception {
    return TextPosition.parse(o.get(key).getAsString());
  }
};
