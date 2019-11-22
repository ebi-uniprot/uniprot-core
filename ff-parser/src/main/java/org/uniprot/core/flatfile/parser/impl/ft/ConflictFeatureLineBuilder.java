package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.uniprot.feature.Feature;

public class ConflictFeatureLineBuilder extends AbstractFeatureLineBuilder {
    protected StringBuilder getDescription(Feature f) {
        StringBuilder sb = FTLineBuilderHelper.getDescriptionString(f);
        if (sb.length() > 0) {
            return new StringBuilder(" (" + sb.toString() + ")");
        } else return sb;
    }
}
