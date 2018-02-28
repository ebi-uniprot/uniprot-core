package uk.ac.ebi.uniprot.parser.impl.ss;



import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;


public class SsLineConverter implements Converter<SsLineObject, InternalSection> {
	private final UniProtFactory factory = UniProtFactory.INSTANCE;
	@Override
	public InternalSection convert(SsLineObject f) {
	
		if(f ==null)
			return null;
		List<InternalLine> internalLines = new ArrayList<>();
		List<SourceLine> sourceLines = new ArrayList<>();
		List<EvidenceLine> evidenceLines = new ArrayList<>();

		for(SsLineObject.EvLine ev:f.ssEVLines){
			evidenceLines.add(convert(ev));
		}

		for(SsLineObject.SsLine ia:f.ssIALines){
			internalLines.add(convert(ia));
		}
		for(String source:f.ssSourceLines){
			sourceLines.add(factory.createSourceLine(source));
		}
		return factory.createInternalSection(internalLines, evidenceLines, sourceLines);
	}
	private InternalLine convert(SsLineObject.SsLine ia){
		return factory.createInternalLine(convertILType(ia.topic), ia.text);
		
	}
	private InternalLineType convertILType(String type){
		if(type.equals("CL")){
			return InternalLineType.CL;
		}else if(type.equals("CP")){
			return InternalLineType.CP;
		}else if(type.equals("CX")){
			return InternalLineType.CX;
		}else if(type.equals("DG")){
			return InternalLineType.DG;
		}else if(type.equals("DR")){
			return InternalLineType.DR;
		}else if(type.equals("ET")){
			return InternalLineType.ET;
		}else if(type.equals("EV")){
			return InternalLineType.EV;
		}else if(type.equals("GO")){
			return InternalLineType.GO;
		}else if(type.equals("HA")){
			return InternalLineType.HA;
		}else if(type.equals("HP")){
			return InternalLineType.HP;
		}else if(type.equals("HR")){
			return InternalLineType.HR;
		}else if(type.equals("HU")){
			return InternalLineType.HU;
		}else if(type.equals("HW")){
			return InternalLineType.HW;
		}else if(type.equals("ID")){
			return InternalLineType.ID;
		}else if(type.equals("IS")){
			return InternalLineType.IS;
		}else if(type.equals("NI")){
			return InternalLineType.NI;
		}else if(type.equals("PE")){
			return InternalLineType.PE;
		}else if(type.equals("PM")){
			return InternalLineType.PM;
		}else if(type.equals("PROSITE")){
			return InternalLineType.PROSITE;
		}else if(type.equals("RU")){
			return InternalLineType.RU;
		}else if(type.equals("SO")){
			return InternalLineType.SO;
		}else if(type.equals("TX")){
			return InternalLineType.TX;
		}else if(type.equals("UP")){
			return InternalLineType.UP;
		}else if(type.equals("YY")){
			return InternalLineType.YY;
		}else if(type.equals("ZA")){
			return InternalLineType.ZA;
		}else if(type.equals("ZB")){
			return InternalLineType.ZB;
		}else if(type.equals("ZC")){
			return InternalLineType.ZC;
		}else if(type.equals("ZR")){
			return InternalLineType.ZR;
		}else if(type.equals("ZZ")){
			return InternalLineType.ZZ;
		}
		 throw new IllegalArgumentException("unknown internal line type " + type);
	}
	private EvidenceLine convert(SsLineObject.EvLine e){
		StringBuilder sb = new StringBuilder();
		sb.append(e.id);
		if(!Strings.isNullOrEmpty(e.db)) {
			sb.append("|");
			sb.append(e.db);
			if(!Strings.isNullOrEmpty(e.attr_1) ) {
				sb.append(":").append(e.attr_1);
			}
		}
		Evidence evidence =EvidenceFactory.INSTANCE.createFromEvidenceLine(sb.toString());
//		EvidenceType evidenceType = EvidenceType.typeOf(e.db);
//		EvidenceCode evidenceCode = EvidenceCode.typeOf(e.id);
//		String attr = e.attr_1;
//		if("-".equals(attr))
//			attr ="";
//	
//		Evidence evidence = EvidenceFactory.INSTANCE.createEvidence(evidenceType, evidenceCode, attr);
		return factory.createEvidenceLine(evidence,  e.date, e.attr_2);
	
	}
	
	
}
