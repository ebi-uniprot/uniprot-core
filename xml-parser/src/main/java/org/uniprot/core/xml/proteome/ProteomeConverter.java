package org.uniprot.core.xml.proteome;

import static org.uniprot.core.proteome.ProteomeType.*;
import static org.uniprot.core.util.Utils.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.ProteomeCompletenessReportBuilder;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.*;
import org.uniprot.core.xml.jaxb.proteome.Proteome;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

public class ProteomeConverter implements Converter<Proteome, ProteomeEntry> {

    private final ObjectFactory xmlFactory;
    private final ComponentConverter componentConverter;
    private final ReferenceConverter referenceConverter;
    private final GenomeAssemblyConverter genomeAssemblyConverter;
    private final GenomeAnnotationConverter genomeAnnotationConverter;
    private final ScoreBuscoConverter scoreBuscoConverter;
    private final ScoreCPDConverter scoreCPDConverter;

    public ProteomeConverter() {
        this(new ObjectFactory());
    }

    public ProteomeConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.componentConverter = new ComponentConverter(xmlFactory);
        this.referenceConverter = new ReferenceConverter(xmlFactory);
        this.genomeAssemblyConverter = new GenomeAssemblyConverter(xmlFactory);
        this.scoreBuscoConverter = new ScoreBuscoConverter(xmlFactory);
        this.scoreCPDConverter = new ScoreCPDConverter(xmlFactory);
        this.genomeAnnotationConverter = new GenomeAnnotationConverter(xmlFactory);
    }

    @Override
    public ProteomeEntry fromXml(Proteome xmlObj) {
        List<Component> components =
                xmlObj.getComponent().stream()
                        .map(componentConverter::fromXml)
                        .collect(Collectors.toList());

        List<Citation> citations =
                xmlObj.getReference().stream()
                        .map(referenceConverter::fromXml)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

        ProteomeEntryBuilder builder = new ProteomeEntryBuilder();
        Integer proteinCount = xmlObj.getProteinCount();
        builder.proteomeId(proteomeId(xmlObj.getUpid()))
                .proteomeType(getProteomeType(xmlObj))
                .description(xmlObj.getDescription())
                .taxonomy(getTaxonomy(xmlObj.getTaxonomy()))
                .modified(XmlConverterHelper.dateFromXml(xmlObj.getModified()))
                .strain(xmlObj.getStrain())
                .isolate(xmlObj.getIsolate())
                .componentsSet(components)
                .citationsSet(citations)
                .genomeAssembly(genomeAssemblyConverter.fromXml(xmlObj.getGenomeAssembly()))
                .proteomeCompletenessReport(getCompletenessReport(xmlObj.getScores()))
                .proteinCount(proteinCount);

        if (Utils.notNull(xmlObj.getGenomeAnnotation())) {
            builder.genomeAnnotation(
                    genomeAnnotationConverter.fromXml(xmlObj.getGenomeAnnotation()));
        }

        if (notNull(xmlObj.getAnnotationScore())) {
            builder.annotationScore(xmlObj.getAnnotationScore().getNormalizedAnnotationScore());
        }
        if (notNull(xmlObj.getExcluded())
                && notNullNotEmpty(xmlObj.getExcluded().getExclusionReason())) {
            xmlObj.getExcluded().getExclusionReason().stream()
                    .map(ExclusionReason::typeOf)
                    .forEach(builder::exclusionReasonsAdd);
        }
        return builder.build();
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
        }
        xmlObj.setProteomeStatus((uniObj.getProteomeType().getName()));
        xmlObj.setModified(XmlConverterHelper.dateToXml(uniObj.getModified()));
        xmlObj.setStrain(uniObj.getStrain());
        xmlObj.setIsolate(uniObj.getIsolate());
        xmlObj.setProteinCount(uniObj.getProteinCount());

        uniObj.getComponents().stream()
                .map(componentConverter::toXml)
                .forEach(val -> xmlObj.getComponent().add(val));

        uniObj.getCitations().stream()
                .map(referenceConverter::toXml)
                .filter(Objects::nonNull)
                .forEach(val -> xmlObj.getReference().add(val));

        if (notNull(uniObj.getAnnotationScore())) {
            AnnotationScoreType annotationScore = xmlFactory.createAnnotationScoreType();
            annotationScore.setNormalizedAnnotationScore(uniObj.getAnnotationScore());
            xmlObj.setAnnotationScore(annotationScore);
        }
        if (notNull(uniObj.getGenomeAssembly())) {
            xmlObj.setGenomeAssembly(genomeAssemblyConverter.toXml(uniObj.getGenomeAssembly()));
        }
        if (notNull(uniObj.getGenomeAnnotation())) {
            xmlObj.setGenomeAnnotation(
                    genomeAnnotationConverter.toXml(uniObj.getGenomeAnnotation()));
        }
        if (notNull(uniObj.getProteomeCompletenessReport())) {
            convertCompletenessReport(uniObj.getProteomeCompletenessReport(), xmlObj);
        }
        if (notNullNotEmpty(uniObj.getExclusionReasons())) {
            convertExclusionReasons(uniObj, xmlObj);
        }
        return xmlObj;
    }

    private void convertCompletenessReport(ProteomeCompletenessReport reports, Proteome xmlObj) {
        if (notNull(reports.getBuscoReport())) {
            xmlObj.getScores().add(scoreBuscoConverter.toXml(reports.getBuscoReport()));
        }
        if (notNull(reports.getCPDReport())) {
            xmlObj.getScores().add(scoreCPDConverter.toXml(reports.getCPDReport()));
        }
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

    private Taxonomy getTaxonomy(Long taxonId) {
        if (taxonId != null && taxonId > 0L) {
            TaxonomyBuilder builder = new TaxonomyBuilder();
            return builder.taxonId(taxonId).build();
        } else {
            return null;
        }
    }

    private ProteomeType getProteomeType(Proteome t) {
        ProteomeType proteomeType = ProteomeType.typeOf(t.getProteomeStatus());
        if (SURVEILLANCE.equals(proteomeType)) {
            return EXCLUDED;
        }
        if (REPRESENTATIVE.equals(proteomeType)) {
            return REFERENCE;
        }
        return proteomeType;
    }
}
