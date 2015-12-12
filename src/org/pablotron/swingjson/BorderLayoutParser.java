package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import java.awt.BorderLayout;
import com.google.gson.JsonObject;

public final class BorderLayoutParser implements LayoutParser {
  protected BorderLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // set layout
    parent.setLayout(new BorderLayout());
  }

  public void add(
    final Context context,
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    parent.add(child, BorderLayoutPositions.get(config));
  }
};
