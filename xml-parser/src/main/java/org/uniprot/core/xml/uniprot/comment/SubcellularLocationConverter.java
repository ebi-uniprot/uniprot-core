package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.SubcellularLocationType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SubcellularLocationConverter implements Converter<SubcellularLocationType, SubcellularLocation> {
    private static final Pattern COMMA = Pattern.compile(",");
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;

    public SubcellularLocationConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public SubcellularLocationConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evRefMapper = evRefMapper;
    }

    @Override
    public SubcellularLocation fromXml(SubcellularLocationType xmlObj) {
        if (xmlObj == null)
            return null;
        SubcellularLocationValue location = convertLocationValue(xmlObj.getLocation());
        SubcellularLocationValue topology = convertLocationValue(xmlObj.getTopology());
        SubcellularLocationValue orientation = convertLocationValue(xmlObj.getOrientation());
        return new SubcellularLocationBuilder()
                .location(location)
                .topology(topology)
                .orientation(orientation)
                .build();
    }

    private SubcellularLocationValue convertLocationValue(List<EvidencedStringType> locations) {
        if ((locations == null) || locations.isEmpty()) {
            return null;
        }
        String locationStr = getLocationValues(locations);
        List<Evidence> evidences = new ArrayList<>();
        for (EvidencedStringType locationType : locations) {
            if (!locationType.getEvidence().isEmpty()) {
                final List<Evidence> evidenceIds = evRefMapper.parseEvidenceIds(locationType.getEvidence());
                for (Evidence evidence : evidenceIds)
                    if (!evidences.contains(evidence)) {
                        evidences.add(evidence);
                    }
            }
        }
        return new SubcellularLocationValueBuilder("", locationStr, evidences).build();
    }

    private String getLocationValues(List<EvidencedStringType> values) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < values.size(); i++) {
            String val = values.get(i).getValue();
            if (!isFirst) {
                val = lowerCaseFirstLetter(val);
                sb.append(", ");
            }
            isFirst = false;
            sb.append(val);
        }
        return sb.toString();
        // Map<EvidencedStringType, String> map = transferCase(values);
        // return values.stream().map(val ->
        // val.getValue()).collect(Collectors.joining(", "));
    }

    private String lowerCaseFirstLetter(String val) {
    	if(Utils.nullOrEmpty(val))
            return val;
        if (val.length() > 1) {
            char second = val.charAt(1);
            if ((second >= 'A') && (second <= 'Z')) {
                return val;
            }
        }
        return Utils.uncapitalize(val);
    }

    @Override
    public SubcellularLocationType toXml(SubcellularLocation uniObj) {
        if (uniObj == null)
            return null;
        SubcellularLocationType subcellularLocationType = xmlUniprotFactory.createSubcellularLocationType();
        String[] locations = COMMA.split(uniObj.getLocation().getValue().trim());
        for (String value : locations) {
            final String aLocation = value.trim();
            if (!aLocation.isEmpty()) {
                subcellularLocationType.getLocation().add(buildLocation(aLocation, uniObj.getLocation()));
            }
        }
        EvidencedStringType orientationType = buildLocation(uniObj.getOrientation());
        if (orientationType != null)
            subcellularLocationType.getOrientation().add(orientationType);
        if (uniObj.getTopology() != null) {
            String[] tops = COMMA.split(uniObj.getTopology().getValue().trim());
            for (String value : tops) {
                final String aTopology = value.trim();
                if (!aTopology.isEmpty()) {
                    subcellularLocationType.getTopology().add(buildLocation(aTopology, uniObj.getTopology()));

                }
            }
        }
        return subcellularLocationType;

    }

    private EvidencedStringType buildLocation(String value, SubcellularLocationValue locationValue) {
        EvidencedStringType typeLocation = xmlUniprotFactory.createEvidencedStringType();
        typeLocation.setValue(Utils.capitalize(value));

        // Evidences
        List<Evidence> evidenceIds = locationValue.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty())
                typeLocation.getEvidence().addAll(evs);
        }
        return typeLocation;

    }

    private EvidencedStringType buildLocation(SubcellularLocationValue locationValue) {
        if ((locationValue != null) && (!locationValue.getValue().isEmpty())) {
            return buildLocation(locationValue.getValue(), locationValue);
        }
        return null;
    }

}
