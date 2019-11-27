package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class FtLineObject implements HasEvidenceInfo {
    private static final Set<FTType> ALTERNATIVE_SEQUENCE_SET =
            EnumSet.of(FTType.CONFLICT, FTType.MUTAGEN, FTType.VARIANT, FTType.VAR_SEQ);
    public List<FT> fts = new ArrayList<FT>();
    public EvidenceInfo evidenceInfo = new EvidenceInfo();

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class FT {
        public FTType type;

        // can have fuzzy type thus cannot use int.
        public String location_start;
        public String location_end;

        public String ft_text;
        public String ftId;
        public String sequence;
    }

    public static enum FTType {
        INIT_MET,
        SIGNAL,
        PROPEP,
        TRANSIT,
        CHAIN,
        PEPTIDE,
        TOPO_DOM,
        TRANSMEM,
        INTRAMEM,
        DOMAIN,
        REPEAT,
        CA_BIND,
        ZN_FING,
        DNA_BIND,
        NP_BIND,
        REGION,
        COILED,
        MOTIF,
        COMPBIAS,
        ACT_SITE,
        METAL,
        BINDING,
        SITE,
        NON_STD,
        MOD_RES,
        LIPID,
        CARBOHYD,
        DISULFID,
        CROSSLNK,
        VAR_SEQ,
        VARIANT,
        MUTAGEN,
        UNSURE,
        CONFLICT,
        NON_CONS,
        NON_TER,
        HELIX,
        STRAND,
        TURN
    }

    public static boolean hasAltSeq(FTType type) {
        return ALTERNATIVE_SEQUENCE_SET.contains(type);
    }
}
