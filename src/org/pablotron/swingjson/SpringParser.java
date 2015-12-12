package org.pablotron.swingjson;

import java.util.Map;
import java.util.HashMap;
import javax.swing.Spring;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public final class SpringParser {
  private SpringParser() {}

  public interface Parser { 
    public Spring parse(
      final Context context,
      final JsonObject spring
    ) throws Exception;
  };

  private static final Map<String, Parser> lut = new HashMap<String, Parser>() {{
    put("constant", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("value"))
          throw new Exception("missing constant spring property: value");

        return Spring.constant(spring.get("value").getAsInt());
      }
    });

    put("constants", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("min"))
          throw new Exception("missing constants spring property: min");
        if (!spring.has("pref"))
          throw new Exception("missing constants spring property: pref");
        if (!spring.has("max"))
          throw new Exception("missing constants spring property: max");

        return Spring.constant(
          spring.get("min").getAsInt(),
          spring.get("pref").getAsInt(),
          spring.get("max").getAsInt()
        );
      }
    });

    put("height", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("id"))
          throw new Exception("missing height spring property: id");

        return Spring.height(context.get(spring.get("id").getAsString()));
      }
    });

    put("max", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("springs"))
          throw new Exception("missing max spring property: springs");
        final JsonArray springs = spring.getAsJsonArray("springs");

        if (springs.size() != 2) 
          throw new Exception("springs array does not have 2 elements");

        return Spring.max(
          SpringParser.parse(context, springs.get(0).getAsJsonObject()),
          SpringParser.parse(context, springs.get(1).getAsJsonObject())
        );
      }
    });

    put("minus", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("spring"))
          throw new Exception("missing minus spring property 'spring'");

        return Spring.minus(SpringParser.parse(
          context,
          spring.get("spring").getAsJsonObject()
        ));
      }
    });

    put("scale", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("spring"))
          throw new Exception("missing minus spring property: spring");
        if (!spring.has("factor"))
          throw new Exception("missing minus spring property: factor");

        return Spring.scale(
          SpringParser.parse(context, spring.get("spring").getAsJsonObject()),
          spring.get("factor").getAsFloat()
        );
      }
    });

    put("sum", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("springs"))
          throw new Exception("missing sum spring property: springs");
        final JsonArray springs = spring.getAsJsonArray("springs");
        if (springs.size() != 2) 
          throw new Exception("springs array does not have 2 elements");

        return Spring.sum(
          SpringParser.parse(context, springs.get(0).getAsJsonObject()),
          SpringParser.parse(context, springs.get(1).getAsJsonObject())
        );
      }
    });

    put("width", new Parser() {
      public Spring parse(
        final Context context,
        final JsonObject spring
      ) throws Exception {
        if (!spring.has("id"))
          throw new Exception("missing height spring property: id");

        return Spring.width(context.get(spring.get("id").getAsString()));
      }
    });
  }};

  protected static Spring parse(
    final Context context,
    final JsonObject spring
  ) throws Exception {
    if (!spring.has("type"))
      throw new Exception("missing spring property: type");
    final String type = spring.get("type").getAsString();

    if (!lut.containsKey(type))
      throw new Exception("unknown spring type: " + type);

    // parse and return spring
    return lut.get(type).parse(context, spring);
  }
};
