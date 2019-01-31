package uk.ac.ebi.uniprot.parser.impl.og;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneLocation;
import uk.ac.ebi.uniprot.domain.uniprot.builder.GeneLocationBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceConverterHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OgLineConverter extends EvidenceCollector implements Converter<OgLineObject, List<GeneLocation>> {
    @Override
    public List<GeneLocation> convert(OgLineObject f) {
        List<GeneLocation> organelles = new ArrayList<>();
        Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper.convert(f
                                                                                          .getEvidenceInfo());
        this.addAll(evidenceMap.values());
        for (OgLineObject.OgEnum ogEnum : f.ogs) {
            GeneEncodingType type = GeneEncodingType.UNKOWN;
            switch (ogEnum) {
                case HYDROGENOSOME:
                    type = GeneEncodingType.HYDROGENOSOME;
                    break;
                case MITOCHONDRION:
                    type = GeneEncodingType.MITOCHONDRION;
                    break;
                case NUCLEOMORPH:
                    type = GeneEncodingType.NUCLEOMORPH;
                    break;
                case PLASTID:
                    type = GeneEncodingType.PLASTID;
                    break;
                case PLASTID_APICOPLAST:
                    type = GeneEncodingType.APICOPLAST_PLASTID;
                    break;
                case PLASTID_CHLOROPLAST:
                    type = GeneEncodingType.CHLOROPLAST_PLASTID;
                    break;
                case PLASTID_ORGANELLAR_CHROMATOPHORE:
                    type = GeneEncodingType.CHROMATOPHORE_PLASTID;
                    break;
                case PLASTID_CYANELLE:
                    type = GeneEncodingType.CYANELLE_PLASTID;
                    break;
                case PLASTID_NON_PHOTOSYNTHETIC:
                    type = GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID;
                    break;
                case PLASMID:
                    type = GeneEncodingType.PLASMID;
                    break;
                default:
                    type = GeneEncodingType.UNKOWN;
                    break;

            }
            GeneLocation org = new GeneLocationBuilder(type, "", evidenceMap.get(ogEnum)).build();

            organelles.add(org);
        }

        for (String val : f.plasmidNames) {
            GeneLocation org = new GeneLocationBuilder(GeneEncodingType.PLASMID, val, evidenceMap.get(val)).build();
            organelles.add(org);
        }

        return organelles;
    }

}
