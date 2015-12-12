package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static java.awt.GridBagConstraints.*;

public final class GridBagAnchorParser {
  private GridBagAnchorParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("north", NORTH);
    put("south", SOUTH);
    put("west", WEST);
    put("east", EAST);
    put("north-west", NORTHWEST);
    put("north-east", NORTHEAST);
    put("south-west", SOUTHWEST);
    put("south-east", SOUTHEAST);
    put("center", CENTER);
    put("page-start", PAGE_START);
    put("page-end", PAGE_END);
    put("line-start", LINE_START);
    put("line-end", LINE_END);
    put("first-line-start", FIRST_LINE_START);
    put("first-line-end", FIRST_LINE_END);
    put("last-line-start", LAST_LINE_START);
    put("last-line-end", LAST_LINE_END);
    put("baseline", BASELINE);
    put("baseline-leading", BASELINE_LEADING);
    put("baseline-trailing", BASELINE_TRAILING);
    put("above-baseline", ABOVE_BASELINE);
    put("above-baseline-leading", ABOVE_BASELINE_LEADING);
    put("above-baseline-trailing", ABOVE_BASELINE_TRAILING);
    put("below-baseline", BELOW_BASELINE);
    put("below-baseline-leading", BELOW_BASELINE_LEADING);
    put("below-baseline-trailing", BELOW_BASELINE_TRAILING);
  }};

  protected static int parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown anchor constraint: " + s);

    return lut.get(s);
  }
};
