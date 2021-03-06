package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum InternalLineType implements EnumDisplay {
    CL(1),
    // Chromosomal location of a gene.
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
    // Complete proteome

    int position;

    InternalLineType(int pos) {
        this.position = pos;
    }

    public int getPosition() {
        return position;
    }

    public @Nonnull String getDisplayName() {
        return name();
    }

    public @Nonnull String getName() {
        return String.valueOf(position);
    }

    public static @Nonnull InternalLineType typeOf(int position) {
        return EnumDisplay.typeOf(String.valueOf(position), InternalLineType.class);
    }
}
