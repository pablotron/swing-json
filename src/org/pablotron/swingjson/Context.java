package org.pablotron.swingjson;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

public final class Context {
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

  private static final ContextErrorHandler DEFAULT_ERROR_HANDLER = new ContextErrorHandler() {
    public void handle(Exception e) {
      e.printStackTrace();
    }
  };

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
