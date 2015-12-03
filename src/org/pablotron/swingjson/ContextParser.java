package org.pablotron.swingjson;

import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

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
    }, 

    LABEL("label") {
      public Component make(
        final JsonObject el,
        final Context context
      ) throws Exception {
        final String text = el.has("text") ? el.get("text").getAsString() : "";
        final JLabel r = new JLabel(context.getText(text));

        init_component(el, r, context);

        return r;
      }
    },

    BUTTON("button") {
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

      public Component make(
        final JsonObject el,
        final Context context
      ) throws Exception {
        final String text = el.has("text") ? el.get("text").getAsString() : "";
        // TODO: icon
        final JButton b = new JButton(context.getText(text));

        // init common component attributes
        init_component(el, b, context);

        // add action
        if (el.has("action")) {
          b.setActionCommand(el.get("action").getAsString());
        }

        // add mnemonic
        if (el.has("mnemonic")) {
          b.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(el.get("mnemonic").getAsString().codePointAt(0)));
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

  /*
   * init common component properties
   */
  private static void init_component(
    final JsonObject el,
    final JComponent r,
    final Context context
  ) throws Exception {
    // add tooltip
    if (el.has("tip")) {
      r.setToolTipText(context.getText(el.get("tip").getAsString()));
    }

    // add enabled
    if (el.has("enabled")) {
      r.setEnabled(el.get("enabled").getAsBoolean());
    }
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
