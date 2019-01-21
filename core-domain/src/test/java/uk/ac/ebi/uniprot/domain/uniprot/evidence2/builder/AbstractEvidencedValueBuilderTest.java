package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AbstractEvidencedValueBuilderTest {


    private static final String DB_ID_1 = "dbId1";
    private static final String DB_NAME_1 = "database Name 1";
    private static final String VALUE_1 = "the value 1";
    private static final Evidence EVIDENCE_1 = new EvidenceBuilder()
            .evidenceCode(EvidenceCode.ECO_0000213)
            .databaseId(DB_ID_1)
            .databaseName(DB_NAME_1)
            .build();

    private static final String DB_ID_2 = "dbId2";
    private static final String DB_NAME_2 = "database Name 2";
    private static final String VALUE_2 = "the value 2";
    private static final Evidence EVIDENCE_2 = new EvidenceBuilder()
            .evidenceCode(EvidenceCode.ECO_0000213)
            .databaseId(DB_ID_2)
            .databaseName(DB_NAME_2)
            .build();


    @Test
    void checkAbstractEvidencedValueBuilderCreationIsAsExpected() {
        EvidencedValueImpl evidencedValue = new TestableEvidencedValueBuilder()
                .value(VALUE_2)
                .evidences(Collections.singletonList(EVIDENCE_1))
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
        EvidencedValueImpl evidencedValue = new TestableEvidencedValueBuilder()
                .value(VALUE_2)
                .evidences(Collections.singletonList(EVIDENCE_1))
                .addEvidence(EVIDENCE_2)
                .build();

        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), is(VALUE_2));
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(2));
        assertThat(evidencedValue.getEvidences(), contains(EVIDENCE_1,EVIDENCE_2));
    }


    @Test
    void checkAbstractEvidencedValueBuilderCreationIsAsExpectedWithoutValues() {
        EvidencedValueImpl evidencedValue = new TestableEvidencedValueBuilder()
                .build();

        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), isEmptyString());
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(0));
    }

    protected void buildEvidencedValueParameters(AbstractEvidencedValueBuilder<?, ?> builder) {
        builder.value(VALUE_1)
               .evidences(Collections.singletonList(EVIDENCE_1))
               .addEvidence(EVIDENCE_2);
    }

    protected void verifyEvidencedValue(EvidencedValue evidencedValue) {
        assertNotNull(evidencedValue);
        assertNotNull(evidencedValue.getValue());
        assertThat(evidencedValue.getValue(), is(VALUE_1));
        assertNotNull(evidencedValue.getEvidences());
        assertThat(evidencedValue.getEvidences().size(), is(2));
        assertThat(evidencedValue.getEvidences(), contains(EVIDENCE_1, EVIDENCE_2));
    }

    private static class TestableEvidencedValueBuilder extends AbstractEvidencedValueBuilder<TestableEvidencedValueBuilder, EvidencedValueImpl> {
        @Override
        public EvidencedValueImpl build() {
            return new EvidencedValueImpl(this.getValue(),this.getEvidences());
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