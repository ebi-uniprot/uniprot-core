package uk.ac.ebi.uniprot.parser.converter;

import org.junit.Test;


import uk.ac.ebi.uniprot.domain.uniprot.comments.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.AlternativeNameSequenceEnum;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CcLineConverterTest {
	private final CcLineConverter converter = new CcLineConverter();
	@Test
	public void testTextOnly(){
		/*
		 * CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-
        CC       mediated immune responses. It cleaves after Lys or Arg. May be
        CC       involved in apoptosis.
        CC   -!- DOMAIN: The di-lysine motif may confer endoplasmic reticulum
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.FUNCTION;
		CcLineObject.FreeText texts = new CcLineObject.FreeText();
		String val ="This enzyme is necessary for target cell lysis in cell-mediated immune responses."
				+ " It cleaves after Lys or Arg. May be involved in apoptosis.";
		texts.texts.add(new EvidencedString(val, new ArrayList<String>()));
		cc1.object =texts;
		CcLineObject.CC cc2 =new CcLineObject.CC();
		cc2.topic =CcLineObject.CCTopicEnum.DOMAIN;
		CcLineObject.FreeText texts2 = new CcLineObject.FreeText();
		String val2 ="The di-lysine motif may confer endoplasmic reticulum localization (By similarity)";
		texts2.texts.add(new EvidencedString(val2, new ArrayList<String>()));
		cc2.object =texts2;
		ccLineO.ccs.add(cc1);
		ccLineO.ccs.add(cc2);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.FUNCTION, comment1.getCommentType());
		assertTrue (comment1 instanceof FunctionComment);
		
		FunctionComment fcomment = (FunctionComment) comment1;
		assertEquals("This enzyme is necessary for target cell lysis in cell-mediated immune responses."
				+ " It cleaves after Lys or Arg. May be involved in apoptosis.",
				fcomment.getTexts().get(0).getValue());
	//	assertEquals(CommentStatus.EXPERIMENTAL, fcomment.getCommentStatus());
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.DOMAIN, comment2.getCommentType());
		assertTrue (comment2 instanceof DomainComment);
		
		DomainComment dcomment = (DomainComment) comment2;
		assertEquals("The di-lysine motif may confer endoplasmic reticulum localization (By similarity)",
				dcomment.getTexts().get(0).getValue());
	}
	@Test
	public void testWebResource(){
		//CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
        //CC       URL="http://bioinf.uta.fi/CD40Lbase/";
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.WEB_RESOURCE;
		CcLineObject.WebResource wr =new CcLineObject.WebResource();
		wr.name ="CD40Lbase";
		wr.note ="CD40L defect database";
		wr.url ="http://bioinf.uta.fi/CD40Lbase/";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.WEBRESOURCE, comment1.getCommentType());
		assertTrue (comment1 instanceof WebResourceComment);
		
		WebResourceComment wcomment = (WebResourceComment) comment1;
		assertEquals("CD40Lbase", wcomment.getResourceName());
		assertEquals("CD40L defect database", wcomment.getNote().get());
		assertEquals("http://bioinf.uta.fi/CD40Lbase/", wcomment.getResourceUrl().get());
		assertFalse(wcomment.isFtp());
		
	}
	
	@Test
	public void testRNAEditing(){
		//CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164;
        //CC       Note=The initiator methionine is created by RNA editing.
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.RNA_EDITING;
		CcLineObject.RnaEditing rnaEd =new CcLineObject.RnaEditing();
		rnaEd.note.add(new EvidencedString(
				"The initiator methionine is created by RNA editing.", new ArrayList<String>()));
		rnaEd.locations.add(1);
		rnaEd.locations.add(56);
		rnaEd.locations.add(89);
		rnaEd.locations.add(103);
		rnaEd.locations.add(126);
		rnaEd.locations.add(164);
	
		cc1.object =rnaEd;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.RNA_EDITING, comment1.getCommentType());
		assertTrue (comment1 instanceof RnaEditingComment);
		
		RnaEditingComment wcomment = (RnaEditingComment) comment1;
		
		assertEquals("The initiator methionine is created by RNA editing",
				wcomment.getNote().get().getTexts().get(0).getValue());
		assertEquals(RnaEditingLocationType.Known, wcomment.getLocationType());
		assertEquals(6, wcomment.getPositions().size());
		assertEquals("1", wcomment.getPositions().get(0).getPosition());
		assertEquals("126", wcomment.getPositions().get(4).getPosition());
		
	}
	@Test
	public void testMassSpectrometry(){
		//CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-
        //CC       2); Source=PubMed:10531593;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;
		CcLineObject.MassSpectrometry wr =new CcLineObject.MassSpectrometry();
		wr.mass =13822;
		wr.method ="MALDI";
		CcLineObject.MassSpectrometryRange mrange = new CcLineObject.MassSpectrometryRange ();
		mrange.start =19;
		mrange.end = 140;
		wr.ranges.add(mrange);
        mrange.range_isoform ="P15522-2";
		wr.sources.add("ECO:0000269|PubMed:15208022");
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.MASS_SPECTROMETRY, comment1.getCommentType());
		assertTrue (comment1 instanceof MassSpectrometryComment);
		
		MassSpectrometryComment wcomment = (MassSpectrometryComment) comment1;
		 
		assertEquals(13822.0,wcomment.getMolWeight(),0.0001);
		assertTrue( wcomment.getMolWeightError().isPresent());
		assertEquals(0,wcomment.getMolWeightError().get(),0.0001);
	//	assertEquals(null, wcomment.getNote());
		List<MassSpectrometryRange> ranges=wcomment.getRanges();
		assertEquals(1, ranges.size());
		MassSpectrometryRange range = ranges.get(0);
		assertEquals("P15522-2", range.getIsoformId());
		assertEquals(19, range.getStart().intValue());
		assertEquals(140, range.getEnd().intValue());
		List<Evidence> sources = wcomment.getEvidences();
		assertEquals(1, sources.size());
		Evidence source = sources.get(0);
		assertEquals("ECO:0000269|PubMed:15208022", source.getValue());
		
	}
	

	@Test
	public void testSequenceCaution(){
		//CC   -!- SEQUENCE CAUTION:
        //CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
       //CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;

		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;
		CcLineObject.SequenceCaution sc =new CcLineObject.SequenceCaution();
		CcLineObject.SequenceCautionObject sco1 =new CcLineObject.SequenceCautionObject();
		sco1.sequence ="CAI12537.1";
		sco1.type = CcLineObject.SequenceCautionType.Erroneous_gene_model_prediction;
		sc.sequenceCautionObjects.add(sco1);
		
		CcLineObject.SequenceCautionObject sco2 =new CcLineObject.SequenceCautionObject();
		sco2.sequence ="CAI39742.1";
		sco2.type = CcLineObject.SequenceCautionType.Erroneous_gene_model_prediction;
		sco2.positions.add(388);
		sco2.positions.add(399);
		sc.sequenceCautionObjects.add(sco2);
		
		cc1.object =sc;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment1.getCommentType());
		assertTrue (comment1 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment = (SequenceCautionComment) comment1;
		
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment2.getCommentType());
		assertTrue (comment2 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment2 = (SequenceCautionComment) comment2;
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment.getSequenceCautionType());
		assertEquals(0, wcomment.getPositions().size());
		assertEquals("CAI12537.1", wcomment.getSequence());
		
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment2.getSequenceCautionType());
		assertEquals(2, wcomment2.getPositions().size());
		assertEquals("CAI39742.1", wcomment2.getSequence());
		assertEquals("388", wcomment2.getPositions().get(0) );
		assertEquals("399", wcomment2.getPositions().get(1) );
		
		
	}
	@Test
	public void testBioPhyChem(){
		//CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
		//CC       Absorption:\n" +
        //CC         Abs(max)=3 nm;\n" +
        //CC         Note=foo bar foo bar\n" 
        //CC       Kinetic parameters:
        //CC         KM=71 uM for ATP;
        //CC         KM=98 uM for ADP;
        //CC         KM=1.5 mM for acetate;
        //CC         KM=0.47 mM for acetyl phosphate;
        //CC       Temperature dependence:
        //CC         Optimum temperature is 65 degrees Celsius. Protected from
        //CC         thermal inactivation by ATP;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
		CcLineObject.BiophysicochemicalProperties wr =
				new CcLineObject.BiophysicochemicalProperties();
		wr.kms.add(new EvidencedString("71 uM for ATP", new ArrayList<String>()));
		wr.kms.add(new EvidencedString("98 uM for ADP", new ArrayList<String>()));
		wr.kms.add(new EvidencedString("1.5 mM for acetate", new ArrayList<String>()));
		wr.kms.add(new EvidencedString("0.47 mM for acetyl phosphate", new ArrayList<String>()));
		String temDe =	"Optimum temperature is 65 degrees Celsius. Protected from " +
			      "thermal inactivation by ATP";
		wr.temperature_dependence.add(new EvidencedString(temDe, new ArrayList<String>()));
			
		wr.bsorption_abs = new EvidencedString ("3 nm", new ArrayList<String>());
		wr.bsorption_note.add(new EvidencedString("foo bar foo bar", new ArrayList<String>()));
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
		assertTrue (comment1 instanceof BioPhysicoChemicalPropertiesComment);
		
		BioPhysicoChemicalPropertiesComment wcomment 
		= (BioPhysicoChemicalPropertiesComment) comment1;
		assertTrue( wcomment.getAbsorption().isPresent());
		assertFalse(wcomment.getPHDependence().isPresent());
		assertFalse(wcomment.getRedoxPotential().isPresent());
		assertTrue(wcomment.getTemperatureDependence().isPresent());
		KineticParameters kp =wcomment.getKineticParameters().get();
		assertEquals(0, kp.getMaximumVelocities().size());
		assertEquals(4, kp.getMichaelisConstants().size());
		List<MichaelisConstant>  mcs =kp.getMichaelisConstants();
		MichaelisConstant mc1 = mcs.get(0);
		assertEquals(71.0, mc1.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
		assertEquals("ATP", mc1.getSubstrate());
		
		MichaelisConstant mc3 = mcs.get(2);
		assertEquals(1.5, mc3.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MILLI_MOL, mc3.getUnit());
		assertEquals("acetate", mc3.getSubstrate());
		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
				wcomment.getTemperatureDependence().get().getTexts().get(0).getValue());
		Absorption ab =wcomment.getAbsorption().get();
		assertEquals(3, ab.getMax());
		assertEquals("foo bar foo bar", ab.getNote().get().getTexts().get(0).getValue());
	}
	
	@Test
	public void testBioPhyChem2(){
		//CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
        //CC       Kinetic parameters:
        //CC         KM=71 uM for ATP;
        //CC         KM=98 uM for ADP;
		//CC       pH dependence:
        //CC         Optimum pH is 7.75;
        //CC       Temperature dependence:
        //CC         Optimum temperature is 65 degrees Celsius. Protected from
        //CC         thermal inactivation by ATP;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
		CcLineObject.BiophysicochemicalProperties wr =
				new CcLineObject.BiophysicochemicalProperties();
		wr.kms.add(new EvidencedString("71 uM for ATP", new ArrayList<String>()));
		wr.kms.add(new EvidencedString("98 uM for ADP", new ArrayList<String>()));
		wr.ph_dependence.add(new EvidencedString("Optimum pH is 7.75", new ArrayList<String>()));
		String tempDe ="Optimum temperature is 65 degrees Celsius. Protected from " +
			      "thermal inactivation by ATP";
		wr.temperature_dependence.add(new EvidencedString(tempDe, new ArrayList<String>()));
				
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
		assertTrue (comment1 instanceof BioPhysicoChemicalPropertiesComment);
		
		BioPhysicoChemicalPropertiesComment wcomment 
		= (BioPhysicoChemicalPropertiesComment) comment1;
		assertEquals(false, wcomment.getAbsorption().isPresent());
		assertTrue(wcomment.getPHDependence().isPresent());
		assertFalse(wcomment.getRedoxPotential().isPresent());
		assertTrue(wcomment.getTemperatureDependence().isPresent());
		KineticParameters kp =wcomment.getKineticParameters().get();
		assertEquals(0, kp.getMaximumVelocities().size());
		assertEquals(2, kp.getMichaelisConstants().size());
		List<MichaelisConstant>  mcs =kp.getMichaelisConstants();
		MichaelisConstant mc1 = mcs.get(0);
		assertEquals(71.0, mc1.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
		assertEquals("ATP", mc1.getSubstrate());
		
		MichaelisConstant mc3 = mcs.get(1);
		assertEquals(98, mc3.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc3.getUnit());
		assertEquals("ADP", mc3.getSubstrate());
		assertEquals("Optimum pH is 7.75", wcomment.getPHDependence().get().getTexts().get(0).getValue());
		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
				wcomment.getTemperatureDependence().get().getTexts().get(0).getValue());
	}
	
	@Test
	public void testAlternatProduct(){
		//CC   -!- ALTERNATIVE PRODUCTS:
        //CC       Event=Alternative splicing; Named isoforms=3;
        //CC         Comment=Additional isoforms seem to exist. Experimental
        //CC         confirmation may be lacking for some isoforms;
        //CC       Name=1; Synonyms=AIRE-1;
        //CC         IsoId=O43918-1; Sequence=Displayed;
		//CC       Name=2; Synonyms=AIRE-2;
        //CC         IsoId=O43918-2; Sequence=VSP_004089;
		 //CC         Note=Major isoform found in 66-78% of cDNA clones
        //CC       Name=3; Synonyms=AIRE-3;
       //CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
		CcLineObject.AlternativeProducts wr =
				new CcLineObject.AlternativeProducts();
		 String commentStr ="Additional isoforms seem to exist. "
			+ "Experimental confirmation may be lacking for some isoforms";
		wr.comment.add(new EvidencedString(commentStr, new ArrayList<String>()));
		wr.namedIsoforms ="3";
		wr.events.add("Alternative splicing");
		CcLineObject.AlternativeProductName alName1 = new 
				CcLineObject.AlternativeProductName();
		alName1.name =new EvidencedString("1", new ArrayList<String>());
		alName1.synNames.add(new EvidencedString("AIRE-1", new ArrayList<String>()));
		alName1.isoId.add("O43918-1");
		alName1.sequence_enum = AlternativeNameSequenceEnum.Displayed;
		
		CcLineObject.AlternativeProductName alName2 = new 
				CcLineObject.AlternativeProductName();
		alName2.name =new EvidencedString("2", new ArrayList<String>());
		alName2.synNames.add(new EvidencedString("AIRE-2", new ArrayList<String>()));
		alName2.isoId.add("O43918-2");
		String alNameNote ="Major isoform found in 66-78% of cDNA clones";
		alName2.note.add(new EvidencedString(alNameNote, new ArrayList<String>()));
		alName2.sequence_FTId.add("VSP_004089");
		
		CcLineObject.AlternativeProductName alName3 = new 
				CcLineObject.AlternativeProductName();
		alName3.name =new EvidencedString("3", new ArrayList<String>());
		alName3.synNames.add(new EvidencedString("AIRE-3", new ArrayList<String>()));
		alName3.isoId.add("O43918-3");
		alName3.sequence_FTId.add("VSP_004089");
		alName3.sequence_FTId.add("VSP_004090");
		
		wr.names.add(alName1);
		wr.names.add(alName2);
		wr.names.add(alName3);
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment1.getCommentType());
		assertTrue (comment1 instanceof AlternativeProductsComment);
		
		AlternativeProductsComment wcomment 
		= (AlternativeProductsComment) comment1;
		List<APEvent> events =wcomment.getEvents();
		List<APIsoform> isoforms =wcomment.getIsoforms();
		assertEquals(1, events.size());
		APEvent event = events.get(0);
		assertEquals("Alternative splicing", event.getValue());
		assertEquals("Additional isoforms seem to exist. "
				+ "Experimental confirmation may be lacking for some isoforms", 
				wcomment.getNote().get().getTexts().get(0).getValue());
		assertEquals(3, isoforms.size());
		APIsoform isoform1 =isoforms.get(0);
		APIsoform isoform2 =isoforms.get(1);
		APIsoform isoform3 =isoforms.get(2);
		assertEquals("1", isoform1.getName().getValue());
		List<IsoformSynonym> syns1 =isoform1.getSynonyms();
		assertEquals(1, syns1.size());
		assertEquals(0, isoform1.getSequenceIds().size());
		assertEquals("AIRE-1", syns1.get(0).getValue());
		assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
		assertFalse(isoform1.getNote().isPresent());
		List<IsoformId> ids1 = isoform1.getIds();
		assertEquals(1, ids1.size());
		assertEquals("O43918-1", ids1.get(0).getValue());
		
		assertEquals("2", isoform2.getName().getValue());
		List<IsoformSynonym> syns2 =isoform2.getSynonyms();
		assertEquals(1, syns2.size());
		assertEquals("AIRE-2", syns2.get(0).getValue());
		List<String> seqIds2 =isoform2.getSequenceIds();
		assertEquals(1, seqIds2.size());
		assertEquals("VSP_004089", seqIds2.get(0));
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertTrue(isoform2.getNote().isPresent());
		assertEquals("Major isoform found in 66-78% of cDNA clones", 
				isoform2.getNote().get().getTexts().get(0).getValue());
		List<IsoformId> ids2 = isoform2.getIds();
		assertEquals(1, ids2.size());
		assertEquals("O43918-2", ids2.get(0).getValue());
		
		assertEquals("3", isoform3.getName().getValue());
		List<IsoformSynonym> syns3 =isoform3.getSynonyms();
		assertEquals(1, syns3.size());
		assertEquals("AIRE-3", syns3.get(0).getValue());
		List<String> seqIds3 =isoform3.getSequenceIds();
		assertEquals(2, seqIds3.size());
		assertEquals("VSP_004089", seqIds3.get(0));
		assertEquals("VSP_004090", seqIds3.get(1));
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertFalse(isoform3.getNote().isPresent());
		List<IsoformId> ids3 = isoform3.getIds();
		assertEquals(1, ids3.size());
		assertEquals("O43918-3", ids3.get(0).getValue());
	}
	@Test
	public void testInteraction(){
		/*
		 CC   -!- INTERACTION:
         CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
         CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
         CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.INTERACTION;
		
		CcLineObject.Interaction ia = new CcLineObject.Interaction();
		cc1.object =ia;
		CcLineObject.InteractionObject iao1 = new CcLineObject.InteractionObject();
		iao1.spAc = "Q9W1K5-1";
		iao1.gene = "CG11299";
		iao1.nbexp = 1;
		iao1.firstId = "EBI-133844";
		iao1.secondId ="EBI-212772";
		
		CcLineObject.InteractionObject iao2 = new CcLineObject.InteractionObject();
		iao2.isSelf =true;
		
		iao2.nbexp = 1;
		iao2.firstId = "EBI-123485";
		iao2.secondId ="EBI-123484";
		
		CcLineObject.InteractionObject iao3 = new CcLineObject.InteractionObject();
		iao3.spAc = "Q8C1S0";
		iao3.gene = "CG112992";
		iao3.nbexp = 1;
		iao3.firstId = "EBI-133844";
		iao3.secondId ="EBI-212775";
		iao3.xeno =true;
		
		ia.interactions.add(iao1);
		ia.interactions.add(iao2);
		ia.interactions.add(iao3);
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		Comment comment1 = comments.get(0);
		assertEquals(CommentType.INTERACTION, comment1.getCommentType());
		assertTrue (comment1 instanceof InteractionComment);
		InteractionComment icomment = (InteractionComment) comment1;
		List<Interaction> interactions =icomment.getInteractions();
		assertEquals(3, interactions.size());
		Interaction inter1 = interactions.get(0);
		Interaction inter2 = interactions.get(1);
		Interaction inter3 = interactions.get(2);
		assertEquals("EBI-133844", inter1.getFirstInteractor().getValue());
		assertEquals("EBI-212772", inter1.getSecondInteractor().getValue());
		assertEquals("Q9W1K5-1", inter1.getInteractorUniProtAccession().getValue());
		assertEquals("CG11299",inter1.getInteractionGeneName());
		assertEquals(1,inter1.getNumberOfExperiments());
		assertEquals(InteractionType.BINARY ,inter1.getInteractionType());
		assertEquals("EBI-123485", inter2.getFirstInteractor().getValue());
		assertEquals("EBI-123484", inter2.getSecondInteractor().getValue());
		assertEquals(InteractionType.SELF ,inter2.getInteractionType());
		assertEquals(1,inter1.getNumberOfExperiments());
		
		assertEquals("EBI-133844", inter3.getFirstInteractor().getValue());
		assertEquals("EBI-212775", inter3.getSecondInteractor().getValue());
		assertEquals("Q8C1S0", inter3.getInteractorUniProtAccession().getValue());
		assertEquals("CG112992",inter3.getInteractionGeneName());
		assertEquals(1,inter3.getNumberOfExperiments());
		assertEquals(InteractionType.XENO ,inter3.getInteractionType());
		
	}
	@Test
	public void testDisease() {
		/*
		 * Kleefstra syndrome (KLESTS) [MIM:610253]: A syndrome characterized by 
		 * severe mental retardation, hypotonia, brachy(micro)cephaly, and facial 
		 * dysmorphisms. Additionally, congenital heart defects, urogenital defects, 
		 * epilepsy and behavioral problems are frequently observed. Note=The disease 
		 * is caused by mutations affecting the gene represented in this entry 
		 * (PubMed:16826528). The syndrome can be either caused by intragenic EHMT1 
		 * mutations leading to haploinsufficiency of the EHMT1 gene or by a submicroscopic 
		 * 9q34.3 deletion. Although it is not known if and to what extent other genes in 
		 * the 9q34.3 region contribute to the syndrome observed in deletion cases, EHMT1 seems to be the major 
		 * determinant of the core disease phenotype (PubMed:19264732)
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.DISEASE;
		CcLineObject.Disease disease = new CcLineObject.Disease ();
		cc1.object =disease;
		disease.name ="Kleefstra syndrome";
		disease.abbr ="KLESTS";
		disease.mim ="610253";
	
		disease.description ="A syndrome characterized by severe mental retardation, hypotonia, brachy(micro)cephaly, and facial dysmorphisms. Additionally, congenital heart defects, urogenital defects, epilepsy and behavioral problems are frequently observed.";
		String disNote ="The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)";
		disease.note.add(new EvidencedString(disNote, new ArrayList<String>()));
	
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.DISEASE, comment1.getCommentType());
		assertTrue (comment1 instanceof DiseaseComment);
		
		DiseaseComment diseaseComment = (DiseaseComment) comment1;
		assertTrue(diseaseComment.hasDefinedDisease());
		assertEquals("Kleefstra syndrome", diseaseComment.getDisease().getDiseaseId().getValue());
		assertEquals("KLESTS", diseaseComment.getDisease().getAcronym());
		assertEquals(disease.description,
				diseaseComment.getDisease().getDescription().getValue());
		DiseaseReference diseaseRef = diseaseComment.getDisease().getReference();
		assertNotNull(diseaseRef);
		assertEquals(DiseaseReferenceType.MIM, diseaseRef.getDiseaseReferenceType());
		assertEquals("610253", diseaseRef.getDiseaseReferenceId());
		assertEquals("The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)", 
				diseaseComment.getNote().get().getTexts().get(0).getValue());
	
	}
	
	@Test
	public void testDisease2() {
		//Note=Frequently mutated in a variety of human cancers (PubMed:15155950)
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.DISEASE;
		CcLineObject.Disease disease = new CcLineObject.Disease ();
		cc1.object =disease;
		String disNote ="Frequently mutated in a variety of human cancers (PubMed:15155950).";

		disease.note.add(new EvidencedString(disNote, new ArrayList<String>()));
		
		
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.DISEASE, comment1.getCommentType());
		assertTrue (comment1 instanceof DiseaseComment);
		
		DiseaseComment diseaseComment = (DiseaseComment) comment1;
		assertFalse(diseaseComment.hasDefinedDisease());
	//	assertEquals(null, diseaseComment.getDisease().getDiseaseId());
//		assertEquals("", diseaseComment.getDisease().getAcronym());
//		assertEquals("",
//				diseaseComment.getDisease().getDescription().getValue());
//		DiseaseReference diseaseRef = diseaseComment.getDisease().getReference();
//		assertNotNull(diseaseRef);
//		assertEquals(DiseaseReferenceType.NONE, diseaseRef.getDiseaseReferenceType());
		assertEquals("Frequently mutated in a variety of human cancers (PubMed:15155950).", 
				diseaseComment.getNote().get().getTexts().get(0).getValue());
	
	}
}
