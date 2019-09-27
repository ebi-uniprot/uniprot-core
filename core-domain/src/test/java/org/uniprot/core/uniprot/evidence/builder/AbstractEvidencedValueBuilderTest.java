package org.uniprot.core.uniprot.evidence.builder;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

public class AbstractEvidencedValueBuilderTest {
    private static final String DB_ID_1 = "PIR_ID";
    private static final String DB_NAME_1 = "PIR";
    private static final String VALUE_1 = "the value 1";
    private static final Evidence EVIDENCE_1 =
            new EvidenceBuilder()
                    .evidenceCode(EvidenceCode.ECO_0000213)
                    .databaseId(DB_ID_1)
                    .databaseName(DB_NAME_1)
                    .build();

    private static final String DB_ID_2 = "PDB_ID";
    private static final String DB_NAME_2 = "PDB";
    private static final String VALUE_2 = "the value 2";
    private static final Evidence EVIDENCE_2 =
            new EvidenceBuilder()
                    .evidenceCode(EvidenceCode.ECO_0000213)
                    .databaseId(DB_ID_2)
                    .databaseName(DB_NAME_2)
                    .build();

    @Test
    void checkAbstractEvidencedValueBuilderCreationIsAsExpected() {
        EvidencedValueImpl evidencedValue =
                new TestableEvidencedValueBuilder()
                        .value(VALUE_2)
                        .evidences(singletonList(EVIDENCE_1))
                        .build();

        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), is(VALUE_2));
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(1));
        assertThat(evidencedValue.getEvidences(), contains(EVIDENCE_1));
    }

    @Test
    void checkAbstractEvidencedValueBuilderCreationAddEvidencesIsAsExpected() {
        EvidencedValueImpl evidencedValue =
                new TestableEvidencedValueBuilder()
                        .value(VALUE_2)
                        .evidences(singletonList(EVIDENCE_1))
                        .addEvidence(EVIDENCE_2)
                        .build();

        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), is(VALUE_2));
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(2));
        assertThat(evidencedValue.getEvidences(), contains(EVIDENCE_1, EVIDENCE_2));
    }

    @Test
    void checkAbstractEvidencedValueBuilderCreationIsAsExpectedWithoutValues() {
        EvidencedValueImpl evidencedValue = new TestableEvidencedValueBuilder().build();

        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), isEmptyString());
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(0));
    }

    protected void buildEvidencedValueParameters(AbstractEvidencedValueBuilder<?, ?> builder) {
        builder.value(VALUE_1).evidences(singletonList(EVIDENCE_1)).addEvidence(EVIDENCE_2);
    }

    protected List<Evidence> getEvidenceList() {
        return singletonList(EVIDENCE_1);
    }

    protected Evidence getEvidence() {
        return EVIDENCE_2;
    }

    protected String getValue() {
        return VALUE_1;
    }

    protected void verifyEvidencedValue(EvidencedValue evidencedValue) {
        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), is(VALUE_1));
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(2));
        assertThat(evidencedValue.getEvidences(), contains(EVIDENCE_1, EVIDENCE_2));
    }

    private static class TestableEvidencedValueBuilder
            extends AbstractEvidencedValueBuilder<
                    TestableEvidencedValueBuilder, EvidencedValueImpl> {
        @Override
        public EvidencedValueImpl build() {
            return new EvidencedValueImpl(this.getValue(), this.getEvidences());
        }

        @Override
        public TestableEvidencedValueBuilder from(EvidencedValueImpl instance) {
            super.from(instance);
            return this;
        }

        @Override
        protected TestableEvidencedValueBuilder getThis() {
            return this;
        }
    }
}
