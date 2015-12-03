package org.pablotron.swingjson;

import java.awt.Component;
import javax.swing.Box;

public enum GlueType {
  HORIZONTAL {
    public Component create() {
      return Box.createHorizontalGlue();
    }
  },

  VERTICAL {
    public Component create() {
      return Box.createVerticalGlue();
    }
  }; 

  public abstract Component create();
};
