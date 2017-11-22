package uk.ac.ebi.uniprot.domain.feature.Builder;

import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;
import uk.ac.ebi.uniprot.domain.feature.VariantFeature;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.feature.impl.CarbohydFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.ChainFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.ConflictFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.FeatureLocationImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.FeatureWithAlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.FeatureWithFeatureIdImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.MutagenFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.PeptideFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.ProPepFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.SimpleFeaturesImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.VarSeqFeatureImpl;
import uk.ac.ebi.uniprot.domain.feature.impl.VariantFeatureImpl;


import java.util.List;

public enum FeatureFactory {
    INSTANCE;
    public FeatureLocation createFeatureLocation(Integer start, Integer end) {
        return new FeatureLocationImpl(start, end);
    }

    public FeatureLocation createFeatureLocation(Integer start, Integer end,
            FeatureLocationModifier startModifier, FeatureLocationModifier endModifier) {
        return new FeatureLocationImpl(start, end, startModifier, endModifier);
    }

    public FeatureDescription createDescription(String description) {
        return FeatureImpl.createDescription(description);
    }

    public FeatureDescription createDescription(List<String> description) {
        return FeatureImpl.createDescription(description);
    }

    public FeatureId createFeatureId(String fId) {
        return FeatureWithFeatureIdImpl.createFeatureId(fId);
    }

    public FeatureSequence createFeatureSequence(String value) {
        return FeatureWithAlternativeSequenceImpl.createFeatureSequence(value);
    }

    public SequenceReport createSequenceReport(List<String> value) {
        return FeatureWithAlternativeSequenceImpl.createSequenceReport(value);
    }

    public SequenceReport createSequenceReport(String value) {
        return FeatureWithAlternativeSequenceImpl.createSequenceReport(value);
    }

    public <T extends Feature> T buildSimpleFeature(FeatureType featureType,
            FeatureLocation location,
            String description) {
        Feature result = null;
        switch (featureType) {
            case ACT_SITE:
                result = new SimpleFeaturesImpl.ActSiteFeatureImpl(location, description);
                break;
            case BINDING:
                result = new SimpleFeaturesImpl.BindingFeatureImpl(location, description);
                break;
            case CA_BIND:
                result = new SimpleFeaturesImpl.CaBindFeatureImpl(location, description);
                break;
            case COILED:
                result = new SimpleFeaturesImpl.CoiledFeatureImpl(location, description);
                break;
            case COMPBIAS:
                result = new SimpleFeaturesImpl.CompBiasFeatureImpl(location, description);
                break;
            case CROSSLNK:
                result = new SimpleFeaturesImpl.CrosslinkFeatureImpl(location, description);
                break;
            case DISULFID:
                result = new SimpleFeaturesImpl.DisulfidFeatureImpl(location, description);
                break;
            case DNA_BIND:
                result = new SimpleFeaturesImpl.DnaBindFeatureImpl(location, description);
                break;
            case DOMAIN:
                result = new SimpleFeaturesImpl.DomainFeatureImpl(location, description);
                break;
            case HELIX:
                result = new SimpleFeaturesImpl.HelixFeatureImpl(location, description);
                break;
            case INIT_MET:
                result = new SimpleFeaturesImpl.InitMetFeatureImpl(location, description);
                break;
            case INTRAMEM:
                result = new SimpleFeaturesImpl.IntramemFeatureImpl(location, description);
                break;
            case LIPID:
                result = new SimpleFeaturesImpl.LipidFeatureImpl(location, description);
                break;
            case METAL:
                result = new SimpleFeaturesImpl.MetalFeatureImpl(location, description);
                break;
            case MOD_RES:
                result = new SimpleFeaturesImpl.ModResFeatureImpl(location, description);
                break;
            case MOTIF:
                result = new SimpleFeaturesImpl.MotifFeatureImpl(location, description);
                break;
            case NON_CONS:
                result = new SimpleFeaturesImpl.NonConsFeatureImpl(location, description);
                break;
            case NON_STD:
                result = new SimpleFeaturesImpl.NonStandardAAFeatureImpl(location, description);
                break;
            case NON_TER:
                result = new SimpleFeaturesImpl.NonTerFeatureImpl(location, description);
                break;
            case NP_BIND:
                result = new SimpleFeaturesImpl.NpBindFeatureImpl(location, description);
                break;
            case REGION:
                result = new SimpleFeaturesImpl.RegionFeatureImpl(location, description);
                break;
            case REPEAT:
                result = new SimpleFeaturesImpl.RepeatFeatureImpl(location, description);
                break;
            case SIGNAL:
                result = new SimpleFeaturesImpl.SignalFeatureImpl(location, description);
                break;
            case SITE:
                result = new SimpleFeaturesImpl.SiteFeatureImpl(location, description);
                break;
            case STRAND:
                result = new SimpleFeaturesImpl.StrandFeatureImpl(location, description);
                break;
            case TOPO_DOM:
                result = new SimpleFeaturesImpl.TopoDomFeatureImpl(location, description);
                break;
            case TRANSIT:
                result = new SimpleFeaturesImpl.TransitFeatureImpl(location, description);
                break;
            case TRANSMEM:
                result = new SimpleFeaturesImpl.TransmemFeatureImpl(location, description);
                break;
            case TURN:
                result = new SimpleFeaturesImpl.TurnFeatureImpl(location, description);
                break;
            case UNSURE:
                result = new SimpleFeaturesImpl.UnsureFeatureImpl(location, description);
                break;
            case ZN_FING:
                result = new SimpleFeaturesImpl.ZnFingFeatureImpl(location, description);
                break;
            default:
                throw new IllegalArgumentException("Feature Type: " + featureType.getName() + " is not supported.");
        }
        return (T) result;
    }

