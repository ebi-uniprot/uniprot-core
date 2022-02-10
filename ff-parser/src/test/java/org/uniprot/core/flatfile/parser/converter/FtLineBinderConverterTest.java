package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTLigand;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTLigandPart;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
 */

public class FtLineBinderConverterTest {
	private final FtLineConverter converter = new FtLineConverter();

	@Test
	void testNewBindingFull() {

		FtLineObject fobj = new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT();
		ft.setType(FtLineObject.FTType.BINDING);
		ft.setLocationStart("313");
		ft.setLocationEnd("317");
		FTLigand ftligand = new FTLigand();
		ftligand.setName("tRNA(Thr)");
		ftligand.setId("ChEBI:CHEBI:29180");
		ftligand.setLabel("1");
		ftligand.setNote("Some note");
		ft.setLigand(ftligand);
		FTLigandPart ftligandPart = new FTLigandPart();
		ftligandPart.setName("tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue");
		ftligandPart.setId("ChEBI:CHEBI:83071");
		ft.setLigandPart(ftligandPart);

		List<String> evIds = List.of("ECO:0000269|PubMed:10319817");
		fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
		fobj.getFts().add(ft);

		List<UniProtKBFeature> features = converter.convert(fobj);
		assertEquals(1, features.size());
		UniProtKBFeature unFeature = features.get(0);
		UniProtKBFeature feature1 = unFeature;
		assertEquals(UniprotKBFeatureType.BINDING, feature1.getType());

		List<Evidence> eviIds = unFeature.getEvidences();
		assertEquals(1, eviIds.size());
		assertEquals("PubMed", eviIds.get(0).getEvidenceCrossReference().getDatabase().getName());
		assertEquals("ECO:0000269|PubMed:10319817", eviIds.get(0).getValue());

		FtLineConverterTest.validateLocation(feature1.getLocation(), 313, 317, PositionModifier.EXACT,
				PositionModifier.EXACT);
		assertEquals(UniprotKBFeatureType.BINDING, feature1.getType());
		Ligand ligand = feature1.getLigand();
		assertNotNull(ligand);
		assertEquals("tRNA(Thr)", ligand.getName());
		assertEquals("ChEBI:CHEBI:29180", ligand.getId());
		assertEquals("1", ligand.getLabel());
		assertEquals("Some note", ligand.getNote());
		LigandPart ligandPart = feature1.getLigandPart();
		assertNotNull(ligandPart);
		assertEquals("tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue", ligandPart.getName());
		assertEquals("ChEBI:CHEBI:83071", ligandPart.getId());
		assertEquals(null, ligandPart.getLabel());
	}

	@Test
	void testNewBindingNoLigandPart() {

		FtLineObject fobj = new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT();
		ft.setType(FtLineObject.FTType.BINDING);
		ft.setLocationStart("313");
		ft.setLocationEnd("317");
		FTLigand ftligand = new FTLigand();
		ftligand.setName("tRNA(Thr)");
		ftligand.setId("ChEBI:CHEBI:29180");
		ftligand.setLabel("1");
		ft.setLigand(ftligand);

		List<String> evIds = List.of("ECO:0000269|PubMed:10319817");
		fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
		fobj.getFts().add(ft);

		List<UniProtKBFeature> features = converter.convert(fobj);
		assertEquals(1, features.size());
		UniProtKBFeature unFeature = features.get(0);
		UniProtKBFeature feature1 = unFeature;
		assertEquals(UniprotKBFeatureType.BINDING, feature1.getType());

		List<Evidence> eviIds = unFeature.getEvidences();
		assertEquals(1, eviIds.size());
		assertEquals("PubMed", eviIds.get(0).getEvidenceCrossReference().getDatabase().getName());
		assertEquals("ECO:0000269|PubMed:10319817", eviIds.get(0).getValue());

		FtLineConverterTest.validateLocation(feature1.getLocation(), 313, 317, PositionModifier.EXACT,
				PositionModifier.EXACT);
		assertEquals(UniprotKBFeatureType.BINDING, feature1.getType());
		Ligand ligand = feature1.getLigand();
		assertNotNull(ligand);
		assertEquals("tRNA(Thr)", ligand.getName());
		assertEquals("ChEBI:CHEBI:29180", ligand.getId());
		assertEquals("1", ligand.getLabel());
		assertEquals(null, ligand.getNote());
		LigandPart ligandPart = feature1.getLigandPart();
		assertNull(ligandPart);

	}

}
