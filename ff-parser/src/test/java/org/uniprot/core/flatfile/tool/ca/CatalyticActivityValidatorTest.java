package org.uniprot.core.flatfile.tool.ca;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.transformer.CommentTransformerHelper;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.CommentType;

class CatalyticActivityValidatorTest {
    private static CatalyticActivityFileRepository repository;

    @BeforeAll
    static void setup() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/catalytic_activity.tsv");
        repository = new CatalyticActivityFileRepository(is);
    }

    @Test
    void testNoReactionRef() {
        String value =
                "Reaction=Selective cleavage of Gln-|-Gly bond in the poliovirus polyprotein."
                        + " In other picornavirus reactions Glu may be substituted for Gln, and Ser or Thr for Gly.; EC=3.4.22.28;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(comment, validated.get());
    }

    @Test
    void testNoReactionRefWrongEC() {
        String value =
                "Reaction=Selective cleavage of Gln-|-Gly bond in the poliovirus polyprotein."
                        + " In other picornavirus reactions Glu may be substituted for Gln, and Ser or Thr for Gly.; EC=3.4.22.29;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertFalse(validated.isPresent());
    }

    @Test
    void testNoReactionRefNoEC() {
        String value =
                "Reaction=Selective cleavage of Gln-|-Gly bond in the poliovirus polyprotein."
                        + " In other picornavirus reactions Glu may be substituted for Gln, and Ser or Thr for Gly.;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(comment, validated.get());
    }

    @Test
    void testNoReactionRefInValid() {
        String value =
                "Reaction=Selective cleavage of Gln-|-Gly bond in the poliovirus polyprotein."
                        + " In other picornavirus reactions Glu may be substituted for Gln, and Ser.; EC=3.4.22.28;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertFalse(validated.isPresent());
    }

    @Test
    void testValid() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbilane + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:57845, ChEBI:CHEBI:58126; EC=2.5.1.61;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(comment, validated.get());
    }

    @Test
    void testValidWrongName() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbila + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:57845, ChEBI:CHEBI:58126; EC=2.5.1.61;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);
        CatalyticActivityComment validComment = createValid();
        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(validComment, validated.get());
    }

    @Test
    void testValidNoEC() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbilane + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:57845, ChEBI:CHEBI:58126;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);

        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(comment, validated.get());
    }

    @Test
    void testValidLessChEBI() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbilane + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:58126; EC=2.5.1.61;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);
        CatalyticActivityComment validComment = createValid();
        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(validComment, validated.get());
    }

    @Test
    void testValidMoreChEBI() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbilane + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:57845, ChEBI:CHEBI:58126, ChEBI:CHEBI:58127; EC=2.5.1.61;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        CatalyticActivityValidator validator = new DefaultCatalyticActivityValidator(repository);
        CatalyticActivityComment validComment = createValid();
        Optional<CatalyticActivityComment> validated = validator.validateAndConvert(comment);
        assertTrue(validated.isPresent());

        assertEquals(validComment, validated.get());
    }

    private CatalyticActivityComment createValid() {
        String value =
                "Reaction=H2O + 4 porphobilinogen = hydroxymethylbilane + 4 NH4(+);"
                        + " Xref=Rhea:RHEA:13185, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938, ChEBI:CHEBI:57845, ChEBI:CHEBI:58126; EC=2.5.1.61;";
        CatalyticActivityComment comment =
                CommentTransformerHelper.transform(value, CommentType.CATALYTIC_ACTIVITY);
        return comment;
    }
}
