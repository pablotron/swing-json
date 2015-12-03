package org.pablotron.swingjson;

import java.io.Reader;
import java.util.Map;

import java.awt.Component;
import javax.swing.JComponent;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class ContextParser {
  /*
   * init common component properties
   *
   * TODO: move this elsewhere
   *
   */
  protected static void init_component(
    final Context context,
    final JsonObject el,
    final JComponent r
  ) throws Exception {
    // add to component group
    if (el.has("group")) {
      context.getGroup(el.get("group").getAsString()).add(r);
    }

    // add tooltip
    if (el.has("tip")) {
      r.setToolTipText(context.getText(el.get("tip").getAsString()));
    }

    // add enabled
    if (el.has("enabled")) {
      r.setEnabled(el.get("enabled").getAsBoolean());
    }
  }

  protected static Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String type = el.get("type").getAsString();
    final Component r = ComponentParsers.get(type).parse(context, el);

    // cache component id
    if (el.has("id"))
      context.put(el.get("id").getAsString(), r);

    // return component
    return r;
  }

  private static final JsonParser json_parser = new JsonParser();
  
  public static Context parse(final Reader in) throws Exception {
    final JsonObject root = json_parser.parse(in).getAsJsonObject();
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
        r.addRoot(parse(r, el.getAsJsonObject()));
    }

    // return context
    return r;
  }
};
