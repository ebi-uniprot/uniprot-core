// package org.uniprot.core.xml.unirule;
//
// import org.uniprot.core.uniprotkb.UniProtKBAccession;
// import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
// import org.uniprot.core.xml.Converter;
// import org.uniprot.core.xml.jaxb.unirule.FusionType;
// import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
//
// import java.util.List;
// import java.util.Objects;
// import java.util.UUID;
// import java.util.concurrent.ThreadLocalRandom;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;
//
// public class UniProtKBAccessionConverterTest{
//    public static FusionType createObject(int listSize) {
//        FusionType fusionType = objectFactory.createFusionType();
//        String random = UUID.randomUUID().toString();
//        List<String> nter =
//                IntStream.range(0, listSize)
//                        .mapToObj(i -> i + "nter-" + random)
//                        .collect(Collectors.toList());
//        List<String> cter =
//                IntStream.range(0, listSize)
//                        .mapToObj(i -> i + "cter-" + random)
//                        .collect(Collectors.toList());
//
//        fusionType.getNter().addAll(nter);
//        fusionType.getCter().addAll(cter);
//
//        return fusionType;
//    }
//
//    public static FusionType createObject() {
//        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
//        return createObject(listSize);
//    }
//
//    public static List<FusionType> createObjects(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> createObject(count))
//                .collect(Collectors.toList());
//    }
// }
