package org.uniprot.core.xml.uniprot;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.ProteinExistence;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.impl.EntryAuditBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.impl.UniProtEntryBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.Entry;
import org.uniprot.core.xml.jaxb.uniprot.EvidenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinExistenceType;
import org.uniprot.core.xml.jaxb.uniprot.ReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;
import org.uniprot.core.xml.uniprot.citation.ReferenceConverter;
import org.uniprot.core.xml.uniprot.comment.CommentConverterFactory;
import org.uniprot.core.xml.uniprot.description.ProteinDescriptionConverter;

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
        this.organismConverter = new OrganismConverter(evRefMapper, xmlUniprotFactory);
        this.organismHostConverter = new OrganismHostConverter(xmlUniprotFactory);
    }

    @Override
    public UniProtEntry fromXml(Entry xmlEntry) {
        Map<Evidence, Integer> evidenceIdMap = fromXmlForEvidences(xmlEntry);
        evRefMapper.reset(evidenceIdMap);
        UniProtEntryBuilder activeEntryBuilder = createUniprotEntryBuilderFromXml(xmlEntry);
        activeEntryBuilder.organism(organismConverter.fromXml(xmlEntry.getOrganism()));
        if (!xmlEntry.getOrganismHost().isEmpty()) {
            activeEntryBuilder.organismHostsSet(
                    xmlEntry.getOrganismHost().stream()
                            .map(organismHostConverter::fromXml)
                            .collect(Collectors.toList()));
        }
        ProteinDescription proteinDescription = descriptionConverter.fromXml(xmlEntry.getProtein());
        activeEntryBuilder.proteinDescription(
                flagUpdater.fromXml(proteinDescription, xmlEntry.getSequence()));
        activeEntryBuilder.genesSet(
                xmlEntry.getGene().stream()
                        .map(geneConverter::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.geneLocationsSet(
                xmlEntry.getGeneLocation().stream()
                        .map(organelleConverter::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.referencesSet(
                xmlEntry.getReference().stream()
                        .map(referenceConverter::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.commentsSet(fromXmlForComments(xmlEntry));
        activeEntryBuilder.uniProtCrossReferencesSet(
                xmlEntry.getDbReference().stream()
                        .filter(val -> !val.getType().equals("EC"))
                        .map(xrefConverter::fromXml)
                        .filter(val -> val != null)
                        .collect(Collectors.toList()));
        activeEntryBuilder.keywordsSet(
                xmlEntry.getKeyword().stream()
                        .map(keywordConverter::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.featuresSet(
                xmlEntry.getFeature().stream()
                        .map(featureConverter::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.sequence(sequenceConverter.fromXml(xmlEntry.getSequence()));
        return activeEntryBuilder.build();
    }

    @Override
    public Entry toXml(UniProtEntry entry) {
        evRefMapper.reset(getEvidences(entry));
        Entry xmlEntry = xmlUniprotFactory.createEntry();
        updateMetaDataToXml(xmlEntry, entry);
        xmlEntry.setOrganism(organismConverter.toXml(entry.getOrganism()));
        entry.getOrganismHosts()
                .forEach(val -> xmlEntry.getOrganismHost().add(organismHostConverter.toXml(val)));
        xmlEntry.setProtein(descriptionConverter.toXml(entry.getProteinDescription()));
        entry.getGenes().forEach(gene -> xmlEntry.getGene().add(geneConverter.toXml(gene)));
        entry.getGeneLocations()
                .forEach(
                        organelle ->
                                xmlEntry.getGeneLocation()
                                        .add(organelleConverter.toXml(organelle)));
        toXmlForCitations(xmlEntry, entry);
        toXmlForComments(xmlEntry, entry);
        xmlEntry.getDbReference()
                .addAll(descriptionConverter.toXmlDbReferences(entry.getProteinDescription()));
        xmlEntry.getDbReference()
                .addAll(
                        entry.getUniProtCrossReferences().stream()
                                .map(xrefConverter::toXml)
                                .collect(Collectors.toList()));
        entry.getKeywords().forEach(val -> xmlEntry.getKeyword().add(keywordConverter.toXml(val)));
        entry.getFeatures().forEach(val -> xmlEntry.getFeature().add(featureConverter.toXml(val)));
        xmlEntry.setSequence(toXmlForSequence(entry));
        updateEvidence(xmlEntry);
        return xmlEntry;
    }

    private void updateEvidence(Entry xmlEntry) {
        Map<Integer, Evidence> mapVal = evRefMapper.getIndexToEvidenceMap();
        mapVal.entrySet()
                .forEach(
                        val -> {
                            EvidenceType xmlEvidence = evidenceConverter.toXml(val.getValue());
                            xmlEvidence.setKey(BigInteger.valueOf(val.getKey().longValue()));
                            xmlEntry.getEvidence().add(xmlEvidence);
                        });
    }

    private SequenceType toXmlForSequence(UniProtEntry entry) {

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
        List<org.uniprot.core.xml.jaxb.uniprot.CommentType> comments = xmlEntry.getComment();
        List<org.uniprot.core.xml.jaxb.uniprot.CommentType> interactionComment =
                comments.stream()
                        .filter(val -> val.getType().equals(INTERACTION))
                        .collect(Collectors.toList());

        boolean interactionsFirst = true;
        for (org.uniprot.core.xml.jaxb.uniprot.CommentType commentType : comments) {
            if (commentType.getType().equals(INTERACTION)) {
                if (interactionsFirst) {
                    interactionsFirst = false;
                    uniComments.add(
                            CommentConverterFactory.INSTANCE
                                    .createInteractionCommentConverter(this.xmlUniprotFactory)
                                    .fromXml(interactionComment));
                }
            } else {
                org.uniprot.core.uniprot.comment.CommentType type =
                        org.uniprot.core.uniprot.comment.CommentType.typeOf(commentType.getType());
                uniComments.add(
                        CommentConverterFactory.INSTANCE
                                .createCommentConverter(type, evRefMapper, xmlUniprotFactory)
                                .fromXml(commentType));
            }
        }
        return uniComments;
    }

    // Must process interaction comments separately
    private void toXmlForComments(Entry xmlEntry, UniProtEntry uniProtEntry) {
        for (Comment comment : uniProtEntry.getComments()) {
            if (comment.getCommentType()
                    == org.uniprot.core.uniprot.comment.CommentType.INTERACTION) {

                xmlEntry.getComment()
                        .addAll(
                                CommentConverterFactory.INSTANCE
                                        .createInteractionCommentConverter(this.xmlUniprotFactory)
                                        .toXml((InteractionComment) comment));
            } else {
                xmlEntry.getComment()
                        .add(
                                CommentConverterFactory.INSTANCE
                                        .createCommentConverter(
                                                comment.getCommentType(),
                                                evRefMapper,
                                                xmlUniprotFactory)
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

    private UniProtEntryBuilder createUniprotEntryBuilderFromXml(Entry xmlEntry) {
        List<String> accessions = xmlEntry.getAccession();
        return new UniProtEntryBuilder(
                        accessions.get(0),
                        xmlEntry.getName().get(0),
                        UniProtEntryType.typeOf(xmlEntry.getDataset()))
                .proteinExistence(ProteinExistence.typeOf(xmlEntry.getProteinExistence().getType()))
                .secondaryAccessionsSet(
                        accessions.subList(1, accessions.size()).stream()
                                .map(sec -> new UniProtAccessionBuilder(sec).build())
                                .collect(Collectors.toList()))
                .entryAudit(entryAuditFromXml(xmlEntry));
    }

    private EntryAudit entryAuditFromXml(Entry xmlEntry) {
        int version = xmlEntry.getVersion();
        LocalDate firstPublic = XmlConverterHelper.dateFromXml(xmlEntry.getCreated());
        LocalDate lastUpdated = XmlConverterHelper.dateFromXml(xmlEntry.getModified());
        int seqVersion = xmlEntry.getSequence().getVersion();
        LocalDate seqDate = XmlConverterHelper.dateFromXml(xmlEntry.getSequence().getModified());
        return new EntryAuditBuilder()
                .firstPublic(firstPublic)
                .lastAnnotationUpdate(lastUpdated)
                .lastSequenceUpdate(seqDate)
                .entryVersion(version)
                .sequenceVersion(seqVersion)
                .build();
    }

    private void updateMetaDataToXml(Entry xmlEntry, UniProtEntry entry) {
        xmlEntry.setDataset(entry.getEntryType().getValue());
        ProteinExistenceType pet = xmlUniprotFactory.createProteinExistenceType();
        pet.setType(entry.getProteinExistence().getValue().toLowerCase());
        xmlEntry.setProteinExistence(pet);
        xmlEntry.getName().add(entry.getUniProtId().getValue());
        xmlEntry.getAccession().add(entry.getPrimaryAccession().getValue());
        entry.getSecondaryAccessions().forEach(val -> xmlEntry.getAccession().add(val.getValue()));
        xmlEntry.setCreated(
                XmlConverterHelper.dateToXml(entry.getEntryAudit().getFirstPublicDate()));
        xmlEntry.setModified(
                XmlConverterHelper.dateToXml(entry.getEntryAudit().getLastAnnotationUpdateDate()));
        xmlEntry.setVersion(entry.getEntryAudit().getEntryVersion());
    }

    private Map<Evidence, Integer> fromXmlForEvidences(Entry xmlEntry) {
        Map<Evidence, Integer> evIdMap = new HashMap<>();
        for (org.uniprot.core.xml.jaxb.uniprot.EvidenceType xmlEvidence : xmlEntry.getEvidence()) {
            Evidence evidence = evidenceConverter.fromXml(xmlEvidence);
            evIdMap.put(evidence, xmlEvidence.getKey().intValue());
        }
        return evIdMap;
    }

    private List<Evidence> getEvidences(UniProtEntry entry) {
        return entry.gatherEvidences();
    }
}
