package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.FieldType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.NameType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.Section;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProteinDescriptionImpl implements ProteinDescription {
    private final Section mainSection;
    private final List<Flag> flags;
    private final List<Section> contains;
    private final List<Section> includes;

    public ProteinDescriptionImpl(Section mainSection,
        List<Flag> flags) {
        this(mainSection, flags, null, null);
    }

    public ProteinDescriptionImpl(Section mainSection,
        List<Flag> flags,
        List<Section> contains, List<Section> includes) {
        this.mainSection = mainSection;
        if ((flags != null) && !flags.isEmpty())
            this.flags = Collections.unmodifiableList(flags);
        else
            this.flags = Collections.emptyList();

        if ((contains != null) && !contains.isEmpty())
            this.contains = Collections.unmodifiableList(contains);
        else
            this.contains = Collections.emptyList();

        if ((includes != null) && !includes.isEmpty())
            this.includes = Collections.unmodifiableList(includes);
        else
            this.includes = Collections.emptyList();
    }

    @Override
    public Name getRecommendedName() {
        List<Name> names = mainSection.getNamesByType(NameType.RECNAME);
        if (names.isEmpty())
            return null;
        else
            return names.get(0);
    }

    @Override
    public boolean hasRecommendedName() {
        return getRecommendedName() != null;
    }

    @Override
    public List<Name> getAlternativeNames() {
        return mainSection.getNamesByType(NameType.ALTNAME);
    }

    @Override
    public boolean hasAlternativeNames() {
        return !getAlternativeNames().isEmpty();
    }

    @Override
    public List<Name> getSubNames() {
        return mainSection.getNamesByType(NameType.SUBNAME);
    }

    @Override
    public boolean hasSubNames() {
        return !getSubNames().isEmpty();
    }

    @Override
    public List<Section> getIncludes() {
        return includes;
    }

    @Override
    public List<Section> getContains() {
        return contains;
    }

    @Override
    public List<Flag> getFlags() {
        return flags;
    }

    @Override
    public List<String> getEcNumbers() {
        return this.mainSection.getNames().stream()
                .flatMap(val -> val.getFields().stream())
                .filter(val -> val.getType() == FieldType.EC)
                .map(val -> val.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contains == null) ? 0 : contains.hashCode());
        result = prime * result + ((flags == null) ? 0 : flags.hashCode());
        result = prime * result + ((includes == null) ? 0 : includes.hashCode());
        result = prime * result + ((mainSection == null) ? 0 : mainSection.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProteinDescriptionImpl other = (ProteinDescriptionImpl) obj;
        if (contains == null) {
            if (other.contains != null)
                return false;
        } else if (!contains.equals(other.contains))
            return false;
        if (flags == null) {
            if (other.flags != null)
                return false;
        } else if (!flags.equals(other.flags))
            return false;
        if (includes == null) {
            if (other.includes != null)
                return false;
        } else if (!includes.equals(other.includes))
            return false;
        if (mainSection == null) {
            if (other.mainSection != null)
                return false;
        } else if (!mainSection.equals(other.mainSection))
            return false;
        return true;
    }

}
