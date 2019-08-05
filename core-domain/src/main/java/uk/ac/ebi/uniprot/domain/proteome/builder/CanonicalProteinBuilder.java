package uk.ac.ebi.uniprot.domain.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.common.Utils;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.proteome.CanonicalProtein;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.impl.CanonicalProteinImpl;

public class CanonicalProteinBuilder implements Builder<CanonicalProteinBuilder, CanonicalProtein> {
	private Protein canonicalProtein;
	private List<Protein> relatedProteins = new ArrayList<>();

	public static CanonicalProteinBuilder newInstance() {
		return new CanonicalProteinBuilder();
	}
	@Override
	public CanonicalProtein build() {
		return new CanonicalProteinImpl(canonicalProtein, relatedProteins);
	}

	public CanonicalProteinBuilder relatedProteins(List<Protein> relatedProteins) {
		this.relatedProteins = Utils.nonNullList(relatedProteins);
		return this;
	}

	public CanonicalProteinBuilder addRelatedProtein(Protein relatedProtein) {
		Utils.nonNullAdd(relatedProtein, relatedProteins);
		return this;
	}

	public CanonicalProteinBuilder canonicalProtein(Protein canonicalProtein) {
		this.canonicalProtein = canonicalProtein;
		return this;
	}

	@Override
	public CanonicalProteinBuilder from(CanonicalProtein instance) {
		this.canonicalProtein = instance.getCanonicalProtein();
		relatedProteins.clear();
		relatedProteins.addAll(instance.getRelatedProteins());
		return this;
	}

}
