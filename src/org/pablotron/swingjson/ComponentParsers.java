package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;

import java.awt.Component;
import javax.swing.JComponent;

import com.google.gson.JsonObject;

public final class ComponentParsers {
  private ComponentParsers() {}

  private static Map<String, ComponentParser> lut = new HashMap<String, ComponentParser>() {{
    put("frame", new FrameParser());
    put("label", new LabelParser());
    put("button", new ButtonParser(ButtonType.BUTTON));
    put("checkbox", new ButtonParser(ButtonType.CHECKBOX));
    put("radio", new ButtonParser(ButtonType.RADIO));
  }};

  protected static ComponentParser get(final String key) throws Exception {
    if (!lut.containsKey(key))
      throw new Exception("unknown type: " + key);

    return lut.get(key);
  }

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

    // set tooltip
    if (el.has("tip")) {
      r.setToolTipText(context.getText(el.get("tip").getAsString()));
    }

    // set enabled
    if (el.has("enabled")) {
      r.setEnabled(el.get("enabled").getAsBoolean());
    }

    // set sizes
    if (el.has("sizes")) {
      final JsonObject sizes = el.getAsJsonObject("sizes");

      if (sizes.has("minimum"))
        r.setMinimumSize(SizeParser.parse(sizes.getAsJsonArray("minimum")));
      if (sizes.has("maximum"))
        r.setMaximumSize(SizeParser.parse(sizes.getAsJsonArray("maximum")));
      if (sizes.has("preferred"))
        r.setPreferredSize(SizeParser.parse(sizes.getAsJsonArray("preferred")));
    }

    // set alignment
    if (el.has("alignment")) {
      final JsonObject align = el.getAsJsonObject("alignment");

      if (align.has("x"))
        r.setAlignmentX(AlignmentParser.parse(align.getAsJsonPrimitive("x")));
      if (align.has("y"))
        r.setAlignmentY(AlignmentParser.parse(align.getAsJsonPrimitive("y")));
    }
  }

  protected static Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final String type = el.get("type").getAsString();
    final Component r = get(type).parse(context, el);

    // cache component id
    if (el.has("id"))
      context.put(el.get("id").getAsString(), r);

    // return component
    return r;
  }
};
