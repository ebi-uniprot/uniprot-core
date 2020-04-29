package org.uniprot.core.xml.unirule;

import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.FusionType;

public class FusionConverterTest extends AbstractConverterTest {

    public static FusionType createObject() {
        FusionType fusionType = objectCreator.createLoremIpsumObject(FusionType.class);
        // fill list types
        List<String> nter =
                objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        fusionType.getNter().addAll(nter);
        List<String> cter =
                objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        fusionType.getCter().addAll(cter);
        return fusionType;
    }
}
