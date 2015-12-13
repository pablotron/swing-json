package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class MenuBarParser implements ComponentParser {
  protected MenuBarParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JMenuBar r = new JMenuBar();

    ComponentParsers.initJComponent(context, el, r);

    // add children
    if (el.has("kids")) {
      for (JsonElement kid: el.getAsJsonArray("kids"))
        r.add(ComponentParsers.parse(context, kid.getAsJsonObject()));
    }

    // add help menu
    if (el.has("help-menu")) {
      final JsonObject o = el.get("help-menu").getAsJsonObject();
      r.setHelpMenu((JMenu) ComponentParsers.parse(context, o));
    }

    // return result
    return r;
  }
};
