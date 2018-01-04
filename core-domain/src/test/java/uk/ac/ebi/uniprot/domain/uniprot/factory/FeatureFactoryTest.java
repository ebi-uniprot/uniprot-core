package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.feature.ActSiteFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.ChainFeature;
import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.Features;
import uk.ac.ebi.uniprot.domain.feature.HasAlternativeSequence;
import uk.ac.ebi.uniprot.domain.feature.HasFeatureId;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.feature.TurnFeature;
import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;
import uk.ac.ebi.uniprot.domain.feature.VariantFeature;
import uk.ac.ebi.uniprot.domain.feature.impl.ChainFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.PeptideFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.ProPepFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.ActSiteFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.BindingFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.CaBindFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.CoiledFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.CompBiasFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.CrosslinkFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.DisulfidFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.DnaBindFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.DomainFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.HelixFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.InitMetFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.IntramemFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.LipidFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.MetalFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.ModResFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.MotifFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.NonConsFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.NonStandardAAFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.NonTerFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.NpBindFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.RegionFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.RepeatFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.SignalFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.SiteFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.StrandFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.TopoDomFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.TransitFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.TransmemFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.TurnFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.UnsureFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl.ZnFingFeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FeatureFactoryTest {

    @Test
    public void testCreateFeatureLocationIntegerInteger() {
        int start = 34;
        int end = 56;

        FeatureLocation location = FeatureFactory.INSTANCE.createFeatureLocation(start, end);
        verifyFeatureLocation(location, start, end, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
    }

    @Test
    public void testCreateFeatureLocation2() {
        Integer start = 22;
        Integer end = 43;
        FeatureLocationModifier mStart = FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE;
        FeatureLocationModifier mEnd = FeatureLocationModifier.UNSURE;
        FeatureLocation location = FeatureFactory.INSTANCE.createFeatureLocation(start, end, mStart, mEnd);

        verifyFeatureLocation(location, start, end, mStart, mEnd);

        start = null;
        end = 11;
        mStart = FeatureLocationModifier.UNKOWN;
        mEnd = FeatureLocationModifier.EXACT;
        location = FeatureFactory.INSTANCE.createFeatureLocation(start, end, mStart, mEnd);

        verifyFeatureLocation(location, start, end, mStart, mEnd);

    }

    private void verifyFeatureLocation(FeatureLocation flocation, Integer start, Integer end,
            FeatureLocationModifier mStart, FeatureLocationModifier mEnd) {
        assertEquals(start, flocation.getStart());
        assertEquals(end, flocation.getEnd());
        assertEquals(mStart, flocation.getStartModifier());
        assertEquals(mEnd, flocation.getEndModifier());
    }

    @Test
    public void testCreateDescriptionString() {
        String description = "Some value";
        FeatureDescription fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, Arrays.asList(description));

        description = "";
        fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, Collections.emptyList());

        description = null;
        fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, Collections.emptyList());
    }

    @Test
    public void testCreateDescriptionListOfString() {
        List<String> description = null;
        FeatureDescription fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, Collections.emptyList());

        description = Collections.emptyList();
        fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, Collections.emptyList());

        description = Arrays.asList(new String[]{"desc1", "Desc2", "descr3"});
        fd = FeatureFactory.INSTANCE.createDescription(description);
        verifyFeatureDescription(fd, description);

    }

    private void verifyFeatureDescription(FeatureDescription fd, List<String> description) {
        assertEquals(description, fd.getDescription());
    }

    @Test
    public void testCreateFeatureId() {
        String fId = "VAR_231";
        FeatureId featureId = FeatureFactory.INSTANCE.createFeatureId(fId);
        assertEquals(fId, featureId.getValue());
    }

    @Test
    public void testCreateFeatureSequence() {
        String seq = "ADFA";
        FeatureSequence fseq = FeatureFactory.INSTANCE.createFeatureSequence(seq);
        assertEquals(seq, fseq.getValue());
    }

    @Test
    public void testCreateSequenceReportListOfString() {
        List<String> report = null;
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, Collections.emptyList());
        report = Collections.emptyList();
        sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, Collections.emptyList());

        report = Arrays.asList(new String[]{"report1", "report2", "report0"});
        sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, report);
    }

    @Test
    public void testCreateSequenceReportString() {
        String report = null;
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, Collections.emptyList());
        report = "";
        sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, Collections.emptyList());

        report = "some data";
        sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        verifySequenceReport(sReport, Arrays.asList(report));

    }

    private void verifySequenceReport(SequenceReport sr, List<String> report) {
        assertEquals(report, sr.getReport());
    }

    @Test
    public void testBuildSimpleFeatureActSite() {
        FeatureType featureType = FeatureType.ACT_SITE;
        FeatureLocation location = createFeatureLocation(35, 56);
        String description = "Some data";
        verifySimpleFeature(ActSiteFeatureImpl.class, featureType, location, description);
    }
   
    @Test
    public void testBuildSimpleFeatureBinding() {
        FeatureType featureType = FeatureType.BINDING;
        FeatureLocation location = createFeatureLocation(37, 50);
        String description = "Some data";
        verifySimpleFeature(BindingFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureCaBind() {
        FeatureType featureType = FeatureType.CA_BIND;
        FeatureLocation location = createFeatureLocation(45, 86);
        String description = "Some data";
        verifySimpleFeature(CaBindFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureCoiled() {
        FeatureType featureType = FeatureType.COILED;
        FeatureLocation location = createFeatureLocation(45, 86);
        String description = "Some data";
        verifySimpleFeature(CoiledFeatureImpl.class, featureType, location, description);
    }
    @Test
    public void testBuildSimpleFeatureCompbias() {
        FeatureType featureType = FeatureType.COMPBIAS;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "Some 2 data";
        verifySimpleFeature(CompBiasFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureCrosslnk() {
        FeatureType featureType = FeatureType.CROSSLNK;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "Some 2 data";
        verifySimpleFeature(CrosslinkFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureDisulfid() {
        FeatureType featureType = FeatureType.DISULFID;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "Some 2 data";
        verifySimpleFeature(DisulfidFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureDnaBind() {
        FeatureType featureType = FeatureType.DNA_BIND;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "";
        verifySimpleFeature(DnaBindFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureDomain() {
        FeatureType featureType = FeatureType.DOMAIN;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description";
        verifySimpleFeature(DomainFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureHelix() {
        FeatureType featureType = FeatureType.HELIX;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(HelixFeatureImpl.class, featureType, location, description);
    }
    @Test
    public void testBuildSimpleFeatureInitMet() {
        FeatureType featureType = FeatureType.INIT_MET;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(InitMetFeatureImpl.class, featureType, location, description);
    }
    @Test
    public void testBuildSimpleFeatureIntramem() {
        FeatureType featureType = FeatureType.INTRAMEM;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(IntramemFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureLipid() {
        FeatureType featureType = FeatureType.LIPID;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(LipidFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureMetal() {
        FeatureType featureType = FeatureType.METAL;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(MetalFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureModRes() {
        FeatureType featureType = FeatureType.MOD_RES;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(ModResFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureMotif() {
        FeatureType featureType = FeatureType.MOTIF;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "another description 2";
        verifySimpleFeature(MotifFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureNonCons() {
        FeatureType featureType = FeatureType.NON_CONS;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = null;
        verifySimpleFeature(NonConsFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureNonStd() {
        FeatureType featureType = FeatureType.NON_STD;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value";
        verifySimpleFeature(NonStandardAAFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureNonTer() {
        FeatureType featureType = FeatureType.NON_TER;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = null;
        verifySimpleFeature(NonTerFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureNpBind() {
        FeatureType featureType = FeatureType.NP_BIND;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = null;
        verifySimpleFeature(NpBindFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureRegin() {
        FeatureType featureType = FeatureType.REGION;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(RegionFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureRepeat() {
        FeatureType featureType = FeatureType.REPEAT;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(RepeatFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureSignal() {
        FeatureType featureType = FeatureType.SIGNAL;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(SignalFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureSite() {
        FeatureType featureType = FeatureType.SITE;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(SiteFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureStrand() {
        FeatureType featureType = FeatureType.STRAND;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(StrandFeatureImpl.class, featureType, location, description);
    } 

    @Test
    public void testBuildSimpleFeatureTopoDom() {
        FeatureType featureType = FeatureType.TOPO_DOM;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(TopoDomFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureTransit() {
        FeatureType featureType = FeatureType.TRANSIT;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(TransitFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureTransmem() {
        FeatureType featureType = FeatureType.TRANSMEM;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(TransmemFeatureImpl.class, featureType, location, description);
    }
    
    @Test
    public void testBuildSimpleFeatureTurn() {
        FeatureType featureType = FeatureType.TURN;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(TurnFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureUnsure() {
        FeatureType featureType = FeatureType.UNSURE;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(UnsureFeatureImpl.class, featureType, location, description);
    }

    @Test
    public void testBuildSimpleFeatureZnFing() {
        FeatureType featureType = FeatureType.ZN_FING;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        verifySimpleFeature(ZnFingFeatureImpl.class, featureType, location, description);
    }


    private void verifySimpleFeature(Class<?> clazz, FeatureType featureType, FeatureLocation location,
            String description) {
        Feature feature = FeatureFactory.INSTANCE.buildSimpleFeature(featureType, location, description);
        assertTrue(feature.getClass() == clazz);
        assertEquals(location, feature.getFeatureLocation());
        assertEquals(featureType, feature.getType());
        List<String> desclist = new ArrayList<>();
        if ((description != null) && (!description.isEmpty())) {
            desclist.add(description);
        }
        assertEquals(desclist, feature.getDescription().getDescription());
    }

    private FeatureLocation createFeatureLocation(int start, int end) {
        return FeatureFactory.INSTANCE.createFeatureLocation(start, end);
    }

    @Test
    public void testBuildSimpleFeatureWithFeatureIdChain() {
        FeatureType featureType = FeatureType.CHAIN;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        String featureId = "VAR_23";
        verifySimpleFeatureWithFeatureId(ChainFeatureImpl.class, featureType, location, description, featureId, false);
        
        featureId = "PRO_23";
        verifySimpleFeatureWithFeatureId(ChainFeatureImpl.class, featureType, location, description, featureId, true);
    }
    
    @Test
    public void testBuildSimpleFeatureWithFeatureIdProPep() {
        FeatureType featureType = FeatureType.PROPEP;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        String featureId = "VAR_23";
        verifySimpleFeatureWithFeatureId(ProPepFeatureImpl.class, featureType, location, description, featureId, false);
        
        featureId = "PRO_23";
        verifySimpleFeatureWithFeatureId(ProPepFeatureImpl.class, featureType, location, description, featureId, true);
    }

    @Test
    public void testBuildSimpleFeatureWithFeatureIdPeptide() {
        FeatureType featureType = FeatureType.PEPTIDE;
        FeatureLocation location = createFeatureLocation(65, 86);
        String description = "some value3";
        String featureId = "VAR_23";
        verifySimpleFeatureWithFeatureId(PeptideFeatureImpl.class, featureType, location, description, featureId, false);
        
        featureId = "PRO_23";
        verifySimpleFeatureWithFeatureId(PeptideFeatureImpl.class, featureType, location, description, featureId, true);
    }
    
    
    private void verifySimpleFeatureWithFeatureId(Class<?> clazz, FeatureType featureType, FeatureLocation location,
            String description, String featureId, boolean validId) {
        Feature feature = FeatureFactory.INSTANCE.buildSimpleFeatureWithFeatureId(featureType, location, description, featureId);
        assertTrue(feature.getClass() == clazz);
        assertEquals(location, feature.getFeatureLocation());
        assertEquals(featureType, feature.getType());
        List<String> desclist = new ArrayList<>();
        if ((description != null) && (!description.isEmpty())) {
            desclist.add(description);
        }
        assertEquals(desclist, feature.getDescription().getDescription());
        HasFeatureId hFeatureId =(HasFeatureId) feature;
        assertEquals(featureId, hFeatureId.getFeatureId().getValue());
        assertEquals(validId, hFeatureId.isValidFeatureId());
    }
    
    @Test
    public void testBuildCarbohydFeature() {
        FeatureLocation location = createFeatureLocation(65, 86);
        String description ="some description";
        String featureId = "CAR_23";
        CarbohydLinkType carbohydLinkType =CarbohydLinkType.OXYGEN;
        String linkedSugar ="Some value";
        CarbohydFeature feature = FeatureFactory.INSTANCE.buildCarbohydFeature(location, 
                description, featureId, carbohydLinkType, linkedSugar);
        assertEquals(description, feature.getDescription().getDescription().get(0));
        assertEquals(FeatureType.CARBOHYD, feature.getType());
        assertEquals(carbohydLinkType, feature.getCarbohydLinkType());
        assertEquals(linkedSugar, feature.getLinkedSugar().getValue());
        assertEquals(featureId, feature.getFeatureId().getValue());
    }

    @Test
    public void testBuildConflictFeature1() {
        FeatureLocation location = createFeatureLocation(65, 86);
       String orginalSequence ="RS";
       List<String> alternativeSequences =Arrays.asList(new String[]{"DB", "DA"});
       List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
       ConflictFeature feature =FeatureFactory.INSTANCE.buildConflictFeature(location,
               orginalSequence, alternativeSequences, report);
        assertEquals(location, feature.getFeatureLocation() );
        assertEquals(FeatureType.CONFLICT, feature.getType());
        verifyAlterSequence(feature, orginalSequence, alternativeSequences,report );
    }
    private void verifyAlterSequence(HasAlternativeSequence alSeq,  String orginalSequence, 
            List<String> alternativeSequences, List<String> report){
        assertEquals(orginalSequence, alSeq.getOriginalSequence().getValue());
        List<String> alSeqs =alSeq.getAlternativeSequences().stream().map(val->val.getValue())
                .collect(Collectors.toList());
        assertEquals(alternativeSequences, alSeqs);
        assertEquals(report, alSeq.getReport().getReport());
        
    }
    private void verifyAlterSequence(HasAlternativeSequence alSeq,  FeatureSequence orginalSequence, 
            List<FeatureSequence> alternativeSequences, SequenceReport report){
        assertEquals(orginalSequence, alSeq.getOriginalSequence());
       
        assertEquals(alternativeSequences, alSeq.getAlternativeSequences());
        assertEquals(report, alSeq.getReport());
        
    }
    
    @Test
    public void testBuildConflictFeature2() {
        FeatureLocation location = createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        ConflictFeature feature =FeatureFactory.INSTANCE.buildConflictFeature(location,
                orginalSequence, alternativeSequences, sReport);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.CONFLICT, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,sReport );
    }

    @Test
    public void testBuildMutagenFeature1() {
        FeatureLocation location = createFeatureLocation(65, 86);
        String orginalSequence ="RS";
        List<String> alternativeSequences =Arrays.asList(new String[]{"DB", "DA"});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        MutagenFeature feature =FeatureFactory.INSTANCE.buildMutagenFeature(location,
                orginalSequence, alternativeSequences, report);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.MUTAGEN, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,report );
    }

    @Test
    public void testBuildMutagenFeature2() {
        FeatureLocation location = createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        MutagenFeature feature =FeatureFactory.INSTANCE.buildMutagenFeature(location,
                orginalSequence, alternativeSequences, sReport);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.MUTAGEN, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,sReport );
    }

    @Test
    public void testBuildVariantFeature1() {
        FeatureLocation location = createFeatureLocation(65, 86);
        String orginalSequence ="RS";
        List<String> alternativeSequences =Arrays.asList(new String[]{"DB", "DA"});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VAR_112");
        VariantFeature feature =FeatureFactory.INSTANCE.buildVariantFeature(location,
                orginalSequence, alternativeSequences, report, featureId);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.VARIANT, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,report );
         assertEquals(featureId, feature.getFeatureId());
    }

    @Test
    public void testBuildVariantFeature2() {
        FeatureLocation location = createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VAR_112");
        VariantFeature feature =FeatureFactory.INSTANCE.buildVariantFeature(location,
                orginalSequence, alternativeSequences, sReport, featureId);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.VARIANT, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,sReport );
         assertEquals(featureId, feature.getFeatureId());
    }

    @Test
    public void testBuildVarSeqFeature1() {
        FeatureLocation location = createFeatureLocation(65, 86);
        String orginalSequence ="RS";
        List<String> alternativeSequences =Arrays.asList(new String[]{"DB", "DA"});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VRS_112");
        VarSeqFeature feature =FeatureFactory.INSTANCE.buildVarSeqFeature(location,
                orginalSequence, alternativeSequences, report, featureId);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.VAR_SEQ, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,report );
         assertEquals(featureId, feature.getFeatureId());
    }

    @Test
    public void testBuildVarSeqFeature2() {
        FeatureLocation location = createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VRS_112");
        VarSeqFeature feature =FeatureFactory.INSTANCE.buildVarSeqFeature(location,
                orginalSequence, alternativeSequences, sReport, featureId);
         assertEquals(location, feature.getFeatureLocation() );
         assertEquals(FeatureType.VAR_SEQ, feature.getType());
         verifyAlterSequence(feature, orginalSequence, alternativeSequences,sReport );
         assertEquals(featureId, feature.getFeatureId());
    }
    @Test
    public void testCreateFeatures(){
        List<Feature> features = new ArrayList<>();
        features.add(createVarSeqFeature());
        features.add(FeatureFactory.INSTANCE.buildSimpleFeature(FeatureType.TURN,
                FeatureFactory.INSTANCE.createFeatureLocation(12, 12), 
                "some desc1"));
        features.add(FeatureFactory.INSTANCE.buildSimpleFeature(FeatureType.TURN,
                FeatureFactory.INSTANCE.createFeatureLocation(20, 23), 
                "some desc2"));
        features.add(FeatureFactory.INSTANCE.buildSimpleFeatureWithFeatureId(FeatureType.CHAIN,
                FeatureFactory.INSTANCE.createFeatureLocation(200, 230), 
                "some desc3", "ft_123"));
        Features uniFeatures = FeatureFactory.INSTANCE.createFeatures(features);
        assertEquals(4, uniFeatures.getAllFeatures().size());
        List<TurnFeature> turnFeatures = uniFeatures.getFeatures(FeatureType.TURN);
        assertEquals(2, turnFeatures.size());
        List<ChainFeature> chainFeatures = uniFeatures.getFeatures(FeatureType.CHAIN);
        assertEquals(1, chainFeatures.size());
        
        List<ActSiteFeature> actSiteFeatures = uniFeatures.getFeatures(FeatureType.ACT_SITE);
        assertEquals(0, actSiteFeatures.size());
    }
    private VarSeqFeature createVarSeqFeature(){
        FeatureLocation location = createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VRS_112");
        return FeatureFactory.INSTANCE.buildVarSeqFeature(location,
                orginalSequence, alternativeSequences, sReport, featureId);
    }
}
