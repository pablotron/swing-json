package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import com.google.gson.JsonObject;

public interface LayoutParser {
  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception;

  public void add(
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception;
};
