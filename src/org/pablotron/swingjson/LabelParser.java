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

    ComponentParsers.init_component(context, el, r);

    return r;
  }
};