    public <T extends Feature> T buildSimpleFeatureWithFeatureId(FeatureType featureType,
            FeatureLocation location,
            String description,
            String featureId) {
        Feature result = null;
        switch (featureType) {
            case ACT_SITE:
            case BINDING:
            case CA_BIND:
            case COILED:
            case COMPBIAS:
            case CROSSLNK:
            case DISULFID:
            case DNA_BIND:
            case DOMAIN:
            case HELIX:
            case INIT_MET:
            case INTRAMEM:
            case LIPID:
            case METAL:
            case MOD_RES:
            case MOTIF:
            case NON_CONS:
            case NON_STD:
            case NON_TER:
            case NP_BIND:
            case REGION:
            case REPEAT:
            case SIGNAL:
            case SITE:
            case STRAND:
            case TOPO_DOM:
            case TRANSIT:
            case TRANSMEM:
            case TURN:
            case UNSURE:
            case ZN_FING:
                result = buildSimpleFeature(featureType, location, description);
                break;
            case CHAIN:
                result = new ChainFeatureImpl(location, description, featureId);
                break;
            case PROPEP:
                result = new ProPepFeatureImpl(location, description, featureId);
                break;
            case PEPTIDE:
                result = new PeptideFeatureImpl(location, description, featureId);
                break;
            default:
                throw new IllegalArgumentException("Feature Type: " + featureType.getName() + " is not supported.");
        }
        return (T) result;
    }

    public CarbohydFeature buildCarbohydFeature(FeatureLocation location, String description,
            String featureId, CarbohydLinkType carbohydLinkType,
            String linkedSugar) {
        return new CarbohydFeatureImpl(location, description, featureId, carbohydLinkType, linkedSugar);
    }

    public ConflictFeature buildConflictFeature(FeatureLocation location,
            String orginalSequence, List<String> alternativeSequences,
            List<String> report) {
        return new ConflictFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report);
    }
    
    public ConflictFeature buildConflictFeature(FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences,
            SequenceReport report) {
        return new ConflictFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report);
    }

    public MutagenFeature buildMutagenFeature(FeatureLocation location,
            String orginalSequence, List<String> alternativeSequences,
            List<String> report) {
        return new MutagenFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report);
    }
    
    public MutagenFeature buildMutagenFeature(FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences,
            SequenceReport report) {
        return new MutagenFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report);
    }
    
    public VariantFeature buildVariantFeature(FeatureLocation location,
            String orginalSequence, List<String> alternativeSequences,
            List<String> report, FeatureId featureId) {
        return new VariantFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report, featureId);
    }
    
    public VariantFeature buildVariantFeature(FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences,
            SequenceReport report, FeatureId featureId) {
        return new VariantFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report, featureId);
    }
    
    public VarSeqFeature buildVarSeqFeature(FeatureLocation location,
            String orginalSequence, List<String> alternativeSequences,
            List<String> report, FeatureId featureId) {
        return new VarSeqFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report, featureId);
    }
    
    public VarSeqFeature buildVarSeqFeature(FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences,
            SequenceReport report, FeatureId featureId) {
        return new VarSeqFeatureImpl(location,
                orginalSequence, alternativeSequences,
                report, featureId);
    }
}
