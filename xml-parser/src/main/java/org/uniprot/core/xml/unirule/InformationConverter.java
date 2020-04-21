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
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.FusionType;
import org.uniprot.core.xml.jaxb.unirule.InformationType;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class InformationConverter implements Converter<InformationType, Information> {
    private final ObjectFactory objectFactory;
    private final FusionConverter fusionConverter;

    public InformationConverter() {
        this(new ObjectFactory());
    }

    public InformationConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.fusionConverter = new FusionConverter(objectFactory);
    }

    @Override
    public Information fromXml(InformationType xmlObj) {
        InformationBuilder builder = new InformationBuilder(xmlObj.getVersion());
        List<String> uniProtIds = extractValues(xmlObj.getUniprotId());
        List<String> names = extractValues(xmlObj.getName());
        List<String> related = extractValues(xmlObj.getRelated());
        List<String> duplicates = extractValues(xmlObj.getDuplicate());
        List<String> plasmaIds = extractValues(xmlObj.getPlasmid());
        Fusion fusion = this.fusionConverter.fromXml(xmlObj.getFusion());
        List<String> accessions = extractValues(xmlObj.getTemplate());
        List<UniProtKBAccession> uniProtKBAccessions =
                Objects.nonNull(accessions)
                        ? accessions.stream()
                                .map(value -> new UniProtKBAccessionBuilder(value).build())
                                .collect(Collectors.toList())
                        : null;

        builder.comment(xmlObj.getComment())
                .oldRuleNum(xmlObj.getOldRuleNum())
                .uniProtIdsSet(uniProtIds)
                .dataClass(DataClassType.typeOf(xmlObj.getDataClass()))
                .namesSet(names)
                .fusion(fusion)
                .relatedSet(related)
                .uniProtAccessionsSet(uniProtKBAccessions)
                .duplicatesSet(duplicates)
                .plasmaIdsSet(plasmaIds)
                .internal(xmlObj.getInternal());

        return builder.build();
    }

    @Override
    public InformationType toXml(Information uniObj) {
        InformationType informationType = this.objectFactory.createInformationType();
        informationType.setVersion(uniObj.getVersion());
        informationType.setComment(uniObj.getComment());
        informationType.setOldRuleNum(uniObj.getOldRuleNum());
        // uniprotId
        MultiValueType uniProtId = createMultiValueType(uniObj.getUniProtIds());
        informationType.setUniprotId(uniProtId);
        // data class
        informationType.setDataClass(uniObj.getDataClass().getName());

        // name
        MultiValueType name = createMultiValueType(uniObj.getNames());
        informationType.setName(name);
        // fusion
        FusionType fusionType = this.fusionConverter.toXml(uniObj.getFusion());
        informationType.setFusion(fusionType);
        // related
        MultiValueType related = createMultiValueType(uniObj.getRelated());
        informationType.setRelated(related);
        // duplicates
        MultiValueType duplicate = createMultiValueType(uniObj.getDuplicates());
        informationType.setDuplicate(duplicate);
        // plasmaIds
        MultiValueType plasmaIds = createMultiValueType(uniObj.getPlasmaIds());
        informationType.setPlasmid(plasmaIds);
        // template
        MultiValueType template = createMultiValueType(null);
        List<String> multiValueValues = template.getValue();
        if (Objects.nonNull(uniObj.getUniProtAccessions())) {
            multiValueValues.addAll(
                    uniObj.getUniProtAccessions().stream()
                            .map(UniProtKBAccession::getValue)
                            .collect(Collectors.toList()));
        }
        informationType.setTemplate(plasmaIds);

        informationType.setInternal(uniObj.getInternal());

        return null;
    }

    private List<String> extractValues(MultiValueType multiValueType) {
        return Objects.nonNull(multiValueType)
                ? multiValueType.getValue().stream().collect(Collectors.toList())
                : null;
    }

    private MultiValueType createMultiValueType(List<String> values) {
        MultiValueType multiValueType = this.objectFactory.createMultiValueType();
        List<String> multiValueValues = multiValueType.getValue();
        if (Objects.nonNull(values)) {
            multiValueValues.addAll(values);
        }
        return multiValueType;
    }
}
