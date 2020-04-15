package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 15 Apr 2020
 */
public enum GenomeAssemblyLevel implements EnumDisplay<GenomeAssemblyLevel> {
  FULL("full"),
  PARTIAL("partial");

  private String name;

  GenomeAssemblyLevel(String name) {
    this.name = name;
  }

  public @Nonnull String getName() {
    return name;
  }

  @Override
  public @Nonnull String toDisplayName() {
    return getName();
  }
}
