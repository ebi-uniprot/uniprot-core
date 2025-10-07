package org.uniprot.core.uniprotkb;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public enum DeletedReason implements EnumDisplay {

    UNKNOWN( "", 0, 7, 9, 10, 12),
    UNDEFINED( "Undefined", -1),
    SOURCE_DELETION_EMBL( "Deleted from sequence source (EMBL)", 1),
    SOURCE_DELETION_TAIR( "Deleted from sequence source (TAIR)", 4),
    SOURCE_DELETION_SGD( "Deleted from sequence source (SGD)", 5),
    SOURCE_DELETION_ENSEMBL( "Deleted from sequence source (ENSEMBL)", 6),
    SOURCE_DELETION_PDB( "Deleted from sequence source (PDB)", 8),
    SOURCE_DELETION_REFSEQ( "Deleted from sequence source (RefSeq)", 11),
    SWISSPROT_DELETION( "Deleted from Swiss-Prot", 2),
    REDUNDANCY( "Redundant sequence", 3),
    PROTEOME_REDUNDANCY( "Redundant proteome", 13),
    PROTEOME_EXCLUSION( "Excluded proteome", 14),
    OVERREPRESENTED( "Over-represented sequence", 15),
    REFERENCE_PROTEOME_EXCLUSION( "Not part of a reference proteome", 16);

    private final List<Integer> ids;
    private final String description;

    DeletedReason(String description,Integer ... id) {
        this.ids = Arrays.asList(id);
        this.description = description;
    }

    @Nonnull
    @Override
    public String getName() {
        return description;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public static DeletedReason fromId(String id) {
            return Arrays.stream(DeletedReason.values())
                    .filter(reason -> reason.getIds().contains(Integer.parseInt(id)))
                    .findFirst()
                    .orElse(DeletedReason.UNDEFINED);
    }
}
