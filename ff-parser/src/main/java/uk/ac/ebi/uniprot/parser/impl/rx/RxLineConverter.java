package uk.ac.ebi.uniprot.parser.impl.rx;


import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.impl.CitationXrefsImpl;
import uk.ac.ebi.uniprot.parser.Converter;

public class RxLineConverter implements Converter<RxLineObject, CitationXrefs> {
	@Override
	public CitationXrefs convert(RxLineObject f) {
		List<CitationXref> xrefs = new ArrayList<>();
		
		if((f==null)||(f.rxs ==null) ||(f.rxs.isEmpty()))
			return CitationXrefsImpl.newInstance().createCitationXrefs(xrefs);
		for (RxLineObject.RX rx: f.rxs){
			
			if(rx.type == RxLineObject.DB.PubMed){
				xrefs.add(CitationXrefsImpl.newInstance().createCitationXref(CitationXrefType.PUBMED, rx.value));
				
			}else if(rx.type == RxLineObject.DB.DOI){
				xrefs.add(CitationXrefsImpl.newInstance().createCitationXref(CitationXrefType.DOI, rx.value));
			}else if(rx.type == RxLineObject.DB.AGRICOLA){
				xrefs.add(CitationXrefsImpl.newInstance().createCitationXref(CitationXrefType.AGRICOLA, rx.value));
			}
		}
		return CitationXrefsImpl.newInstance().createCitationXrefs(xrefs);
	}

}
