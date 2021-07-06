package org.uniprot.utils.uniprotkb;

import java.util.Optional;

import org.uniprot.core.cv.go.GoEvidenceType;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.cv.evidence.GOEvidences;

/**
 * @author jluo
 * @date: 28 Jun 2021
 */
public class GoEvidenceUtils {
    public static Optional<EvidenceCode> goEvidenceType2EvidenceCode(
            GoEvidenceType goEvidenceType) {
        Optional<String> result = GOEvidences.INSTANCE.convertGAFToECO(goEvidenceType.name());
        return result.map(EvidenceCode::typeOf);
    }
}
