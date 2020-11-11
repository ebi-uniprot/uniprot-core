package org.uniprot.core.xml.proteome;

import static org.uniprot.core.util.Utils.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.ProteomeCompletenessReportBuilder;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.*;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

public class ProteomeConverter implements Converter<Proteome, ProteomeEntry> {

    private final ObjectFactory xmlFactory;
    private final ComponentConverter componentConverter;
    private final CanonicalProteinConverter canonicalProteinConverter;
    private final RedundantProteomeConverter redundantProteomeConverter;
    private final CrossReferenceConverter xrefConverter;
    private final ReferenceConverter referenceConverter;
    private final GenomeAssemblyConverter genomeAssemblyConverter;
    private final ScoreBuscoConverter scoreBuscoConverter;
    private final ScoreCPDConverter scoreCPDConverter;

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
        this.genomeAssemblyConverter = new GenomeAssemblyConverter(xmlFactory);
        this.scoreBuscoConverter = new ScoreBuscoConverter(xmlFactory);
        this.scoreCPDConverter = new ScoreCPDConverter(xmlFactory);
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
        ProteomeType proteomeType = getProteomeType(xmlObj);
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
                        .filter(Objects::nonNull)
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
                .superkingdom(convertSuperkingdom(xmlObj))
                .citationsSet(citations)
                .genomeAssembly(genomeAssemblyConverter.fromXml(xmlObj.getGenomeAssembly()))
                .proteomeCompletenessReport(getCompletenessReport(xmlObj.getScores()));

        if (notNull(xmlObj.getAnnotationScore())) {
            builder.annotationScore(xmlObj.getAnnotationScore().getNormalizedAnnotationScore());
        }

