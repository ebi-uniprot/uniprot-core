package org.uniprot.core.xml.proteome;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.Superkingdom;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.AnnotationScoreType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.Proteome;
import org.uniprot.core.xml.jaxb.proteome.SuperregnumType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import com.google.common.base.Strings;

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
        this.componentConverter = new ComponentConverter(xmlFactory);
        this.canonicalProteinConverter = new CanonicalProteinConverter(xmlFactory);
        this.redundantProteomeConverter = new RedundantProteomeConverter(xmlFactory);
        this.xrefConverter = new CrossReferenceConverter(xmlFactory);
        this.referenceConverter = new ReferenceConverter(xmlFactory);
    }

    @Override
    public ProteomeEntry fromXml(Proteome xmlObj) {
        List<Component> components =
                xmlObj.getComponent().stream()
                        .map(componentConverter::fromXml)
                        .collect(Collectors.toList());
        List<CanonicalProtein> canonicalProteins =
                xmlObj.getCanonicalGene().stream()
                        .map(canonicalProteinConverter::fromXml)
                        .collect(Collectors.toList());
        org.uniprot.core.proteome.ProteomeType proteomeType = getProteomeType(xmlObj);
        List<RedundantProteome> redundantProteomes =
                xmlObj.getRedundantProteome().stream()
                        .map(redundantProteomeConverter::fromXml)
                        .collect(Collectors.toList());
        List<CrossReference<ProteomeDatabase>> xrefs =
                xmlObj.getDbReference().stream()
                        .map(xrefConverter::fromXml)
                        .collect(Collectors.toList());

        List<Citation> citations =
                xmlObj.getReference().stream()
                        .map(referenceConverter::fromXml)
                        .filter(val -> val != null)
                        .collect(Collectors.toList());

        ProteomeEntryBuilder builder = new ProteomeEntryBuilder();
        builder.proteomeId(proteomeId(xmlObj.getUpid()))
                .proteomeType(proteomeType)
                .description(xmlObj.getDescription())
                .taxonomy(getTaxonomy(xmlObj.getTaxonomy(), xmlObj.getName()))
                .modified(XmlConverterHelper.dateFromXml(xmlObj.getModified()))
                .strain(xmlObj.getStrain())
                .isolate(xmlObj.getIsolate())
                .sourceDb(xmlObj.getSource())
                .componentsSet(components)
                .canonicalProteinsSet(canonicalProteins)
                .geneCount(canonicalProteins.size())
                .redundantProteomesSet(redundantProteomes)
                .proteomeCrossReferencesSet(xrefs)
                .superkingdom(Superkingdom.fromValue(xmlObj.getSuperregnum().value()))
                .citationsSet(citations);

        if (xmlObj.getAnnotationScore() != null) {
            builder.annotationScore(xmlObj.getAnnotationScore().getNormalizedAnnotationScore());
        }

        if (!Strings.isNullOrEmpty(xmlObj.getRedundantTo())) {
            builder.redundantTo(proteomeId(xmlObj.getRedundantTo()));
        }
        if (!Strings.isNullOrEmpty(xmlObj.getPanproteome())) {
            builder.panproteome(proteomeId(xmlObj.getPanproteome()));
        }

        return builder.build();
    }

    private ProteomeId proteomeId(String id) {
        return new ProteomeIdBuilder(id).build();
    }

    @Override
    public Proteome toXml(ProteomeEntry uniObj) {
        Proteome xmlObj = xmlFactory.createProteome();
        xmlObj.setUpid(uniObj.getId().getValue());
        xmlObj.setDescription(uniObj.getDescription());
        xmlObj.setTaxonomy(uniObj.getTaxonomy().getTaxonId());
        xmlObj.setName(uniObj.getTaxonomy().getScientificName());
        org.uniprot.core.proteome.ProteomeType type = uniObj.getProteomeType();
        if (type == org.uniprot.core.proteome.ProteomeType.REFERENCE) {
            xmlObj.setIsReferenceProteome(true);
        } else if (type == org.uniprot.core.proteome.ProteomeType.REPRESENTATIVE) {
            xmlObj.setIsRepresentativeProteome(true);
        } else if (type == org.uniprot.core.proteome.ProteomeType.REDUNDANT) {
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

        uniObj.getProteomeCrossReferences().stream()
                .map(xrefConverter::toXml)
                .forEach(val -> xmlObj.getDbReference().add(val));

        uniObj.getCitations().stream()
                .map(referenceConverter::toXml)
                .filter(val -> val != null)
                .forEach(val -> xmlObj.getReference().add(val));

        if ((uniObj.getRedundantTo() != null)
                && !Strings.isNullOrEmpty(uniObj.getRedundantTo().getValue())) {
            xmlObj.setRedundantTo(uniObj.getRedundantTo().getValue());
        }

        AnnotationScoreType annotationScore = xmlFactory.createAnnotationScoreType();
        annotationScore.setNormalizedAnnotationScore(uniObj.getAnnotationScore());
        xmlObj.setAnnotationScore(annotationScore);
        if (uniObj.getPanproteome() != null) {
            xmlObj.setPanproteome(uniObj.getPanproteome().getValue());
        }
        return xmlObj;
    }

    private Taxonomy getTaxonomy(Long taxonId, String name) {
        TaxonomyBuilder builder = new TaxonomyBuilder();
        return builder.taxonId(taxonId).scientificName(name).build();
    }

    private org.uniprot.core.proteome.ProteomeType getProteomeType(Proteome t) {
        if (t.isIsReferenceProteome()) return org.uniprot.core.proteome.ProteomeType.REFERENCE;
        else if (t.isIsRepresentativeProteome()) {
            return org.uniprot.core.proteome.ProteomeType.REPRESENTATIVE;
        } else if ((t.getRedundantTo() != null) && (!t.getRedundantTo().isEmpty())) {
            return org.uniprot.core.proteome.ProteomeType.REDUNDANT;
        } else return org.uniprot.core.proteome.ProteomeType.NORMAL;
    }
}
