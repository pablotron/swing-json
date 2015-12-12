package org.pablotron.swingjson;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import com.google.gson.JsonObject;

public final class GridBagConstraintsParser {
  private GridBagConstraintsParser() {}

  protected static GridBagConstraints parse(final JsonObject o) throws Exception {
    GridBagConstraints c = new GridBagConstraints();

    if (o.has("gridx"))
      c.gridx = o.get("gridx").getAsInt();
    if (o.has("gridy"))
      c.gridy = o.get("gridy").getAsInt();

    if (o.has("gridwidth"))
      c.gridwidth = o.get("gridwidth").getAsInt();
    if (o.has("gridheight"))
      c.gridheight = o.get("gridheight").getAsInt();

    if (o.has("fill"))
      c.fill = GridBagFillParser.parse(o.get("fill").getAsString());

    if (o.has("ipadx"))
      c.ipadx = o.get("ipadx").getAsInt();
    if (o.has("ipady"))
      c.ipady = o.get("ipady").getAsInt();

    if (o.has("anchor"))
      c.anchor = GridBagAnchorParser.parse(o.get("anchor").getAsString());

    if (o.has("weightx"))
      c.weightx = o.get("weightx").getAsDouble();
    if (o.has("weighty"))
      c.weighty = o.get("weighty").getAsDouble();

    if (o.has("insets")) {
      JsonObject insets = o.getAsJsonObject("insets");

      c.insets = new Insets(
        insets.get("top").getAsInt(),
        insets.get("left").getAsInt(),
        insets.get("bottom").getAsInt(),
        insets.get("right").getAsInt()
      );
    }

    return c;
  }
};
