package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import javax.swing.JTabbedPane;
import static javax.swing.JTabbedPane.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class TabbedPaneParser implements ComponentParser {
  protected TabbedPaneParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JTabbedPane r = new JTabbedPane();

    ComponentParsers.initJComponent(context, el, r);

    // set divider size
    if (el.has("layout"))
      r.setTabLayoutPolicy(get_layout(el.get("layout").getAsString()));

    // set divider size
    if (el.has("placement"))
      r.setTabLayoutPolicy(get_placement(el.get("placement").getAsString()));

    // get tabs
    if (el.has("tabs")) {
      final JsonArray tabs = el.getAsJsonArray("tabs");

      for (int i = 0, l = tabs.size(); i < l; i++) {
        final JsonObject tab = tabs.get(i).getAsJsonObject();

        // check for required properties
        if (!tab.has("text"))
          throw new Exception("missing tab property: text");
        if (!tab.has("kid"))
          throw new Exception("missing tab property: kid");

        // add tab
        r.addTab(
          context.getText(tab.get("text").getAsString()), 
          ComponentParsers.parse(context, tab.get("kid").getAsJsonObject())
        );

        // set selected
        if (tab.has("selected") && tab.get("selected").getAsBoolean())
          r.setSelectedIndex(i);

        // TODO: set icon, background, mnemonic, and displayed mnemonic

        // set enabled
        if (tab.has("enabled"))
          r.setEnabledAt(i, tab.get("enabled").getAsBoolean());

        // set tooltip
        if (tab.has("tip"))
          r.setToolTipTextAt(i, context.getText(tab.get("tip").getAsString()));
      }
    }

    // return result
    return r;
  }

  private static final Map<String, Integer> layouts = new HashMap<String, Integer>() {{
    put("wrap", WRAP_TAB_LAYOUT);
    put("scroll", SCROLL_TAB_LAYOUT);
  }};

  private static int get_layout(final String s) throws Exception {
    if (!layouts.containsKey(s))
      throw new Exception("unknown tab layout: " + s);

    return layouts.get(s);
  }

  private static final Map<String, Integer> placements = new HashMap<String, Integer>() {{
    put("top", TOP);
    put("left", LEFT);
    put("bottom", BOTTOM);
    put("right", RIGHT);
  }};

  private static int get_placement(final String s) throws Exception {
    if (!placements.containsKey(s))
      throw new Exception("unknown tab placement: " + s);

    return placements.get(s);
  }
};
