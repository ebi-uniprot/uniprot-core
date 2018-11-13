package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismNameImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNameImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNodeImpl;

public enum TaxonomyFactory {
    INSTANCE;
	 private final static Pattern ORGANISM_PATTERN = Pattern
				.compile("([a-zA-Z 0-9\\'\\-\\._/]+)((( \\()([a-zA-Z 0-9\\'\\-\\._/]+)(\\)))" + "(( \\()([a-zA-Z 0-9,\\'\\-\\._/]+)(\\)))?)?");

    public TaxonName createTaxonName(String name) {
    	return new TaxonNameImpl(name);
    }
    public TaxonName createTaxonName( String scientificName, String commonName){
        return createTaxonName( scientificName, commonName, Collections.emptyList());
    }
    
    public TaxonName createTaxonName(String scientificName, String commonName, List<String> synonyms){
        return new TaxonNameImpl( scientificName, commonName, synonyms);
    }
    
   
	public OrganismName createOrganismName(String scientificName) {
		return createOrganismName(scientificName, null);
	}

	public OrganismName createOrganismName(String scientificName, String commonName) {
		return createOrganismName(scientificName, commonName, Collections.emptyList());
	}

	public OrganismName createOrganismName(String scientificName, String commonName, List<String> synonyms) {
		return new OrganismNameImpl(scientificName, commonName, synonyms);
	}

	public Organism createOrganism( OrganismName name, long taxId) {
		return new OrganismImpl(name, taxId);
	}
	public OrganismName createFromOrganismLine(String organismStr) {
		Matcher matcher = TaxonomyFactory.ORGANISM_PATTERN.matcher(organismStr);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Organism String cannot be parsed: " + organismStr);
		}
		String scientificName = matcher.group(1);
		String commonName = "";
		if (matcher.group(5) != null) {
			commonName = matcher.group(5);
		}
		String synonymStr = matcher.group(9);
		List<String> synonyms = null;
		if (synonymStr != null) {
			synonyms = Arrays.asList(synonymStr.split(", "));
		} else {
			synonyms = Collections.emptyList();
		}
		return createOrganismName(scientificName, commonName, synonyms);
	}
    
    public Taxon createTaxon(TaxonName name, long taxonId ){
        return new  TaxonImpl(  name, taxonId);
    }
    public Taxon createTaxon( String name, long taxonId ){
        return new  TaxonImpl(createTaxonName(name), taxonId);
    };
    public TaxonNode createTaxonNode(TaxonNode parent, Taxon taxon, TaxonomyRank rank) {
    	return new TaxonNodeImpl(parent, taxon, rank);
    }
}
