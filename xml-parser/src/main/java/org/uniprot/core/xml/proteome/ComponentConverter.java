package org.uniprot.core.xml.proteome;

import static org.uniprot.core.util.Utils.notNull;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ComponentType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

import com.google.common.base.Strings;

public class ComponentConverter implements Converter<ComponentType, Component> {
    private final ObjectFactory xmlFactory;
    private final GenomeAnnotationConverter genomeAnnotationConverter;

    public ComponentConverter() {
        this(new ObjectFactory());
    }

    public ComponentConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.genomeAnnotationConverter = new GenomeAnnotationConverter(xmlFactory);
    }

    @Override
    public Component fromXml(ComponentType xmlObj) {
        ComponentBuilder builder = new ComponentBuilder();
        builder.name(xmlObj.getName());
        builder.description(xmlObj.getDescription());
        List<CrossReference<ProteomeDatabase>> xrefs = new ArrayList<>();
        if (!xmlObj.getGenomeAccession().isEmpty()) {
            xmlObj.getGenomeAccession().stream()
                    .map(
                            val ->
                                    new CrossReferenceBuilder<ProteomeDatabase>()
                                            .database(ProteomeDatabase.GENOME_ACCESSION)
                                            .id(val)
                                            .build())
                    .forEach(xrefs::add);
        }
        if (!Strings.isNullOrEmpty(xmlObj.getBiosampleId())) {
            xrefs.add(
                    new CrossReferenceBuilder<ProteomeDatabase>()
                            .database(ProteomeDatabase.BIOSAMPLE)
                            .id(xmlObj.getBiosampleId())
                            .build());
        }
        builder.proteomeCrossReferencesSet(xrefs);

        if (Utils.notNull(xmlObj.getGenomeAnnotation())) {
            builder.genomeAnnotation(
                    genomeAnnotationConverter.fromXml(xmlObj.getGenomeAnnotation()));
        }
        return builder.build();
    }

    @Override
    public ComponentType toXml(Component uniObj) {
        ComponentType xmlObj = xmlFactory.createComponentType();
        xmlObj.setName(uniObj.getName());
        xmlObj.setDescription(uniObj.getDescription());

        uniObj.getProteomeCrossReferences().stream()
                .filter(val -> val.getDatabase() == ProteomeDatabase.BIOSAMPLE)
                .findFirst()
                .ifPresent(
                        proteomeDatabaseCrossReference ->
                                xmlObj.setBiosampleId(proteomeDatabaseCrossReference.getId()));

        uniObj.getProteomeCrossReferences().stream()
                .filter(val -> val.getDatabase() == ProteomeDatabase.GENOME_ACCESSION)
                .map(CrossReference::getId)
                .forEach(val -> xmlObj.getGenomeAccession().add(val));

        if (notNull(uniObj.getGenomeAnnotation())) {
            xmlObj.setGenomeAnnotation(
                    genomeAnnotationConverter.toXml(uniObj.getGenomeAnnotation()));
        }
        return xmlObj;
    }
}
