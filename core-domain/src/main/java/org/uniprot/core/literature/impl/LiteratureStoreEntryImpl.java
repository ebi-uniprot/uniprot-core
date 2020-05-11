package org.uniprot.core.literature.impl;

import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStoreEntry;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
public class LiteratureStoreEntryImpl implements LiteratureStoreEntry {

    private static final long serialVersionUID = 7032356535824460436L;
    private LiteratureEntry literatureEntry;
    private List<LiteratureMappedReference> literatureMappedReferences;

    LiteratureStoreEntryImpl() {
        this(null, null);
    }

    LiteratureStoreEntryImpl(
            LiteratureEntry literatureEntry,
            List<LiteratureMappedReference> literatureMappedReferences) {
        this.literatureEntry = literatureEntry;
        this.literatureMappedReferences = Utils.unmodifiableList(literatureMappedReferences);
    }

    @Override
    public List<LiteratureMappedReference> getLiteratureMappedReferences() {
        return literatureMappedReferences;
    }

    @Override
    public LiteratureEntry getLiteratureEntry() {
        return literatureEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureStoreEntryImpl that = (LiteratureStoreEntryImpl) o;
        return Objects.equals(getLiteratureEntry(), that.getLiteratureEntry())
                && Objects.equals(
                        getLiteratureMappedReferences(), that.getLiteratureMappedReferences());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiteratureEntry(), getLiteratureMappedReferences());
    }

    @Override
    public String toString() {
        return "LiteratureStoreEntryImpl{"
                + "literatureEntry="
                + literatureEntry
                + ", literatureMappedReferences="
                + literatureMappedReferences
                + '}';
    }
}
