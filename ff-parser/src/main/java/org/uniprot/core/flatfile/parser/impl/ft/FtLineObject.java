package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class FtLineObject implements HasEvidenceInfo {
    private static final Set<FTType> ALTERNATIVE_SEQUENCE_SET =
            EnumSet.of(FTType.CONFLICT, FTType.MUTAGEN, FTType.VARIANT, FTType.VAR_SEQ);
    private List<FT> fts = new ArrayList<>();
    private EvidenceInfo evidenceInfo = new EvidenceInfo();

    public List<FT> getFts() {
        return fts;
    }

    public void setFts(List<FT> fts) {
        this.fts = fts;
    }

    public void setEvidenceInfo(EvidenceInfo evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class FT {
        private FTType type;

        // can have fuzzy type thus cannot use int.
        private String locationStart;
        private String locationEnd;

        private String ftText;
        private String ftId;
        private String sequence;

        public FTType getType() {
            return type;
        }

        public void setType(FTType type) {
            this.type = type;
        }

        public String getLocationStart() {
            return locationStart;
        }

        public void setLocationStart(String locationStart) {
            this.locationStart = locationStart;
        }

        public String getLocationEnd() {
            return locationEnd;
        }

        public void setLocationEnd(String locationEnd) {
            this.locationEnd = locationEnd;
        }

        public String getFtText() {
            return ftText;
        }

        public void setFtText(String ftText) {
            this.ftText = ftText;
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

    public enum FTType {
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
