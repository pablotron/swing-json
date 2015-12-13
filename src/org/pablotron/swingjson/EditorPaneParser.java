package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JEditorPane;
import com.google.gson.JsonObject;

public final class EditorPaneParser implements ComponentParser {
  protected EditorPaneParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JEditorPane r = new JEditorPane();

    ComponentParsers.initJComponent(context, el, r);

    if (el.has("content-type")) 
      r.setContentType(el.get("content-type").getAsString());

    if (el.has("text")) 
      r.setText(context.getText(el.get("text").getAsString()));

    if (el.has("page"))
      r.setPage(el.get("page").getAsString());

    // set editable
    if (el.has("editable"))
      r.setEditable(el.get("editable").getAsBoolean());

    // return result
    return r;
  }
};
