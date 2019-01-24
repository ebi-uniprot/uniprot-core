package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.FreeText;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.Entry;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinExistenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.citation.ReferenceConverter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.comment.CommentConverterFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.description.ProteinDescriptionConverter;

public class UniProtEntryConverter implements Converter<Entry, UniProtEntry> {
	private static final String INTERACTION = "interaction";
	private EvidenceIndexMapper evRefMapper;
	private ObjectFactory xmlUniprotFactory;
	private EvidenceConverter evidenceConverter;
	private ProteinDescriptionConverter descriptionConverter;
	private GeneConverter geneConverter;
	private OrganelleConverter organelleConverter;
	private ReferenceConverter referenceConverter;
	private UniProtCrossReferenceConverter xrefConverter;
	private KeywordConverter keywordConverter;
	private FeatureConverter featureConverter;
	private SequenceConverter sequenceConverter;
	private FlagUpdater flagUpdater;
	private OrganismConverter organismConverter;
	private OrganismHostConverter organismHostConverter;
	public UniProtEntryConverter() {
		init();
	}

	public void init() {
		evRefMapper = new EvidenceIndexMapper();
		xmlUniprotFactory = new ObjectFactory();
		evidenceConverter = new EvidenceConverter(xmlUniprotFactory);
		descriptionConverter = new ProteinDescriptionConverter(evRefMapper, xmlUniprotFactory);
		geneConverter = new GeneConverter(evRefMapper, xmlUniprotFactory);
		organelleConverter = new OrganelleConverter(evRefMapper, xmlUniprotFactory);
		this.referenceConverter = new ReferenceConverter(evRefMapper, xmlUniprotFactory);
		this.xrefConverter = new UniProtCrossReferenceConverter(xmlUniprotFactory);
		this.keywordConverter = new KeywordConverter(evRefMapper, xmlUniprotFactory);
		this.featureConverter = new FeatureConverter(evRefMapper, xmlUniprotFactory);
		this.sequenceConverter = new SequenceConverter(xmlUniprotFactory);
		flagUpdater = new FlagUpdater();
		this.organismConverter =new OrganismConverter(evRefMapper, xmlUniprotFactory);
		this.organismHostConverter =new OrganismHostConverter(xmlUniprotFactory);
	}

	@Override
	public UniProtEntry fromXml(Entry xmlEntry) {
		Map<Evidence, Integer> evidenceIdMap = fromXmlForEvidences(xmlEntry);
		evRefMapper.reset(evidenceIdMap);
		UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
		updateMetaDataFromXml(xmlEntry, builder);
		builder.organism(organismConverter.fromXml(xmlEntry.getOrganism()));
		if(!xmlEntry.getOrganismHost().isEmpty()) {
			builder.organismHosts(
			xmlEntry.getOrganismHost().stream()
			.map(organismHostConverter::fromXml)
			.collect(Collectors.toList()));
		}
		ProteinDescription proteinDescription =descriptionConverter.fromXml(xmlEntry.getProtein());
		builder.proteinDescription(flagUpdater.fromXml(proteinDescription, xmlEntry.getSequence()));
		builder.genes(xmlEntry.getGene().stream().map(geneConverter::fromXml).collect(Collectors.toList()));
		builder.organelles(
				xmlEntry.getGeneLocation().stream().map(organelleConverter::fromXml).collect(Collectors.toList()));
		builder.references(
				xmlEntry.getReference().stream().map(referenceConverter::fromXml).collect(Collectors.toList()));
		builder.comments(fromXmlForComments(xmlEntry));
		builder.uniProtDBCrossReferences(xmlEntry.getDbReference().stream().filter(val -> !val.getType().equals("EC"))
				.map(xrefConverter::fromXml).filter(val -> val != null).collect(Collectors.toList()));
		builder.keywords(xmlEntry.getKeyword().stream().map(keywordConverter::fromXml).collect(Collectors.toList()));
		builder.features(xmlEntry.getFeature().stream().map(featureConverter::fromXml).collect(Collectors.toList()));
		builder.sequence(sequenceConverter.fromXml(xmlEntry.getSequence()));
		return builder.build();
	}

