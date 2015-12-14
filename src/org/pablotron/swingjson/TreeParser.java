package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class TreeParser implements ComponentParser {
  protected TreeParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JTree r;

    if (el.has("root")) {
      r = new JTree(TreeNodeParser.parse(context, el.getAsJsonObject("root")));
    } else {
      r = new JTree();
    }

    ComponentParsers.initJComponent(context, el, r);

    if (el.has("anchor-selection-path")) {
      // TODO
    }

    if (el.has("cell-editor")) {
      // TODO
    }

    // set cell renderer
    if (el.has("cell-renderer")) {
      r.setCellRenderer(TreeCellRendererParser.parse(
        context,
        el.getAsJsonObject("cell-renderer")
      ));
    }

    if (el.has("drag-enabled"))
      r.setDragEnabled(el.get("drag-enabled").getAsBoolean());

    if (el.has("drop-mode")) {
      // TODO
    }

    if (el.has("editable"))
      r.setEditable(el.get("editable").getAsBoolean());

    if (el.has("expanded-state")) {
      // TODO
    }

    if (el.has("expands-selected-paths"))
      r.setExpandsSelectedPaths(el.get("expands-selected-paths").getAsBoolean());

    if (el.has("invokes-stop-cell-editing"))
      r.setInvokesStopCellEditing(el.get("invokes-stop-cell-editing").getAsBoolean());

    if (el.has("large-model"))
      r.setLargeModel(el.get("large-model").getAsBoolean());

    if (el.has("lead-selection-path")) {
      // TODO
    }

    if (el.has("model")) {
      // TODO
    }

    if (el.has("root-visible"))
      r.setRootVisible(el.get("root-visible").getAsBoolean());

    if (el.has("row-height"))
      r.setRowHeight(el.get("row-height").getAsInt());

    if (el.has("scrolls-on-expand"))
      r.setScrollsOnExpand(el.get("scrolls-on-expand").getAsBoolean());

    if (el.has("selection-interval")) {
      final JsonArray a = el.getAsJsonArray("selection-interval");
      r.setSelectionInterval(a.get(0).getAsInt(), a.get(1).getAsInt());
    }

    if (el.has("selection-model")) {
      // TODO
    }

    // set selection mode
    if (el.has("selection-mode")) {
      r.getSelectionModel().setSelectionMode(TreeSelectionModeParser.parse(
        el.get("selection-mode").getAsString()
      ));
    }

    if (el.has("selection-path")) {
      // TODO
    }

    if (el.has("selection-paths")) {
      // TODO
    }

    if (el.has("selection-row"))
      r.setSelectionRow(el.get("selection-row").getAsInt());

    if (el.has("selection-rows")) {
      final JsonArray a = el.getAsJsonArray("selection-rows");

      final int rows[] = new int[a.size()];
      for (int i = 0, l = a.size(); i < l; i++)
        rows[i] = a.get(i).getAsInt();

      r.setSelectionRows(rows);
    }

    if (el.has("shows-root-handles"))
      r.setShowsRootHandles(el.get("shows-root-handles").getAsBoolean());

    if (el.has("toggle-click-count"))
      r.setToggleClickCount(el.get("toggle-click-count").getAsInt());


    if (el.has("ui")) {
      // TODO
    }

    if (el.has("visible-row-count"))
      r.setVisibleRowCount(el.get("visible-row-count").getAsInt());

    // return result
    return r;
  }
};
