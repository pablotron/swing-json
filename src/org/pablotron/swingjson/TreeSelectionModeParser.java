package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import static javax.swing.tree.TreeSelectionModel.*;

public final class TreeSelectionModeParser {
  private TreeSelectionModeParser() {}

  private static final Map<String, Integer> lut = new HashMap<String, Integer>() {{
    put("contiguous", CONTIGUOUS_TREE_SELECTION);
    put("discontiguous", DISCONTIGUOUS_TREE_SELECTION);
    put("single", SINGLE_TREE_SELECTION);
  }};

  protected static int parse(final String s) throws Exception {
    if (!lut.containsKey(s))
      throw new Exception("unknown tree selection mode: " + s);

    return lut.get(s);
  }
};