	@Override
	public Entry toXml(UniProtEntry entry) {
		evRefMapper.reset(getEvidences(entry));
		Entry xmlEntry = xmlUniprotFactory.createEntry();
		updateMetaDataToXml(xmlEntry, entry);
		xmlEntry.setOrganism(organismConverter.toXml(entry.getOrganism()));
		entry.getOrganismHosts().forEach(val -> xmlEntry.getOrganismHost().add(organismHostConverter.toXml(val)));
		xmlEntry.setProtein(descriptionConverter.toXml(entry.getProteinDescription()));
		entry.getGenes().forEach(gene -> xmlEntry.getGene().add(geneConverter.toXml(gene)));
		entry.getOrganelles().forEach(organelle -> xmlEntry.getGeneLocation().add(organelleConverter.toXml(organelle)));
		toXmlForCitations(xmlEntry, entry);
		toXmlForComments(xmlEntry, entry);
		xmlEntry.getDbReference().addAll(descriptionConverter.toXmlDbReferences(entry.getProteinDescription()));
		xmlEntry.getDbReference().addAll(
				entry.getDatabaseCrossReferences().stream().map(xrefConverter::toXml).collect(Collectors.toList()));
		entry.getKeywords().forEach(val -> xmlEntry.getKeyword().add(keywordConverter.toXml(val)));
		entry.getFeatures().forEach(val -> xmlEntry.getFeature().add(featureConverter.toXml(val)));
		xmlEntry.setSequence(toXmlForSequence(entry));
		updateEvidence(xmlEntry);
		return xmlEntry;
	}

	private void updateEvidence(Entry xmlEntry){
		Map<Integer, Evidence> mapVal = evRefMapper.getIndexToEvidenceMap();
		mapVal.entrySet().forEach(val -> {
			EvidenceType xmlEvidence = evidenceConverter.toXml(val.getValue());
			xmlEvidence.setKey(BigInteger.valueOf(val.getKey().longValue()));
			xmlEntry.getEvidence().add(xmlEvidence);
		});
	}
	 private SequenceType toXmlForSequence( UniProtEntry entry) {

	        // Sequence related
	        SequenceType sequenceXml = sequenceConverter.toXml(entry.getSequence());
	      
	        sequenceXml.setVersion(entry.getEntryAudit().getSequenceVersion());
			LocalDate date = entry.getEntryAudit().getLastSequenceUpdateDate();
			sequenceXml.setModified(XmlConverterHelper.dateToXml(date));
			flagUpdater.toXml(sequenceXml, entry.getProteinDescription());
			
			return sequenceXml;
		
	    }
	
	// For interactions, multiple comment lines must be wrapped up into one comment
	// line in the flat file..
	// ..with multiple interactions.
	private List<Comment> fromXmlForComments(Entry xmlEntry) {
		List<Comment> uniComments = new ArrayList<>();
		List<CommentType> comments = xmlEntry.getComment();
		List<CommentType> interactionComment = comments.stream().filter(val -> val.getType().equals(INTERACTION))
				.collect(Collectors.toList());

		boolean interactionsFirst = true;
		for (CommentType commentType : comments) {
			if (commentType.getType().equals(INTERACTION)) {
				if (interactionsFirst) {
					interactionsFirst = false;
					uniComments.add(CommentConverterFactory.INSTANCE
							.createInteractionCommentConverter(this.xmlUniprotFactory).fromXml(interactionComment));
				}
			} else {
				uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType type = uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType
						.typeOf(commentType.getType());
				uniComments.add(CommentConverterFactory.INSTANCE
						.createCommentConverter(type, evRefMapper, xmlUniprotFactory).fromXml(commentType));
			}
		}
		return uniComments;
	}

