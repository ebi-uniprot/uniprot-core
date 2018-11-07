package uk.ac.ebi.uniprot.parser.impl.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ac.UniProtAcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.UniProtDrObjects;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineConverter;


public class EntryObjectConverter implements Converter<EntryObject, UniProtEntry> {
	private final  AcLineConverter acLineConverter = new AcLineConverter();
	private final  CcLineConverter ccLineConverter = new CcLineConverter();
	private final  DeLineConverter deLineConverter = new DeLineConverter();
	
	private final  DtLineConverter dtLineConverter = new DtLineConverter();
	private final  FtLineConverter ftLineConverter = new FtLineConverter();
	private final  GnLineConverter gnLineConverter = new GnLineConverter();
	private final  IdLineConverter idLineConverter = new IdLineConverter();
	private final  KwLineConverter kwLineConverter = new KwLineConverter();
	private final  OcLineConverter ocLineConverter = new OcLineConverter();
	private final  OgLineConverter ogLineConverter = new OgLineConverter();
	
	private final  OhLineConverter ohLineConverter = new OhLineConverter();
	private final  OsLineConverter osLineConverter = new OsLineConverter();
	private final  OxLineConverter oxLineConverter = new OxLineConverter();
	private final  PeLineConverter peLineConverter = new PeLineConverter();

	private final  SqLineConverter sqLineConverter = new SqLineConverter();
	private final  SsLineConverter ssLineConverter = new SsLineConverter();
	
	private final ReferenceObjectConverter refObjConverter = new ReferenceObjectConverter();
	private final DrLineConverter drLineConverter;

	public EntryObjectConverter(){
		drLineConverter = new DrLineConverter();
	}
	public EntryObjectConverter(boolean ignoreWrongDR){
		drLineConverter = new DrLineConverter(ignoreWrongDR);
	}

	@Override
	public UniProtEntry convert(EntryObject f) {
		clear();
		UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        Map.Entry<UniProtId, UniProtEntryType> ids  =idLineConverter.convert(f.id);
        builder.uniProtId(ids.getKey())
        .entryType(ids.getValue());
		UniProtAcLineObject acLineObj =acLineConverter.convert(f.ac);
		builder.accession(acLineObj.getPrimaryAccession())
		.secondaryAccessions(acLineObj.getSecondAccessions())
		.entryAudit(dtLineConverter.convert(f.dt));
		if(f.cc !=null)
			builder.comments(ccLineConverter.convert(f.cc));
		builder.proteinDescription(deLineConverter.convert(f.de));
		UniProtDrObjects drObjects = drLineConverter.convert(f.dr);
		builder.uniProtDBCrossReferences(drObjects.drObjects);
		
		if(f.ft !=null){
			builder.features(ftLineConverter.convert(f.ft));
		}
		if(f.gn !=null)
			
			builder.genes(gnLineConverter.convert(f.gn));
		if(f.kw !=null){
			builder.keywords(kwLineConverter.convert(f.kw));
		}
		builder.taxonomyLineage(ocLineConverter.convert(f.oc));
		if(f.og !=null){
			builder.organelles(ogLineConverter.convert(f.og));
		}
		if(f.oh !=null){
			builder.organismHosts(ohLineConverter.convert(f.oh));
		}

		builder.organism(osLineConverter.convert(f.os));
		builder.uniProtTaxonId(oxLineConverter.convert(f.ox));
		builder.proteinExistence(peLineConverter.convert(f.pe));
		builder.sequence(sqLineConverter.convert(f.sq));
		List<UniProtReference<? extends Citation>> citations = new ArrayList<>();
		for(EntryObject.ReferenceObject refObj: f.ref){
			citations.add(refObjConverter.convert(refObj));
		}
		builder.uniProtReferences(citations);
		
		InternalSection usl = ssLineConverter.convert(f.ss);
	
		if(drObjects.ssProsites !=null){
			List<InternalLine> internalLines = new ArrayList<>();
			internalLines.addAll(drObjects.ssProsites);
			if(usl !=null)
				internalLines.addAll(usl.getInternalLines());
			if(usl !=null)
				builder.internalSection( UniProtFactory.INSTANCE.createInternalSection(internalLines,
						usl.getEvidenceLines(), usl.getSourceLines()));
			else {
				builder.internalSection( UniProtFactory.INSTANCE.createInternalSection(internalLines,
						Collections.emptyList(), Collections.emptyList()));
			}
		}else {
			builder.internalSection(usl);
		}

		
	//	entry.setEvidences(buildEvidences(ssEvidences));
		return builder.build();
	
	}

	private void clear(){
		ccLineConverter.clear();
		deLineConverter.clear();
		ftLineConverter.clear();
		gnLineConverter.clear();
		kwLineConverter.clear();
		ogLineConverter.clear();
		oxLineConverter.clear();
		refObjConverter.clear();
		drLineConverter.clear();
	}
	private List<Evidence> buildEvidences(List<Evidence> ssEvidences) {
		List<Evidence> evidences =new ArrayList<>();
		Collection<Evidence> evidenceIds = new HashSet<>();
		evidenceIds.addAll(ccLineConverter.getEvidences());
		evidenceIds.addAll(deLineConverter.getEvidences());
		evidenceIds.addAll(ftLineConverter.getEvidences());
		evidenceIds.addAll(gnLineConverter.getEvidences());
		evidenceIds.addAll(kwLineConverter.getEvidences());
		
		
		evidenceIds.addAll(ogLineConverter.getEvidences());
		evidenceIds.addAll(oxLineConverter.getEvidences());		
		evidenceIds.addAll(refObjConverter.getEvidences());
		evidenceIds.addAll(drLineConverter.getEvidences());
		List<Evidence> evIds = new ArrayList<>();
		evIds.addAll(evidenceIds);
		Collections.sort(evIds);
		if((ssEvidences ==null) || (ssEvidences.isEmpty())){
			evidences.addAll(evidenceIds);
		}else{
			evidences.addAll(ssEvidences);
		}
		
		return evidences;
	}
}
