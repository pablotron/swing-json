package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JToolBar;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class ToolBarParser implements ComponentParser {
  protected ToolBarParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JToolBar r;

    if (el.has("text")) {
      r = new JToolBar(context.getText(el.get("text").getAsString()));
    } else {
      r = new JToolBar();
    }

    ComponentParsers.initJComponent(context, el, r);

    // set boarder-painted
    if (el.has("border-painted"))
      r.setBorderPainted(el.get("border-painted").getAsBoolean());

    // set floatable
    if (el.has("floatable"))
      r.setFloatable(el.get("floatable").getAsBoolean());

    // set orientation
    if (el.has("orientation")) {
      r.setOrientation(OrientationParser.parse(
        el.get("orientation").getAsString()
      ));
    }

    // set rollover
    if (el.has("rollover"))
      r.setRollover(el.get("rollover").getAsBoolean());

    if (el.has("kids")) {
      for (final JsonElement kid: el.getAsJsonArray("kids"))
        r.add(ComponentParsers.parse(context, kid.getAsJsonObject()));
    }

    // return result
    return r;
  }
};
