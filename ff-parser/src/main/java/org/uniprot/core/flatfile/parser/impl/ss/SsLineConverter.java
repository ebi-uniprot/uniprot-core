package org.uniprot.core.flatfile.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.InternalLineType;
import org.uniprot.core.uniprotkb.InternalSection;
import org.uniprot.core.uniprotkb.SourceLine;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceLineBuilder;
import org.uniprot.core.uniprotkb.impl.InternalLineBuilder;
import org.uniprot.core.uniprotkb.impl.InternalSectionBuilder;
import org.uniprot.core.uniprotkb.impl.SourceLineBuilder;

import com.google.common.base.Strings;

public class SsLineConverter implements Converter<SsLineObject, InternalSection> {
    @Override
    public InternalSection convert(SsLineObject f) {

        if (f == null) return null;
        List<InternalLine> internalLines = new ArrayList<>();
        List<SourceLine> sourceLines = new ArrayList<>();
        List<EvidenceLine> evidenceLines = new ArrayList<>();

        for (SsLineObject.EvLine ev : f.getSsEVLines()) {
            evidenceLines.add(convert(ev));
        }

        for (SsLineObject.SsLine ia : f.getSsIALines()) {
            internalLines.add(convert(ia));
        }
        for (String source : f.getSsSourceLines()) {
            sourceLines.add(new SourceLineBuilder(source).build());
        }
        return new InternalSectionBuilder()
                .internalLinesSet(internalLines)
                .sourceLinesSet(sourceLines)
                .evidenceLinesSet(evidenceLines)
                .build();
    }

    private InternalLine convert(SsLineObject.SsLine ia) {
        return new InternalLineBuilder(convertILType(ia.getTopic()), ia.getText()).build();
    }

    private InternalLineType convertILType(String type) {
        if (type.equals("CL")) {
            return InternalLineType.CL;
        } else if (type.equals("CP")) {
            return InternalLineType.CP;
        } else if (type.equals("CX")) {
            return InternalLineType.CX;
        } else if (type.equals("DG")) {
            return InternalLineType.DG;
        } else if (type.equals("DR")) {
            return InternalLineType.DR;
        } else if (type.equals("ET")) {
            return InternalLineType.ET;
        } else if (type.equals("EV")) {
            return InternalLineType.EV;
        } else if (type.equals("GO")) {
            return InternalLineType.GO;
        } else if (type.equals("HA")) {
            return InternalLineType.HA;
        } else if (type.equals("HP")) {
            return InternalLineType.HP;
        } else if (type.equals("HR")) {
            return InternalLineType.HR;
        } else if (type.equals("HU")) {
            return InternalLineType.HU;
        } else if (type.equals("HW")) {
            return InternalLineType.HW;
        } else if (type.equals("ID")) {
            return InternalLineType.ID;
        } else if (type.equals("IS")) {
            return InternalLineType.IS;
        } else if (type.equals("NI")) {
            return InternalLineType.NI;
        } else if (type.equals("PE")) {
            return InternalLineType.PE;
        } else if (type.equals("PM")) {
            return InternalLineType.PM;
        } else if (type.equals("PROSITE")) {
            return InternalLineType.PROSITE;
        } else if (type.equals("RU")) {
            return InternalLineType.RU;
        } else if (type.equals("SO")) {
            return InternalLineType.SO;
        } else if (type.equals("TX")) {
            return InternalLineType.TX;
        } else if (type.equals("UP")) {
            return InternalLineType.UP;
        } else if (type.equals("YY")) {
            return InternalLineType.YY;
        } else if (type.equals("ZA")) {
            return InternalLineType.ZA;
        } else if (type.equals("ZB")) {
            return InternalLineType.ZB;
        } else if (type.equals("ZC")) {
            return InternalLineType.ZC;
        } else if (type.equals("ZR")) {
            return InternalLineType.ZR;
        } else if (type.equals("ZZ")) {
            return InternalLineType.ZZ;
        }
        throw new IllegalArgumentException("unknown internal line type " + type);
    }

    private EvidenceLine convert(SsLineObject.EvLine e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getId());
        if (isValidDb(e.getDb())) {
            sb.append("|");
            sb.append(e.getDb());
            if (!Strings.isNullOrEmpty(e.getAttr1())) {
                sb.append(":").append(e.getAttr1());
            }
        }
        return new EvidenceLineBuilder()
                .evidence(sb.toString())
                .creationDate(e.getDate())
                .curator(e.getAttr2())
                .build();
    }

    private boolean isValidDb(String db) {
        return !Strings.isNullOrEmpty(db) && !db.equals("-");
    }
}
