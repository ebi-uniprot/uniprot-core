package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.xml.jaxb.uniref.MemberType;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
class RepresentativeMemberConverterTest {

    @Test
    void testToXml() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        RepresentativeMember member =
                new RepresentativeMemberBuilder()
                        .memberIdType(type)
                        .memberId(memberId)
                        .organismName("Homo sapiens")
                        .organismTaxId(9606)
                        .sequenceLength(length)
                        .proteinName(pName)
                        .uniparcId(new UniParcIdBuilder(upi).build())
                        .accessionsAdd(new UniProtAccessionBuilder(memberId).build())
                        .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                        .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                        .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                        .isSeed(true)
                        .sequence(sequence)
                        .build();
        RepresentativeMemberConverter converter = new RepresentativeMemberConverter();
        MemberType xmlMember = converter.toXml(member);
        assertEquals(memberId, xmlMember.getDbReference().getId());
        RepresentativeMember converted = converter.fromXml(xmlMember);
        assertEquals(member, converted);
    }
}
