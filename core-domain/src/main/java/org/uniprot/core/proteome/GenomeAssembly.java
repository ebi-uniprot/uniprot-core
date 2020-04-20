package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 15 Apr 2020
 */
public interface GenomeAssembly extends Serializable {

    String getAssemblyId();

    String getGenomeAssemblyUrl();

    GenomeAssemblyLevel getLevel();

    GenomeAssemblySource getSource();
}
