package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.*;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public enum TaxonomyFactory {
	INSTANCE;
	private static final String STRAIN = " (strain";
	public final static Pattern ORGANISM_PATTERN = Pattern
			.compile("([a-zA-Z 0-9\\'\\-\\._/]+)((( \\()([a-zA-Z 0-9\\'\\-\\._/]+)(\\)))"
					+ "(( \\()([a-zA-Z 0-9,\\'\\-\\._/]+)(\\)))?)*");

	public TaxonName createTaxonName(String name) {
		return new TaxonNameImpl(name);
	}

	public TaxonName createTaxonName(String scientificName, String commonName) {
		return createTaxonName(scientificName, commonName, Collections.emptyList());
	}

	public TaxonName createTaxonName(String scientificName, String commonName, List<String> synonyms) {
		return new TaxonNameImpl(scientificName, commonName, synonyms);
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

	public Organism createOrganism(OrganismName name, long taxId) {
		return new OrganismImpl(name, taxId);
	}

//	public OrganismName createFromOrganismLine(String organismStr) {
//		Matcher matcher = TaxonomyFactory.ORGANISM_PATTERN.matcher(organismStr);
//		if (!matcher.matches()) {
//			throw new IllegalArgumentException("Organism String cannot be parsed: " + organismStr);
//		}
//		String scientificName = matcher.group(1);
//		String commonName = "";
//		if (matcher.group(5) != null) {
//			commonName = matcher.group(5);
//		}
//		String synonymStr = matcher.group(9);
//		List<String> synonyms = null;
//		if (synonymStr != null) {
//			synonyms = Arrays.asList(synonymStr.split(", "));
//		} else {
//			synonyms = Collections.emptyList();
//		}
//		return createOrganismName(scientificName, commonName, synonyms);
//	}

	public OrganismName createFromOrganismLine(String organismName) {
		String value = organismName;
		if ((value.endsWith(".") && (value.charAt(value.length() - 2) != ')'))
				|| (value.charAt(value.length() - 1) != ')')) {
			return TaxonomyFactory.INSTANCE.createOrganismName(value);

		}
		if ((value.endsWith(".") && (value.charAt(value.length() - 2) == ')'))
				|| (value.charAt(value.length() - 1) == ')')) {
			if (value.indexOf(" (") == -1) {
				return TaxonomyFactory.INSTANCE.createOrganismName(value);

			}
		}
		if (value.endsWith(".")) {
			value = value.substring(0, value.length() - 1);
		}

		String rest = value.trim();
		int strainStart = -1;
		int start = 0;
		int startBracketIndex = -1;
		if (rest.contains(STRAIN)) {
			strainStart = rest.indexOf(STRAIN);
			start = getEndBlacket(rest, strainStart + STRAIN.length());
			if (start == -1) {
				return TaxonomyFactory.INSTANCE.createOrganismName(value);
			}

		}
		startBracketIndex = rest.indexOf("(", start + 1);
		if (startBracketIndex == -1) {
			return TaxonomyFactory.INSTANCE.createOrganismName(value);
		}
		String scientificName = rest.substring(0, startBracketIndex).trim();

		int endBracketIndex =getEndBlacket(rest, startBracketIndex +1);
		if (endBracketIndex == -1) {
			throw new IllegalArgumentException("organism name: " + organismName + " is not right");
		}
		String commonName = rest.substring(startBracketIndex + 1, endBracketIndex).trim();
		rest = rest.substring(endBracketIndex + 1).trim();
		String synonym = null;
		if (!rest.isEmpty()) {
			startBracketIndex = rest.indexOf("(");
			if (startBracketIndex == -1) {
				throw new IllegalArgumentException("organism name: " + organismName + " is not right");
			}
			endBracketIndex =getEndBlacket(rest, startBracketIndex +1);
			if (endBracketIndex == -1) {
				throw new IllegalArgumentException("organism name: " + organismName + " is not right");
			}
			synonym = rest.substring(startBracketIndex + 1, endBracketIndex).trim();
			return TaxonomyFactory.INSTANCE.createOrganismName(scientificName, commonName, Arrays.asList(synonym.split(", ")));
		} else {
			return TaxonomyFactory.INSTANCE.createOrganismName(scientificName, commonName, Collections.emptyList());
		}
	}
	
	 private int getEndBlacket(String val, int start) {
	        char endC = ')';
	        char startC = '(';
	        String value = val;
	        if (value.endsWith(".")) {
	            value = value.substring(0, value.length() - 1);
	        }
	        if (value.charAt(value.length() - 1) != endC)
	            return -1;
	        int countStart=0; 
	        int countEnd =0;
	        for(int i=start; i< value.length(); i++) {
	        	if (value.charAt(i) ==endC) {
	        		if(countStart ==countEnd)
	        			return i;
	        		else
	        			countEnd ++;
	        	}else if (value.charAt(i) ==startC) {
	        		countStart++;
	        	}
	        }
	      
	        return -1;

	    }

	public Taxon createTaxon(TaxonName name, long taxonId) {
		return new TaxonImpl(name, taxonId);
	}

	public Taxon createTaxon(String name, long taxonId) {
		return new TaxonImpl(createTaxonName(name), taxonId);
	};

	public TaxonNode createTaxonNode(TaxonNode parent, Taxon taxon, TaxonomyRank rank) {
		return new TaxonNodeImpl(parent, taxon, rank);
	}
}
