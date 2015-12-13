package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.JTextArea;
import com.google.gson.JsonObject;

public final class TextAreaParser implements ComponentParser {
  protected TextAreaParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JTextArea r = new JTextArea();

    ComponentParsers.initJComponent(context, el, r);

    // set value
    if (el.has("text"))
      r.setText(context.getText(el.get("text").getAsString()));

    // set value
    if (el.has("editable"))
      r.setEditable(el.get("editable").getAsBoolean());

    // set rows
    if (el.has("rows"))
      r.setRows(el.get("rows").getAsInt());

    // set cols
    if (el.has("cols"))
      r.setColumns(el.get("cols").getAsInt());

    // set tabs
    if (el.has("tabs"))
      r.setTabSize(el.get("tabs").getAsInt());

    // set line wrapping
    if (el.has("line-wrap"))
      r.setLineWrap(el.get("line-wrap").getAsBoolean());

    // set word wrap
    if (el.has("word-wrap"))
      r.setWrapStyleWord(el.get("word-wrap").getAsBoolean());

    // return result
    return r;
  }
};
