package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryRangeBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.MSCommentConverter;
import uk.ac.ebi.uniprot.xml.uniprot.comment.MSRangeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class MSCommentConverterTest {

    @Test
    void testRangeConverter() {
        MSRangeConverter converter = new MSRangeConverter();
        Range range = new Range(123, 234);
        String isoformId = "Some id";
        MassSpectrometryRange msRange = createMassSpectrometryRange(range, isoformId);
        LocationType type = converter.toXml(msRange);
        System.out.println(UniProtXmlTestHelper.toXmlString(type, LocationType.class, "location"));
        MassSpectrometryRange converted = converter.fromXml(type);
        assertEquals(msRange, converted);

    }

    @Test
    void test() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        Range range = new Range(26, 54);
        Evidence evidence = parseEvidenceLine("ECO:0000269|PubMed:22887697");
        String isoformId = "";
        MassSpectrometryRange msRange = createMassSpectrometryRange(range, isoformId);
        ranges.add(msRange);
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment = builder.molWeight(3042.79f)
                .method(MassSpectrometryMethod.ELECTROSPRAY)
                .ranges(ranges)
                .note("Monoisotopic mass.")
                .evidences(singletonList(evidence))
                .build();

        MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        MassSpectrometryComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testWithError() {
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        Range range = new Range(26, 54);
        Evidence evidence = parseEvidenceLine("ECO:0000269|PubMed:22887697");
        String isoformId = "";
        MassSpectrometryRange msRange = createMassSpectrometryRange(range, isoformId);
        ranges.add(msRange);
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment = builder.molWeight(3042.79f)
                .molWeightError(0.023f)
                .method(MassSpectrometryMethod.ELECTROSPRAY)
                .ranges(ranges)
                .note("Monoisotopic mass.")
                .evidences(Arrays.asList(evidence))
                .build();

        MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        MassSpectrometryComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    private MassSpectrometryRange createMassSpectrometryRange(Range range, String isoformId) {
        return new MassSpectrometryRangeBuilder()
                .range(range)
                .isoformId(isoformId)
                .build();
    }
}