        if (notNullNotEmpty(xmlObj.getRedundantTo())) {
            builder.redundantTo(proteomeId(xmlObj.getRedundantTo()));
        }
        if (notNullNotEmpty(xmlObj.getPanproteome())) {
            builder.panproteome(proteomeId(xmlObj.getPanproteome()));
        }
        if (notNull(xmlObj.getExcluded())
                && notNullNotEmpty(xmlObj.getExcluded().getExclusionReason())) {
            xmlObj.getExcluded().getExclusionReason().stream()
                    .map(ExclusionReason::typeOf)
                    .forEach(builder::exclusionReasonsAdd);
        }
        return builder.build();
    }

    private Superkingdom convertSuperkingdom(Proteome xmlObj) {
        if (notNull(xmlObj.getSuperregnum())) {
            return Superkingdom.typeOf(xmlObj.getSuperregnum().value());
        } else {
            return null;
        }
    }

    private ProteomeCompletenessReport getCompletenessReport(List<ScoreType> scores) {
        ProteomeCompletenessReport result = null;
        if (notNullNotEmpty(scores)) {
            ProteomeCompletenessReportBuilder builder = new ProteomeCompletenessReportBuilder();
            Optional<BuscoReport> buscoReport =
                    scores.stream()
                            .filter(scoreType -> scoreType.getName().equalsIgnoreCase("busco"))
                            .map(scoreBuscoConverter::fromXml)
                            .findFirst();
            builder.buscoReport(buscoReport.orElse(null));

            Optional<CPDReport> cpdReport =
                    scores.stream()
                            .filter(scoreType -> scoreType.getName().equalsIgnoreCase("cpd"))
                            .map(scoreCPDConverter::fromXml)
                            .findFirst();
            builder.cpdReport(cpdReport.orElse(null));
            result = builder.build();
        }
        return result;
    }

    private ProteomeId proteomeId(String id) {
        return new ProteomeIdBuilder(id).build();
    }

    @Override
    public Proteome toXml(ProteomeEntry uniObj) {
        Proteome xmlObj = xmlFactory.createProteome();
        xmlObj.setUpid(uniObj.getId().getValue());
        xmlObj.setDescription(uniObj.getDescription());
        if (notNull(uniObj.getTaxonomy())) {
            xmlObj.setTaxonomy(uniObj.getTaxonomy().getTaxonId());
            xmlObj.setName(uniObj.getTaxonomy().getScientificName());
        }
        ProteomeType type = uniObj.getProteomeType();
        if (type == ProteomeType.REFERENCE || type == ProteomeType.REFERENCE_AND_REPRESENTATIVE) {
            xmlObj.setIsReferenceProteome(true);
        }
        if (type == ProteomeType.REPRESENTATIVE
                || type == ProteomeType.REFERENCE_AND_REPRESENTATIVE) {
            xmlObj.setIsRepresentativeProteome(true);
        }
        xmlObj.setModified(XmlConverterHelper.dateToXml(uniObj.getModified()));
        xmlObj.setStrain(uniObj.getStrain());
        xmlObj.setIsolate(uniObj.getIsolate());
        xmlObj.setSource(uniObj.getSourceDb());
        if (notNull(uniObj.getSuperkingdom())) {
            xmlObj.setSuperregnum(SuperregnumType.fromValue(uniObj.getSuperkingdom().getName()));
        }

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
                .filter(Objects::nonNull)
                .forEach(val -> xmlObj.getReference().add(val));

        if ((notNull(uniObj.getRedundantTo()))
                && notNullNotEmpty(uniObj.getRedundantTo().getValue())) {
            xmlObj.setRedundantTo(uniObj.getRedundantTo().getValue());
        }

        AnnotationScoreType annotationScore = xmlFactory.createAnnotationScoreType();
        annotationScore.setNormalizedAnnotationScore(uniObj.getAnnotationScore());
        xmlObj.setAnnotationScore(annotationScore);
        if (notNull(uniObj.getPanproteome())) {
            xmlObj.setPanproteome(uniObj.getPanproteome().getValue());
        }
        if (notNull(uniObj.getGenomeAssembly())) {
            xmlObj.setGenomeAssembly(genomeAssemblyConverter.toXml(uniObj.getGenomeAssembly()));
        }
        if (notNull(uniObj.getProteomeCompletenessReport())) {
            ProteomeCompletenessReport reports = uniObj.getProteomeCompletenessReport();
            if (notNull(reports.getBuscoReport())) {
                xmlObj.getScores().add(scoreBuscoConverter.toXml(reports.getBuscoReport()));
            }
            if (notNull(reports.getCPDReport())) {
                xmlObj.getScores().add(scoreCPDConverter.toXml(reports.getCPDReport()));
            }
        }
        if (notNullNotEmpty(uniObj.getExclusionReasons())) {
            convertExclusionReasons(uniObj, xmlObj);
        }
        return xmlObj;
    }

    private void convertExclusionReasons(ProteomeEntry uniObj, Proteome xmlObj) {
        List<String> exclusions =
                uniObj.getExclusionReasons().stream()
                        .map(ExclusionReason::getDisplayName)
                        .collect(Collectors.toList());
        ExclusionType exclusionType = xmlFactory.createExclusionType();
        exclusionType.getExclusionReason().addAll(exclusions);
        xmlObj.setExcluded(exclusionType);
    }

    private Taxonomy getTaxonomy(Long taxonId, String name) {
        if (taxonId != null) {
            TaxonomyBuilder builder = new TaxonomyBuilder();
            return builder.taxonId(taxonId).scientificName(name).build();
        } else {
            return null;
        }
    }

    private ProteomeType getProteomeType(Proteome t) {
        if (t.getExcluded() != null
                && (t.getExcluded().getExclusionReason() != null)
                && (!t.getExcluded().getExclusionReason().isEmpty())) {
            return ProteomeType.EXCLUDED;
        } else if (t.isIsRepresentativeProteome() && (t.isIsReferenceProteome())) {
            return ProteomeType.REFERENCE_AND_REPRESENTATIVE;
        } else if (t.isIsReferenceProteome()) {
            return ProteomeType.REFERENCE;
        } else if (t.isIsRepresentativeProteome()) {
            return ProteomeType.REPRESENTATIVE;
        } else if ((t.getRedundantTo() != null) && (!t.getRedundantTo().isEmpty())) {
            return ProteomeType.REDUNDANT;
        } else {
            return ProteomeType.NORMAL;
        }
    }
}
