package org.pablotron.swingjson;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class TreeNodeParser {
  private TreeNodeParser() {}

  protected static MutableTreeNode parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    // create result
    final DefaultMutableTreeNode r = new DefaultMutableTreeNode(
      new TreeNodeData(
        el.has("text") ? context.getText(el.get("text").getAsString()) : "",
        el.has("data") ? el.get("data") : null
      ), 

      // allow kids implicitly by presence of kids property
      el.has("kids")
    );

    // add kids
    if (el.has("kids")) {
      for (final JsonElement kid: el.getAsJsonArray("kids"))
        r.add(TreeNodeParser.parse(context, kid.getAsJsonObject()));
    }

    // return result
    return r;
  }
};
