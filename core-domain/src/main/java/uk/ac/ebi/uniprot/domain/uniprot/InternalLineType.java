package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.common.EnumDisplay;

/**
 * @author jluo
 */
public enum InternalLineType implements EnumDisplay<InternalLineType> {
    CL(1),
    //Chromosomal location of a gene.
    CP(2),
    // Complete proteome.
    CX(3),
    // Complete proteome.
    DR(4),
    // cross ref.
    DG(5),
    // Descendant groups
    GO(6),
    // GO stuff
    EV(7),
    // Evidence tag description.
    HA(8),
    // unirule
    HR(9),
    // unirule removed
    HW(10),
    // unirule warning
    HU(11),
    // unirule unsolved
    HP(12),
    // Unirule preannotation
    ID(13),
    // Machine suggested entry name (TrEMBL).
    IS(14),
    // next IsoId
    NI(15),
    // XXXXX (proposed new ID)
    PM(16),
    // Pattern matches (TrEMBL only).
    SO(17),
    // Source of entries from non-standard data source.
    YY(18),
    // Important comments (generally from Amos).
    ZA(19),
    // First curated by (Initials, date);
    ZB(20),
    // Update by ...
    ZC(21),
    // Followup person.
    ZR(22),
    // To be revisited (Amos).
    ZZ(23),
    // Miscelanous
    ET(24),
    // Tagged
    PE(25),
    // Tagged
    RU(26),
    // rulebase id
    TX(27),
    // taxon
    PROSITE(28),
    // PROSITE special case
    UP(29),
    ZD(23);
    //Complete proteome


    int position;

    InternalLineType(int pos) {
        this.position = pos;
    }

    public static InternalLineType getTypeByPosition(int position) {
        for (InternalLineType type : values()) {
            if (type.getPosition() == position) {
                return type;
            }
        }
        throw new IllegalArgumentException("unknown position " + position);
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toDisplayName() {
        return name();
    }
}
