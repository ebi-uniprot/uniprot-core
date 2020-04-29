package org.uniprot.core.xml;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bbottema.loremipsumobjects.ClassBindings;
import org.bbottema.loremipsumobjects.LoremIpsumObjectCreator;
import org.bbottema.loremipsumobjects.typefactories.MethodBasedFactory;
import org.uniprot.core.util.EnumDisplay;
import org.uniprot.core.xml.jaxb.uniprot.*;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.uniprot.UniProtKBCrossReferenceConverterTest;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class AbstractConverterTest {

    protected static ObjectFactory uniRuleObjectFactory;
    protected static org.uniprot.core.xml.jaxb.uniprot.ObjectFactory uniProtObjectFactory;
    protected static String random;
    protected static LoremIpsumObjectCreator objectCreator;
    protected static ClassBindings classBindings;


    static {
        classBindings = new ClassBindings();
        try {
            classBindings.bind(XMLGregorianCalendar.class,
                    new MethodBasedFactory<>(AbstractConverterTest.class.getMethod("createXMLGregorianCalendar")));
//            classBindings.bind(EnumDisplay.class, new MethodBasedFactory<>(AbstractConverterTest.class.getMethod("getEnum")));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(0);
        }
        objectCreator = new LoremIpsumObjectCreator(classBindings);
        uniRuleObjectFactory = new ObjectFactory();
        uniProtObjectFactory = new org.uniprot.core.xml.jaxb.uniprot.ObjectFactory();
        random = UUID.randomUUID().toString().substring(0, 4);
    }

    public static XMLGregorianCalendar createXMLGregorianCalendar() throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar();
    }



    public static MoleculeType createMoleculeType() {
        // molecule
        MoleculeType molecule = uniProtObjectFactory.createMoleculeType();
        String mid = "mid-" + random;
        String mvalue = "mvalue-" + random;
        molecule.setValue(mvalue);
        molecule.setId(mid);
        return molecule;
    }

    public static List<Integer> createListOfInt(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> ThreadLocalRandom.current().nextInt())
                .collect(Collectors.toList());
    }

    public static List<String> createListOfString(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> i + "-str-" + random)
                .collect(Collectors.toList());
    }

    public static List<PropertyType> createListOfPropertyType(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> createPropertyType())
                .collect(Collectors.toList());
    }

    public static PropertyType createPropertyType() {
        String type = "type-" + random;
        String value = "value-" + random;
        PropertyType propertyType = uniProtObjectFactory.createPropertyType();
        propertyType.setType(type);
        propertyType.setValue(value);
        return propertyType;
    }

    public static PhysiologicalReactionType createPhysiologicalReactionType(int listSize) {
        PhysiologicalReactionType physiologicalReactionType =
                uniProtObjectFactory.createPhysiologicalReactionType();
        physiologicalReactionType.setDbReference(
                UniProtKBCrossReferenceConverterTest.createObject());
        String direction = "direction-" + random;
        physiologicalReactionType.setDirection(direction);
        List<Integer> evidences = createListOfInt(listSize);
        physiologicalReactionType.getEvidence().addAll(evidences);
        return physiologicalReactionType;
    }

    public static List<PhysiologicalReactionType> createPhysiologicalReactionTypes(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createPhysiologicalReactionType(count))
                .collect(Collectors.toList());
    }

    public static CofactorType createCofactorType(int listSize) {
        CofactorType cofactorType = uniProtObjectFactory.createCofactorType();
        cofactorType.setDbReference(UniProtKBCrossReferenceConverterTest.createObject());
        String name = "name-" + random;
        cofactorType.setName(name);
        List<Integer> evidences = createListOfInt(listSize);
        cofactorType.getEvidence().addAll(evidences);
        return cofactorType;
    }

    public static List<CofactorType> createCofactorTypes(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createCofactorType(count))
                .collect(Collectors.toList());
    }

    public static CommentType.Conflict createConflict() {
        CommentType.Conflict conflict = uniProtObjectFactory.createCommentTypeConflict();
        String type = "type-" + random;
        String ref = "ref-" + random;
        conflict.setType(type);
        conflict.setRef(ref);
        conflict.setSequence(createSequence());
        return conflict;
    }

    public static CommentType.Conflict.Sequence createSequence() {
        CommentType.Conflict.Sequence sequence = uniProtObjectFactory.createCommentTypeConflictSequence();
        String resource = "resource-" + random;
        String id = "id-" + random;
        Integer version = ThreadLocalRandom.current().nextInt();
        sequence.setResource(resource);
        sequence.setId(id);
        sequence.setVersion(version);

        return sequence;
    }

    public static List<CommentType.Link> createListOfLinks(int listSize) {
        return IntStream.range(0, listSize).mapToObj(i -> createLink()).collect(Collectors.toList());

    }

    public static CommentType.Link createLink() {
        String uri = "uri-" + random;
        CommentType.Link link = uniProtObjectFactory.createCommentTypeLink();
        link.setUri(uri);
        return link;
    }

}
