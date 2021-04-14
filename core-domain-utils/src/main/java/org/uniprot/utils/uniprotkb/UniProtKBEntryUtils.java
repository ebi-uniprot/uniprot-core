package org.uniprot.utils.uniprotkb;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

/**
 * This class provides utility methods for retrieving information from a
 * UniProtKB entry.
 *
 * <p>
 * Created 30/11/2020
 *
 * @author Edd
 */
public class UniProtKBEntryUtils {

	private static final String COLON = ":";
	private static final String GO = "GO";

	/**
	 * Retrieves a list of {@link GoTerm} objects from a {@link UniProtKBEntry}
	 * instance.
	 *
	 * @param entry the entry
	 * @return the list of GO terms extracted from the supplied entry
	 */
	public static List<Go> getGOTerms(UniProtKBEntry entry) {
		List<UniProtKBCrossReference> goXRefs = entry.getUniProtCrossReferencesByType(GO);
		return goXRefs.stream().map(UniProtKBEntryUtils::toGo).filter(val -> val != null).collect(Collectors.toList());
	}

	public static Go toGo(UniProtKBCrossReference xref) {
		UniProtKBDatabase db = xref.getDatabase();
		if (!db.getName().equals(GO))
			return null;

		String id = xref.getId();
		List<Property> properties = xref.getProperties();
		String description = properties.get(0).getValue();
		String evidence = properties.get(1).getValue();
		String[] aspectName = description.split(COLON);
		String[] evidenceToken = evidence.split(COLON);
		GoAspect aspect = GoAspect.typeOf(aspectName[0]);
		String name = aspectName[1];
		GoEvidenceType goEvidenceType = GoEvidenceType.typeOf(evidenceToken[0]);
		String goEvidenceSource = evidenceToken[1];
		GoBuilder builder = new GoBuilder();
		return builder.id(id).name(name).aspect(aspect).goEvidenceType(goEvidenceType)
				.goEvidenceSource(goEvidenceSource).build();
	}

	public static UniProtKBCrossReference buildXrefFromGoTerm(Go goTerm) {
		UniProtKBDatabase type = new UniProtKBDatabaseImpl(GO);
		String term = goTerm.getAspect().getCode() + COLON + goTerm.getName();
		String evidence = goTerm.getGoEvidenceType().name() + COLON + goTerm.getGoEvidenceSource();
		return new UniProtCrossReferenceBuilder().database(type).id(goTerm.getId())
				.propertiesAdd(type.getUniProtDatabaseAttribute(0), term)
				.propertiesAdd(type.getUniProtDatabaseAttribute(1), evidence)
				.build();
	}

}
