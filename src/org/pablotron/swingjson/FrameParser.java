package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class FrameParser implements ComponentParser {
  protected FrameParser() {}

  private int get_close_operation(final String key) throws Exception {
    switch (key) {
    case "do-nothing":
      return JFrame.DO_NOTHING_ON_CLOSE;
    case "hide":
      return JFrame.HIDE_ON_CLOSE;
    case "dispose":
      return JFrame.DISPOSE_ON_CLOSE;
    case "exit":
      return JFrame.EXIT_ON_CLOSE;
    default:
      throw new Exception("unknown frame close operation: " + key);
    }
  }

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String text = el.has("text") ? el.get("text").getAsString() : "";
    final JFrame r = new JFrame(context.getText(text));
    final Container content = r.getContentPane();
    LayoutParser layout = LayoutParsers.get(null);

    // set close operation
    if (el.has("layout")) {
      layout = LayoutParsers.get(el.get("layout").getAsString());
      layout.set(content, el);
    }

    // set colors
    if (el.has("colors")) {
      final JsonObject o = el.getAsJsonObject("colors");

      if (o.has("fg"))
        r.setForeground(context.getColor(o.get("fg").getAsString()));
      if (o.has("bg"))
        r.setBackground(context.getColor(o.get("bg").getAsString()));
    }

    // set cursor
    if (el.has("cursor"))
      r.setCursor(CursorParser.parse(el.get("cursor").getAsString()));

    // add menu bar
    if (el.has("menubar")) {
      final JsonObject o = el.get("menubar").getAsJsonObject();
      r.setJMenuBar((JMenuBar) ComponentParsers.parse(context, o));
    }

    // set opacity
    if (el.has("opacity"))
      r.setOpacity(el.get("opacity").getAsFloat());

    // set resizable
    if (el.has("resizable"))
      r.setResizable(el.get("resizable").getAsBoolean());

    // set sizes
    if (el.has("size")) {
      final JsonObject o = el.getAsJsonObject("size");

      if (o.has("min"))
        r.setMinimumSize(SizeParser.parse(o.getAsJsonArray("min")));
      if (o.has("max"))
        r.setMaximumSize(SizeParser.parse(o.getAsJsonArray("max")));
      if (o.has("preferred"))
        r.setPreferredSize(SizeParser.parse(o.getAsJsonArray("preferred")));
    }

    if (el.has("state")) {
      // TODO: use setExtendedState
    }

    // set undecorated
    if (el.has("undecorated"))
      r.setUndecorated(el.get("undecorated").getAsBoolean());

    if (el.has("kids")) {
      for (final JsonElement kid: el.getAsJsonArray("kids")) {
        final JsonObject o = kid.getAsJsonObject();
        layout.add(context, content, o, ComponentParsers.parse(context, o));
      }
    }

    // set close operation
    if (el.has("on-close")) {
      r.setDefaultCloseOperation(
        get_close_operation(el.get("on-close").getAsString())
      );
    }

    // set default button
    if (el.has("default-button")) {
      final String id = el.get("default-button").getAsString();

      context.addInit(new Runnable() {
        public void run() {
          r.getRootPane().setDefaultButton((JButton) context.get(id));
        }
      });
    }

    // add pack runnable
    if (!el.has("pack") || el.get("pack").getAsBoolean()) {
      context.addInit(new Runnable() {
        public void run() {
          r.pack();
        }
      });
    }

    // add show runnable
    if (el.has("show") && el.get("show").getAsBoolean()) {
      context.addInit(new Runnable() {
        public void run() {
          r.setVisible(true);
        }
      });
    }

    // return result
    return r;
  }
};
