package uk.ac.ebi.uniprot.xml.proteome;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.proteome.*;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeEntryBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeIdBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.TaxonomyBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.Proteome;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.SuperregnumType;
import uk.ac.ebi.uniprot.xml.uniprot.XmlConverterHelper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProteomeConverter implements Converter<Proteome, ProteomeEntry> {

	private final ObjectFactory xmlFactory;
	private final ComponentConverter componentConverter;
	private final CanonicalProteinConverter canonicalProteinConverter;
	private final RedundantProteomeConverter redundantProteomeConverter;
	private final CrossReferenceConverter xrefConverter;
	private final ReferenceConverter referenceConverter;
	public ProteomeConverter() {
		this(new ObjectFactory());
	}

	public ProteomeConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
		this.componentConverter =new ComponentConverter(xmlFactory);
		this.canonicalProteinConverter = new CanonicalProteinConverter(xmlFactory);
		this.redundantProteomeConverter = new RedundantProteomeConverter(xmlFactory);
		this.xrefConverter =new CrossReferenceConverter(xmlFactory);
		this.referenceConverter =new ReferenceConverter(xmlFactory);
	}
	
	@Override
	public ProteomeEntry fromXml(Proteome xmlObj) {
		List<Component> components = xmlObj.getComponent().stream().map(componentConverter::fromXml).collect(Collectors.toList());
		List<CanonicalProtein> canonicalProteins = xmlObj.getCanonicalGene().stream().map(canonicalProteinConverter::fromXml)
				.collect(Collectors.toList());
		ProteomeType proteomeType = getProteomeType(xmlObj);
		List<RedundantProteome> redundantProteomes=
		xmlObj.getRedundantProteome().stream()
		.map(redundantProteomeConverter::fromXml).collect(Collectors.toList());
		List<DBCrossReference<ProteomeXReferenceType>> xrefs =			
				xmlObj.getDbReference().stream()
				.map(xrefConverter::fromXml)
				.collect(Collectors.toList());
		
		List<Citation> citations = 
				xmlObj.getReference().stream()
				.map(referenceConverter::fromXml)
				.filter(val -> val!=null)
				.collect(Collectors.toList());
		
		Optional<Long> proteinCount=
		components.stream().map(val ->val.getProteinCount()).reduce((val1, val2)->val1+val2);
		
		ProteomeEntryBuilder builder = ProteomeEntryBuilder.newInstance();
		builder.proteomeId(proteomeId(xmlObj.getUpid()))
		.proteomeType(proteomeType)
			.description(xmlObj.getDescription())
			.taxonomy(getTaxonomy(xmlObj.getTaxonomy(), xmlObj.getName()))
			.modified(XmlConverterHelper.dateFromXml(xmlObj.getModified()))
			.strain(xmlObj.getStrain())
			.isolate(xmlObj.getIsolate())
			.sourceDb(xmlObj.getSource())		
			.components(components)
			.canonicalProteins(canonicalProteins)
			.geneCount(canonicalProteins.size())
			.redundantProteomes(redundantProteomes)
			.dbXReferences(xrefs)
			.proteinCount(proteinCount.isPresent()? proteinCount.get():0)
			.superkingdom(Superkingdom.fromValue(xmlObj.getSuperregnum().value()))
			.references(citations);
			
	if(!Strings.isNullOrEmpty(xmlObj.getRedundantTo())) {
		builder.redundantTo(proteomeId(xmlObj.getRedundantTo()));
	}

	
		return builder.build();
	}
	private ProteomeId proteomeId(String id) {
		return new ProteomeIdBuilder(id).build();
	}
	
	
	
	@Override
	public uk.ac.ebi.uniprot.xml.jaxb.proteome.Proteome toXml(ProteomeEntry uniObj) {
		uk.ac.ebi.uniprot.xml.jaxb.proteome.Proteome xmlObj = xmlFactory.createProteome();
		xmlObj.setUpid(uniObj.getId().getValue());
		xmlObj.setDescription(uniObj.getDescription());
		xmlObj.setTaxonomy(uniObj.getTaxonomy().getTaxonId());
		xmlObj.setName(uniObj.getTaxonomy().getScientificName());
		ProteomeType type =uniObj.getProteomeType();
		if(type ==ProteomeType.REFERENCE) {
			xmlObj.setIsReferenceProteome(true);
		}else if(type ==ProteomeType.REPRESENTATIVE) {
			xmlObj.setIsRepresentativeProteome(true);
		}else if(type == ProteomeType.REDUNDANT) {
			xmlObj.setIsReferenceProteome(true);
		}
		xmlObj.setModified(XmlConverterHelper.dateToXml(uniObj.getModified()));
		xmlObj.setStrain(uniObj.getStrain());
		xmlObj.setIsolate(uniObj.getIsolate());
		xmlObj.setSource(uniObj.getSourceDb());
		xmlObj.setSuperregnum(SuperregnumType.fromValue(uniObj.getSuperkingdom().getName()));
		
		uniObj.getComponents().stream()
		.map(componentConverter::toXml)
		.forEach(val -> xmlObj.getComponent().add(val));
		
		uniObj.getCanonicalProteins().stream()
		.map(canonicalProteinConverter::toXml)
		.forEach(val -> xmlObj.getCanonicalGene().add(val));
		
		uniObj.getRedudantProteomes().stream()
		.map(redundantProteomeConverter::toXml)
		.forEach(val -> xmlObj.getRedundantProteome().add(val));
		
		uniObj.getDbXReferences().stream()
		.map(xrefConverter::toXml)
		.forEach(val -> xmlObj.getDbReference().add(val));
		
		uniObj.getReferences().stream().map(referenceConverter::toXml)
		.filter(val -> val!=null)
		.forEach(val -> xmlObj.getReference().add(val));
		
		if((uniObj.getRedundantTo() !=null) && !Strings.isNullOrEmpty(uniObj.getRedundantTo().getValue())){
			xmlObj.setRedundantTo(uniObj.getRedundantTo().getValue());
		}
		
		return xmlObj;
	}
	private Taxonomy getTaxonomy(Long taxonId, String name) {	
			TaxonomyBuilder builder = TaxonomyBuilder.newInstance();
			return builder.taxonId(taxonId).scientificName(name).build();
	}
	
	private ProteomeType getProteomeType(uk.ac.ebi.uniprot.xml.jaxb.proteome.Proteome t) {
		if(t.isIsReferenceProteome())
			return ProteomeType.REFERENCE;
		else if (t.isIsRepresentativeProteome()) {
			return ProteomeType.REPRESENTATIVE;
		}else if((t.getRedundantTo() != null) && (!t.getRedundantTo().isEmpty())) {
			return ProteomeType.REDUNDANT;
		}else
			return ProteomeType.NORMAL;
	}
}
