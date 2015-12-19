package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

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
    put("toolbar", new ToolBarParser());
    put("tree", new TreeParser());
  }};

  protected static ComponentParser get(final String key) throws Exception {
    if (!lut.containsKey(key))
      throw new Exception("unknown component type: " + key);

    return lut.get(key);
  }

  protected static void initAbstractButton(
    final Context context,
    final JsonObject el,
    final AbstractButton r
  ) throws Exception {
    initJComponent(context, el, r);

    // set action
    if (el.has("action"))
      r.setAction(context.getAction(el.get("action").getAsString()));

    // set action command
    if (el.has("action-command"))
      r.setActionCommand(el.get("action-command").getAsString());

    // set border-painted
    if (el.has("border-painted"))
      r.setBorderPainted(el.get("border-painted").getAsBoolean());

    // set content-area-filled
    if (el.has("content-area-filled"))
      r.setContentAreaFilled(el.get("content-area-filled").getAsBoolean());

    if (el.has("disabled-icon"))
      r.setDisabledIcon(context.getIcon(el.get("disabled-icon").getAsString()));

    if (el.has("disabled-selected-icon"))
      r.setDisabledSelectedIcon(context.getIcon(el.get("disabled-selected-icon").getAsString()));

    // set focus-painted
    if (el.has("focus-painted"))
      r.setFocusPainted(el.get("focus-painted").getAsBoolean());

    // set hide-action-text
    if (el.has("hide-action-text"))
      r.setHideActionText(el.get("hide-action-text").getAsBoolean());

    // add horizontal alignment
    if (el.has("horizontal-alignment")) {
      r.setHorizontalAlignment(TextPosition.parse(
        el.get("horizontal-alignment").getAsString()
      ));
    }

    // add horizontal text position
    if (el.has("horizontal-text-position")) {
      r.setHorizontalTextPosition(TextPosition.parse(
        el.get("horizontal-text-position").getAsString()
      ));
    }

    if (el.has("icon"))
      r.setIcon(context.getIcon(el.get("icon").getAsString()));

    if (el.has("icon-text-gap"))
      r.setIconTextGap(el.get("icon").getAsInt());

    // add listeners
    if (el.has("listeners")) {
      final JsonObject o = el.getAsJsonObject("listeners");

      if (o.has("action")) {
        for (final JsonElement e: o.getAsJsonArray("action")) {
          r.addActionListener((ActionListener) context.getListener(
            "action",
            e.getAsString()
          ));
        }
      }
      if (o.has("change")) {
        for (final JsonElement e: o.getAsJsonArray("change")) {
          r.addChangeListener((ChangeListener) context.getListener(
            "change",
            e.getAsString()
          ));
        }
      }

      if (o.has("item")) {
        for (final JsonElement e: o.getAsJsonArray("item")) {
          r.addItemListener((ItemListener) context.getListener(
            "item",
            e.getAsString()
          ));
        }
      }
    }

    if (el.has("margin")) {
      final JsonObject o = el.getAsJsonObject("margin");

      r.setMargin(new Insets(
        o.get("top").getAsInt(),
        o.get("left").getAsInt(),
        o.get("bottom").getAsInt(),
        o.get("right").getAsInt()
      ));
    }

    // add mnemonic
    if (el.has("mnemonic")) {
      r.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(
        el.get("mnemonic").getAsString().codePointAt(0)
      ));
    }

    if (el.has("multi-click-threshhold"))
      r.setMultiClickThreshhold(el.get("multi-click-threshhold").getAsLong());

    if (el.has("pressed-icon"))
      r.setPressedIcon(context.getIcon(el.get("pressed-icon").getAsString()));

    if (el.has("rollover-enabled"))
      r.setRolloverEnabled(el.get("rollover-enabled").getAsBoolean());

    if (el.has("rollover-icon"))
      r.setRolloverIcon(context.getIcon(el.get("rollover-icon").getAsString()));

    if (el.has("rollover-selected-icon"))
      r.setRolloverSelectedIcon(context.getIcon(el.get("rollover-selected-icon").getAsString()));

    // set selected
    if (el.has("selected"))
      r.setSelected(el.get("selected").getAsBoolean());

    // set selected icon
    if (el.has("selected-icon"))
      r.setSelectedIcon(context.getIcon(el.get("selected-icon").getAsString()));

    // set vertical alignment
    if (el.has("vertical-alignment")) {
      r.setVerticalAlignment(TextPosition.parse(
        el.get("vertical-alignment").getAsString()
      ));
    }

    // set vertical text position
    if (el.has("vertical-text-position")) {
      r.setVerticalTextPosition(TextPosition.parse(
        el.get("vertical-text-position").getAsString()
      ));
    }

    // set button group
    if (el.has("button-group"))
      context.getButtonGroup(el.get("button-group").getAsString()).add(r);
  }

  /*
   * init common component properties
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

    // set client properties
    if (el.has("client-properties")) {
      final JsonObject o = el.getAsJsonObject("client-properties");
      for (Map.Entry<String, JsonElement> e: o.entrySet())
        r.putClientProperty(e.getKey(), e.getValue().getAsString());
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

    // add listeners
    if (el.has("listeners")) {
      final JsonObject o = el.getAsJsonObject("listeners");

      if (o.has("ancestor")) {
        for (final JsonElement e: o.getAsJsonArray("ancestor")) {
          r.addAncestorListener((AncestorListener) context.getListener(
            "ancestor",
            e.getAsString()
          ));
        }
      }

      if (o.has("vetoable-change")) {
        final JsonArray a = o.getAsJsonArray("vetoable-change");

        for (final JsonElement e: o.getAsJsonArray("vetoable-change")) {
          r.addVetoableChangeListener((VetoableChangeListener) context.getListener(
            "vetoable-change",
            e.getAsString()
          ));
        }
      }
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

    // add listeners
    if (el.has("listeners")) {
      final JsonObject o = el.getAsJsonObject("listeners");

      if (o.has("window-focus")) {
        for (final JsonElement e: o.getAsJsonArray("window-focus")) {
          r.addWindowFocusListener((WindowFocusListener) context.getListener(
            "window-focus",
            e.getAsString()
          ));
        }
      }

      if (o.has("window")) {
        for (final JsonElement e: o.getAsJsonArray("window")) {
          r.addWindowListener((WindowListener) context.getListener(
            "window",
            e.getAsString()
          ));
        }
      }

      if (o.has("window-state")) {
        for (final JsonElement e: o.getAsJsonArray("window-state")) {
          r.addWindowStateListener((WindowStateListener) context.getListener(
            "window-state",
            e.getAsString()
          ));
        }
      }
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

    // add listeners
    if (el.has("listeners")) {
      final JsonObject o = el.getAsJsonObject("listeners");

      if (o.has("container")) {
        for (final JsonElement e: o.getAsJsonArray("container")) {
          r.addContainerListener((ContainerListener) context.getListener(
            "container",
            e.getAsString()
          ));
        }
      }
    }
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

    // add listeners
    if (el.has("listeners")) {
      final JsonObject o = el.getAsJsonObject("listeners");

      if (o.has("focus")) {
        for (final JsonElement e: o.getAsJsonArray("focus")) {
          r.addFocusListener((FocusListener) context.getListener(
            "focus",
            e.getAsString()
          ));
        }
      }

      if (o.has("hierarchy-bounds")) {
        for (final JsonElement e: o.getAsJsonArray("hierarchy-bounds")) {
          r.addHierarchyBoundsListener((HierarchyBoundsListener) context.getListener(
            "hierarchy-bounds",
            e.getAsString()
          ));
        }
      }

      if (o.has("hierarchy")) {
        for (final JsonElement e: o.getAsJsonArray("hierarchy")) {
          r.addHierarchyListener((HierarchyListener) context.getListener(
            "hierarchy",
            e.getAsString()
          ));
        }
      }

      if (o.has("input-method")) {
        for (final JsonElement e: o.getAsJsonArray("input-method")) {
          r.addInputMethodListener((InputMethodListener) context.getListener(
            "input-method",
            e.getAsString()
          ));
        }
      }

      if (o.has("key")) {
        for (final JsonElement e: o.getAsJsonArray("key")) {
          r.addKeyListener((KeyListener) context.getListener(
            "key",
            e.getAsString()
          ));
        }
      }

      if (o.has("mouse")) {
        for (final JsonElement e: o.getAsJsonArray("mouse")) {
          r.addMouseListener((MouseListener) context.getListener(
            "mouse",
            e.getAsString()
          ));
        }
      }

      if (o.has("mouse-motion")) {
        for (final JsonElement e: o.getAsJsonArray("mouse-motion")) {
          r.addMouseMotionListener((MouseMotionListener) context.getListener(
            "mouse-motion",
            e.getAsString()
          ));
        }
      }

      if (o.has("mouse-wheel")) {
        for (final JsonElement e: o.getAsJsonArray("mouse-wheel")) {
          r.addMouseWheelListener((MouseWheelListener) context.getListener(
            "mouse-wheel",
            e.getAsString()
          ));
        }
      }

      if (o.has("property-change")) {
        for (final JsonElement e: o.getAsJsonArray("property-change")) {
          r.addPropertyChangeListener((PropertyChangeListener) context.getListener(
            "property-change",
            e.getAsString()
          ));
        }
      }
    }

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
