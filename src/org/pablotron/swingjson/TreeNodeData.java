package org.pablotron.swingjson;

public final class TreeNodeData {
  private final String name;
  private final Object data;

  protected TreeNodeData(final String name, final Object data) {
    this.name = name;
    this.data = data;
  }

  public String getName() {
    return this.name;
  }

  public Object getData() {
    return this.data;
  }

  public String toString() {
    return this.name;
  }
};
