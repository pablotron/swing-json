package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.*;
import com.google.gson.JsonObject;

public final class ScrollPanePolicyParser {
  private ScrollPanePolicyParser() {}

  private static enum Policy {
    AS_NEEDED(
      "as-needed",
      HORIZONTAL_SCROLLBAR_AS_NEEDED,
      VERTICAL_SCROLLBAR_AS_NEEDED
    ),

    ALWAYS(
      "always",
      HORIZONTAL_SCROLLBAR_ALWAYS,
      VERTICAL_SCROLLBAR_ALWAYS
    ),

    NEVER(
      "never",
      HORIZONTAL_SCROLLBAR_NEVER,
      HORIZONTAL_SCROLLBAR_NEVER
    );
    
    private final String key;
    private final int h, v;

    private Policy(final String key, final int h, final int v) {
      this.key = key;
      this.h = h;
      this.v = v;
    }

    public void set(
      final JScrollPane r,
      final JsonObject o,
      final String dir
    ) throws Exception {
      if (o.has(dir)) {
        if (dir.equals("horizontal")) {
          r.setHorizontalScrollBarPolicy(this.h);
        } else if (dir.equals("vertical")) {
          r.setHorizontalScrollBarPolicy(this.v);
        } else {
          throw new Exception("unknown orientation: " + dir);
        }
      }
    }

    public String toString() {
      return this.key;
    }
  };

  private static Map<String, Policy> policies = new HashMap<String, Policy>() {{
    for (final Policy p: Policy.values())
      put(p.toString(), p);
  }};

  protected static void set(
    final JScrollPane r,
    final JsonObject o,
    final String dir
  ) throws Exception {
    if (o.has(dir)) {
      final String key = o.get(dir).getAsString();
      if (!policies.containsKey(key))
        throw new Exception("unknown scroll policy: " + key);

      policies.get(key).set(r, o, dir);
    }
  }
};
