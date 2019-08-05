package uk.ac.ebi.uniprot.flatfile.parser.impl.pe;


import org.uniprot.core.uniprot.ProteinExistence;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

public class PeLineConverter implements Converter<PeLineObject, ProteinExistence> {
    @Override
    public ProteinExistence convert(PeLineObject f) {
        int val = f.level;
        ProteinExistence pe = ProteinExistence.UNKNOWN;
        switch (val) {
            case 1:
                pe = ProteinExistence.PROTEIN_LEVEL;
                break;
            case 2:
                pe = ProteinExistence.TRANSCRIPT_LEVEL;
                break;
            case 3:
                pe = ProteinExistence.HOMOLOGY;
                break;
            case 4:
                pe = ProteinExistence.PREDICTED;
                break;
            case 5:
                pe = ProteinExistence.UNCERTAIN;
                break;
        }
        return pe;
    }
}
