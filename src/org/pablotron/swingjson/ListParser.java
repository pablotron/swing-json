package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import static javax.swing.JList.*;
import static javax.swing.ListSelectionModel.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class ListParser implements ComponentParser {
  protected ListParser() {}

  public Component parse(
    final Context context,
    final JsonObject el
  ) throws Exception {
    final JList<ListParserItem> r = new JList<ListParserItem>();

    ComponentParsers.init_component(context, el, r);

    // set visible row count
    if (el.has("visible-row-count"))
      r.setVisibleRowCount(el.get("visible-row-count").getAsInt());

    // set layout orientation
    if (el.has("layout-orientation")) {
      r.setLayoutOrientation(get_layout_orientation(
        el.get("layout-orientation").getAsString()
      ));
    }

    // set selection mode
    if (el.has("selection-mode")) {
      r.setLayoutOrientation(get_selection_mode(
        el.get("selection-mode").getAsString()
      ));
    }

    // set drag enabled
    if (el.has("drag-enabled"))
      r.setDragEnabled(el.get("drag-enabled").getAsBoolean());

    // get tabs
    if (el.has("items")) {
      final JsonArray items = el.getAsJsonArray("items");

      if (items.size() > 0) {
        final List<Integer> selected = new ArrayList<Integer>();
        final List<Integer> visible = new ArrayList<Integer>();

        // set list model
        final DefaultListModel<ListParserItem> model = new DefaultListModel<ListParserItem>();
        r.setModel(model);

        // add items
        for (int i = 0, l = items.size(); i < l; i++) {
          final JsonObject o = items.get(i).getAsJsonObject();

          // check for required properties
          if (!o.has("text"))
            throw new Exception("missing list item property: text");

          // add item
          model.addElement(new ListParserItem(
            context.getText(o.get("text").getAsString()),
            o.has("data") ? o.get("data").getAsJsonObject() : null
          ));

          // add index to selected list
          if (o.has("selected") && o.get("selected").getAsBoolean())
            selected.add(i);

          if (o.has("ensure-visible") && o.get("ensure-visible").getAsBoolean())
            visible.add(i);
        }

        // set selected indices
        if (selected.size() > 1) {
          final int indices[] = new int[selected.size()];

          for (int i = 0, l = selected.size(); i < l; i++)
            indices[i] = selected.get(i);

          // ArrayList.toArray() hates me
          r.setSelectedIndices(indices);
        } else if (selected.size() == 1) {
          r.setSelectedIndex(selected.get(0));
        }

        // set ensure visible indices
        for (final int i: visible) 
          r.ensureIndexIsVisible(i);
      }
    }

    // return result
    return r;
  }

  private static final Map<String, Integer> layout_orientations = new HashMap<String, Integer>() {{
    put("horizonal-wrap", HORIZONTAL_WRAP);
    put("vertical-wrap", VERTICAL_WRAP);
    put("vertical", VERTICAL);
  }};

  private static int get_layout_orientation(final String s) throws Exception {
    if (!layout_orientations.containsKey(s))
      throw new Exception("unknown list layout orientation: " + s);

    return layout_orientations.get(s);
  }

  private static final Map<String, Integer> selection_modes = new HashMap<String, Integer>() {{
    put("single", SINGLE_SELECTION);
    put("single-interval", SINGLE_INTERVAL_SELECTION);
    put("multiple-interval", MULTIPLE_INTERVAL_SELECTION);
  }};

  private static int get_selection_mode(final String s) throws Exception {
    if (!selection_modes.containsKey(s))
      throw new Exception("unknown list selection mode: " + s);

    return selection_modes.get(s);
  }
};
