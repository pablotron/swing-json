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
    final Context context,
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    // get card id
    if (!config.has("id"))
      throw new Exception("missing card property: id");
    final String id = config.get("id").getAsString();

    // add to parent
    parent.add(child, id);

    // show child (if requested)
    if (config.has("show") && config.get("show").getAsBoolean())
      ((CardLayout) parent.getLayout()).show(parent, id);
  }
};
