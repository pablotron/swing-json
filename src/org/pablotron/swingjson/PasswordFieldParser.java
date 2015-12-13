package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JPasswordField;
import com.google.gson.JsonObject;

public final class PasswordFieldParser implements ComponentParser {
  protected PasswordFieldParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JPasswordField r;

    // create result
    if (el.has("text") && el.has("cols")) {
      r = new JPasswordField(
        context.getText(el.get("text").getAsString()),
        el.get("cols").getAsInt()
      );
    } else if (el.has("text")) {
      r = new JPasswordField(
        context.getText(el.get("text").getAsString())
      );
    } else if (el.has("cols")) {
      r = new JPasswordField(
        el.get("cols").getAsInt()
      );
    } else {
      r = new JPasswordField();
    }

    ComponentParsers.init_component(context, el, r);

    // set echo char
    if (el.has("echo-char"))
      r.setEchoChar(el.get("echo-char").getAsString().charAt(0));

    // return result
    return r;
  }
};
