package org.uniprot.core.cv.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.xdb.CrossRefEntry;
import org.uniprot.core.impl.StatisticsBuilder;

import java.util.List;

class CrossRefEntryBuilderTest {

    @Test
    void canSet_name() {
        String name = "crfName";
        CrossRefEntry obj = new CrossRefEntryBuilder().name(name).build();
        assertEquals(name, obj.getName());
    }

    @Test
    void canSet_accession() {
        String accession = "accession";
        CrossRefEntry obj = new CrossRefEntryBuilder().id(accession).build();
        assertEquals(accession, obj.getId());
    }

    @Test
    void canSet_abbrev() {
        String abbrev = "abbrev";
        CrossRefEntry obj = new CrossRefEntryBuilder().abbrev(abbrev).build();
        assertEquals(abbrev, obj.getAbbrev());
    }

    @Test
    void canSet_doiId() {
        String doiId = "doiId";
        CrossRefEntry obj = new CrossRefEntryBuilder().doiId(doiId).build();
        assertEquals(doiId, obj.getDoiId());
    }

    @Test
    void canSet_linkType() {
        String linkType = "linkType";
        CrossRefEntry obj = new CrossRefEntryBuilder().linkType(linkType).build();
        assertEquals(linkType, obj.getLinkType());
    }

    @Test
    void canSet_servers() {
        List<String> servers = List.of("server1", "server2");
        CrossRefEntry obj = new CrossRefEntryBuilder().serversSet(servers).build();
        assertEquals(servers, obj.getServers());
    }

    @Test
    void canAdd_servers() {
        String server = "server";
        CrossRefEntry obj = new CrossRefEntryBuilder().serversAdd(server).build();
        assertEquals(List.of(server), obj.getServers());
    }

    @Test
    void canSet_dbUrl() {
        String dbUrl = "dbUrl";
        CrossRefEntry obj = new CrossRefEntryBuilder().dbUrl(dbUrl).build();
        assertEquals(dbUrl, obj.getDbUrl());
    }

    @Test
    void canSet_category() {
        String category = "category";
        CrossRefEntry obj = new CrossRefEntryBuilder().category(category).build();
        assertEquals(category, obj.getCategory());
    }

    @Test
    void canSet_pubMedId() {
        String pubMedId = "pubMedId";
        CrossRefEntry obj = new CrossRefEntryBuilder().pubMedId(pubMedId).build();
        assertEquals(pubMedId, obj.getPubMedId());
    }

    @Test
    void canSet_statistics() {
        Statistics statistics =
                new StatisticsBuilder()
                        .reviewedProteinCount(10)
                        .unreviewedProteinCount(100)
                        .build();
        CrossRefEntry obj = new CrossRefEntryBuilder().statistics(statistics).build();
        assertEquals(statistics, obj.getStatistics());
    }

    @Test
    void canCreateBuilderFromInstance() {
        CrossRefEntry obj = new CrossRefEntryBuilder().build();
        CrossRefEntryBuilder builder = CrossRefEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        CrossRefEntry obj = new CrossRefEntryBuilder().build();
        CrossRefEntry obj2 = new CrossRefEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
