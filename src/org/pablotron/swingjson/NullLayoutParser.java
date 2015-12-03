package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import com.google.gson.JsonObject;

public final class NullLayoutParser implements LayoutParser {
  public void set(
    final Container container,
    final JsonObject el
  ) throws Exception {
    // do nothing
  }

  public void add(
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    parent.add(child);
  }
};
