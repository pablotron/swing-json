package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.KeyStroke;
import javax.swing.JMenuItem;
import com.google.gson.JsonObject;

public final class MenuItemParser implements ComponentParser {
  protected MenuItemParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    // check for required parameter
    if (!el.has("text"))
      throw new Exception("missing menu parameter: text");

    // create result
    final JMenuItem r = new JMenuItem(context.getText(
      el.get("text").getAsString()
    ));

    ComponentParsers.init_component(context, el, r);

    // set accelerator
    // FIXME: does this belong in init_component?
    if (el.has("accelerator")) {
      r.setAccelerator(KeyStroke.getKeyStroke(
        el.get("accelerator").getAsString()
      ));
    }

    // set armed
    if (el.has("armed"))
      r.setArmed(el.get("armed").getAsBoolean());

    // set selected
    if (el.has("selected"))
      r.setSelected(el.get("selected").getAsBoolean());

    // return result
    return r;
  }
};
