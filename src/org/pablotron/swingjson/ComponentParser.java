package org.pablotron.swingjson;

import java.awt.Component;
import com.google.gson.JsonObject;
import org.pablotron.swingjson.Context;

public interface ComponentParser {
  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception;
};
