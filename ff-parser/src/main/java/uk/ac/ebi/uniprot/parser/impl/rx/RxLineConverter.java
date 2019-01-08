package uk.ac.ebi.uniprot.parser.impl.rx;


import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.ArrayList;
import java.util.List;

public class RxLineConverter implements Converter<RxLineObject, List<DBCrossReference<CitationXrefType>>> {
	@Override
	public List<DBCrossReference<CitationXrefType>> convert(RxLineObject f) {
		List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
		
		if((f==null)||(f.rxs ==null) ||(f.rxs.isEmpty()))
			return xrefs;
		for (RxLineObject.RX rx: f.rxs){
			
			if(rx.type == RxLineObject.DB.PubMed){
				xrefs.add(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, rx.value));
				
			}else if(rx.type == RxLineObject.DB.DOI){
				xrefs.add(new DBCrossReferenceImpl<>(CitationXrefType.DOI, rx.value));
			}else if(rx.type == RxLineObject.DB.AGRICOLA){
				xrefs.add(new DBCrossReferenceImpl<>(CitationXrefType.AGRICOLA, rx.value));
			}
		}
	 	return xrefs;
	}
	

}
