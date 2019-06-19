package uk.ac.ebi.uniprot.cv.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.common.Pair;
import uk.ac.ebi.uniprot.common.PairImpl;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordCategory;
import uk.ac.ebi.uniprot.cv.keyword.KeywordEntry;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordEntryImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;

import java.util.*;
import java.util.stream.Collectors;

public class KeywordFileReader extends AbstractFileReader<KeywordEntry> {
	private static final String WW_LINE = "WW";
	private static final String CA_LINE = "CA";
	private static final String GO_LINE = "GO";
	private static final String HI_LINE = "HI";
	private static final String SY_LINE = "SY";
	private static final String DE_LINE = "DE";
	private static final String AC_LINE = "AC";
	private static final String IC_LINE = "IC";
	private static final String ID_LINE = "ID";
	private static final String SPLIT_SPACES = "   ";
	private static final String CATEGORY_SEPARATOR = ":";
	private static final String HIERARCHY_SEPARATOR = ";";
	private static final Logger LOG = LoggerFactory.getLogger(KeywordFileReader.class);


	public List<KeywordEntry> parseLines(List<String> lines) {
		List<KeyFileEntry> rawList = convertLinesIntoInMemoryObjectList(lines);
		List<KeywordEntry> list = parseKeywordFileEntryList(rawList);
		updateListWithRelationShips(list, rawList);
		cleanParentChildren(list);
		return list;
	}

	public Map<String, Pair<String, KeywordCategory> > parseFileToAccessionMap(String fileName) {
		List<KeywordEntry> keywordEntryList = parse(fileName);
		return keywordEntryList.stream()
				.collect(Collectors.toMap(this::getId, this::getAccessionCategoryPair));
	}

	private String getId(KeywordEntry keyword) {
		return keyword.getKeyword().getId();
	}

	private Pair<String, KeywordCategory> getAccessionCategoryPair(KeywordEntry keyword) {
		String accession = keyword.getAccession();
		Keyword kcategory = keyword.getCategory();
		KeywordCategory category =KeywordCategory.UNKNOWN;
		if(kcategory !=null) {
			category = KeywordCategory.fromValue(kcategory.getId());
		}else {
			 category = KeywordCategory.fromValue(keyword.getKeyword().getId());
		}
	
		return new PairImpl<>(accession, category);
	}

	private void cleanParentChildren(List<KeywordEntry> list) {
		for (KeywordEntry entry : list) {
			KeywordEntryImpl entryImpl = (KeywordEntryImpl) entry;
			Set<KeywordEntry> newParentList = cleanParentChildren(entryImpl.getParents());
			entryImpl.setParents(newParentList);
		}
	}

	private Set<KeywordEntry> cleanParentChildren(Set<KeywordEntry> list) {
		Set<KeywordEntry> result = new HashSet<>();
		for (KeywordEntry entry : list) {
			KeywordEntryImpl newEntry = new KeywordEntryImpl();
			newEntry.setKeyword(entry.getKeyword());
			newEntry.setParents(cleanParentChildren(entry.getParents()));
			result.add(newEntry);
		}
		return result;
	}

	private void updateListWithRelationShips(List<KeywordEntry> list, List<KeyFileEntry> rawList) {
		for (KeyFileEntry raw : rawList) {
			// category will not have relationship, so ignore them
			if (raw.hi.isEmpty()) {
				continue;
			}
			
			// Only getting keywords
			KeywordEntryImpl target = (KeywordEntryImpl) findByIdentifier(list, raw.id);
			assert (target != null);

			// Setting the category
			KeywordEntry category = findByIdentifier(list, raw.ca);
			if (category != null) {
				target.setCategory(category.getKeyword());
			}

			final List<String> withOutCategory = raw.hi.stream()
					.map(s -> s.substring(s.indexOf(CATEGORY_SEPARATOR) + 1)).collect(Collectors.toList());

			final Set<String> directRelations = withOutCategory.stream().map(s -> s.split(HIERARCHY_SEPARATOR))
					.filter(arr -> arr.length >= 2).map(arr -> arr[arr.length - 2])
					.map(this::trimSpacesAndRemoveLastDot).collect(Collectors.toSet());

			// getting relationships and update it parents for directRelations
			final List<KeywordEntry> relations = directRelations.stream().map(s -> findByIdentifier(list, s))
					.filter(Objects::nonNull)
					.peek(keywordEntry -> {
						keywordEntry.getParents().add(target);
					})
					.collect(Collectors.toList());

			// Only setting hierarchy if present
			target.getChildren().addAll(relations);
			if(relations.isEmpty() && (category !=null) ) {
				target.getChildren().add(category);
				category.getParents().add(target);
			}
		}
	}

