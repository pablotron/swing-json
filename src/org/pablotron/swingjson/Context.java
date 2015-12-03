package org.pablotron.swingjson;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Component;
import java.awt.Component;
import javax.swing.ButtonGroup;

public final class Context {
  private final List<Component> roots = new ArrayList<>();
  private final Map<String, Component> ids = new HashMap<>();
  private final Map<String, String> texts = new HashMap<>();
  private final Map<String, List<Component>> groups = new HashMap<>();
  private final Map<String, ButtonGroup> button_groups = new HashMap<>();
  private final List<Runnable> inits = new ArrayList<>();

  protected Context() {}

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
};
