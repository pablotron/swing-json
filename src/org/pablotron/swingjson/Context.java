package org.pablotron.swingjson;

import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public final class Context {
  private static final ContextErrorHandler DEFAULT_ERROR_HANDLER = new ContextErrorHandler() {
    public void handle(Exception e) {
      e.printStackTrace();
    }
  };

  private static final JsonParser parser = new JsonParser();

  public static Context parse(
    final Reader in,
    final ContextErrorHandler handler
  ) throws Exception {
    final JsonObject root = parser.parse(in).getAsJsonObject();
    final Context r = new Context(handler);

    try {
      // parse text
      if (root.has("text")) {
        final JsonObject text = root.getAsJsonObject("text");

        for (final Map.Entry<String, JsonElement> e: text.entrySet())
          r.addText(e.getKey(), e.getValue().getAsString());
      }

      // set look and feel
      if (root.has("style")) {
        final String style = root.get("style").getAsString();

        for (final LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
          if (style.equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
          }
        }
      }

      if (root.has("colors")) {
        final JsonObject o = root.getAsJsonObject("colors");

        // add colors
        for (final Map.Entry<String, JsonElement> e: o.entrySet())
          r.addColor(e.getKey(), e.getValue().getAsString());
      }

      if (root.has("icons")) {
        final JsonObject o = root.getAsJsonObject("icons");

        // add icons
        for (final Map.Entry<String, JsonElement> e: o.entrySet()) {
          final JsonElement v = e.getValue();

          if (v.isJsonObject()) {
            final JsonObject h = v.getAsJsonObject();

            // check for required parameter
            if (!h.has("url"))
              throw new Exception("missing icon property: url");

            r.addIcon(
              e.getKey(),
              h.get("url").getAsString(),
              h.has("text") ? r.getText(h.get("text").getAsString()) : null
            );
          } else {
            r.addIcon(e.getKey(), v.getAsString(), null);
          }
        }
      }

      if (root.has("fonts")) {
        final JsonObject o = root.getAsJsonObject("fonts");

        // add fonts
        for (final Map.Entry<String, JsonElement> e: o.entrySet())
          r.addFont(e.getKey(), FontParser.parse(e.getValue()));
      }

      // parse and add roots
      if (root.has("kids")) {
        for (final JsonElement el: root.getAsJsonArray("kids"))
          r.addRoot(ComponentParsers.parse(r, el.getAsJsonObject()));
      }
    } catch (Exception e) {
      r.error(e);
    }

    // return context
    return r;
  }

  public static Context parse(final Reader in) throws Exception {
    return parse(in, null);
  }

  private final List<Component> roots = new ArrayList<>();
  private final Map<String, Component> ids = new HashMap<>();
  private final Map<String, String> texts = new HashMap<>();
  private final Map<String, Font> fonts = new HashMap<>();
  private final Map<String, Color> colors = new HashMap<>();
  private final Map<String, ImageIcon> icons = new HashMap<>();
  private final Map<String, List<Component>> groups = new HashMap<>();
  private final Map<String, ButtonGroup> button_groups = new HashMap<>();
  private final List<Runnable> inits = new ArrayList<>();
  private final ContextErrorHandler handler;

  protected Context(final ContextErrorHandler handler) {
    this.handler = (handler != null) ? handler : DEFAULT_ERROR_HANDLER;
  }

  protected Context() {
    this(null);
  }

  public void put(final String id, final Component component) {
    ids.put(id, component);
  }

  public Component get(final String id) {
    return ids.get(id);
  }

  void addRoot(final Component root) {
    roots.add(root);
  }

  public List<Component> getRoots() {
    return roots;
  }

  public void addText(final String key, final String text) {
    texts.put(key, text);
  }

  public String getText(final String key) throws Exception {
    if (!texts.containsKey(key))
      throw new Exception("unknown text key: " + key);

    return texts.get(key);
  }

  public void addColor(final String key, final String color) {
    // TODO: add ColorParser
    colors.put(key, Color.decode(color));
  }

  public Color getColor(final String key) throws Exception {
    if (!colors.containsKey(key))
      throw new Exception("unknown color: " + key);

    return colors.get(key);
  }

  public void addIcon(final String key, final String url, final String text) {
    final ImageIcon icon;

    if (text != null) {
      icon = new ImageIcon(url, text);
    } else {
      icon = new ImageIcon(url);
    }

    icons.put(key, icon);
  }

  public ImageIcon getIcon(final String key) throws Exception {
    if (!icons.containsKey(key))
      throw new Exception("unknown icon: " + key);

    return icons.get(key);
  }

  public void addFont(final String key, final Font font) {
    fonts.put(key, font);
  }

  public Font getFont(final String key) throws Exception {
    if (!fonts.containsKey(key))
      throw new Exception("unknown font: " + key);

    return fonts.get(key);
  }

  public List<Component> getGroup(final String key) {
    if (!groups.containsKey(key)) {
      groups.put(key, new ArrayList<Component>());
    }

    return groups.get(key);
  }

  public ButtonGroup getButtonGroup(final String key) {
    if (!button_groups.containsKey(key)) {
      button_groups.put(key, new ButtonGroup());
    }

    return button_groups.get(key);
  }

  public void addInit(final Runnable init) {
    inits.add(init);
  }

  public void init() {
    for (final Runnable init: inits)
      init.run();
  }

  public void error(final Exception e) {
    handler.handle(e);
  }
};
