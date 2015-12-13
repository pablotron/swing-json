package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JComboBox;
import com.google.gson.JsonObject;

public final class ComboBoxParser implements ComponentParser {
  protected ComboBoxParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final ComboBoxItems items = ComboBoxItems.parse(context, el);
    final JComboBox r = new JComboBox<String>(items.items);

    ComponentParsers.initJComponent(context, el, r);

    // set selected item
    if (items.index != -1)
      r.setSelectedIndex(items.index);

    // set editable
    if (el.has("editable") && el.get("editable").getAsBoolean())
      r.setEditable(true);

    // return result
    return r;
  }
};
