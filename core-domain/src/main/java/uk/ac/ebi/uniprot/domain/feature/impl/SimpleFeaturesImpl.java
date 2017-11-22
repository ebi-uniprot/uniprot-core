package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.ActSiteFeature;
import uk.ac.ebi.uniprot.domain.feature.BindingFeature;
import uk.ac.ebi.uniprot.domain.feature.CaBindFeature;
import uk.ac.ebi.uniprot.domain.feature.CoiledFeature;
import uk.ac.ebi.uniprot.domain.feature.CompBiasFeature;
import uk.ac.ebi.uniprot.domain.feature.CrosslinkFeature;
import uk.ac.ebi.uniprot.domain.feature.DisulfidFeature;
import uk.ac.ebi.uniprot.domain.feature.DnaBindFeature;
import uk.ac.ebi.uniprot.domain.feature.DomainFeature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.HelixFeature;
import uk.ac.ebi.uniprot.domain.feature.InitMetFeature;
import uk.ac.ebi.uniprot.domain.feature.IntramemFeature;
import uk.ac.ebi.uniprot.domain.feature.LipidFeature;
import uk.ac.ebi.uniprot.domain.feature.MetalFeature;
import uk.ac.ebi.uniprot.domain.feature.ModResFeature;
import uk.ac.ebi.uniprot.domain.feature.MotifFeature;
import uk.ac.ebi.uniprot.domain.feature.NonConsFeature;
import uk.ac.ebi.uniprot.domain.feature.NonStandardAAFeature;
import uk.ac.ebi.uniprot.domain.feature.NonTerFeature;
import uk.ac.ebi.uniprot.domain.feature.NpBindFeature;
import uk.ac.ebi.uniprot.domain.feature.RegionFeature;
import uk.ac.ebi.uniprot.domain.feature.RepeatFeature;
import uk.ac.ebi.uniprot.domain.feature.SignalFeature;
import uk.ac.ebi.uniprot.domain.feature.SiteFeature;
import uk.ac.ebi.uniprot.domain.feature.StrandFeature;
import uk.ac.ebi.uniprot.domain.feature.TopoDomFeature;
import uk.ac.ebi.uniprot.domain.feature.TransitFeature;
import uk.ac.ebi.uniprot.domain.feature.TransmemFeature;
import uk.ac.ebi.uniprot.domain.feature.TurnFeature;
import uk.ac.ebi.uniprot.domain.feature.UnsureFeature;
import uk.ac.ebi.uniprot.domain.feature.ZnFingFeature;

