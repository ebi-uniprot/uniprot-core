package org.uniprot.core.uniprot.impl;

import static org.uniprot.core.util.Utils.nullOrEmpty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.HasEvidencesImpl;
import org.uniprot.core.util.Utils;

public class KeywordImpl extends HasEvidencesImpl implements Keyword {

    public static final String DEFAULT_ACCESSION = "KW-00000";
    public static final String DEFAULT_NAME = "NAME NOT SET";
    private static final long serialVersionUID = -8858878734008282808L;
    private String id;
    private KeywordCategory category;
    private String name;

    // no arg constructor for JSON deserialization
    KeywordImpl() {
        this("", "", KeywordCategory.UNKNOWN, Collections.emptyList());
    }

    KeywordImpl(String id, String name, KeywordCategory category, List<Evidence> evidences) {
        super(evidences);
        if (nullOrEmpty(id)) {
            this.id = DEFAULT_ACCESSION;
        } else this.id = id;
        if (Utils.notNull(category)) this.category = category;
        else this.category = KeywordCategory.UNKNOWN;
        if (nullOrEmpty(name)) {
            this.name = DEFAULT_NAME;
        } else this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public KeywordCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KeywordImpl keyword = (KeywordImpl) o;
        return id.equals(keyword.id) && category == keyword.category && name.equals(keyword.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, category, name);
    }
}
