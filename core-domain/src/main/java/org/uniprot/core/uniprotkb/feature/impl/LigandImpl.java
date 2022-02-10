package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.feature.Ligand;

/**
 *
 * @author jluo
 * @date: 8 Feb 2022
 *
*/

public class LigandImpl implements Ligand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2943315009073742949L;
	private final String name;
	private final String id;
	private final String label;
	private final String note;

	LigandImpl() {
		this("", "", "", "");
	}

	public LigandImpl(String name, String id, String label, String note) {
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
		LigandImpl that = (LigandImpl) obj;
		return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(label, that.label)
				&& Objects.equals(note, that.note);
	}
}

