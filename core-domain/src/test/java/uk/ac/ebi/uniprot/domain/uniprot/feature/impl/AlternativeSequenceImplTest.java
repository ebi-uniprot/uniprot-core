package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.SequenceReport;

class AlternativeSequenceImplTest {

	@Test
	void testFull() {
		List<String> value =Arrays.asList("some report", "another report");
		SequenceReport report = AlternativeSequenceImpl.createReport(value);
		AlternativeSequence as =new AlternativeSequenceImpl("AB", Arrays.asList("DC", "SDGASS"),
				report
				);
		assertEquals("AB", as.getOriginalSequence());
		assertEquals(Arrays.asList("DC", "SDGASS"), as.getAlternativeSequences());
		assertEquals(report, as.getReport());
		TestHelper.verifyJson(as);
	}

	@Test
	void testMissing() {
		List<String> value =Arrays.asList("some report", "another report");
		SequenceReport report = AlternativeSequenceImpl.createReport(value);
		AlternativeSequence as =new AlternativeSequenceImpl("AB", Collections.emptyList(),
				report
				);
		assertEquals("AB", as.getOriginalSequence());
		assertTrue(as.getAlternativeSequences().isEmpty());
		assertEquals(report, as.getReport());
		
		TestHelper.verifyJson(as);
	}

	@Test
	void testReport() {
		String value ="some report";
		SequenceReport report = AlternativeSequenceImpl.createReport(value);
		assertEquals(1, report.getValue().size());
		assertEquals(value, report.getValue().get(0));
		TestHelper.verifyJson(report);
	}
	@Test
	void testReportMulti() {
		List<String> value =Arrays.asList("some report", "another report");
		SequenceReport report = AlternativeSequenceImpl.createReport(value);
		assertEquals(value, report.getValue());
		TestHelper.verifyJson(report);
	}
	@Test
	void testReportEmpty() {
		String value ="";
		SequenceReport report = AlternativeSequenceImpl.createReport(value);
		assertEquals(0, report.getValue().size());
		TestHelper.verifyJson(report);
	}
}
