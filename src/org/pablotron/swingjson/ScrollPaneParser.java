package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.*;
import com.google.gson.JsonObject;

public final class ScrollPaneParser implements ComponentParser {
  protected ScrollPaneParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JScrollPane r = new JScrollPane();

    ComponentParsers.initJComponent(context, el, r);

    if (el.has("kid")) {
      final JsonObject kid = el.get("kid").getAsJsonObject();
      r.setViewportView(ComponentParsers.parse(context, kid));
    }

    if (el.has("policy")) {
      final JsonObject o = el.get("policy").getAsJsonObject();

      ScrollPanePolicyParser.set(r, o, "horizontal");
      ScrollPanePolicyParser.set(r, o, "vertical");
    }

    // return result
    return r;
  }
};
