package org.uniprot.core.flatfile.parser.impl.rn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprotkb.evidence.Evidence;

public class RnLineConverter extends EvidenceCollector
        implements Converter<RnLineObject, List<Evidence>> {
    @Override
    public List<Evidence> convert(RnLineObject f) {
        Map<Object, List<Evidence>> evidences =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        List<Evidence> evIds = evidences.get(f.number);
        if (evIds == null) evIds = new ArrayList<>();
        return evIds;
    }
}