	private KeywordEntry findByIdentifier(List<KeywordEntry> list, String id) {
		return list.stream().filter(s -> s.getKeyword().getId().equals(trimSpacesAndRemoveLastDot(id))).findFirst().orElse(null);
	}

	private List<KeywordEntry> parseKeywordFileEntryList(List<KeyFileEntry> rawList) {
		return rawList.stream().map(this::parseKeywordFileEntry).collect(Collectors.toList());
	}

	private KeywordEntry parseKeywordFileEntry(KeyFileEntry entry) {
		final String identifier = entry.id != null ? entry.id : entry.ic;
		KeywordEntryImpl retObj = new KeywordEntryImpl();
		Keyword keyword  =new KeywordImpl(trimSpacesAndRemoveLastDot(identifier), entry.ac);
		retObj.setKeyword(keyword);
		// definition
		String def = String.join(" ", entry.de);
		retObj.setDefinition(def.isEmpty() ? null : def);

		// Synonyms
		List<String> synList = entry.sy.stream().flatMap(s -> Arrays.asList(s.split(";")).stream())
				.map(this::trimSpacesAndRemoveLastDot).collect(Collectors.toList());
		retObj.setSynonyms(synList.isEmpty() ? null : synList);

		// GoMapping
		List<GeneOntology> goList = entry.go.stream().map(this::parseGeneOntology).collect(Collectors.toList());
		retObj.setGeneOntologies(goList);

		// Sites
		retObj.setSites(entry.ww.isEmpty() ? null : entry.ww);

		return retObj;
	}

	private String trimSpacesAndRemoveLastDot(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
	}

	private GeneOntology parseGeneOntology(String go) {
		String[] tokens = go.split(";");
		return new GeneOntologyImpl(tokens[0], tokens[1].trim());
	}

	private List<KeyFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
		// At the time of writing code there was 1200 entries in file
		List<KeyFileEntry> retList = new ArrayList<>(1250);

		int i = 0;

		// Ignore the header lines and information
		for (; i < lines.size(); i++) {
			String lineIgnore = lines.get(i);
			if (lineIgnore.startsWith("______")) {
				break;
			}
		}

		// Ignore underscore ___ line
		i++;

		// reached entries lines
		KeyFileEntry entry = new KeyFileEntry();

		// create in memory list of objects
		while (i < lines.size()) {
			String line = lines.get(i);
			if(COPYRIGHT_LINES.contains(line)) {
				i++;
				continue;
			}
			// For terminating line no need to complete loop
			if (line.equals("//")) {
				retList.add(entry);
				entry = new KeyFileEntry();
				i++;
				continue;
			}

			
			String[] tokens = line.split(SPLIT_SPACES);
			switch (tokens[0]) {
			case ID_LINE:
				entry.id = tokens[1];
				break;
			case IC_LINE:
				entry.ic = tokens[1];
				break;
			case AC_LINE:
				entry.ac = tokens[1];
				break;
			case DE_LINE:
				entry.de.add(tokens[1]);
				break;
			case SY_LINE:
				entry.sy.add(tokens[1]);
				break;
			case HI_LINE:
				entry.hi.add(tokens[1]);
				break;
			case GO_LINE:
				entry.go.add(tokens[1]);
				break;
			case CA_LINE:
				entry.ca = tokens[1];
				break;
			case WW_LINE:
				entry.ww.add(tokens[1]);
				break;
			default:
				LOG.info("Unhandle line found while parsing file: {}", line);

			}

			// read and save next line
			i++;
		}
		return retList;
	}

	private class KeyFileEntry {
		String id;
		String ic;
		String ac;
		List<String> de;
		List<String> sy;
		List<String> hi;
		List<String> go;
		List<String> ww;
		String ca;

		KeyFileEntry() {
			de = new ArrayList<>();
			sy = new ArrayList<>();
			hi = new ArrayList<>();
			go = new ArrayList<>();
			ww = new ArrayList<>();
		}
	}
}
