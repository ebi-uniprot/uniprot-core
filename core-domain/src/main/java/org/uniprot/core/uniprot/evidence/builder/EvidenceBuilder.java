package org.uniprot.core.uniprot.evidence.builder;

import static org.uniprot.core.util.Utils.notNull;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.evidence.impl.EvidenceImpl;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class EvidenceBuilder implements Builder<Evidence> {
    private EvidenceCode evidenceCode;
    private String databaseName;
    private String databaseId;

    @Override
    public @Nonnull Evidence build() {
        if (databaseName == null && databaseId == null) {
            return new EvidenceImpl(evidenceCode, null);
        } else {
            return new EvidenceImpl(evidenceCode, databaseName, databaseId);
        }
    }

    public static @Nonnull EvidenceBuilder from(Evidence instance) {
        CrossReference<EvidenceDatabase> source = instance.getSource();
        EvidenceBuilder retBuilder = new EvidenceBuilder().evidenceCode(instance.getEvidenceCode());
        if (notNull(source)) {
            retBuilder.databaseId(source.getId()).databaseName(source.getDatabase().getName());
        }
        return retBuilder;
    }

    public @Nonnull EvidenceBuilder evidenceCode(EvidenceCode evidenceCode) {
        this.evidenceCode = evidenceCode;
        return this;
    }

    public @Nonnull EvidenceBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public @Nonnull EvidenceBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }
}