public class SimpleFeaturesImpl {
   static public class ActSiteFeatureImpl extends FeatureImpl implements ActSiteFeature {
        public ActSiteFeatureImpl(FeatureLocation location, String description) {
            super(FeatureType.ACT_SITE, location, description);
        }    
    }
  static public class BindingFeatureImpl extends FeatureImpl implements BindingFeature {
       public BindingFeatureImpl(FeatureLocation location, String description) {
           super(FeatureType.BINDING, location, description);
       }
       
   }
  static public class CaBindFeatureImpl extends FeatureImpl implements CaBindFeature {
      public CaBindFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.CA_BIND, location, description);
      }
  }
  static public class CoiledFeatureImpl extends FeatureImpl implements CoiledFeature {
      public CoiledFeatureImpl( FeatureLocation location, String description) {
          super(FeatureType.COILED, location,description);
      }
  }
  static public class CompBiasFeatureImpl extends FeatureImpl implements CompBiasFeature {
      public CompBiasFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.COMPBIAS, location, description);
      }
  }
  static public class CrosslinkFeatureImpl extends FeatureImpl implements CrosslinkFeature {
      public CrosslinkFeatureImpl( FeatureLocation location, String description) {
          super(FeatureType.CROSSLNK, location, description);
      }
  }
  static public class DisulfidFeatureImpl extends FeatureImpl implements DisulfidFeature {
      public DisulfidFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.DISULFID, location, description);
      }    
  }
  static public class DnaBindFeatureImpl extends FeatureImpl implements DnaBindFeature {
      public DnaBindFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.DNA_BIND, location, description);
      }
  }
  static public class DomainFeatureImpl extends FeatureImpl implements DomainFeature {
      public DomainFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.DOMAIN, location, description);
      }
  }
  static public class HelixFeatureImpl extends FeatureImpl implements HelixFeature {
      public HelixFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.HELIX, location, description);
      }    
  }
  static public class InitMetFeatureImpl extends FeatureImpl implements InitMetFeature {
      public InitMetFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.INIT_MET, location, description);
      }    
  }

  static public class IntramemFeatureImpl extends FeatureImpl implements IntramemFeature {
      public IntramemFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.INTRAMEM, location, description);
      }    
  }
  static public class LipidFeatureImpl extends FeatureImpl implements LipidFeature {
      public LipidFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.LIPID, location, description);
      }    
  }
  static public class MetalFeatureImpl extends FeatureImpl implements MetalFeature {
      public MetalFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.METAL, location, description);
      }    
  }
  static public class ModResFeatureImpl extends FeatureImpl implements ModResFeature {
      public ModResFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.MOD_RES, location, description);
      }    
  }
  static public class MotifFeatureImpl extends FeatureImpl implements MotifFeature {
      public MotifFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.MOTIF, location, description);
      }    
  }
  static public class NonConsFeatureImpl extends FeatureImpl implements NonConsFeature {
      public NonConsFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.NON_CONS, location, description);
      }    
  }
  static public class NonStandardAAFeatureImpl extends FeatureImpl implements NonStandardAAFeature {
      public NonStandardAAFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.NON_STD, location, description);
      }   
  }
  static public class NonTerFeatureImpl extends FeatureImpl implements NonTerFeature {
      public NonTerFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.NON_TER, location, description);
      }    
  }
  static public class NpBindFeatureImpl extends FeatureImpl implements NpBindFeature {
      public NpBindFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.NP_BIND, location, description);
      }    
  }

  static public class RegionFeatureImpl extends FeatureImpl implements RegionFeature {
    public RegionFeatureImpl(FeatureLocation location, String description) {
        super(FeatureType.REGION, location, description);
    }  
  }
  static public class RepeatFeatureImpl extends FeatureImpl implements RepeatFeature {
      public RepeatFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.REPEAT, location, description);
      } 
  }
  static public class SignalFeatureImpl extends FeatureImpl implements SignalFeature {
      public SignalFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.SIGNAL, location, description);
      } 
  }
  static public class SiteFeatureImpl extends FeatureImpl implements SiteFeature {
      public SiteFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.SITE, location, description);
      } 
  }
  static public class StrandFeatureImpl extends FeatureImpl implements StrandFeature {
      public StrandFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.STRAND, location, description);
      }
  }
  static public class TopoDomFeatureImpl extends FeatureImpl implements TopoDomFeature {
      public TopoDomFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.TOPO_DOM, location, description);
      }
  }
  static public class TransitFeatureImpl extends FeatureImpl implements TransitFeature {
      public TransitFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.TRANSIT, location, description);
      }
  }
  static public class TransmemFeatureImpl extends FeatureImpl implements TransmemFeature {
      public TransmemFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.TRANSMEM, location, description);
      }
  }
  static public class TurnFeatureImpl extends FeatureImpl implements TurnFeature {
      public TurnFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.TURN, location, description);
      }
  }
  static public class UnsureFeatureImpl extends FeatureImpl implements UnsureFeature {
      public UnsureFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.UNSURE, location, description);
      }
  }
  static public class ZnFingFeatureImpl extends FeatureImpl implements ZnFingFeature {
      public ZnFingFeatureImpl(FeatureLocation location, String description) {
          super(FeatureType.ZN_FING, location, description);
      }
  }

}
