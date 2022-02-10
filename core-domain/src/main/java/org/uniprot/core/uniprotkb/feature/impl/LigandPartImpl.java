package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.feature.LigandPart;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
*/

public class LigandPartImpl implements LigandPart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3142605597644565663L;
	private final String name;
	private final String id;
	private final String label;
	private final String note;

	LigandPartImpl() {
		this("", "", "", "");
	}

	public LigandPartImpl(String name, String id, String label, String note) {
		super();
		this.name = name;
		this.id = id;
		this.label = label;
		this.note = note;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id, label, note);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigandPartImpl that = (LigandPartImpl) obj;
		return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(label, that.label)
				&& Objects.equals(note, that.note);
	}

}

