package org.uniprot.core.cv.ec;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ECEntryBuilder {
    private String id;
    private String label;

    public ECEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ECEntryBuilder label(String label) {
        this.label = label;
        return this;
    }

    public ECEntry build() {
        return new ECEntryImpl(id, label);
    }
}
