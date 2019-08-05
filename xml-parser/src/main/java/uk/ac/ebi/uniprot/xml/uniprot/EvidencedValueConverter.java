package uk.ac.ebi.uniprot.xml.uniprot;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

public class EvidencedValueConverter implements Converter<EvidencedStringType, EvidencedValue> {

	private static final String STOP = ".";
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	private final boolean text2Xml;

	public EvidencedValueConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory(), false);
	}

	public EvidencedValueConverter(EvidenceIndexMapper evRefMapper,
			ObjectFactory xmlUniprotFactory, boolean text2Xml) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.text2Xml = text2Xml;

	}

	@Override
	public EvidencedValue fromXml(EvidencedStringType xmlObj) {
		String value =xmlObj.getValue();
		if(text2Xml) {
			value = XmlConverterHelper.removeIfPostfix(value, STOP);
		}
		List<Evidence> evidences = null;
		if(! xmlObj.getEvidence().isEmpty()){
			evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		}else
			evidences = new ArrayList<>();
		return new EvidencedValueBuilder(value, evidences).build();
	}

	@Override
	public EvidencedStringType toXml(EvidencedValue uniObj) {
		 EvidencedStringType textType = xmlUniprotFactory.createEvidencedStringType();
		  if(this.text2Xml){
			  textType.setValue(XmlConverterHelper.addIfNoPostfix(uniObj.getValue(), STOP));       
		  }else{
			  textType.setValue(uniObj.getValue());       
		  }
         if(!uniObj.getEvidences().isEmpty()){
      		List<Integer> evs = evRefMapper.writeEvidences(uniObj.getEvidences());
      		textType.getEvidence().addAll(evs);
         }  
         return textType;
	}

}
