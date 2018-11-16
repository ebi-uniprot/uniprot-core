package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CCLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.cc.CCLineBuilderFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;

public abstract class CCBuildTestAbstr {
	CCLineBuilder builder = new CCLineBuilder();
	
	<T extends Comment> void doTest(String ftLine, T comment) {
        FFLineBuilder<Comment> builder =CCLineBuilderFactory
                .create(comment);

        FFLine ffLine = builder.buildWithEvidence(comment);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ftLine);
        assertEquals(ftLine, resultString);
    }

	<T extends Comment>  void doTestString(String ccLine, T comment){
	    FFLineBuilder<T> builder =CCLineBuilderFactory
                .create(comment);
        String value = builder.buildString(comment);
        
        System.out.println(value);
        assertEquals(ccLine, value);
    }
	
	<T extends Comment> void doTestStringEv(String ccLine, T comment){
	    FFLineBuilder<Comment> builder =CCLineBuilderFactory
                .create(comment);
        String value = builder.buildStringWithEvidence(comment);
        
        System.out.println(value);
        assertEquals(ccLine, value);
    }
	
	protected void doTest(String ccLine) {
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(ccLine +"\n");
		CcLineConverter converter = new CcLineConverter();
		List<Comment> comments = converter.convert(obj);
		FFLine ffLine = builder.buildWithEvidence(comments);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		System.out.println(ccLine);
		assertEquals(ccLine, resultString);
		System.out.println(builder.buildString(comments));
	
	}
	protected List<Evidence> createEvidence(List<String> evIds) {
		EvidenceFactory evFactory = UniProtFactory.INSTANCE.getEvidenceFactory();
		return evIds.stream().map(val -> evFactory.createFromEvidenceLine(val)).collect(Collectors.toList());

	}
	protected EvidencedValue createEvidencedValue(String val, List<String> evIds){
		return UniProtFactory.INSTANCE.createEvidencedValue(val, createEvidence(evIds));
	}
	
	protected Note buildNote(String note, List<String> evids){
		if(Strings.isNullOrEmpty(note))
			return null;
		List<EvidencedValue> evidencedValues = new ArrayList<>();
		evidencedValues.add(createEvidencedValue(note, evids));
		return CommentFactory.INSTANCE.createNote(evidencedValues);
		
	}
	
	
	protected Note buildNote(List<Map.Entry<String, List<String>>> notes){
		if(!notes.isEmpty()) {
			List<EvidencedValue> evidencedValues =
					notes
					.stream().map(entry ->createEvidencedValue(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			return CommentFactory.INSTANCE.createNote(evidencedValues);
		}
		return null;

	}
}
