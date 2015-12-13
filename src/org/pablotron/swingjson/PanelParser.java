package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JPanel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class PanelParser implements ComponentParser {
  protected PanelParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JPanel panel = new JPanel();
    LayoutParser layout = LayoutParsers.get(null);

    // shared component init
    ComponentParsers.initJComponent(context, el, panel);

    // set layout
    if (el.has("layout")) {
      layout = LayoutParsers.get(el.get("layout").getAsString());
      layout.set(panel, el);
    }

    // add children
    if (el.has("kids")) {
      for (final JsonElement kid: el.getAsJsonArray("kids")) {
        final JsonObject o = kid.getAsJsonObject();
        layout.add(context, panel, o, ComponentParsers.parse(context, o));
      }
    }

    // return result
    return panel;
  }
};
