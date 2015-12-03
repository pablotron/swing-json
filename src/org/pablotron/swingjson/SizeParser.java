package org.pablotron.swingjson;

import java.awt.Dimension;
import com.google.gson.JsonArray;

public final class SizeParser {
  private SizeParser() {}

  public static Dimension parse(final JsonArray a) {
    return new Dimension(a.get(0).getAsInt(), a.get(1).getAsInt());
  }
};
