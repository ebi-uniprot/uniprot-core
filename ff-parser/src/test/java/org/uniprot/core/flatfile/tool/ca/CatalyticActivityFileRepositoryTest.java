package org.uniprot.core.flatfile.tool.ca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CatalyticActivityFileRepositoryTest {
    private static CatalyticActivityFileRepository repository;

    @BeforeAll
    static void setup() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/catalytic_activity.tsv");
        repository = new CatalyticActivityFileRepository(is);
    }

    @Test
    void testGetByRheaIdValid() {
        String rheaId = "RHEA:10004";
        CatalyticActivity catalyticActivity = repository.getByRheaId(rheaId);
        String text = "benzyl isothiocyanate = benzyl thiocyanate";

        assertNotNull(catalyticActivity);
        assertEquals(rheaId, catalyticActivity.getRheaUn());
        assertEquals(text, catalyticActivity.getText());
    }

    @Test
    void testGetByRheaIdInValid() {
        String rheaId = "RHEA:0004";
        CatalyticActivity catalyticActivity = repository.getByRheaId(rheaId);
        assertNull(catalyticActivity);
    }

    @Test
    void testGetByOldTextValid() {
        String text = "(6-4) photoproduct (in DNA) = 2 pyrimidine residues (in DNA).";
        CatalyticActivity catalyticActivity = repository.getByOldText(text);
        assertNotNull(catalyticActivity);
        assertEquals(text, catalyticActivity.getText());
    }

    @Test
    void testGetByOldTextInValid() {
        String text = "(6-4) photoproduct (in DNA) = 2 pyrimidine residues (in DNA)";
        CatalyticActivity catalyticActivity = repository.getByOldText(text);
        assertNull(catalyticActivity);
    }

    @Test
    void testInvalidHeader() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/invalid_header.tsv");
        Assertions.assertThrows(
                CatalyticActivityMappingException.class,
                () -> {
                    new CatalyticActivityFileRepository(is);
                });
    }

    @Test
    void testFewerColumns() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/fewer_column.tsv");
        Assertions.assertThrows(
                CatalyticActivityMappingException.class,
                () -> {
                    new CatalyticActivityFileRepository(is);
                });
    }

    @Test
    void testMoreColumns() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/more_column.tsv");
        Assertions.assertThrows(
                CatalyticActivityMappingException.class,
                () -> {
                    new CatalyticActivityFileRepository(is);
                });
    }

    @Test
    void testEmptyText() {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("catalyticactivity/invalidText.tsv");
        Assertions.assertThrows(
                CatalyticActivityMappingException.class,
                () -> {
                    new CatalyticActivityFileRepository(is);
                });
    }
}
