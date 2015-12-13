package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import java.awt.Cursor;
import static java.awt.Cursor.*;

public final class CursorParser {
  private CursorParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("crosshair", CROSSHAIR_CURSOR);
    put("custom", CUSTOM_CURSOR);
    put("default", DEFAULT_CURSOR);
    put("e-resize", E_RESIZE_CURSOR);
    put("hand", HAND_CURSOR);
    put("move", MOVE_CURSOR);
    put("n-resize", N_RESIZE_CURSOR);
    put("ne-resize", NE_RESIZE_CURSOR);
    put("nw-resize", NW_RESIZE_CURSOR);
    put("s-resize", S_RESIZE_CURSOR);
    put("se-resize", SE_RESIZE_CURSOR);
    put("sw-resize", SW_RESIZE_CURSOR);
    put("text", TEXT_CURSOR);
    put("w-resize", W_RESIZE_CURSOR);
    put("wait", WAIT_CURSOR);
  }};

  private static final Map<String, Cursor> cache = new HashMap<String, Cursor>();

  protected static Cursor parse(
    final String s
  ) throws Exception {
    final Cursor r;

    if (cache.containsKey(s)) {
      // get from cache
      r = cache.get(s);
    } else {
      if (lut.containsKey(s)) {
        // build new predefined cursor
        r = new Cursor(lut.get(s));
      } else {
        // build custom cursor
        r = Cursor.getSystemCustomCursor(s);
      }

      // cache cursor
      cache.put(s, r);
    }

    // return result
    return r;
  }
};