	// Must process interaction comments separately
	private void toXmlForComments(Entry xmlEntry, UniProtEntry uniProtEntry) {
		for (Comment comment : uniProtEntry.getComments()) {
			if (comment.getCommentType() == uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType.INTERACTION) {

				xmlEntry.getComment().addAll(CommentConverterFactory.INSTANCE
						.createInteractionCommentConverter(this.xmlUniprotFactory).toXml((InteractionComment) comment));
			} else {
				xmlEntry.getComment()
						.add(CommentConverterFactory.INSTANCE
								.createCommentConverter(comment.getCommentType(), evRefMapper, xmlUniprotFactory)
								.toXml(comment));
			}
		}
	}

	private void toXmlForCitations(Entry xmlEntry, UniProtEntry uniProtEntry) {
		int keyVal = 0;
		for (UniProtReference citation : uniProtEntry.getReferences()) {
			final ReferenceType referenceType = referenceConverter.toXml(citation);
			referenceType.setKey(String.valueOf(++keyVal)); // todo required??
			xmlEntry.getReference().add(referenceType);
			// citationHandler.addIndex(keyVal);
		}
	}

	private void updateMetaDataFromXml(Entry xmlEntry, UniProtEntryBuilder builder) {
		builder.entryType(UniProtEntryType.typeOf(xmlEntry.getDataset()));
		builder.proteinExistence(ProteinExistence.typeOf(xmlEntry.getProteinExistence().getType()));
		builder.uniProtId(xmlEntry.getName().get(0));
		List<String> accessions = xmlEntry.getAccession();
		builder.primaryAccession(accessions.get(0));
		builder.secondaryAccessions(accessions.subList(1, accessions.size()).stream()
				.map(val -> UniProtFactory.INSTANCE.createUniProtAccession(val)).collect(Collectors.toList()));

		EntryAudit entryAudit = entryAuditFromXml(xmlEntry);
		builder.entryAudit(entryAudit);

	}

	private EntryAudit entryAuditFromXml(Entry xmlEntry) {
		int version = xmlEntry.getVersion();
		LocalDate firstPublic = XmlConverterHelper.dateFromXml(xmlEntry.getCreated());
		LocalDate lastUpdated = XmlConverterHelper.dateFromXml(xmlEntry.getModified());
		int seqVersion = xmlEntry.getSequence().getVersion();
		LocalDate seqDate = XmlConverterHelper.dateFromXml(xmlEntry.getSequence().getModified());
		return UniProtFactory.INSTANCE.createEntryAudit(firstPublic, lastUpdated, seqDate, version, seqVersion);
	}

	private void updateMetaDataToXml(Entry xmlEntry, UniProtEntry entry) {
		xmlEntry.setDataset(entry.getEntryType().getValue());
		ProteinExistenceType pet = xmlUniprotFactory.createProteinExistenceType();
		pet.setType(entry.getProteinExistence().getValue().toLowerCase());
		xmlEntry.setProteinExistence(pet);
		xmlEntry.getName().add(entry.getUniProtId().getValue());
		xmlEntry.getAccession().add(entry.getPrimaryAccession().getValue());
		entry.getSecondaryAccessions().forEach(val -> xmlEntry.getAccession().add(val.getValue()));
		xmlEntry.setCreated(XmlConverterHelper.dateToXml(entry.getEntryAudit().getFirstPublicDate()));
		xmlEntry.setModified(XmlConverterHelper.dateToXml(entry.getEntryAudit().getLastAnnotationUpdateDate()));
		xmlEntry.setVersion(entry.getEntryAudit().getEntryVersion());
	}

	private Map<Evidence, Integer> fromXmlForEvidences(Entry xmlEntry) {
		Map<Evidence, Integer> evIdMap = new HashMap<>();
		for (uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidenceType xmlEvidence : xmlEntry.getEvidence()) {
			Evidence evidence = evidenceConverter.fromXml(xmlEvidence);
			evIdMap.put(evidence, xmlEvidence.getKey().intValue());
		}
		return evIdMap;
	}

