package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineConstant;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;

import com.google.common.base.Strings;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
 */

public class BindingFeatureLineBuilder extends AbstractFeatureLineBuilder {
	private static final String LIGAND_PREFIX = "/ligand=\"";
    private static final String LIGAND_ID_PREFIX = "/ligand_id=\"";
    private static final String LIGAND_LABEL_PREFIX = "/ligand_label=\"";
    private static final String LIGAND_NOTE_PREFIX = "/ligand_note=\"";
    
    private static final String LIGAND_PART_PREFIX = "/ligand_part=\"";
    private static final String LIGAND_PART_ID_PREFIX = "/ligand_part_id=\"";
    private static final String LIGAND_PART_LABEL_PREFIX = "/ligand_part_label=\"";
    private static final String LIGAND_PART_NOTE_PREFIX = "/ligand_part_note=\"";
    
	@Override
	protected List<String> buildLigands(UniProtKBFeature f, boolean includeFFMarkup) {
		List<String> lines = new ArrayList<>();
        Ligand ligand = f.getLigand();
        if (ligand != null) {
            lines.addAll(buildLigandLine(LIGAND_PREFIX, ligand.getName(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_ID_PREFIX, ligand.getId(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_LABEL_PREFIX, ligand.getLabel(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_NOTE_PREFIX, ligand.getNote(),includeFFMarkup ));
        }
        LigandPart ligandPart = f.getLigandPart();
        if (ligandPart != null) {
            lines.addAll(buildLigandLine(LIGAND_PART_PREFIX, ligandPart.getName(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_PART_ID_PREFIX, ligandPart.getId(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_PART_LABEL_PREFIX, ligandPart.getLabel(),includeFFMarkup ));
            lines.addAll(buildLigandLine(LIGAND_PART_NOTE_PREFIX, ligandPart.getNote(),includeFFMarkup ));
        }
        
        return lines;
	}

	
	private List<String> buildLigandLine(String linePrefix, String value,  boolean includeFFMarkup) {
        if (Strings.isNullOrEmpty(value))
            return List.of();

        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup)
            sb.append(FTLineBuilderHelper.FT_LINE_PREFIX_2);
        sb.append(linePrefix).append(value);
        String[] seps = { FFLineConstant.SEPARATOR, FFLineConstant.DASH };

        sb.append(FTLineBuilderHelper.DOUBLE_QUOTE);
        if (includeFFMarkup) {
            return FFLineWrapper.buildLines(sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2,
            		FFLineConstant.LINE_LENGTH);
        } else {
            List<String> result = new ArrayList<>();
            result.add(sb.toString());
            return result;
        }

    }
}
