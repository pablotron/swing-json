package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class MenuParser implements ComponentParser {
  protected MenuParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    // check for required parameter
    if (!el.has("text"))
      throw new Exception("missing menu parameter: text");

    // create result
    final JMenu r = new JMenu(context.getText(el.get("text").getAsString()));

    ComponentParsers.init_component(context, el, r);

    // add children
    if (el.has("kids")) {
      for (JsonElement kid: el.getAsJsonArray("kids"))
        r.add(ComponentParsers.parse(context, kid.getAsJsonObject()));
    }

    // set accelerator
    // FIXME: does this belong in init_component?
    if (el.has("accelerator")) {
      r.setAccelerator(KeyStroke.getKeyStroke(
        el.get("accelerator").getAsString()
      ));
    }

    // set component orientation
    // FIXME: does this belong in init_component?
    if (el.has("component-orientation")) {
      r.setComponentOrientation(ComponentOrientationParser.parse(
        el.get("component-orientation").getAsString()
      ));
    }

    // set delay
    if (el.has("delay"))
      r.setDelay(el.get("delay").getAsInt());

    if (el.has("menu-location")) {
      final JsonObject o = el.get("menu-location").getAsJsonObject();
      r.setMenuLocation(o.get("x").getAsInt(), o.get("y").getAsInt());
    }

    // set popup menu visible
    if (el.has("popup-menu-visible"))
      r.setPopupMenuVisible(el.get("popup-menu-visible").getAsBoolean());

    // set selected
    if (el.has("selected"))
      r.setSelected(el.get("selected").getAsBoolean());

    // return result
    return r;
  }
};
