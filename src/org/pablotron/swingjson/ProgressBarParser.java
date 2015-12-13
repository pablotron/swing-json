package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JProgressBar;
import com.google.gson.JsonObject;

public final class ProgressBarParser implements ComponentParser {
  protected ProgressBarParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JProgressBar r = new JProgressBar();

    ComponentParsers.init_component(context, el, r);

    // set value
    if (el.has("value"))
      r.setValue(el.get("value").getAsInt());

    // set min
    if (el.has("min"))
      r.setMinimum(el.get("min").getAsInt());

    // set max
    if (el.has("max"))
      r.setMaximum(el.get("max").getAsInt());

    // set indeterminite
    if (el.has("indeterminate"))
      r.setIndeterminate(el.get("indeterminate").getAsBoolean());

    // set orientation
    if (el.has("orientation")) {
      r.setOrientation(OrientationParser.parse(
        el.get("orientation").getAsString()
      ));
    }

    // set painted flags
    if (el.has("painted")) {
      final JsonObject o = el.get("painted").getAsJsonObject();

      if (o.has("border"))
        r.setBorderPainted(o.get("border").getAsBoolean());

      if (o.has("string"))
        r.setStringPainted(o.get("string").getAsBoolean());
    }

    // set string
    if (el.has("text"))
      r.setString(context.getText(el.get("text").getAsString()));

    // return result
    return r;
  }
};
