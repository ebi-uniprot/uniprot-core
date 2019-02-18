package uk.ac.ebi.uniprot.domain;

public final class Property implements Pair<String, String>, Comparable<Property> {
    private String key;
    private String value;

    private Property() {

    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Property other = (Property) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    @Override
    public int compareTo(Property o) {
        return this.key.compareTo(o.getKey());
    }

}
