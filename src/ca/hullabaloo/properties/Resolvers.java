package ca.hullabaloo.properties;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

class Resolvers {
  public static Resolver viewOf(final Properties properties) {
    return new Resolver() {
      public String resolve(String name) {
        return properties.getProperty(name);
      }
    };
  }

  public static Resolver viewOf(final Map<String, String> properties) {
    return new Resolver() {
      public String resolve(String name) {
        return properties.get(name);
      }
    };
  }

  public static Resolver viewOf(final ResourceBundle properties) {
    return new Resolver() {
      public String resolve(String name) {
        return properties.getString(name);
      }
    };
  }

  public static Resolver and(Iterable<Resolver> resolvers) {
    final Resolver[] copy = copyOf(resolvers);
    return new Resolver() {
      public String resolve(String name) {
        for (Resolver resolver : copy) {
          String value = resolver.resolve(name);
          if (value != null) {
            return value;
          }
        }
        return null;
      }
    };
  }

  private static Resolver[] copyOf(Iterable<Resolver> resolvers) {
    ArrayList<Resolver> list = new ArrayList<Resolver>();
    for (Resolver r : resolvers) {
      list.add(r);
    }
    Resolver[] result = new Resolver[list.size()];
    return list.toArray(result);
  }

  public static Resolver empty() {
    return new Resolver() {
      public String resolve(String name) {
        return null;
      }
    };
  }
}
