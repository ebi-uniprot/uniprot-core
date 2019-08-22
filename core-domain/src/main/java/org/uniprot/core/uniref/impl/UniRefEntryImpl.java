package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class UniRefEntryImpl implements UniRefEntry {

	private static final long serialVersionUID = -3900697447474386293L;
	private UniRefEntryId id;
	private String name;
	private int memberCount;
	private LocalDate updated;
	private UniRefType entryType;
	private long commonTaxonId;
	private String commonTaxonName;
	private List<GoTerm> goTerms;
	private RepresentativeMember representativeMember;
	private List<UniRefMember> members;
	
	
	protected UniRefEntryImpl() {
		goTerms = Collections.emptyList();
		members = Collections.emptyList();
	}
	public UniRefEntryImpl( UniRefEntryId id,
	 String name, 
	 int memberCount,
	 LocalDate updated,
	 UniRefType entryType,
	 long commonTaxonId,
	 String commonTaxonName,
	 List<GoTerm> goTerms,
	 RepresentativeMember representativeMember,
	 List<UniRefMember> members) {
		this.id = id;
		this.name = name;	
		this.updated = updated;
		this.entryType =entryType;
		this.commonTaxonId =commonTaxonId;
		this.commonTaxonName =commonTaxonName;
		this.goTerms= Utils.nonNullUnmodifiableList(goTerms);
		this.representativeMember = representativeMember;
		this.members = Utils.nonNullUnmodifiableList(members);
		if(memberCount ==0) {
			this.memberCount = this.members.size()+1;
		}else {
			this.memberCount = memberCount;
		}

	}
	@Override
	public UniRefEntryId getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getUpdated() {
		return updated;
	}

	@Override
	public UniRefType getEntryType() {
		return entryType;
	}

	@Override
	public long getCommonTaxonId() {
		return commonTaxonId;
	}

	@Override
	public String getCommonTaxonName() {
		return commonTaxonName;
	}

	@Override
	public List<GoTerm> getGoTerms() {
		return goTerms;
	}

	@Override
	public RepresentativeMember getRepresentativeMember() {
		return representativeMember;
	}

	@Override
	public List<UniRefMember> getMembers() {
		return members;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, updated, entryType, commonTaxonId,
				commonTaxonName, goTerms, representativeMember, members, memberCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniRefEntryImpl other = (UniRefEntryImpl) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(name, other.name)
				&& Objects.equals(memberCount, other.memberCount)
				&& Objects.equals(updated, other.updated)
				&& Objects.equals(entryType, other.entryType)
				&& Objects.equals(commonTaxonId, other.commonTaxonId)
				&& Objects.equals(commonTaxonName, other.commonTaxonName)
				&& Objects.equals(goTerms, other.goTerms)
				&& Objects.equals(representativeMember, other.representativeMember)
				&& Objects.equals(members, other.members)
				;

	}
	@Override
	public int getMemberCount() {
		return memberCount;
	}
}
