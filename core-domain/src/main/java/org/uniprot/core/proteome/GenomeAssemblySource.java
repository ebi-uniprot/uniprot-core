package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 15 Apr 2020
 *
*/

public enum GenomeAssemblySource implements EnumDisplay<GenomeAssemblySource> {
	ENA("ENA/EMBL"),
	ENSEMBLFUNGI("EnsemblFungi"),
	ENSEMBLPLANTS("EnsemblPlants"),
	ENSEMBLBACTERIA("EnsemblBacteria"),
	ENSEMBLPROTISTS("EnsemblProtists"),
	ENSEMBLMETAZOA("EnsemblMetazoa"),
	ENSEMBL("Ensembl"),
	REFSEQ("Refseq"),
	WORMBASE("WormBase"),
	VECTORBASE("VectorBase");
	
	private String name;
	GenomeAssemblySource(String name){
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

