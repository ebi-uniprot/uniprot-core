package uk.ac.ebi.uniprot.xml.uniparc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.cv.taxonomy.TaxonomicNode;
import uk.ac.ebi.uniprot.cv.taxonomy.TaxonomyRepo;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcIdBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.TaxonomyBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.Entry;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.ObjectFactory;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
 */

public class UniParcEntryConverter implements Converter<Entry, UniParcEntry> {
	private static final String UNIPARC = "uniparc";
	private final ObjectFactory xmlFactory;
	private final SequenceFeatureConverter seqFeatureConverter;
	private final UniParcDBCrossReferenceConverter xrefConverter;
	private final SequenceConverter sequenceConverter;
	private final TaxonomyRepo taxonomyRepo;

	public UniParcEntryConverter() {
		this(new ObjectFactory(), null);
	}
	public UniParcEntryConverter(ObjectFactory xmlFactory,TaxonomyRepo taxonomyRepo ) {
		this.xmlFactory = xmlFactory;
		this.seqFeatureConverter = new SequenceFeatureConverter(xmlFactory);
		this.xrefConverter = new UniParcDBCrossReferenceConverter(xmlFactory);
		this.sequenceConverter = new SequenceConverter(xmlFactory);
		this.taxonomyRepo = taxonomyRepo;
	}
	public UniParcEntryConverter(TaxonomyRepo taxonomyRepo) {
		this(new ObjectFactory(), taxonomyRepo);
	}

	@Override
	public UniParcEntry fromXml(Entry xmlObj) {
		UniParcEntryBuilder builder = new UniParcEntryBuilder();
		builder.uniParcId(new UniParcIdBuilder(xmlObj.getAccession()).build())
				.sequence(sequenceConverter.fromXml(xmlObj.getSequence()))
				.sequenceFeatures(xmlObj.getSignatureSequenceMatch().stream().map(seqFeatureConverter::fromXml)
						.collect(Collectors.toList()));
		if (xmlObj.getUniProtKBExclusion() != null) {
			builder.uniprotExclusionReason(xmlObj.getUniProtKBExclusion());
		}

		List<UniParcDBCrossReference> xrefs = xmlObj.getDbReference().stream().map(xrefConverter::fromXml)
				.collect(Collectors.toList());
		builder.databaseCrossReferences(xrefs);
		List<Taxonomy> taxonomies = xrefs.stream().flatMap(val -> val.getProperties().stream())
				.filter(val -> val.getKey().equals(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID))
				.map(val -> val.getValue()).distinct().map(this::convertTaxonomy).collect(Collectors.toList());
		builder.taxonomies(taxonomies);

		return builder.build();
	}

	private Taxonomy convertTaxonomy(String taxId) {
		TaxonomyBuilder builder= new TaxonomyBuilder().taxonId(Long.parseLong(taxId));
		Optional<TaxonomicNode> opNode = getTaxonomyNode(taxId);
		if(opNode.isPresent()) {
			TaxonomicNode node = opNode.get();
			builder.scientificName(node.scientificName());
			if(!Strings.isNullOrEmpty(node.commonName())){
				builder.commonName(node.commonName());
			}
			if(!Strings.isNullOrEmpty(node.synonymName())) {
				builder.addSynonyms(node.synonymName());
			}
			if(!Strings.isNullOrEmpty(node.mnemonic())) {
				builder.mnemonic(node.mnemonic());
			}
		}
		
		return builder.build();
	}

	private  Optional<TaxonomicNode> getTaxonomyNode(String taxId){
		if(taxonomyRepo ==null) {
			return Optional.empty();
		}else
			return taxonomyRepo.retrieveNodeUsingTaxID(Integer.parseInt(taxId));
	}
	@Override
	public Entry toXml(UniParcEntry uniObj) {
		Entry entry = xmlFactory.createEntry();
		entry.setDataset(UNIPARC);
		entry.setAccession(uniObj.getUniParcId().getValue());
		if (!Strings.isNullOrEmpty(uniObj.getUniProtExclusionReason())) {
			entry.setUniProtKBExclusion(uniObj.getUniProtExclusionReason());
		}
		entry.setSequence(sequenceConverter.toXml(uniObj.getSequence()));
		uniObj.getDbXReferences().stream().map(xrefConverter::toXml).forEach(val -> entry.getDbReference().add(val));
		uniObj.getSequenceFeatures().stream().map(seqFeatureConverter::toXml)
				.forEach(val -> entry.getSignatureSequenceMatch().add(val));
		return entry;
	}

}
