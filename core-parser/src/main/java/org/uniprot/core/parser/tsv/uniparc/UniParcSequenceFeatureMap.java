package org.uniprot.core.parser.tsv.uniparc;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcSequenceFeatureMap implements NamedValueMap {
    private static final String DELIMITER = ",";

    public static final List<String> FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "InterPro",
                            "CDD",
                            "Gene3D",
                            "HAMAP",
                            "PANTHER",
                            "Pfam",
                            "PIRSF",
                            "PRINTS",
                            "PROSITE",
                            "SFLD",
                            "SMART",
                            "SUPFAM",
                            "NCBIfam",
                            "FunFam"));

    private final List<SequenceFeature> features;

    public UniParcSequenceFeatureMap(List<SequenceFeature> features) {
        this.features = features;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), getInterPro());
        map.put(FIELDS.get(1), getData(SignatureDbType.CDD));
        map.put(FIELDS.get(2), getData(SignatureDbType.GENE3D));
        map.put(FIELDS.get(3), getData(SignatureDbType.HAMAP));
        map.put(FIELDS.get(4), getData(SignatureDbType.PANTHER));
        map.put(FIELDS.get(5), getData(SignatureDbType.PFAM));

        map.put(FIELDS.get(6), getData(SignatureDbType.PIRSF));
        map.put(FIELDS.get(7), getData(SignatureDbType.PRINTS));
        map.put(FIELDS.get(8), getData(SignatureDbType.PROSITE));

        map.put(FIELDS.get(9), getData(SignatureDbType.SFLD));
        map.put(FIELDS.get(10), getData(SignatureDbType.SMART));
        map.put(FIELDS.get(11), getData(SignatureDbType.SUPFAM));
        map.put(FIELDS.get(12), getData(SignatureDbType.NCBIFAM));
        map.put(FIELDS.get(13), getData(SignatureDbType.FUNFAM));

        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    private String getInterPro() {
        return features.stream()
                .map(val -> val.getInterProDomain())
                .filter(Objects::nonNull)
                .map(val -> val.getId())
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .collect(Collectors.joining(DELIMITER));
    }

    private String getData(SignatureDbType type) {
        return features.stream()
                .filter(val -> val.getSignatureDbType() == type)
                .map(val -> val.getSignatureDbId())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER));
    }
}
