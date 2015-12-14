package org.pablotron.swingjson;

import java.awt.Color;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class TreeCellRendererParser {
  private TreeCellRendererParser() {}

  protected static TreeCellRenderer parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    // create result
    final DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();

    // TODO: add initJLabel
    ComponentParsers.initJComponent(context, el, r);

    if (el.has("tree-cell-colors")) {
      final JsonObject o = el.getAsJsonObject("tree-cell-colors");

      if (o.has("bg"))
        r.setBackground(Color.decode(o.get("bg").getAsString()));

      if (o.has("non-selection")) {
        final JsonObject h = o.getAsJsonObject("non-selection");

        if (h.has("bg")) {
          r.setBackgroundNonSelectionColor(context.getColor(
            h.get("bg").getAsString()
          ));
        }

        if (h.has("text")) {
          r.setTextNonSelectionColor(context.getColor(
            h.get("text").getAsString()
          ));
        }
      }

      if (o.has("selection")) {
        final JsonObject h = o.getAsJsonObject("selection");

        if (h.has("bg")) {
          r.setBackgroundSelectionColor(context.getColor(
            h.get("bg").getAsString()
          ));
        }

        if (h.has("text")) {
          r.setTextSelectionColor(context.getColor(
            h.get("text").getAsString()
          ));
        }

        if (h.has("border")) {
          r.setBorderSelectionColor(context.getColor(
            h.get("border").getAsString()
          ));
        }
      }
    }

    if (el.has("tree-cell-icons")) {
      final JsonObject o = el.getAsJsonObject("tree-cell-icons");

      if (o.has("closed")) {
        // TODO
      }

      if (o.has("leaf")) {
        // TODO
      }

      if (o.has("open")) {
        // TODO
      }
    }

    // return result
    return r;
  }
};
