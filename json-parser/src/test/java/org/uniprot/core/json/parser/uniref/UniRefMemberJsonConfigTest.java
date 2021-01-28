package org.uniprot.core.json.parser.uniref;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.OverlapRegionBuilder;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author lgonzales
 * @since 12/01/2021
 */
class UniRefMemberJsonConfigTest {

    @Test
    void testFullUniRefMemberJsonRoundTrip() {
        UniRefMember entry = createRepresentativeMember();
        ValidateJson.verifyJsonRoundTripParser(
                UniRefMemberJsonConfig.getInstance().getFullObjectMapper(), entry);
        try {
            ObjectMapper mapper = UniRefMemberJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testNoUnsetField() {
        UniRefMember entry = createRepresentativeMember();
        ValidateJson.verifyEmptyFields(entry, "ancestors");
    }

    private static RepresentativeMember createRepresentativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVEDDFSAGSADFAFWERDGDSDGFDSHSDJHETRHJREH";
        Sequence sequence = new SequenceBuilder(seq).build();
        String memberId = "P12345_HUMAN";
        int length = 312;
        String pName = "some protein name";
        String upi = "UPI0000083A08";

        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB;

        return new RepresentativeMemberBuilder()
                .memberIdType(type)
                .memberId(memberId)
                .organismName("Homo sapiens")
                .organismTaxId(9606)
                .sequenceLength(length)
                .proteinName(pName)
                .uniparcId(new UniParcIdBuilder(upi).build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12345").build())
                .accessionsAdd(new UniProtKBAccessionBuilder("P12346").build())
                .uniref100Id(new UniRefEntryIdBuilder("UniRef100_P03923").build())
                .uniref90Id(new UniRefEntryIdBuilder("UniRef90_P03943").build())
                .uniref50Id(new UniRefEntryIdBuilder("UniRef50_P03973").build())
                .overlapRegion(new OverlapRegionBuilder().start(10).end(20).build())
                .isSeed(true)
                .sequence(sequence)
                .build();
    }
}
