package org.uniprot.core.flatfile.parser.impl.og;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprotkb.GeneEncodingType;
import org.uniprot.core.uniprotkb.GeneLocation;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.GeneLocationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OgLineConverter extends EvidenceCollector
        implements Converter<OgLineObject, List<GeneLocation>> {
    @Override
    public List<GeneLocation> convert(OgLineObject f) {
        List<GeneLocation> organelles = new ArrayList<>();
        Map<Object, List<Evidence>> evidenceMap =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidenceMap.values());
        for (OgLineObject.OgEnum ogEnum : f.ogs) {
            GeneEncodingType type = GeneEncodingType.UNKNOWN;
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
                    type = GeneEncodingType.APICOPLAST;
                    break;
                case PLASTID_CHLOROPLAST:
                    type = GeneEncodingType.CHLOROPLAST;
                    break;
                case PLASTID_ORGANELLAR_CHROMATOPHORE:
                    type = GeneEncodingType.ORGANELLAR_CHROMATOPHORE;
                    break;
                case PLASTID_CYANELLE:
                    type = GeneEncodingType.CYANELLE;
                    break;
                case PLASTID_NON_PHOTOSYNTHETIC:
                    type = GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID;
                    break;
                case PLASMID:
                    type = GeneEncodingType.PLASMID;
                    break;
                default:
                    type = GeneEncodingType.UNKNOWN;
                    break;
            }
            GeneLocation org =
                    new GeneLocationBuilder()
                            .geneEncodingType(type)
                            .value("")
                            .evidencesSet(evidenceMap.get(ogEnum))
                            .build();

            organelles.add(org);
        }

        for (String val : f.plasmidNames) {
            GeneLocation org =
                    new GeneLocationBuilder()
                            .geneEncodingType(GeneEncodingType.PLASMID)
                            .value(val)
                            .evidencesSet(evidenceMap.get(val))
                            .build();
            organelles.add(org);
        }

        return organelles;
    }
}
