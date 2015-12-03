package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JButton;

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
    final JFrame frame = new JFrame(context.getText(text));
    final Container content = frame.getContentPane();
    LayoutParser layout = LayoutParsers.get(null);

    // set close operation
    if (el.has("layout")) {
      layout = LayoutParsers.get(el.get("layout").getAsString());
      layout.set(content, el);
    }

    if (el.has("kids")) {
      for (final JsonElement kid: el.getAsJsonArray("kids")) {
        layout.add(content, kid.getAsJsonObject(), ComponentParsers.parse(
          context,
          kid.getAsJsonObject()
        ));
      }
    }

    // set close operation
    if (el.has("close-operation")) {
      frame.setDefaultCloseOperation(
        get_close_operation(el.get("close-operation").getAsString())
      );
    }

    // set default button
    if (el.has("default-button")) {
      final String id = el.get("default-button").getAsString();

      context.addInit(new Runnable() {
        public void run() {
          frame.getRootPane().setDefaultButton((JButton) context.get(id));
        }
      });
    }

    // add pack runnable
    context.addInit(new Runnable() {
      public void run() {
        frame.pack();
      }
    });

    // add show runnable
    if (el.has("show") && el.get("show").getAsBoolean()) {
      context.addInit(new Runnable() {
        public void run() {
          frame.setVisible(true);
        }
      });
    }

    // return result
    return frame;
  }
};
