package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JSlider;
import com.google.gson.JsonObject;

public final class SliderParser implements ComponentParser {
  protected SliderParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JSlider r = new JSlider();

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

    // set orientation
    if (el.has("orientation")) {
      r.setOrientation(OrientationParser.parse(
        el.get("orientation").getAsString()
      ));
    }

    if (el.has("tick-spacing")) {
      final JsonObject o = el.get("tick-spacing").getAsJsonObject();

      // set major tick spacing
      if (o.has("major"))
        r.setMajorTickSpacing(o.get("major").getAsInt());

      // set minor tick spacing
      if (o.has("minor"))
        r.setMinorTickSpacing(o.get("minor").getAsInt());
    }

    // set paint flags
    if (el.has("paint")) {
      final JsonObject o = el.get("paint").getAsJsonObject();

      // set paint ticks
      if (o.has("ticks"))
        r.setPaintTicks(o.get("ticks").getAsBoolean());

      // set paint labels
      if (o.has("labels"))
        r.setPaintLabels(o.get("labels").getAsBoolean());
    }

    // set standard labels
    if (el.has("standard-labels")) {
      final JsonObject o = el.get("standard-labels").getAsJsonObject();

      if (o.has("increment") && o.has("start")) {
        r.setLabelTable(r.createStandardLabels(
          o.get("increment").getAsInt(),
          o.get("start").getAsInt()
        ));
      } else if (o.has("increment")) {
        r.setLabelTable(r.createStandardLabels(
          o.get("increment").getAsInt()
        ));
      } else {
        throw new Exception("missing standard-labels property: increment");
      }
    }

    // set labels
    if (el.has("labels")) {
      final JsonObject o = el.get("labels").getAsJsonObject();
      r.setLabelTable(SliderLabelsParser.parse(context, o));
    }

    // return result
    return r;
  }
};
