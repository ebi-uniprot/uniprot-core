package org.uniprot.core.cv.disease.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.disease.DiseaseCrossReference;

public class DiseaseCrossReferenceBuilder implements Builder<DiseaseCrossReference> {

    private String databaseType;
    private String id;
    private List<String> properties = new ArrayList<>();

    public @Nonnull DiseaseCrossReferenceBuilder databaseType(String databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public @Nonnull DiseaseCrossReferenceBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull DiseaseCrossReferenceBuilder propertiesSet(List<String> properties) {
        this.properties = modifiableList(properties);
        return this;
    }

    public @Nonnull DiseaseCrossReferenceBuilder propertiesAdd(String property) {
        addOrIgnoreNull(property, this.properties);
        return this;
    }

    public @Nonnull DiseaseCrossReference build() {
        return new DiseaseCrossReferenceImpl(databaseType, id, properties);
    }

    public static @Nonnull DiseaseCrossReferenceBuilder from(
            @Nonnull DiseaseCrossReference instance) {
        return new DiseaseCrossReferenceBuilder()
                .databaseType(instance.getDatabaseType())
                .id(instance.getId())
                .propertiesSet(instance.getProperties());
    }
}
