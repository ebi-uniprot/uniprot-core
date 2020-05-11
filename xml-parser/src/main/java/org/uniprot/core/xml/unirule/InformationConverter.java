package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.impl.InformationBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.FusionType;
import org.uniprot.core.xml.jaxb.unirule.InformationType;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class InformationConverter implements Converter<InformationType, Information> {
    private final ObjectFactory objectFactory;
    private final FusionConverter fusionConverter;
    private final MultiValueConverter multiValueConverter;
    private final UniProtKBAccessionConverter accessionConverter;

    public InformationConverter() {
        this(new ObjectFactory());
    }

    public InformationConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.fusionConverter = new FusionConverter(objectFactory);
        this.multiValueConverter = new MultiValueConverter(objectFactory);
        this.accessionConverter = new UniProtKBAccessionConverter(objectFactory);
    }

    @Override
    public Information fromXml(InformationType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        InformationBuilder builder = new InformationBuilder(xmlObj.getVersion());
        List<String> uniProtIds = this.multiValueConverter.fromXml(xmlObj.getUniprotId());
        List<String> names = this.multiValueConverter.fromXml(xmlObj.getName());
        List<String> related = this.multiValueConverter.fromXml(xmlObj.getRelated());
        List<String> duplicates = this.multiValueConverter.fromXml(xmlObj.getDuplicate());
        List<String> plasmaIds = this.multiValueConverter.fromXml(xmlObj.getPlasmid());
        Fusion fusion = this.fusionConverter.fromXml(xmlObj.getFusion());
        List<String> accessions = this.multiValueConverter.fromXml(xmlObj.getTemplate());
        List<UniProtKBAccession> uniProtKBAccessions = null;
        if (Utils.notNullNotEmpty(accessions)) {
            uniProtKBAccessions =
                    accessions.stream()
                            .map(UniProtKBAccessionBuilder::new)
                            .map(UniProtKBAccessionBuilder::build)
                            .collect(Collectors.toList());
        }

        builder.comment(xmlObj.getComment())
                .oldRuleNum(xmlObj.getOldRuleNum())
                .uniProtIdsSet(uniProtIds)
                .namesSet(names)
                .fusion(fusion)
                .relatedSet(related)
                .uniProtAccessionsSet(uniProtKBAccessions)
                .duplicatesSet(duplicates)
                .plasmaIdsSet(plasmaIds)
                .internal(xmlObj.getInternal());

        if (Objects.nonNull(xmlObj.getDataClass())) {
            builder.dataClass(DataClassType.typeOf(xmlObj.getDataClass()));
        }

        return builder.build();
    }

    @Override
    public InformationType toXml(Information uniObj) {
        if (Objects.isNull(uniObj)) return null;

        InformationType informationType = this.objectFactory.createInformationType();
        informationType.setVersion(uniObj.getVersion());
        informationType.setComment(uniObj.getComment());
        informationType.setOldRuleNum(uniObj.getOldRuleNum());
        // uniprotId
        MultiValueType uniProtId = this.multiValueConverter.toXml(uniObj.getUniProtIds());
        informationType.setUniprotId(uniProtId);
        if (Objects.nonNull(uniObj.getDataClass())) { // data class
            informationType.setDataClass(uniObj.getDataClass().getName());
        }
        // name
        MultiValueType name = this.multiValueConverter.toXml(uniObj.getNames());
        informationType.setName(name);
        // fusion
        FusionType fusionType = this.fusionConverter.toXml(uniObj.getFusion());
        informationType.setFusion(fusionType);
        // related
        MultiValueType related = this.multiValueConverter.toXml(uniObj.getRelated());
        informationType.setRelated(related);
        // duplicates
        MultiValueType duplicate = this.multiValueConverter.toXml(uniObj.getDuplicates());
        informationType.setDuplicate(duplicate);
        // plasmaIds
        MultiValueType plasmaIds = this.multiValueConverter.toXml(uniObj.getPlasmaIds());
        informationType.setPlasmid(plasmaIds);
        // template
        List<String> accessions =
                uniObj.getUniProtAccessions().stream()
                        .map(this.accessionConverter::toXml)
                        .collect(Collectors.toList());

        MultiValueType template = this.multiValueConverter.toXml(accessions);
        informationType.setTemplate(template);

        informationType.setInternal(uniObj.getInternal());

        return informationType;
    }
}
