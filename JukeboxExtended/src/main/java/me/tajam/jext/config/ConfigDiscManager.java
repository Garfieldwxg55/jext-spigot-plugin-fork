package me.tajam.jext.config;

import me.tajam.jext.DiscContainer;
import me.tajam.jext.Log;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigDiscManager {

  private static final String PATH = "disc";
  private static ConfigDiscManager instance = null;
  public static ConfigDiscManager getInstance() {
    if (instance == null) {
      instance = new ConfigDiscManager();
    }
    return instance;
  }

  HashMap<String, ConfigDiscData> discMap;

  private ConfigDiscManager() {
    discMap = new HashMap<String, ConfigDiscData>();
  }

  public void load(ConfigurationSection section) {
    ConfigurationSection subsection = section.getConfigurationSection(PATH);
    final Set<String> keys = subsection.getKeys(false);
    for (String key : keys) {
      ConfigDiscData discData = new ConfigDiscData(key);
      discData.load(subsection);
      discMap.put(discData.getStringData(ConfigDiscData.Path.NAMESPACE), discData);
    }
    new Log().okay().t("Loaded ").b().t(" disc(s).").send(discMap.size());
  }

  public DiscContainer getDisc(String namespace) {
    ConfigDiscData discData = discMap.get(namespace);
    if (discData == null) return null;
    return new DiscContainer(discData);
  }

  public String getNamespaceFromModelData(int modelData) {
      for (Map.Entry<String, ConfigDiscData> entry : discMap.entrySet()) {
          String s = entry.getKey();
          ConfigDiscData configDiscData = entry.getValue();
          if (configDiscData.getIntegerData(ConfigDiscData.Path.MODEL_DATA) == modelData)
              return s;
      }
      return null;
  }

  public boolean haveDisc(String namespace) {
    return discMap.containsKey(namespace);
  }

  public Set<String> getNamespaces() {
    return discMap.keySet();
  }

}