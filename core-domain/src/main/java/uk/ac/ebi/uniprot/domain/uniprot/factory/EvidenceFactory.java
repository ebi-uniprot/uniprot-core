package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

public final class EvidenceFactory {
    // private static final List<String> NOT_EVIDENCE_TYPES = Arrays.asList(
    // "PubMed", "Reference", "Ref");
    // private static final List<EvidenceCode> MAPPED_ECO_CODES = Arrays.asList(
    // EvidenceCode.ECO_0000312, EvidenceCode.ECO_0000250);

    public static Evidence from(String val) {
        String[] token = val.split("\\|");
        String code = token[0];
        String attribute = "";
        String type = "";
        String tempTypeVal = "";
        if (token.length == 2) {
            String[] tokens2 = token[1].split(":");
            if (tokens2.length == 2) {
                type = tokens2[0];
                attribute = tokens2[1];
            } else {
                tempTypeVal = tokens2[0];
            }
        }
        EvidenceCode evidenceCode = EvidenceCode.NOT_SPECIFIED;
        EvidenceType evidenceType = EvidenceType.NOT_SPECIFIED;
        try {
            evidenceCode = EvidenceCode.typeOf(code);
            evidenceType = convertToEvidenceType(evidenceCode);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            evidenceCode = EvidenceCode.NOT_SPECIFIED;
        }
        if (!type.isEmpty()) {
            try {
                EvidenceType evidenceType1 = EvidenceType.typeOf(type);
                if(evidenceType1 !=EvidenceType.NOT_SPECIFIED)
                    evidenceType = evidenceType1;

            } catch (Exception e) {

            }
        } else if (!tempTypeVal.isEmpty()) {
            EvidenceType tempType = EvidenceType.NOT_SPECIFIED;
            try {
                tempType = EvidenceType.typeOf(tempTypeVal);

            } catch (Exception e) {
            }
            if (tempType == EvidenceType.NOT_SPECIFIED) {
                attribute = tempTypeVal;
            } else {
                evidenceType = tempType;
            }
        }

        return new EvidenceImpl(evidenceType, evidenceCode, attribute);
    }

    public static EvidenceType convertToEvidenceType(EvidenceCode evidenceCode) {
        EvidenceType type = EvidenceType.NOT_SPECIFIED;
        switch (evidenceCode) {
            case ECO_0000305:
                type = EvidenceType.CURATOR;
                break;
            case ECO_0000303:
                type = EvidenceType.OPINION;
                break;
            case ECO_0000269:
                type = EvidenceType.EXPERIMENTAL;
                break;
            case ECO_0000312:
                type = EvidenceType.IMPORT;
                break;
            case ECO_0000250:
                type = EvidenceType.SIMILARITY;
                break;
            case ECO_0000255:
                type = EvidenceType.HAMAP_RULE;
                break;
            case ECO_0000213:
                type = EvidenceType.Proteomics;
                break;
            case ECO_0000257:
                type = EvidenceType.SEQUENCE_ANALAYSIS;
                break;
            default:
                type = EvidenceType.NOT_SPECIFIED;
        }

        return type;
    }
}
