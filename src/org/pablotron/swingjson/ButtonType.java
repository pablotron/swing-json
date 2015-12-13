package org.pablotron.swingjson;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;

public enum ButtonType {
  DEFAULT {
    public AbstractButton createButton(final String text) {
      // TODO: icon
      return new JButton(text);
    }

    public JMenuItem createMenuItem(final String text) {
      return new JMenuItem(text);
    }
  },

  CHECKBOX {
    public AbstractButton createButton(final String text) {
      // TODO: icon
      return new JCheckBox(text);
    }

    public JMenuItem createMenuItem(final String text) {
      return new JCheckBoxMenuItem(text);
    }
  },

  RADIO {
    public AbstractButton createButton(final String text) {
      // TODO: icon
      return new JRadioButton(text);
    }

    public JMenuItem createMenuItem(final String text) {
      return new JRadioButtonMenuItem(text);
    }
  };

  public abstract AbstractButton createButton(final String text);
  public abstract JMenuItem createMenuItem(final String text);
};
