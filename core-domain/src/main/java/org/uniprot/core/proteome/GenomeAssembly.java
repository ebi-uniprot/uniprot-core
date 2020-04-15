package org.uniprot.core.proteome;

/**
 *
 * @author jluo
 * @date: 15 Apr 2020
 *
*/

public interface GenomeAssembly {
	
	String getAssemblyId();
	String getGenomeAssemblyUrl();
	GenomeAssemblyLevel getLevel();
	GenomeAssemblySource getSource();
}

