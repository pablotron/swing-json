package org.pablotron.swingjson;

import java.util.Map;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.JComponent;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class SliderLabelsParser {
  private SliderLabelsParser() {}

  protected static Dictionary<Integer, JComponent> parse(
    final Context context,
    final JsonObject o
  ) throws Exception {
    final Hashtable<Integer, JComponent> r = new Hashtable<>();

    // populate result
    for (final Map.Entry<String, JsonElement> e: o.entrySet()) {
      r.put(Integer.parseInt(e.getKey()), (JComponent) ComponentParsers.parse(
        context,
        e.getValue().getAsJsonObject()
      ));
    }

    // return result
    return r;
  }
};
