package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import java.awt.CardLayout;
import com.google.gson.JsonObject;

public final class CardLayoutParser implements LayoutParser {
  protected CardLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // set layout
    parent.setLayout(new CardLayout());
  }

  public void add(
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    if (!config.has("id"))
      throw new Exception("missing card property: id");

    parent.add(child, config.get("id").getAsString());
  }
};
