package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.uniprot.core.publication.CommunityAnnotation;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class CommunityAnnotationBuilderTest {
    private static final String VALUE = "value";

    @Test
    void canCreateViaFrom() {
        CommunityAnnotation ann = new CommunityAnnotationBuilder().comment(VALUE).build();
        CommunityAnnotationBuilder builder = CommunityAnnotationBuilder.from(ann);
        assertThat(builder.build().getComment(), is(VALUE));
    }

    @ParameterizedTest(name = "Checking: {0} with value <{1}>")
    @MethodSource("provideTestCriteria")
    void testBuilderAttribute(
            String description,
            String value,
            BiConsumer<CommunityAnnotationBuilder, String> builderOperation,
            Function<CommunityAnnotationBuilder, String> returnValue) {

        CommunityAnnotationBuilder builder = new CommunityAnnotationBuilder();
        builderOperation.accept(builder, value);
        assertThat(returnValue.apply(builder), is(value));
    }

    @SuppressWarnings("squid:S1905")
    private static Stream<Arguments> provideTestCriteria() {
        BiConsumer<CommunityAnnotationBuilder, String> commentSetter =
                CommunityAnnotationBuilder::comment;
        Function<CommunityAnnotationBuilder, String> commentGetter = b -> b.build().getComment();

        BiConsumer<CommunityAnnotationBuilder, String> diseaseSetter =
                CommunityAnnotationBuilder::disease;
        Function<CommunityAnnotationBuilder, String> diseaseGetter = b -> b.build().getDisease();

        BiConsumer<CommunityAnnotationBuilder, String> functionSetter =
                CommunityAnnotationBuilder::function;
        Function<CommunityAnnotationBuilder, String> functionGetter = b -> b.build().getFunction();

        BiConsumer<CommunityAnnotationBuilder, String> proteinOrGeneSetter =
                CommunityAnnotationBuilder::proteinOrGene;
        Function<CommunityAnnotationBuilder, String> proteinOrGeneGetter =
                b -> b.build().getProteinOrGene();

        String comment = "comment";
        String disease = "disease";
        String function = "function";
        String proteinOrGene = "proteinOrGene";

        return Stream.of(
                // setting comments with value/null/""
                Arguments.of(comment, VALUE, commentSetter, commentGetter),
                Arguments.of(comment, null, commentSetter, commentGetter),
                Arguments.of(comment, "", commentSetter, commentGetter),
                // setting disease with value/null/""
                Arguments.of(disease, VALUE, diseaseSetter, diseaseGetter),
                Arguments.of(disease, null, diseaseSetter, diseaseGetter),
                Arguments.of(disease, "", diseaseSetter, diseaseGetter),
                // setting function with value/null/""
                Arguments.of(function, VALUE, functionSetter, functionGetter),
                Arguments.of(function, null, functionSetter, functionGetter),
                Arguments.of(function, "", functionSetter, functionGetter),
                // setting proteinOrGene with value/null/""
                Arguments.of(proteinOrGene, VALUE, proteinOrGeneSetter, proteinOrGeneGetter),
                Arguments.of(proteinOrGene, null, proteinOrGeneSetter, proteinOrGeneGetter),
                Arguments.of(proteinOrGene, "", proteinOrGeneSetter, proteinOrGeneGetter));
    }
}
