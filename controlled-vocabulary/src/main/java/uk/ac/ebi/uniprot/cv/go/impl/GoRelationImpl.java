package uk.ac.ebi.uniprot.cv.go.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.cv.go.GoRelation;

public class GoRelationImpl implements GoRelation {
	private final Map<String, List<String>> isAMap;
	private final Map<String, List<String>> isPartMap;
	private Map<String, List<String>> childMap;
	private List<String> roots;
	public GoRelationImpl(Map<String, List<String>> isAMap, 
			Map<String, List<String>> isPartMap
			) {
		this.isAMap =isAMap;
		this.isPartMap = isPartMap;
		childMap =new HashMap<>();
		isAMap.entrySet().stream()
		.forEach(val -> updateChildMap(val.getKey(), val.getValue(), childMap));
		isPartMap.entrySet().stream()
		.forEach(val -> updateChildMap(val.getKey(), val.getValue(), childMap));
		
		Set<String> hasChild = childMap.keySet();
		Set<String> hasParent =isAMap.keySet();
		roots=
		hasChild.stream().filter(val ->!hasParent.contains(val))
		.collect(Collectors.toList());
	}
	
	private void updateChildMap(String child,
			List<String> parents, Map<String, List<String>> childMap) {
		for(String parent: parents) {
			List<String> children = childMap.get(parent);
			if(children ==null) {
				children = new ArrayList<>();
				childMap.put(parent, children);
			}
			if(!children.contains(child)) {
				children.add(child);
			}
		}
	}
	@Override
	public List<String> getIsA(String goId) {
		return isAMap.get(goId);
	}

	@Override
	public List<String> getPartOf(String goId) {
		return isPartMap.get(goId);
	}

	@Override
	public List<String> getChildren(String goId) {
		if((goId ==null) || goId.isEmpty())
			return roots;
		return childMap.get(goId);
	}

	
}
