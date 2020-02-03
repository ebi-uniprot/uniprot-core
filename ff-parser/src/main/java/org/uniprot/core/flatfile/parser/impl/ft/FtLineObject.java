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
        private FTType type;

        // can have fuzzy type thus cannot use int.
        private String location_start;
        private String location_end;

        private String ft_text;
        private String ftId;
        private String sequence;

        public FTType getType() {
            return type;
        }

        public void setType(FTType type) {
            this.type = type;
        }

        public String getLocation_start() {
            return location_start;
        }

        public void setLocation_start(String location_start) {
            this.location_start = location_start;
        }

        public String getLocation_end() {
            return location_end;
        }

        public void setLocation_end(String location_end) {
            this.location_end = location_end;
        }

        public String getFt_text() {
            return ft_text;
        }

        public void setFt_text(String ft_text) {
            this.ft_text = ft_text;
        }

        public String getFtId() {
            return ftId;
        }

        public void setFtId(String ftId) {
            this.ftId = ftId;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }
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
