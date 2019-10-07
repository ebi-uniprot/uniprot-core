package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.builder.EvidenceLineBuilder;

import static org.junit.jupiter.api.Assertions.*;

class InternalSectionBuilderTest {

  @Test
  void nullInternalLine_willBeIgnore() {
    InternalSection internalSection = new InternalSectionBuilder().addInternalLine(null).build();
    assertTrue(internalSection.getInternalLines().isEmpty());
  }

  @Test
  void singleInternalLine_canBeAdded() {
    InternalLine internalLine = new InternalLineBuilder(InternalLineType.PE, "abc").build();
    InternalSection internalSection = new InternalSectionBuilder().addInternalLine(internalLine)
      .build();
    assertEquals(internalLine, internalSection.getInternalLines().get(0));
  }

  @Test
  void nullEvidence_willBeIgnore() {
    InternalSection internalSection = new InternalSectionBuilder().addEvidenceLine(null).build();
    assertTrue(internalSection.getInternalLines().isEmpty());
  }

  @Test
  void singleEvidenceLine_canBeAdded() {
    EvidenceLine evdLine = new EvidenceLineBuilder().build();
    InternalSection internalSection = new InternalSectionBuilder().addEvidenceLine(evdLine)
      .build();
    assertEquals(evdLine, internalSection.getEvidenceLines().get(0));
  }

  @Test
  void nullSourceLine_willBeIgnore() {
    InternalSection internalSection = new InternalSectionBuilder().addSourceLine(null).build();
    assertTrue(internalSection.getInternalLines().isEmpty());
  }

  @Test
  void singleSourceLine_canBeAdded() {
    SourceLine line = new SourceLineBuilder("line").build();
    InternalSection internalSection = new InternalSectionBuilder().addSourceLine(line)
      .build();
    assertEquals(line, internalSection.getSourceLines().get(0));
  }

  @Test
  void canCreateBuilderFromInstance() {
    InternalSection obj = new InternalSectionBuilder().build();
    InternalSectionBuilder builder = new InternalSectionBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    InternalSection obj = new InternalSectionBuilder().build();
    InternalSection obj2 = new InternalSectionBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}