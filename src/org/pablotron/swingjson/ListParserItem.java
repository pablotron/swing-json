package org.pablotron.swingjson;

public final class ListParserItem {
  private final String text;
  private final Object data;

  public ListParserItem(final String text, final Object data) {
    this.text = text;
    this.data = data;
  }

  public Object getData() {
    return this.data;
  }

  public String toString() {
    return this.text;
  }
};