	private List<Evidence> getEvidences(UniProtEntry entry){
		Set<Evidence> evidences =new TreeSet<>();
		updateHasEvidence(evidences, entry.getOrganism());
		updateProteinDescriptionEvidences(evidences, entry.getProteinDescription());
		if(entry.getGenes() !=null) {
			entry.getGenes().forEach(val -> updateGeneEvidences(evidences, val));
		}
		if(entry.getComments() !=null) {
			entry.getComments().forEach(val -> updateCommentEvidences(evidences, val));
		}
		if(entry.getFeatures() !=null) {
			entry.getFeatures().forEach(val -> updateHasEvidence(evidences, val));
		}
		if(entry.getKeywords() !=null) {
			entry.getKeywords().forEach(val -> updateHasEvidence(evidences, val));
		}
		entry.getReferences().forEach(val -> updateReferenceEvidences(evidences, val));
		if(entry.getOrganelles() !=null) {
			entry.getOrganelles().forEach(val  -> updateHasEvidence(evidences, val));
		}
		List<Evidence> values =new  ArrayList<>(evidences);	
		return values;
	}
	private void updateReferenceEvidences(Set<Evidence> evidences,  UniProtReference ref) {
		updateHasEvidence(evidences, ref);
		updateHasEvidences(evidences, ref.getReferenceComments());
	}
	private <T extends Comment> void updateCommentEvidences(Set<Evidence> evidences, T comment) {
		if(comment instanceof FreeTextComment) {
			FreeTextComment ftComment = (FreeTextComment) comment;
			updateFreeTextEvidences(evidences, ftComment);
		}
		else if(comment instanceof AlternativeProductsComment) {
			AlternativeProductsComment apComment = (AlternativeProductsComment) comment;
			apComment.getIsoforms()
			.forEach(val ->updateAPIsoformEvidences(evidences, val));
			updateNoteEvidences(evidences, apComment.getNote());
		}
		else if(comment instanceof BPCPComment) {
			BPCPComment bpcpComment =(BPCPComment) comment;
			updateHasEvidence(evidences, bpcpComment.getAbsorption());
			if(bpcpComment.getKineticParameters() !=null) {
				bpcpComment.getKineticParameters().getMaximumVelocities()
				.forEach(val -> updateHasEvidence(evidences, val));

				bpcpComment.getKineticParameters().getMichaelisConstants()
				.forEach(val -> updateHasEvidence(evidences, val));
				updateNoteEvidences(evidences, bpcpComment.getKineticParameters().getNote());
			}
			
			
			updateFreeTextEvidences(evidences, bpcpComment.getPhDependence());
			updateFreeTextEvidences(evidences, bpcpComment.getRedoxPotential());
			updateFreeTextEvidences(evidences, bpcpComment.getTemperatureDependence());
		}
		else if(comment instanceof CatalyticActivityComment) {
			CatalyticActivityComment caComment = (CatalyticActivityComment) comment;
			updateHasEvidence(evidences, caComment.getReaction());
			updateHasEvidences(evidences, caComment.getPhysiologicalReactions());		
		}
		else if(comment instanceof CofactorComment) {
			CofactorComment cfComment = (CofactorComment) comment;
			updateNoteEvidences(evidences, cfComment.getNote());
			updateHasEvidences(evidences, cfComment.getCofactors());		
		}
		else if(comment instanceof DiseaseComment) {
			DiseaseComment diComment = (DiseaseComment) comment;
			updateNoteEvidences(evidences, diComment.getNote());
			updateHasEvidence(evidences, diComment.getDisease());		
		}
		else if(comment instanceof MassSpectrometryComment) {
			MassSpectrometryComment msComment = (MassSpectrometryComment) comment;
			evidences.addAll(msComment.getEvidences());	
		}
		else if(comment instanceof SequenceCautionComment) {
			SequenceCautionComment csComment = (SequenceCautionComment) comment;
			updateHasEvidence(evidences, csComment);		
		}
		else if(comment instanceof RnaEditingComment) {
			RnaEditingComment reComment = (RnaEditingComment) comment;
			updateNoteEvidences(evidences, reComment.getNote());
			updateHasEvidences(evidences, reComment.getPositions());		
		}
		else if(comment instanceof SubcellularLocationComment) {
			SubcellularLocationComment slComment = (SubcellularLocationComment) comment;
			updateNoteEvidences(evidences, slComment.getNote());
			slComment.getSubcellularLocations().forEach(
					val -> updateSubcellularLocationEvidence(evidences, val));	
		}
		
	}
	private void updateSubcellularLocationEvidence(Set<Evidence> evidences, SubcellularLocation sl) {
		updateHasEvidence(evidences, sl.getLocation());
		updateHasEvidence(evidences, sl.getOrientation());
		updateHasEvidence(evidences, sl.getTopology());
	}
	private void updateAPIsoformEvidences(Set<Evidence> evidences, APIsoform ap) {
		updateHasEvidence(evidences, ap.getName());
		updateHasEvidences(evidences, ap.getSynonyms());
		updateNoteEvidences(evidences, ap.getNote());
	}
	private void updateGeneEvidences(Set<Evidence> evidences, Gene gene) {
		updateHasEvidence(evidences, gene.getGeneName());
		updateHasEvidences(evidences, gene.getSynonyms());
		updateHasEvidences(evidences, gene.getOrfNames());
		updateHasEvidences(evidences, gene.getOrderedLocusNames());
		
	}
	
