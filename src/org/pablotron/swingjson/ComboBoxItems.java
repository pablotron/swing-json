package org.pablotron.swingjson;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class ComboBoxItems {
  private static final String[] STRINGS = new String[0];

  public String items[] = STRINGS;
  public int index = -1;

  private ComboBoxItems() {}

  protected static ComboBoxItems parse(
    final Context context,
    final JsonObject config
  ) throws Exception {
    final ComboBoxItems r = new ComboBoxItems();
    final List<String> items = new ArrayList<>();

    if (config.has("items")) {
      final JsonArray els = config.getAsJsonArray("items");

      for (int i = 0, l = els.size(); i < l; i++) {
        final JsonElement el = els.get(i);

        if (el.isJsonObject()) {
          final JsonObject item = el.getAsJsonObject();
          
          // get text property
          if (!item.has("text"))
            throw new Exception("missing combobox item property: text");
          items.add(context.getText(item.get("text").getAsString()));

          // get selected property
          if (item.has("selected") && item.get("selected").getAsBoolean())
            r.index = i;
        } else {
          // add string
          items.add(context.getText(el.getAsString()));
        }
      }

      // save results
      r.items = items.toArray(STRINGS);
    }

    // return results
    return r;
  }
};
