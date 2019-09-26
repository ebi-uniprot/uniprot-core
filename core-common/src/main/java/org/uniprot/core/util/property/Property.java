package org.uniprot.core.util.property;

import java.util.List;

public interface Property {
  String getString(String key);

  String optString(String key, String defaultValue);

  List<Property> getProperties(String key);

  static List<Property> parseJsonArray(String jsonArray) {
    return PropertyObject.spliteratorToListIgnoreOthers(new PropertyArray(jsonArray).spliterator());
  }
}
