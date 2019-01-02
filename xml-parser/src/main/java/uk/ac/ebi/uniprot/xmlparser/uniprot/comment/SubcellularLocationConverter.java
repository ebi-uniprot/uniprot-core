package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SubcellularLocationType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class SubcellularLocationConverter implements Converter<SubcellularLocationType, SubcellularLocation> {
	   private static final Pattern COMMA = Pattern.compile(",");
	private final ObjectFactory xmlUniprotFactory;
	private final RnaEdPositionConverter positionConverter;
	private final EvidencedValueConverter evValueConverter;
	private final EvidenceIndexMapper evRefMapper;

	public SubcellularLocationConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public SubcellularLocationConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.positionConverter = new RnaEdPositionConverter(evRefMapper, xmlUniprotFactory);
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
		this.evRefMapper =evRefMapper;
	}
	
	
	@Override
	public SubcellularLocation fromXml(SubcellularLocationType xmlObj) {
		   if(xmlObj ==null)
			   return null;
		   SubcellularLocationValue location = convertLocationValue(xmlObj.getLocation());
		   SubcellularLocationValue topology = convertLocationValue(xmlObj.getTopology());
		   SubcellularLocationValue orientation =convertLocationValue(xmlObj.getOrientation());
		return SubcellularLocationCommentBuilder.createSubcellularLocation(location, topology, orientation);
	}
	
	private SubcellularLocationValue convertLocationValue(List<EvidencedStringType>  locations) {
		if((locations ==null) || locations.isEmpty()) {
			return null;
		}
		  String locationStr = getLocationValues(locations);
		  List<Evidence> evidences =new ArrayList<>();
		  for (EvidencedStringType locationType : locations) {
              if (!locationType.getEvidence().isEmpty()) {
                  final List<Evidence> evidenceIds = evRefMapper
                          .parseEvidenceIds(locationType.getEvidence());
                  for(Evidence evidence: evidenceIds)
                	  if(!evidences.contains(evidence)) {
                		  evidences.add(evidence);
                	  }                
              }
          }
		return SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationStr, evidences);
	}
	 private String getLocationValues(List<EvidencedStringType> values) {
	   //     Map<EvidencedStringType, String> map = transferCase(values);
	        return values.stream().map(val -> val.getValue()).collect(Collectors.joining(", "));
	    }

	  
	
	@Override
	public SubcellularLocationType toXml(SubcellularLocation uniObj) {
		if(uniObj ==null)
			return null;
		 SubcellularLocationType subcellularLocationType = xmlUniprotFactory.createSubcellularLocationType();
	            String[] locations = COMMA.split(uniObj.getLocation().getValue().trim());
	            for (String value : locations) {
	                final String aLocation = value.trim();
	                if (!aLocation.isEmpty()) {
	                    subcellularLocationType.getLocation()
	                            .add(buildLocation(aLocation, uniObj.getLocation()));
	                }
	            }
	            EvidencedStringType orientationType = buildLocation(uniObj.getOrientation());
	            if (orientationType != null)
	                subcellularLocationType.getOrientation().add(orientationType);

	            String[] tops = COMMA.split(uniObj.getTopology().getValue().trim());
	            for (String value : tops) {
	                final String aTopology = value.trim();
	                if (!aTopology.isEmpty()) {
	                    subcellularLocationType.getTopology()
	                            .add(buildLocation(aTopology, uniObj.getTopology()));

	                }
	            }
	            return subcellularLocationType;
	
	}
	   private EvidencedStringType buildLocation(String value, SubcellularLocationValue locationValue) {
			EvidencedStringType typeLocation = xmlUniprotFactory.createEvidencedStringType();
            typeLocation.setValue(StringUtils.capitalize(value));
           
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
