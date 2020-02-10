package org.uniprot.core.cv.ec;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ECBuilder {
    private String id;
    private String label;

    public ECBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ECBuilder label(String label) {
        this.label = label;
        return this;
    }

    public EC build() {
        return new ECImpl(id, label);
    }
}
