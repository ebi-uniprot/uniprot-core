package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.SequenceFeatureLocation;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequenceFeatureLocationBuilderTest {
    @Test
    void testStart() {
        int start = 10;
        SequenceFeatureLocation location =
                new SequenceFeatureLocationBuilder().start(start).build();
        assertEquals(start, location.getStart());
    }

    @Test
    void testEnd() {
        int end = 20;
        SequenceFeatureLocation location =
                new SequenceFeatureLocationBuilder().end(end).build();
        assertEquals(end, location.getEnd());
    }

    @Test
    void testRange() {
        int start = 10;
        int end = 20;
        SequenceFeatureLocation location =
                new SequenceFeatureLocationBuilder().range(start, end).build();
        assertEquals(start, location.getStart());
        assertEquals(end, location.getEnd());
    }

    @Test
    void testAlignment() {
        String alignment = "M55";
        SequenceFeatureLocation location =
                new SequenceFeatureLocationBuilder().alignment(alignment).build();
        assertEquals(alignment, location.getAlignment());
    }
    @Test
    void testFromSequenceFeatureLocation() {
        SequenceFeatureLocation location = new SequenceFeatureLocationImpl(10, 20, "VALUE");
        SequenceFeatureLocation fromLocation = SequenceFeatureLocationBuilder.from(location).build();
        assertEquals(location, fromLocation);
    }
}