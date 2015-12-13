package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JSeparator;
import com.google.gson.JsonObject;

public final class SeparatorParser implements ComponentParser {
  protected SeparatorParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    
    // create result
    final JSeparator r = new JSeparator(OrientationParser.parse(
      el.has("orientation") ? el.get("orientation").getAsString() : null
    ));

    ComponentParsers.init_component(context, el, r);

    // return result
    return r;
  }
};
