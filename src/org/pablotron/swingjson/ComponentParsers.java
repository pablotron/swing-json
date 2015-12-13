package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import com.google.gson.JsonObject;

public final class ComponentParsers {
  private ComponentParsers() {}

  private static Map<String, ComponentParser> lut = new HashMap<String, ComponentParser>() {{
    put("frame", new FrameParser());
    put("label", new LabelParser());
    put("button", new ButtonParser(ButtonType.DEFAULT));
    put("checkbox", new ButtonParser(ButtonType.CHECKBOX));
    put("radio", new ButtonParser(ButtonType.RADIO));
    put("rigid-area", new RigidAreaParser());
    put("horizontal-glue", new GlueParser(GlueType.HORIZONTAL));
    put("vertical-glue", new GlueParser(GlueType.VERTICAL));
    put("box-filler", new BoxFillerParser());
    put("panel", new PanelParser());
    put("combobox", new ComboBoxParser());
    put("editor-pane", new EditorPaneParser());
    put("scroll-pane", new ScrollPaneParser());
    put("split-pane", new SplitPaneParser());
    put("tabbed-pane", new TabbedPaneParser());
    put("list", new ListParser());
    put("menubar", new MenuBarParser());
    put("menu", new MenuParser());
    put("menuitem", new MenuItemParser(ButtonType.DEFAULT));
    put("checkbox-menuitem", new MenuItemParser(ButtonType.CHECKBOX));
    put("radio-menuitem", new MenuItemParser(ButtonType.RADIO));
    put("separator", new SeparatorParser());
    put("password", new PasswordFieldParser());
    put("progressbar", new ProgressBarParser());
    put("slider", new SliderParser());
    put("text-area", new TextAreaParser());
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
  protected static JComponent init_component(
    final Context context,
    final JsonObject el,
    final JComponent r
  ) throws Exception {
    // add to component group
    if (el.has("group"))
      context.getGroup(el.get("group").getAsString()).add(r);

    // set alignment
    if (el.has("alignment")) {
      final JsonObject o = el.getAsJsonObject("alignment");

      if (o.has("x"))
        r.setAlignmentX(AlignmentParser.parse(o.getAsJsonPrimitive("x")));
      if (o.has("y"))
        r.setAlignmentY(AlignmentParser.parse(o.getAsJsonPrimitive("y")));
    }

    // set border
    if (el.has("border"))
      r.setBorder(BorderParser.parse(el.getAsJsonObject("border")));

    // set component popup menu
    if (el.has("component-popup-menu")) {
      r.setComponentPopupMenu((JPopupMenu) parse(
        context, 
        el.getAsJsonObject("component-popup-menu")
      ));
    }

    // set colors
    if (el.has("colors")) {
      final JsonObject o = el.getAsJsonObject("colors");

      if (o.has("fg"))
        r.setForeground(context.getColor(o.get("fg").getAsString()));
      if (o.has("bg"))
        r.setBackground(context.getColor(o.get("bg").getAsString()));
    }

    // set cursor
    if (el.has("cursor"))
      r.setCursor(CursorParser.parse(el.get("cursor").getAsString()));

    // set debug graphics options
    if (el.has("debug-graphics-options")) {
      // TODO
    }

    // set double buffered
    if (el.has("double-buffered"))
      r.setDoubleBuffered(el.get("double-buffered").getAsBoolean());

    // set enabled
    if (el.has("enabled"))
      r.setEnabled(el.get("enabled").getAsBoolean());

    // set focus traversal keys
    if (el.has("focus-traversal-keys")) {
      // TODO
    }

    // set font
    if (el.has("font"))
      r.setFont(context.getFont(el.get("font").getAsString()));

    // set inherits-popup-menu
    if (el.has("inherits-popup-menu"))
      r.setInheritsPopupMenu(el.get("inherits-popup-menu").getAsBoolean());

    // set input map
    if (el.has("input-map")) {
      // TODO
    }

    // set input verifier
    if (el.has("input-verifier")) {
      // TODO
    }

    // set opacity
    if (el.has("opaque"))
      r.setOpaque(el.get("opaque").getAsBoolean());

    // set request focus enabled
    if (el.has("request-focus-enabled"))
      r.setRequestFocusEnabled(el.get("request-focus-enabled").getAsBoolean());

    // set sizes
    if (el.has("size")) {
      final JsonObject o = el.getAsJsonObject("size");

      if (o.has("min"))
        r.setMinimumSize(SizeParser.parse(o.getAsJsonArray("min")));
      if (o.has("max"))
        r.setMaximumSize(SizeParser.parse(o.getAsJsonArray("max")));
      if (o.has("preferred"))
        r.setPreferredSize(SizeParser.parse(o.getAsJsonArray("preferred")));
    }

    // set tooltip
    if (el.has("tip"))
      r.setToolTipText(context.getText(el.get("tip").getAsString()));

    if (el.has("transfer-handler")) {
      // TODO
    }

    if (el.has("ui")) {
      // TODO
    }

    // set verify-input-when-focus-target 
    if (el.has("verify-input-when-focus-target"))
      r.setVerifyInputWhenFocusTarget(el.get("verify-input-when-focus-target").getAsBoolean());

    // set visible (do we want to defer this until later?
    if (el.has("show"))
      r.setVisible(el.get("show").getAsBoolean());

    return r;
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
