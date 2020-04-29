package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.InformationType;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;

public class InformationConverterTest extends AbstractConverterTest {
    public static InformationType createObject() {
        InformationType information = objectCreator.createLoremIpsumObject(InformationType.class);
        // fill array ones
        MultiValueType uniprotId = MultiValueConverterTest.createObject();
        information.setUniprotId(uniprotId);
        MultiValueType name = MultiValueConverterTest.createObject();
        information.setName(name);
        MultiValueType related = MultiValueConverterTest.createObject();
        information.setRelated(related);
        MultiValueType template = MultiValueConverterTest.createObject();
        information.setTemplate(template);
        MultiValueType duplicate = MultiValueConverterTest.createObject();
        information.setDuplicate(duplicate);
        MultiValueType plasmid = MultiValueConverterTest.createObject();
        information.setPlasmid(plasmid);
        // update enum string
        updateEnums(information);
        return information;
    }

    public static List<InformationType> createObjects() {
        InformationTypeList informationTypes =
                objectCreator.createLoremIpsumObject(InformationTypeList.class);
        informationTypes.forEach(informationType -> updateEnums(informationType));
        return informationTypes;
    }

    public static class InformationTypeList extends ArrayList<InformationType> {}

    private static void updateEnums(InformationType informationType) {
        String dataClass =
                ThreadLocalRandom.current().nextBoolean()
                        ? DataClassType.PROTEIN.getDisplayName()
                        : DataClassType.DOMAIN.getDisplayName();
        informationType.setDataClass(dataClass);
    }
}
