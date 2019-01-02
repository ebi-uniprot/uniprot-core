package uk.ac.ebi.uniprot.domain;

public enum PositionModifier implements EnumDisplay<PositionModifier> {
    EXACT,
    OUTSIDE,
    UNKOWN,
    UNSURE;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
