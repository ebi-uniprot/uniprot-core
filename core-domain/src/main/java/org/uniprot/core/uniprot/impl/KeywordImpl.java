package org.uniprot.core.uniprot.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

public class KeywordImpl extends EvidencedValueImpl implements Keyword {

    public static final String DEFAULT_ACCESSION = "KW-00000";
    private static final long serialVersionUID = -8858878734008282808L;
    private String id;
    private KeywordCategory category;
    private KeywordImpl() {
        this("", "", KeywordCategory.UNKNOWN, Collections.emptyList());
    }

    public KeywordImpl(String id, String value, KeywordCategory category, List<Evidence> evidences) {
        super(value, evidences);
        if (id == null || id.isEmpty()) {
            this.id = DEFAULT_ACCESSION;
        } else
            this.id = id;
        this.category =category;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KeywordImpl keyword = (KeywordImpl) o;
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

	@Override
	public KeywordCategory getCategory() {
		return category;
	}
}
