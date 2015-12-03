package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import com.google.gson.JsonObject;

public final class ComponentParsers {
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

    // add tooltip
    if (el.has("tip")) {
      r.setToolTipText(context.getText(el.get("tip").getAsString()));
    }

    // add enabled
    if (el.has("enabled")) {
      r.setEnabled(el.get("enabled").getAsBoolean());
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
