package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class EvidencedString {
    private final String value;
    private final List<String> evidences = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public List<String> getEvidences() {
        return evidences;
    }

    public EvidencedString(String value, List<String> evidences) {
        this.value = value;
        if (evidences != null) {
            this.evidences.addAll(evidences);
        }
    }

    public static EvidencedString get(String text1) {
        return new EvidencedString(text1, null);
    }

    public static EvidencedString get(String text1, List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return get(text1);
        } else {
            return new EvidencedString(text1, strings);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EvidencedString evValue = (EvidencedString) o;

        if (evidences != null && !evidences.isEmpty()
                ? !evidences.equals(evValue.evidences)
                : evValue.evidences != null && !evValue.evidences.isEmpty()) return false;

        return value.equals(evValue.value);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 29 * result + value.hashCode();
        result = 29 * result + evidences.hashCode();
        return result;
    }
}
