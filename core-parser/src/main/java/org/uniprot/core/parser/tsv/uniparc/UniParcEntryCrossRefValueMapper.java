package org.uniprot.core.parser.tsv.uniparc;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.uniparc.ProteomeIdComponent;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author sahmad
 * @created 24/03/2021
 */
public class UniParcEntryCrossRefValueMapper implements EntityValueMapper<UniParcCrossReference> {
    private static final String EMPTY_STRING = "";

    @Override
    public Map<String, String> mapEntity(UniParcCrossReference entity, List<String> fieldNames) {
        Map<String, String> fieldValue = new HashMap<>();
        fieldValue.put("database", entity.getDatabase().getName());
        fieldValue.put("accession", entity.getId());
        fieldValue.put("gene", getNullSafeValue(entity.getGeneName()));
        fieldValue.put("ncbiGi", getNullSafeValue(entity.getNcbiGi()));
        fieldValue.put(
                "organism",
                Objects.nonNull(entity.getOrganism())
                        ? entity.getOrganism().getScientificName()
                        : "");
        fieldValue.put(
                "organism_id",
                Objects.nonNull(entity.getOrganism())
                        ? String.valueOf(entity.getOrganism().getTaxonId())
                        : "");
        fieldValue.put("protein", getNullSafeValue(entity.getProteinName()));
        fieldValue.put("proteome", getProteome(entity));
        fieldValue.put("active", entity.isActive() ? "Yes" : "No");
        fieldValue.put("first_seen", getNullSafeDate(entity.getCreated()));
        fieldValue.put("last_seen", getNullSafeDate(entity.getLastUpdated()));
        fieldValue.put("timeline", getDiffInDays(entity.getCreated(), entity.getLastUpdated()));
        fieldValue.put(
                "version",
                String.valueOf(Objects.nonNull(entity.getVersion()) ? entity.getVersion() : ""));
        fieldValue.put("version_uniparc", String.valueOf(entity.getVersionI()));
        return fieldValue;
    }

    private String getDiffInDays(LocalDate start, LocalDate end) {
        if (Objects.isNull(start) || Objects.isNull(end)) {
            return EMPTY_STRING;
        }
        return String.valueOf(ChronoUnit.DAYS.between(end, start));
    }

    private String getNullSafeDate(LocalDate date) {
        if (Objects.isNull(date)) {
            return EMPTY_STRING;
        }
        return date.toString();
    }

    private String getNullSafeOrganism(Organism organism) {
        if (Objects.nonNull(organism)) {
            return organism.getScientificName();
        }
        return EMPTY_STRING;
    }

    private String getNullSafeOrganismId(Organism organism) {
        if (Objects.nonNull(organism)) {
            return String.valueOf(organism.getTaxonId());
        }
        return EMPTY_STRING;
    }

    private String getNullSafeVersion(Integer version) {
        if (Objects.nonNull(version)) {
            return String.valueOf(version);
        }
        return EMPTY_STRING;
    }

    private String getNullSafeValue(String value) {
        return Objects.isNull(value) ? EMPTY_STRING : value;
    }

    private String getProteome(UniParcCrossReference entity) {
        List<ProteomeIdComponent> proteomeIdComponents = entity.getProteomeIdComponents();
        List<String> proteomeComponents = new ArrayList<>();
        for (ProteomeIdComponent proteomeIdComponent : proteomeIdComponents) {
            String proteome = proteomeIdComponent.getProteomeId();
            if (Utils.notNullNotEmpty(proteome)) {
                if (Utils.notNullNotEmpty(proteomeIdComponent.getComponent())) {
                    proteome += ":" + proteomeIdComponent.getComponent();
                }
                proteomeComponents.add(proteome);
            }
        }
        return String.join(";", proteomeComponents);
    }
}
