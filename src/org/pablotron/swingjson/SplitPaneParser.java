package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class SplitPaneParser implements ComponentParser {
  protected SplitPaneParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    // get split
    if (!el.has("split"))
      throw new Exception("mising splitpane parameter: split");

    final int split = get_split(el.get("split").getAsString());
    final JSplitPane r = new JSplitPane(split);

    ComponentParsers.initJComponent(context, el, r);

    // set divider size
    if (el.has("divider-size"))
      r.setDividerSize(el.get("divider-size").getAsInt());

    // set continuous layout
    if (el.has("continuous"))
      r.setContinuousLayout(el.get("continuous").getAsBoolean());

    // set one touch expandable
    if (el.has("one-touch"))
      r.setOneTouchExpandable(el.get("one-touch").getAsBoolean());

    // set divider location
    if (el.has("divider-location"))
      set_divider_location(r, el);

    // set last divider location
    if (el.has("last-divider-location"))
      r.setLastDividerLocation(el.get("last-divider-location").getAsInt());

    // set resize weight
    if (el.has("resize-weight"))
      r.setResizeWeight(el.get("resize-weight").getAsFloat());

    // get kids
    if (!el.has("kids"))
      throw new Exception("mising splitpane parameter: kids");
    final JsonArray kids = el.getAsJsonArray("kids");

    // check kids
    if (kids.size() != 2)
      throw new Exception("splitpane must have 2 kids");

    // add kids
    for (final JsonElement kid: el.getAsJsonArray("kids"))
      r.add(ComponentParsers.parse(context, kid.getAsJsonObject()));

    // return result
    return r;
  }

  private static final Map<String, Integer> splits = new HashMap<String, Integer>() {{
    put("horizontal", HORIZONTAL_SPLIT);
    put("vertical", VERTICAL_SPLIT);
  }};

  private static int get_split(final String s) throws Exception {
    if (!splits.containsKey(s))
      throw new Exception("unknown split: " + s);

    return splits.get(s);
  }

  private static void set_divider_location(
    final JSplitPane r,
    final JsonObject el
  ) throws Exception {
    final JsonObject o = el.getAsJsonObject("divider-location");

    if (o.has("percent")) {
      r.setDividerLocation(o.get("percent").getAsDouble());
    } else if (o.has("pixels")) {
      r.setDividerLocation(o.get("pixels").getAsInt());
    } else {
      throw new Exception("divider location must have 'percent' or 'pixels' property");
    }
  }
};
