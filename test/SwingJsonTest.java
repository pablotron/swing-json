package test;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import javax.swing.SwingUtilities;
import org.pablotron.swingjson.ContextParser;
import org.pablotron.swingjson.Context;

public final class SwingJsonTest {
  public static void main(String args[]) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        init();
      }
    });
  }

  private static void init() {
    try {
      final InputStreamReader in = new InputStreamReader(
        SwingJsonTest.class.getResourceAsStream("test.json")
      );

      ContextParser.parse(in).init();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
