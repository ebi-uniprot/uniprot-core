package org.uniprot.core.parser.tsv.uniparc;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author sahmad
 * @created 24/03/2021
 */
public class UniParcEntryCrossRefValueMapper implements EntityValueMapper<UniParcCrossReference> {
    @Override
    public Map<String, String> mapEntity(UniParcCrossReference entity, List<String> fieldNames) {
        Map<String, String> fieldValue = new HashMap<>();
        fieldValue.put("database", entity.getDatabase().getName());
        fieldValue.put("accession", entity.getId());
        fieldValue.put("gene", getNullSafeValue(entity.getGeneName()));
        fieldValue.put("ncbiGi", getNullSafeValue(entity.getNcbiGi()));
        fieldValue.put("organism", Objects.nonNull(entity.getOrganism()) ? entity.getOrganism().getScientificName() : "");
        fieldValue.put("organism_id", Objects.nonNull(entity.getOrganism()) ? String.valueOf(entity.getOrganism().getTaxonId()) : "");
        fieldValue.put("protein", getNullSafeValue(entity.getProteinName()));
        fieldValue.put("proteome", getProteome(entity));
        fieldValue.put("active", entity.isActive() ? "Yes" : "No");
        fieldValue.put("first_seen", getNullSafeDate(entity.getCreated()));
        fieldValue.put("last_seen", getNullSafeDate(entity.getLastUpdated()));
        fieldValue.put("timeline", getDiffInDays(entity.getCreated(), entity.getLastUpdated()));
        fieldValue.put("version", String.valueOf(Objects.nonNull(entity.getVersion())? entity.getVersion():""));
        fieldValue.put("version_uniparc", String.valueOf(entity.getVersionI()));
        return fieldValue;
    }

    private String getDiffInDays(LocalDate start, LocalDate end) {
        if (Objects.isNull(start) || Objects.isNull(end)) {
            return "";
        }
        return String.valueOf(ChronoUnit.DAYS.between(end, start));
    }

    private String getNullSafeDate(LocalDate date) {
        if (Objects.isNull(date)) {
            return "";
        }
        return date.toString();
    }

    private String getNullSafeValue(String value){
        return Objects.isNull(value) ? "" : value;
    }

    private String getProteome(UniParcCrossReference entity) {
        if(Utils.notNullNotEmpty(entity.getProteomeId()) && Utils.notNullNotEmpty(entity.getComponent())) {
            return entity.getProteomeId() + ":" + entity.getComponent();
        }
        return "";
    }
}
