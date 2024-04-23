package org.uniprot.core.uniprotkb;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public enum DeletedReason implements EnumDisplay {

    UNKNOWN( "Unknown reason", 0, 4, 5, 7, 9, 12),
    SOURCE_DELETION( "Sequence source deletion", 1, 6, 8, 11),
    SWISSPROT_DELETION( "SwissProt deletion", 2),
    REDUNDANCY( "Sequence redundancy", 3),
    ENTRY_TYPE_CHANGE( "Entry type change", 10),
    PROTEOME_REDUNDANCY( "Proteome Redundancy", 13),
    PROTEOME_EXCLUSION( "Proteome Exclusion", 14),
    OVERREPRESENTED( "Overrepresented sequence", 15);

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
        try {
            return Arrays.stream(DeletedReason.values())
                    .filter(reason -> reason.getIds().contains(Integer.parseInt(id)))
                    .findFirst()
                    .orElseThrow();
        } catch (Exception e) {
            throw new IllegalArgumentException("The DeletedReason id '" + id + "' doesn't exist.");
        }
    }
}
