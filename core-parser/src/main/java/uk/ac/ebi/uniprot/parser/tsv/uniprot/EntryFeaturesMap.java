package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FeatureLineBuilderFactory;

public class EntryFeaturesMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("ft:var_seq", "ft:variant", "ft:non_con", "ft:non_std",
                                                            "ft:non_ter", "ft:conflict", "ft:unsure", "ft:act_site", "ft:binding", "ft:ca_bind", "ft:dna_bind",
                                                            "ft:metal", "ft:np_bind", "ft:site", "ft:mutagen", "ft:intramem", "ft:top_dom", "ft:transmem", "ft:chain",
                                                            "ft:crosslnk", "ft:disulfide", "ft:carbohyd", "ft:init_met", "ft:lipid", "ft:mod_res", "ft:peptide",
                                                            "ft:propep", "ft:signal", "ft:transit", "ft:strand", "ft:helix", "ft:turn", "ft:coiled", "ft:compbias",
                                                            "ft:domain", "ft:motif", "ft:region", "ft:repeat", "ft:zn_fing", "dr:dbsnp"

    );


    private static final Pattern DBSNP_PATTERN = Pattern.compile("(.+)dbSNP(\\:)(rs(\\d+))(.*)");
    private static final Map<String, String> FEATURETYPE_2_NAME = new HashMap<>();

    static {
        FEATURETYPE_2_NAME.put("INIT_MET", "Initiator methionine");
        FEATURETYPE_2_NAME.put("SIGNAL", "Signal peptide");
        FEATURETYPE_2_NAME.put("PROPEP", "Propeptide");
        FEATURETYPE_2_NAME.put("TRANSIT", "Transit peptide");
        FEATURETYPE_2_NAME.put("CHAIN", "Chain");
        FEATURETYPE_2_NAME.put("PEPTIDE", "Peptide");
        FEATURETYPE_2_NAME.put("TOPO_DOM", "Topological domain");
        FEATURETYPE_2_NAME.put("TRANSMEM", "Transmembrane");
        FEATURETYPE_2_NAME.put("DOMAIN", "Domain");

        FEATURETYPE_2_NAME.put("REPEAT", "Repeat");
        FEATURETYPE_2_NAME.put("CA_BIND", "Calcium-binding");
        FEATURETYPE_2_NAME.put("ZN_FING", "Zinc finger");
        FEATURETYPE_2_NAME.put("DNA_BIND", "DNA-binding");
        FEATURETYPE_2_NAME.put("NP_BIND", "Nucleotide-binding");
        FEATURETYPE_2_NAME.put("REGION", "Region");
        FEATURETYPE_2_NAME.put("COILED", "Coiled coil");
        FEATURETYPE_2_NAME.put("MOTIF", "Motif");

        FEATURETYPE_2_NAME.put("COMPBIAS", "Compositional biased");
        FEATURETYPE_2_NAME.put("ACT_SITE", "Active site");
        FEATURETYPE_2_NAME.put("METAL", "Metal binding");
        FEATURETYPE_2_NAME.put("BINDING", "binding site");
        FEATURETYPE_2_NAME.put("SITE", "Site");
        FEATURETYPE_2_NAME.put("NON_STD", "Non-standard residue");
        FEATURETYPE_2_NAME.put("MOD_RES", "Modified residue");
        FEATURETYPE_2_NAME.put("LIPID", "Lipidation");

        FEATURETYPE_2_NAME.put("CARBOHYD", "Glycosylation");
        FEATURETYPE_2_NAME.put("DISULFID", "Disulfide bond");
        FEATURETYPE_2_NAME.put("CROSSLNK", "Cross-link");
        FEATURETYPE_2_NAME.put("VAR_SEQ", "Alternative sequence");
        FEATURETYPE_2_NAME.put("VARIANT", "Natural variant");
        FEATURETYPE_2_NAME.put("MUTAGEN", "Mutagenesis");
        FEATURETYPE_2_NAME.put("UNSURE", "Sequence uncertainty");

        FEATURETYPE_2_NAME.put("CONFLICT", "Sequence conflict");
        FEATURETYPE_2_NAME.put("NON_CONS", "Non-adjacent residues");
        FEATURETYPE_2_NAME.put("NON_TER", "Non-terminal residue");
        FEATURETYPE_2_NAME.put("HELIX", "Helix");
        FEATURETYPE_2_NAME.put("TURN", "Turn");
        FEATURETYPE_2_NAME.put("STRAND", "Beta strand");
        FEATURETYPE_2_NAME.put("INTRAMEM", "Intramembrane");

    }

    private final List<Feature> features;

    public EntryFeaturesMap(List<Feature> features) {
        if (features == null) {
            this.features = Collections.emptyList();
        } else
            this.features = Collections.unmodifiableList(features);
    }

    @Override
    public Map<String, String> attributeValues() {
        if (features.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        Map<FeatureType, List<Feature>> featureMap = new HashMap<>();
        for (Feature feature : features) {
            featureMap.computeIfAbsent(feature.getType(), k -> new ArrayList<>()).add(feature);
        }
        featureMap.forEach((featureType, features) -> {
            String key = "ft:" + featureType.name().toLowerCase();
            String value = features.stream().map(EntryFeaturesMap::featureToString)
                    .collect(Collectors.joining("; "));
            map.put(key, value);
            if (featureType.equals(FeatureType.VARIANT)) {
                String dbSnps = variantTodbSnp(features);
                if (dbSnps != null && !dbSnps.isEmpty()) {
                    map.put("dr:dbsnp", dbSnps);
                }
            }
        });

        return map;
    }

    private String variantTodbSnp(List<Feature> features) {
        return features.stream().map(Feature::getDescription).map(this::getDbsnpFromFeatureDescription)
                .filter(val -> val != null && !val.isEmpty()).collect(Collectors.joining(" "));

    }

    private String getDbsnpFromFeatureDescription(FeatureDescription description) {
        Matcher matcher = DBSNP_PATTERN.matcher(description.getValue());
        if (matcher.matches()) {
            return matcher.group(3);
        } else {
            return null;
        }
    }

    public static List<String> getFeatures(List<Feature> features) {
        if (features == null) {
            return Collections.emptyList();
        }

        Map<String, Long> values = features.stream().map(val -> FEATURETYPE_2_NAME.get(val.getType().name()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(val -> val, TreeMap::new, Collectors.counting()));
        return values.entrySet().stream().map(val -> (val.getKey() + " (" + val.getValue().toString() + ")"))
                .collect(Collectors.toList());

    }

    public static String featureToString(Feature feature) {
    	FFLineBuilder< Feature> fbuilder =FeatureLineBuilderFactory.create(feature);
    	return fbuilder.buildStringWithEvidence(feature).replaceAll("\n",  " " );
    }
    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
