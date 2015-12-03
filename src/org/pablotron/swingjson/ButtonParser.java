package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import com.google.gson.JsonObject;

public final class ButtonParser implements ComponentParser {
  private final ButtonType type;

  public ButtonParser(final ButtonType type) {
    this.type = type;
  }

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String text = el.has("text") ? context.getText(el.get("text").getAsString()) : "";
    final AbstractButton b = type.create(text);

    // init common component attributes
    ContextParser.init_component(context, el, b);

    // add action
    if (el.has("action")) {
      b.setActionCommand(el.get("action").getAsString());
    }

    // add mnemonic
    if (el.has("mnemonic")) {
      b.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(
        el.get("mnemonic").getAsString().codePointAt(0)
      ));
    }

    // add vertical text position
    if (el.has("vertical-text-position")) {
      b.setVerticalTextPosition(TextPosition.parse(
        el.get("vertical-text-position").getAsString()
      ));
    }

    // add horizontal text position
    if (el.has("horizontal-text-position")) {
      b.setHorizontalTextPosition(TextPosition.parse(
        el.get("horizontal-text-position").getAsString()
      ));
    }

    // handle selected
    if (el.has("selected")) {
      b.setSelected(el.get("selected").getAsBoolean());
    }

    // handle button group
    if (el.has("button-group")) {
      context.getButtonGroup(el.get("button-group").getAsString()).add(b);
    }

    // return button
    return b;
  }
};
