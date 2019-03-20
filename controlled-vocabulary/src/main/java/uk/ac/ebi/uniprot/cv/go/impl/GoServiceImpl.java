package uk.ac.ebi.uniprot.cv.go.impl;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.cv.go.GoRelation;
import uk.ac.ebi.uniprot.cv.go.GoService;
import uk.ac.ebi.uniprot.cv.go.GoTerm;

public class GoServiceImpl implements GoService {
	Map<String, GoTerm> goIdToTermMap;
	GoRelation goRelation;

	public GoServiceImpl(String goFilePath) {
		build(goFilePath, goFilePath);
	}
	public GoServiceImpl(String goTermFile, String goRelationFile) {
		build(goTermFile, goRelationFile);
	}
	
	private void build(String goTermFile, String goRelationFile) {
		GoTermFileReader goTermReader  =new GoTermFileReader(goTermFile);
		goIdToTermMap = goTermReader.read();
		GoRelationFileReader goRelationReader  =new GoRelationFileReader(goTermFile);
		 goRelation = goRelationReader.read();
		
	}
	
	@Override
	public GoTerm getGoTermById(String id) {
		return goIdToTermMap.get(id);
	}

	@Override
	public List<String> getIsAById(String id) {
		return goRelation.getIsA(id);
	}

	@Override
	public List<String> getPartOfById(String id) {
		return goRelation.getPartOf(id);
	}

	@Override
	public List<String> getChildrenById(String id) {
		return goRelation.getChildren(id);
	}

}
