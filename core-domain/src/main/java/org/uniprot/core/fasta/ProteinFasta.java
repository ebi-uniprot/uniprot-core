package org.uniprot.core.fasta;

import java.io.Serializable;

import org.uniprot.core.Sequence;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public interface ProteinFasta extends Serializable {

    String getId();

    Sequence getSequence();
}
