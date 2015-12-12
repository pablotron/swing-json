package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import com.google.gson.JsonObject;

public final class GridLayoutParser implements LayoutParser {
  protected GridLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    int rows = 0;
    if (el.has("rows"))
      rows = el.get("rows").getAsInt();

    int cols = 0;
    if (el.has("cols"))
      cols = el.get("cols").getAsInt();

    int hgap = 0;
    if (el.has("hgap"))
      hgap = el.get("hgap").getAsInt();

    int vgap = 0;
    if (el.has("vgap"))
      vgap = el.get("vgap").getAsInt();

    // set layout
    parent.setLayout(new GridLayout(rows, cols, hgap, vgap));
  }

  public void add(
    final Context context,
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    // add to parent
    parent.add(child);
  }
};
