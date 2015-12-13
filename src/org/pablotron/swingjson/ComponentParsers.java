package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.Frame;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

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
    put("text", new TextFieldParser());
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
  protected static void initJComponent(
    final Context context,
    final JsonObject el,
    final JComponent r
  ) throws Exception {
    initContainer(context, el, r);

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
  }

  protected static void initFrame(
    final Context context,
    final JsonObject el,
    final Frame r
  ) throws Exception {
    initWindow(context, el, r);

    if (el.has("extended-state")) {
      // TODO
    }

    // set opacity
    if (el.has("opacity"))
      r.setOpacity(el.get("opacity").getAsFloat());

    // set resizable
    if (el.has("resizable"))
      r.setResizable(el.get("resizable").getAsBoolean());

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

    if (el.has("shape")) {
      // TODO
    }

    // set title
    if (el.has("title"))
      r.setTitle(el.get("title").getAsString());

    // set undecorated
    if (el.has("undecorated"))
      r.setUndecorated(el.get("undecorated").getAsBoolean());
  }

  protected static void initWindow(
    final Context context,
    final JsonObject el,
    final Window r
  ) throws Exception {
    initContainer(context, el, r);

    if (el.has("always-on-top"))
      r.setAlwaysOnTop(el.get("always-on-top").getAsBoolean());

    if (el.has("auto-request-focus"))
      r.setAutoRequestFocus(el.get("auto-request-focus").getAsBoolean());

    // set focusable window state
    if (el.has("focusable-window-state"))
      r.setFocusableWindowState(el.get("focusable-window-state").getAsBoolean());

    // set focus cycle root
    if (el.has("focus-cycle-root"))
      r.setFocusCycleRoot(el.get("focus-cycle-root").getAsBoolean());

    if (el.has("icon-images")) {
      // TODO
    }

    // set location by platform
    if (el.has("location-by-platform"))
      r.setLocationByPlatform(el.get("location-by-platform").getAsBoolean());

    // set location relative to
    if (el.has("location-relative-to")) {
      r.setLocationRelativeTo(
        context.get(el.get("location-relative-to").getAsString())
      );
    }

    if (el.has("modal-exclusion-type")) {
      // TODO
    }

    if (el.has("window-type")) {
      // TODO
    }
  }

  protected static void initContainer(
    final Context context,
    final JsonObject el,
    final Container r
  ) throws Exception {
    initComponent(context, el, r);
  }

  protected static void initComponent(
    final Context context,
    final JsonObject el,
    final Component r
  ) throws Exception {
    // add to component group
    if (el.has("group"))
      context.getGroup(el.get("group").getAsString()).add(r);

    if (el.has("bounds")) {
      final JsonObject o = el.getAsJsonObject("bounds");

      r.setBounds(
        o.get("x").getAsInt(),
        o.get("y").getAsInt(),
        o.get("w").getAsInt(),
        o.get("h").getAsInt()
      );
    }

    // set cursor
    if (el.has("cursor"))
      r.setCursor(CursorParser.parse(el.get("cursor").getAsString()));

    // set component orientation
    if (el.has("component-orientation")) {
      r.setComponentOrientation(ComponentOrientationParser.parse(
        el.get("component-orientation").getAsString()
      ));
    }

    if (el.has("drop-target")) {
      // TODO
    }

    if (el.has("focusable"))
      r.setFocusable(el.get("focusable").getAsBoolean());

    if (el.has("focus-traversal-keys")) {
      // TODO
    }

    if (el.has("ignore-repaint"))
      r.setIgnoreRepaint(el.get("ignore-repaint").getAsBoolean());

    if (el.has("locale")) {
      // TODO
    }

    // set location
    if (el.has("location")) {
      final JsonArray o = el.getAsJsonArray("location");
      r.setLocation(o.get(0).getAsInt(), o.get(1).getAsInt());
    }

    // set component name
    if (el.has("component-name"))
      r.setName(context.getText(el.get("component-name").getAsString()));

    // set size
    if (el.has("component-size"))
      r.setSize(SizeParser.parse(el.getAsJsonArray("component-size")));
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
