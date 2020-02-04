package org.uniprot.core.flatfile.parser.impl.rc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

public class RcLineConverter extends EvidenceCollector
        implements Converter<RcLineObject, List<ReferenceComment>> {
    @Override
    public List<ReferenceComment> convert(RcLineObject f) {
        List<ReferenceComment> sss = new ArrayList<>();
        for (RcLineObject.RC rc : f.rcs) {
            ReferenceCommentType type = convert(rc.tokenType);
            Map<Object, List<Evidence>> evidences =
                    EvidenceConverterHelper.convert(rc.getEvidenceInfo());
            this.addAll(evidences.values());
            for (String val : rc.values) {
                ReferenceComment refComment =
                        new ReferenceCommentBuilder()
                                .type(type)
                                .value(val)
                                .evidencesSet(evidences.get(val))
                                .build();
                sss.add(refComment);
            }
        }
        return sss;
    }

    private ReferenceCommentType convert(RcLineObject.RcTokenEnum type) {
        switch (type) {
            case STRAIN:
                return ReferenceCommentType.STRAIN;
            case PLASMID:
                return ReferenceCommentType.PLASMID;
            case TRANSPOSON:
                return ReferenceCommentType.TRANSPOSON;
            case TISSUE:
                return ReferenceCommentType.TISSUE;
            default:
                return ReferenceCommentType.STRAIN;
        }
    }
}
