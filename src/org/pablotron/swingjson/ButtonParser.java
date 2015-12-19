package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.AbstractButton;
import com.google.gson.JsonObject;

public final class ButtonParser implements ComponentParser {
  private final ButtonType type;

  protected ButtonParser(final ButtonType type) {
    this.type = type;
  }

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String text = el.has("text") ? context.getText(el.get("text").getAsString()) : "";
    final AbstractButton b = type.createButton(text);

    // init common component attributes
    ComponentParsers.initAbstractButton(context, el, b);

    // return button
    return b;
  }
};
