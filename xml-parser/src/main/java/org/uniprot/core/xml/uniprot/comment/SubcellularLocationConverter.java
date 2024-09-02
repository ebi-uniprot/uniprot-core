package org.uniprot.core.xml.uniprot.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationValue;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.SubcellularLocationType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public class SubcellularLocationConverter
        implements Converter<SubcellularLocationType, SubcellularLocation> {
    private static final Pattern COMMA = Pattern.compile(",");
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;
    private final SubcellLocationNameMap subcellLocationNameMap;

    public SubcellularLocationConverter(
            EvidenceIndexMapper evRefMapper, SubcellLocationNameMap subcellLocationNameMap) {
        this(evRefMapper, new ObjectFactory(), subcellLocationNameMap);
    }

    public SubcellularLocationConverter(
            EvidenceIndexMapper evRefMapper,
            ObjectFactory xmlUniprotFactory,
            SubcellLocationNameMap subcellLocationNameMap) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evRefMapper = evRefMapper;
        this.subcellLocationNameMap = subcellLocationNameMap;
    }

    @Override
    public SubcellularLocation fromXml(SubcellularLocationType xmlObj) {
        if (xmlObj == null) return null;
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
                final List<Evidence> evidenceIds =
                        evRefMapper.parseEvidenceIds(locationType.getEvidence());
                for (Evidence evidence : evidenceIds)
                    if (!evidences.contains(evidence)) {
                        evidences.add(evidence);
                    }
            }
        }
        return new SubcellularLocationValueBuilder()
                .id("")
                .value(locationStr)
                .evidencesSet(evidences)
                .build();
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
        String name = sb.toString();
        if (values.size() > 1) {
            return subcellLocationNameMap.getLocationName(name);
        } else {
            return name;
        }
    }

    private String lowerCaseFirstLetter(String val) {
        if (Utils.nullOrEmpty(val)) return val;
        if (val.length() > 1) {
            char second = val.charAt(1);
            if ((second >= 'A') && (second <= 'Z')) {
                return val;
            }
        }
        return Utils.lowerFirstChar(val);
    }

    @Override
    public SubcellularLocationType toXml(SubcellularLocation uniObj) {
        if (uniObj == null) return null;
        SubcellularLocationType subcellularLocationType =
                xmlUniprotFactory.createSubcellularLocationType();
        String[] locations = COMMA.split(uniObj.getLocation().getValue().trim());
        for (String value : locations) {
            final String aLocation = value.trim();
            if (!aLocation.isEmpty()) {
                subcellularLocationType
                        .getLocation()
                        .add(buildLocation(aLocation, uniObj.getLocation()));
            }
        }
        EvidencedStringType orientationType = buildLocation(uniObj.getOrientation());
        if (orientationType != null) subcellularLocationType.getOrientation().add(orientationType);
        if (uniObj.getTopology() != null) {
            String[] tops = COMMA.split(uniObj.getTopology().getValue().trim());
            for (String value : tops) {
                final String aTopology = value.trim();
                if (!aTopology.isEmpty()) {
                    subcellularLocationType
                            .getTopology()
                            .add(buildLocation(aTopology, uniObj.getTopology()));
                }
            }
        }
        return subcellularLocationType;
    }

    private EvidencedStringType buildLocation(
            String value, SubcellularLocationValue locationValue) {
        EvidencedStringType typeLocation = xmlUniprotFactory.createEvidencedStringType();
        typeLocation.setValue(capitalizeFirstLetter(value));

        // Evidences
        List<Evidence> evidenceIds = locationValue.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty()) typeLocation.getEvidence().addAll(evs);
        }
        return typeLocation;
    }

    
    private String capitalizeFirstLetter(String value) {
        if(Character.isUpperCase(value.charAt(0))) {
            return value;
        }
        String loc = subcellLocationNameMap.getName(value); 
        return loc==null?Utils.upperFirstChar(value):loc;      
    }
    
    private EvidencedStringType buildLocation(SubcellularLocationValue locationValue) {
        if ((locationValue != null) && (!locationValue.getValue().isEmpty())) {
            return buildLocation(locationValue.getValue(), locationValue);
        }
        return null;
    }
}
