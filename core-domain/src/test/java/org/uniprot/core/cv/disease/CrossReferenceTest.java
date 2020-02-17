package org.uniprot.core.cv.disease;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceImpl;

public class CrossReferenceTest {
    private String random;
    private String id;
    private String dbType;
    private List<String> props;

    @BeforeEach
    void setUp() {
        this.random = UUID.randomUUID().toString();
        this.id = "id-" + this.random;
        this.dbType = "dbtype-" + this.random;
        this.props =
                IntStream.range(0, 5)
                        .mapToObj(i -> "props-" + i + this.random)
                        .collect(Collectors.toList());
    }

    @Test
    void testCreateObject() {

        DiseaseCrossReference xref = createCrossRef(false);
        // verify the values
        Assertions.assertEquals(this.id, xref.getId());
        Assertions.assertEquals(this.dbType, xref.getDatabaseType());
        Assertions.assertNotNull(xref.getProperties());
        Assertions.assertTrue(xref.getProperties().isEmpty());
    }

    @Test
    void testToStringWithoutProps() {
        DiseaseCrossReferenceImpl xref = createCrossRef(false);
        String expected = this.dbType + "; " + this.id + ".";
        Assertions.assertEquals(expected, xref.toString());
    }

    @Test
    void testToStringWithProps() {
        DiseaseCrossReferenceImpl xref = createCrossRef(true);
        String expected = this.dbType + "; " + this.id;
        for (String property : this.props) {
            expected = expected + "; " + property;
        }
        expected = expected + ".";
        Assertions.assertEquals(expected, xref.toString());
    }

    @Test
    void testEqualsValue() {
        DiseaseCrossReferenceImpl xref1 = createCrossRef(false);
        DiseaseCrossReferenceImpl xref2 = createCrossRef(false);
        Assertions.assertTrue(xref1.equals(xref2));
        Assertions.assertEquals(xref1.hashCode(), xref2.hashCode());
    }

    @Test
    void testEqualsRef() {
        DiseaseCrossReferenceImpl xref1 = createCrossRef(false);
        Assertions.assertTrue(xref1.equals(xref1));
        Assertions.assertEquals(xref1.hashCode(), xref1.hashCode());
    }

    @Test
    void testEqualsWithNull() {
        DiseaseCrossReferenceImpl xref1 = createCrossRef(false);
        Assertions.assertFalse(xref1.equals(null));
    }

    private DiseaseCrossReferenceImpl createCrossRef(boolean withProps) {
        if (withProps) {
            return new DiseaseCrossReferenceImpl(this.dbType, this.id, this.props);
        } else {
            return new DiseaseCrossReferenceImpl(this.dbType, this.id);
        }
    }
}
