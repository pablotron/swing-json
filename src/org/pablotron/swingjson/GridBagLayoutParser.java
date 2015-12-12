package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import com.google.gson.JsonObject;

public final class GridBagLayoutParser implements LayoutParser {
  protected GridBagLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // set layout
    parent.setLayout(new GridBagLayout());
  }

  public void add(
    final Context context,
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    // get constraints
    if (!config.has("constraints"))
      throw new Exception("missing component property: constraints");
    final JsonObject o = config.getAsJsonObject("constraints");

    // add to parent
    parent.add(child, GridBagConstraintsParser.parse(o));
  }
};