	private void updateProteinDescriptionEvidences(Set<Evidence> evidences, ProteinDescription pd) {
		updateProteinNameEvidences(evidences, pd.getRecommendedName());
		if(pd.getAlternativeNames() !=null) {
			pd.getAlternativeNames().forEach(val -> updateProteinNameEvidences(evidences, val));
		}
		if(pd.getSubmissionNames() !=null) {
			pd.getSubmissionNames().forEach(val -> updateProteinNameEvidences(evidences, val));
		}
		if(pd.getIncludes() !=null) {
			pd.getIncludes().forEach(val -> updateProteinSectionEvidences(evidences, val));
		}
		if(pd.getContains() !=null) {
			pd.getContains().forEach(val -> updateProteinSectionEvidences(evidences, val));
		}
		if(pd.getAllergenName() !=null) {
			updateHasEvidence(evidences,pd.getAllergenName()  );
		}
		if(pd.getBiotechName() !=null) {
			updateHasEvidence(evidences,pd.getBiotechName()  );
		}
		if((pd.getCdAntigenNames() !=null) && !pd.getCdAntigenNames().isEmpty() ) {
			updateHasEvidences(evidences,pd.getCdAntigenNames()  );
		}
		
		if((pd.getInnNames() !=null) && !pd.getInnNames().isEmpty() ) {
			updateHasEvidences(evidences,pd.getInnNames()  );
		}
	}
	private void updateProteinSectionEvidences(Set<Evidence> evidences, ProteinSection ps) {
		updateProteinNameEvidences(evidences, ps.getRecommendedName());
		if(ps.getAlternativeNames() !=null) {
			ps.getAlternativeNames().forEach(val -> updateProteinNameEvidences(evidences, val));
		}
	}
	private void updateProteinNameEvidences(Set<Evidence> evidences, ProteinName pn){
		if(pn ==null) {
			return ;
		}
		updateHasEvidence(evidences, pn.getFullName());
		updateHasEvidences(evidences, pn.getShortNames());
		updateHasEvidences(evidences, pn.getEcNumbers());
		
	}
	private void updateFreeTextEvidences(Set<Evidence> evidences, FreeText text) {
		if(text !=null) {
			updateHasEvidences(evidences, text.getTexts());
		}
	}
	private void updateNoteEvidences(Set<Evidence> evidences, Note note) {
		if(note !=null) {
			updateHasEvidences(evidences, note.getTexts());
		}
	}
	private void updateHasEvidences(Set<Evidence> evidences, List<? extends HasEvidences> hes) {
		if( hes !=null) {
			hes.forEach( val -> updateHasEvidence(evidences, val));
		}
	}
	private void updateHasEvidence(Set<Evidence> evidences, HasEvidences he) {
		if(he !=null) {
			evidences.addAll(he.getEvidences());
		}
	}
}
