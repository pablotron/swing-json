package org.pablotron.swingjson;

import java.io.Reader;
import java.util.Map;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class ContextParser {
  private ContextParser() {}

  private static final JsonParser parser = new JsonParser();

  public static Context parse(
    final Reader in,
    final ContextErrorHandler handler
  ) throws Exception {
    final JsonObject root = parser.parse(in).getAsJsonObject();
    final Context r = new Context(handler);

    try {
      // parse text
      if (root.has("text")) {
        final JsonObject text = root.getAsJsonObject("text");

        for (final Map.Entry<String, JsonElement> e: text.entrySet())
          r.addText(e.getKey(), e.getValue().getAsString());
      }

      // parse and add roots
      if (root.has("kids")) {
        for (final JsonElement el: root.getAsJsonArray("kids"))
          r.addRoot(ComponentParsers.parse(r, el.getAsJsonObject()));
      }
    } catch (Exception e) {
      r.error(e);
    }

    // return context
    return r;
  }

  public static Context parse(final Reader in) throws Exception {
    return parse(in, null);
  }
};
