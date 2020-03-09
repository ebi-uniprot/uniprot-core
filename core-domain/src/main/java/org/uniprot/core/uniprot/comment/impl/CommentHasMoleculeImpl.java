package org.uniprot.core.uniprot.comment.impl;

import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.HasMolecule;

/**
 * @author jluo
 * @date: 20 Nov 2019
 */
public abstract class CommentHasMoleculeImpl extends CommentImpl implements HasMolecule {
    /** */
    private static final long serialVersionUID = 1533166121392422458L;

    private String molecule;

    CommentHasMoleculeImpl(CommentType commentType, String molecule) {
        super(commentType);
        this.molecule = molecule;
    }

    @Override
    public String getMolecule() {
        return this.molecule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CommentHasMoleculeImpl comment = (CommentHasMoleculeImpl) o;
        return Objects.equals(molecule, comment.molecule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), molecule);
    }
}
