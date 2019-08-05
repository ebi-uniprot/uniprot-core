package org.uniprot.core.uniparc.impl;

import java.util.Objects;

import org.uniprot.core.uniparc.InterproGroup;

/**
 *
 * @author jluo
 * @date: 22 May 2019
 *
*/

public class InterProGroupImpl implements InterproGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1200750330707649533L;
	private String id;
	private String name;
	
	protected InterProGroupImpl() {
		
	}
	public InterProGroupImpl(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterProGroupImpl other = (InterProGroupImpl) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);

	}
}

