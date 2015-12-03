package org.pablotron.swingjson;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public enum ButtonType {
  BUTTON { 
    public AbstractButton create(final String text) {
      // TODO: icon
      return new JButton(text);
    }
  },

  CHECKBOX {
    public AbstractButton create(final String text) {
      // TODO: icon
      return new JCheckBox(text);
    }
  },

  RADIO {
    public AbstractButton create(final String text) {
      // TODO: icon
      return new JRadioButton(text);
    }
  };

  public abstract AbstractButton create(final String text);
};
