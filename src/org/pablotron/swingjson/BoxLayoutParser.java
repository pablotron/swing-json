package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;
import com.google.gson.JsonObject;

public final class BoxLayoutParser implements LayoutParser {
  protected BoxLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // set layout
    parent.setLayout(new BoxLayout(parent, BoxLayoutAxisParser.parse(
      el.get("axis").getAsString()
    )));
  }

  public void add(
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    parent.add(child);
  }
};
