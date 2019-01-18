package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
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
	}

	@Override
	public UniProtEntry fromXml(Entry xmlEntry) {
		Map<Evidence, Integer> evidenceIdMap = fromXmlForEvidences(xmlEntry);
		evRefMapper.reset(evidenceIdMap);
		UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
		updateMetaDataFromXml(xmlEntry, builder);
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
		evRefMapper.reset(Collections.emptyList());
		Entry xmlEntry = xmlUniprotFactory.createEntry();
		updateMetaDataToXml(xmlEntry, entry);
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

}
