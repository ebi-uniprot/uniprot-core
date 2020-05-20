package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;

public class VarSeqFeatureBuilder extends AbstractFeatureLineBuilder {

    @Override
    protected StringBuilder getDescription(UniProtKBFeature f) {
        StringBuilder sb = FTLineBuilderHelper.getDescriptionString(f);
        if (sb.length() > 0) {
            return new StringBuilder(" (" + sb.toString() + ")");
        } else return sb;
    }
}
