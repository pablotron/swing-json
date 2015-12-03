package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import com.google.gson.JsonObject;

public final class ButtonParser implements ComponentParser {
  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String text = el.has("text") ? el.get("text").getAsString() : "";
    // TODO: icon
    final JButton b = new JButton(context.getText(text));

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
      b.setVerticalTextPosition(parse_text_position(
        el.get("vertical-text-position").getAsString()
      ));
    }

    // add horizontal text position
    if (el.has("horizontal-text-position")) {
      b.setHorizontalTextPosition(parse_text_position(
        el.get("horizontal-text-position").getAsString()
      ));
    }

    // return button
    return b;
  }

  private int parse_text_position(final String s) throws Exception {
    switch (s) {
    case "bottom":
      return javax.swing.SwingConstants.BOTTOM;
    case "center":
      return javax.swing.SwingConstants.CENTER;
    case "east":
      return javax.swing.SwingConstants.EAST;
    case "horizontal":
      return javax.swing.SwingConstants.HORIZONTAL;
    case "leading":
      return javax.swing.SwingConstants.LEADING;
    case "left":
      return javax.swing.SwingConstants.LEFT;
    case "next":
      return javax.swing.SwingConstants.NEXT;
    case "north":
      return javax.swing.SwingConstants.NORTH;
    case "north-east":
      return javax.swing.SwingConstants.NORTH_EAST;
    case "north-west":
      return javax.swing.SwingConstants.NORTH_WEST;
    case "previous":
      return javax.swing.SwingConstants.PREVIOUS;
    case "right":
      return javax.swing.SwingConstants.RIGHT;
    case "south":
      return javax.swing.SwingConstants.SOUTH;
    case "south-east":
      return javax.swing.SwingConstants.SOUTH_EAST;
    case "south-west":
      return javax.swing.SwingConstants.SOUTH_WEST;
    case "top":
      return javax.swing.SwingConstants.TOP;
    case "trailing":
      return javax.swing.SwingConstants.TRAILING;
    case "vertical":
      return javax.swing.SwingConstants.VERTICAL;
    case "west":
      return javax.swing.SwingConstants.WEST;
    default:
      throw new Exception("unknown position: " + s);
    }
  }
};
