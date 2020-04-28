// package org.uniprot.core.xml.unirule;
//
// import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceBuilderTest;
// import org.uniprot.core.unirule.impl.MultiValueBuilderTest;
// import org.uniprot.core.xml.AbstractConverterTest;
// import org.uniprot.core.xml.jaxb.uniprot.*;
// import org.uniprot.core.xml.uniprot.EvidencedStringTypeConverterTest;
// import org.uniprot.core.xml.uniprot.UniProtKBCrossReferenceConverterTest;
// import org.uniprot.core.xml.uniprot.comment.SubcellularLocationConverterTest;
//
// import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;
// import java.util.concurrent.ThreadLocalRandom;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;
//
// public class CommentConverterTest  extends AbstractConverterTest {
//
//    public static CommentType createObject(int listSize) {
//        CommentType commentType = uniProtObjectFactory.createCommentType();
//        String random = UUID.randomUUID().toString().substring(0, 4);
//        // molecule
//        commentType.setMolecule(createMoleculeType());
//        // absorption
//        CommentType.Absorption absorption = uniProtObjectFactory.createCommentTypeAbsorption();
//        absorption.setMax(EvidencedStringTypeConverterTest.createObject(listSize));
//        List<EvidencedStringType> text = EvidencedStringTypeConverterTest.createObjects(listSize);
//        absorption.getText().addAll(text);
//        commentType.setAbsorption(absorption);
//        // kinetic
//        CommentType.Kinetics kinetics = uniProtObjectFactory.createCommentTypeKinetics();
//        List<EvidencedStringType> km = EvidencedStringTypeConverterTest.createObjects(listSize);
//        List<EvidencedStringType> vmax = EvidencedStringTypeConverterTest.createObjects(listSize);
//        kinetics.getKM().addAll(km);
//        kinetics.getVmax().addAll(vmax);
//        kinetics.getText().addAll(text);
//        commentType.setKinetics(kinetics);
//        // phDepend
//        CommentType.PhDependence phDependence =
// uniProtObjectFactory.createCommentTypePhDependence();
//        phDependence.getText().addAll(text);
//        commentType.setPhDependence(phDependence);
//        // redoxPotential
//        CommentType.RedoxPotential redoxPotential =
// uniProtObjectFactory.createCommentTypeRedoxPotential();
//        redoxPotential.getText().addAll(text);
//        commentType.setRedoxPotential(redoxPotential);
//
//        // temperatureDependence
//        CommentType.TemperatureDependence temperatureDependence =
// uniProtObjectFactory.createCommentTypeTemperatureDependence();
//        temperatureDependence.getText().addAll(text);
//        commentType.setTemperatureDependence(temperatureDependence);
//        // reaction
//        ReactionType reaction = uniProtObjectFactory.createReactionType();
//        String reactionText = "reactText-" + random;
//        reaction.setText(reactionText);
//        reaction.getEvidence().addAll(createListOfInt(listSize));
//
// reaction.getDbReference().addAll(UniProtKBCrossReferenceConverterTest.createObjects(listSize));
//        commentType.setReaction(reaction);
//        //physiologicalReaction
//        List<PhysiologicalReactionType> physiologicalReaction =
// createPhysiologicalReactionTypes(listSize);
//        List<CofactorType> cofactor = createCofactorTypes(listSize);
//        List<SubcellularLocationType> subcellularLocation =
// SubcellularLocationConverterTest.createObjects(listSize);
//
//        CommentType.Conflict conflict = createConflict();
//        List<CommentType.Link> link = Arrays.asList(uniProtObjectFactory.createCommentTypeLink());
//        List<EventType> event = Arrays.asList(uniProtObjectFactory.createEventType());
//        List<IsoformType> isoform = Arrays.asList(uniProtObjectFactory.createIsoformType());
//        List<InteractantType> interactant =
// Arrays.asList(uniProtObjectFactory.createInteractantType());
//        Boolean organismsDiffer = ThreadLocalRandom.current().nextBoolean();
//        Integer experiments = ThreadLocalRandom.current().nextInt();
//        CommentType.Disease disease = uniProtObjectFactory.createCommentTypeDisease();
//        List<LocationType> location = Arrays.asList(uniProtObjectFactory.createLocationType());
//        List<EvidencedStringType> text =
// Arrays.asList(uniProtObjectFactory.createEvidencedStringType());
//        String type = "type-" + random;
//        String locationType = "localtionType-" + random;
//        String name = "name-" + random;
//        Float mass = ThreadLocalRandom.current().nextFloat();
//        String error = "error-" + random;
//        String method = "method-" + random;
//        List<Integer> evidence = IntStream.range(0, listSize).mapToObj(i ->
// ThreadLocalRandom.current().nextInt()).collect(Collectors.toList());
//        return commentType;
//    }
//
//    public static CommentType createObject() {
//        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
//        return createObject(listSize);
//    }
//
//    public static List<CommentType> createObjects(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> createObject(count))
//                .collect(Collectors.toList());
//    }
// }
