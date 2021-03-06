package me.tajam.jext.namespace;

import java.util.HashMap;

import me.tajam.jext.namespace.JextNamespace.DefinedNamespace;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class NamespaceAuthor extends Namespace {

  NamespaceAuthor(JavaPlugin plugin, HashMap<DefinedNamespace, NamespacedKey> namespaceMap) {
    super(plugin, namespaceMap);
  }

  @Override
  public DefinedNamespace getKey() {
    return DefinedNamespace.AUTHOR;
  }
  
}