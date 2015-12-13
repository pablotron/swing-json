package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JTextField;
import com.google.gson.JsonObject;

public final class TextFieldParser implements ComponentParser {
  protected TextFieldParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JTextField r = new JTextField();

    ComponentParsers.init_component(context, el, r);

    // set value
    if (el.has("text"))
      r.setText(context.getText(el.get("text").getAsString()));

    // set value
    if (el.has("editable"))
      r.setEditable(el.get("editable").getAsBoolean());

    // set cols
    if (el.has("cols"))
      r.setColumns(el.get("cols").getAsInt());

    if (el.has("horizontal-alignment")) {
      r.setHorizontalAlignment(TextPosition.parse(
        el.get("horizontal-alignment").getAsString()
      ));
    }

    // return result
    return r;
  }
};
