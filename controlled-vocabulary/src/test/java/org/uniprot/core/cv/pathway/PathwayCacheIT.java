package org.uniprot.core.cv.pathway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PathwayCacheIT {
    private static List<Pathway> pathways;

    @BeforeAll
    static void setup() {
        pathways = PathwayCache.INSTANCE.get("");
    }

    @Test
    void testNoParent() {
        String acc = "UPA00611";
        Optional<Pathway> opVal =
                pathways.stream().filter(val -> val.getAccession().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<Pathway> hi = opVal.map(Pathway::getIsAParents).orElse(Collections.emptyList());
        List<Pathway> hp = opVal.map(Pathway::getPartOfParents).orElse(Collections.emptyList());
        assertTrue(hi.isEmpty());
        assertTrue(hp.isEmpty());
    }

    @Test
    void testMultiXrefWithHi() {
        String acc = "UPA00056";
        Optional<Pathway> opVal =
                pathways.stream().filter(val -> val.getAccession().equals(acc)).findFirst();
        List<Pathway> hi = opVal.map(Pathway::getIsAParents).orElse(Collections.emptyList());
        List<Pathway> hp = opVal.map(Pathway::getPartOfParents).orElse(Collections.emptyList());
        assertTrue(opVal.isPresent());
        assertEquals(1, hi.size());
        assertTrue(hp.isEmpty());
    }

    @Test
    void testWithHP() {
        String acc = "UPA00056";
        Optional<Pathway> opVal =
                pathways.stream().filter(val -> val.getAccession().equals(acc)).findFirst();
        assertTrue(opVal.isPresent());
        List<Pathway> hi = opVal.map(Pathway::getIsAParents).orElse(Collections.emptyList());
        List<Pathway> hp = opVal.map(Pathway::getPartOfParents).orElse(Collections.emptyList());
        assertEquals(1, hi.size());
        assertTrue(hp.isEmpty());
    }
}
