package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryBuilder;
import org.uniprot.core.xml.XmlChainIterator;
import org.uniprot.core.xml.jaxb.uniref.Entry;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
class UniRefEntryConverterTest {
    static final String UNIREF_ROOT_ELEMENT = "entry";

    @Test
    void testFromXml() throws Exception {
        String file = "/uniref/50_Q9EPS7_Q95604.xml";
        InputStream is = UniRefEntryConverterTest.class.getResourceAsStream(file);

        assertNotNull(is);

        List<InputStream> iss = Arrays.asList(is);

        XmlChainIterator<Entry, Entry> chainingIterators =
                new XmlChainIterator<>(
                        iss.iterator(), Entry.class, UNIREF_ROOT_ELEMENT, Function.identity());
        int count = 0;
        while (chainingIterators.hasNext()) {
            Entry entry = chainingIterators.next();
            rountripTest(entry);
            count++;
        }
        is.close();
        assertEquals(2, count);
    }

    @Test
    void testCommonOrganismWithCommonName() throws Exception {
        UniRefEntryConverter converter = new UniRefEntryConverter();
        UniRefEntry uniRefEntry =
                new UniRefEntryBuilder()
                        .id("UniRef50_P12345")
                        .entryType(UniRefType.UniRef50)
                        .commonTaxon(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Homo Sapiens")
                                        .commonName("Human")
                                        .build())
                        .memberCount(1)
                        .representativeMember(
                                new RepresentativeMemberBuilder()
                                        .memberId("P12345_HUMAN")
                                        .memberIdType(UniRefMemberIdType.UNIPROTKB_TREMBL)
                                        .accessionsAdd(
                                                new UniProtKBAccessionBuilder("P12345").build())
                                        .sequence(new SequenceBuilder("AAAAA").build())
                                        .organismName("Homo Sapiens (Human)")
                                        .organismTaxId(9606L)
                                        .build())
                        .build();
        Entry xmlConverter = converter.toXml(uniRefEntry);
        assertNotNull(xmlConverter);
        UniRefEntry entryConverted = converter.fromXml(xmlConverter);
        assertNotNull(entryConverted);
        assertEquals(uniRefEntry, entryConverted);
    }

    @Test
    void testCommonOrganismWithoutCommonName() throws Exception {
        UniRefEntryConverter converter = new UniRefEntryConverter();
        UniRefEntry uniRefEntry =
                new UniRefEntryBuilder()
                        .id("UniRef50_P12345")
                        .entryType(UniRefType.UniRef50)
                        .commonTaxon(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Homo Sapiens")
                                        .build())
                        .memberCount(1)
                        .representativeMember(
                                new RepresentativeMemberBuilder()
                                        .memberId("P12345_HUMAN")
                                        .memberIdType(UniRefMemberIdType.UNIPROTKB_TREMBL)
                                        .accessionsAdd(
                                                new UniProtKBAccessionBuilder("P12345").build())
                                        .sequence(new SequenceBuilder("AAAAA").build())
                                        .organismName("Homo Sapiens")
                                        .organismTaxId(9606L)
                                        .build())
                        .build();
        Entry xmlConverter = converter.toXml(uniRefEntry);
        assertNotNull(xmlConverter);
        UniRefEntry entryConverted = converter.fromXml(xmlConverter);
        assertNotNull(entryConverted);
        assertEquals(uniRefEntry, entryConverted);
    }

    private void rountripTest(Entry xmlEntry) {
        UniRefEntryConverter converter = new UniRefEntryConverter();
        UniRefEntry entry = converter.fromXml(xmlEntry);
        Entry converted = converter.toXml(entry);
        UniRefEntry converted2 = converter.fromXml(converted);
        assertEquals(entry, converted2);
    }

    @Test
    void testInvalidId() throws Exception {
        Entry xmlEntry = new Entry();
        xmlEntry.setId("INVALID");
        UniRefEntryConverter converter = new UniRefEntryConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.fromXml(xmlEntry));
    }
}
