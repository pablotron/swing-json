package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import com.google.gson.JsonObject;

public final class FlowLayoutParser implements LayoutParser {
  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // TODO: add support for configuring layout
    parent.setLayout(new FlowLayout());
  }

  public void add(
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    parent.add(child);
  }
};
