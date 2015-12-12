package org.pablotron.swingjson;

import java.awt.Component;
import java.awt.Container;
import javax.swing.SpringLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

public final class SpringLayoutParser implements LayoutParser {
  protected SpringLayoutParser() {}

  public void set(
    final Container parent,
    final JsonObject el
  ) throws Exception {
    // set layout
    parent.setLayout(new SpringLayout());
  }

  public void add(
    final Context context,
    final Container parent,
    final JsonObject config,
    final Component child
  ) throws Exception {
    if (config.has("constraints")) {
      final SpringLayout layout = (SpringLayout) parent.getLayout();

      // defer init in case target is not defined
      context.addInit(new Runnable() {
        public void run() {
          try {
            for (final JsonElement el: config.getAsJsonArray("constraints"))
              constrain(context, layout, child, el.getAsJsonObject());
          } catch (Exception e) {
            context.error(e);
          }
        }
      });
    }

    // add to parent
    parent.add(child);
  }

  private void constrain(
    final Context context,
    final SpringLayout layout,
    final Component child,
    final JsonObject constraint
  ) throws Exception {
    // get side
    if (!constraint.has("side"))
      throw new Exception("missing constraint property: side");
    final String side = SpringSideParser.parse(
      constraint.get("side").getAsString()
    );

    // get target
    if (!constraint.has("target-id"))
      throw new Exception("missing constraint property: target-id");
    final Component target = context.get(
      constraint.get("target-id").getAsString()
    );

    // get target side
    if (!constraint.has("target-side"))
      throw new Exception("missing constraint property: target-side");
    final String target_side = SpringSideParser.parse(
      constraint.get("target-side").getAsString()
    );

    if (constraint.has("pad")) {
      // add pad constraint
      layout.putConstraint(side, child, constraint.get(
        "pad"
      ).getAsInt(), target_side, target);
    } else if (constraint.has("spring")) {
      // add spring constraint
      layout.putConstraint(side, child, SpringParser.parse(
        context,
        constraint.getAsJsonObject("spring")
      ), target_side, target);
    } else {
      // unknown constraint type
      throw new Exception("constraint must have 'spring' or 'pad' property");
    }
  }
};
