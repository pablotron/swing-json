package org.pablotron.swingjson;

import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import org.pablotron.swingjson.Context;

public final class ContextParser {
  public static enum ComponentType {
    FRAME("frame") {
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

      public Component make(
        final JsonObject el,
        final Context context
      ) throws Exception {
        final String text = el.has("text") ? el.get("text").getAsString() : "";
        final JFrame frame = new JFrame(context.getText(text));

        if (el.has("kids")) {
          for (final JsonElement kid: el.getAsJsonArray("kids")) {
            frame.getContentPane().add(ContextParser.make(
              kid.getAsJsonObject(),
              context
            ));
          }
        }

        // set close operation
        if (el.has("close-operation")) {
          frame.setDefaultCloseOperation(
            get_close_operation(el.get("close-operation").getAsString())
          );
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
    }, 

    LABEL("label") {
      public Component make(
        final JsonObject el,
        final Context context
      ) throws Exception {
        final String text = el.has("text") ? el.get("text").getAsString() : "";
        return new JLabel(context.getText(text));
      }
    };

    private final String type;

    ComponentType(final String type) {
      this.type = type;
    }

    public String getType() {
      return this.type;
    }

    public abstract Component make(
      final JsonObject el,
      final Context context
    ) throws Exception;
  };

  private static final JsonParser parser = new JsonParser();
  private static final Map<String, ComponentType> types = new HashMap<>();

  // init type map
  static {
    for (final ComponentType t: ComponentType.values())
      types.put(t.getType(), t);
  }

  private static ComponentType get_type(final String type) throws Exception {
    if (!types.containsKey(type))
      throw new Exception("unknown type: " + type);

    return types.get(type);
  }

  private static Component make(
    final JsonObject el,
    final Context context
  ) throws Exception {
    final String type = el.get("type").getAsString();
    final Component r = get_type(type).make(el, context);

    // cache component id
    if (el.has("id"))
      context.put(el.get("id").getAsString(), r);

    // return component
    return r;
  }

  public static Context parse(final Reader in) throws Exception {
    final JsonObject root = parser.parse(in).getAsJsonObject();
    final Context r = new Context();

    // parse text
    if (root.has("text")) {
      final JsonObject text = root.getAsJsonObject("text");

      for (final Map.Entry<String, JsonElement> e: text.entrySet())
        r.addText(e.getKey(), e.getValue().getAsString());
    }

    // parse and add roots
    if (root.has("kids")) {
      for (final JsonElement el: root.getAsJsonArray("kids"))
        r.addRoot(make(el.getAsJsonObject(), r));
    }

    // return context
    return r;
  }
};
