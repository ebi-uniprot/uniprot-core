package org.uniprot.core.cv.taxonomy;

/**
 * Concrete implementation of a TaxonomicNode.
 *
 * Contains a builder to aid in the creation of a TaxonomicNodeImpl.
 *
 * Note: All information necessary to populate the node has to be provided at build time.
 */
class TaxonomicNodeImpl implements TaxonomicNode {
	public static final String UNDEFINED_SCIENTIFIC_NAME = "Undefined";

    private int id;
    private String scientificName;
    private String commonName;
    private String synonymName;
    private String mnemonic;

    private TaxonomicNode parent;

	private TaxonomicNodeImpl(int id, String scientificName, String commonName, String synonymName,String mnemonic,
							 TaxonomicNode parent) {
		this.id = id;
		this.scientificName = scientificName;
		this.commonName = commonName;
		this.synonymName = synonymName;
		this.mnemonic = mnemonic;
		this.parent = parent;
	}

	@Override
    public int id() {
        return this.id;
    }

    @Override
    public String scientificName() {
        return this.scientificName;
    }

    @Override
    public String commonName() {
        return this.commonName;
    }

    @Override
    public String synonymName() {
        return this.synonymName;
    }

	@Override
	public String mnemonic() {
		return this.mnemonic;
	}

	@Override
    public TaxonomicNode parent() {
        return this.parent;
    }

	public boolean hasParent() {
		return parent != null;
	}

	public static class Builder {
		private int id;
		private String scientificName;
		private String commonName;
		private String synonymName;
		private String mnemonic;
		private TaxonomicNode parent;

		public Builder(int id, String scientificName) {
			this.id = id;
			this.scientificName = scientificName;
		}

		public Builder withCommonName(String name) {
			this.commonName = name;
			return this;
		}

		public Builder withSynonymName(String name) {
			this.synonymName = name;
			return this;
		}

		public Builder withMnemonic(String name) {
			this.mnemonic = name;
			return this;
		}

		public Builder childOf(TaxonomicNode parent) {
			this.parent = parent;
			return this;
		}

		public TaxonomicNodeImpl build() {
			return new TaxonomicNodeImpl(id, scientificName, commonName, synonymName,mnemonic, parent);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TaxonomicNodeImpl that = (TaxonomicNodeImpl) o;

		if (id != that.id) {
			return false;
		}
		if (commonName != null ? !commonName.equals(that.commonName) : that.commonName != null) {
			return false;
		}
		if (parent != null ? !parent.equals(that.parent) : that.parent != null) {
			return false;
		}
		if (scientificName != null ? !scientificName.equals(that.scientificName) : that.scientificName != null) {
			return false;
		}
		if (synonymName != null ? !synonymName.equals(that.synonymName) : that.synonymName != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (scientificName != null ? scientificName.hashCode() : 0);
		result = 31 * result + (commonName != null ? commonName.hashCode() : 0);
		result = 31 * result + (synonymName != null ? synonymName.hashCode() : 0);
		result = 31 * result + (parent != null ? parent.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TaxonomicNodeImpl{" +
			   "id=" + id +
			   ", scientificName='" + scientificName + '\'' +
			   ", commonName='" + commonName + '\'' +
			   ", synonymName='" + synonymName + '\'' +
			   ", parent=" + parent +
			   '}';
	}
}