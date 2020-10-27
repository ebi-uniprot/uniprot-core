package org.uniprot.core.genecentric.impl;

import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.impl.UniProtKBFastaImpl;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class ProteinImpl extends UniProtKBFastaImpl implements Protein {

    private static final long serialVersionUID = -2231166801909993211L;

    ProteinImpl() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public ProteinImpl(
            UniProtKBEntryType entryType,
            String id,
            UniProtKBId uniProtkbId,
            String proteinName,
            Organism organism,
            String geneName,
            ProteinExistence proteinExistence,
            FlagType flagType,
            Sequence sequence,
            Integer sequenceVersion) {
        super(
                entryType,
                id,
                uniProtkbId,
                proteinName,
                organism,
                geneName,
                proteinExistence,
                flagType,
                sequence,
                sequenceVersion);
    }
}
