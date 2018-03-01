package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.EnumMap;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FreeTextComment;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;



public class CCLineBuilderFactory {
	private static  Map< CommentType, FFLineBuilder<? extends Comment> > commentBuilders 
	= new EnumMap<>(CommentType.class);
	static{
		commentBuilders.put(CommentType.ALTERNATIVE_PRODUCTS,
				new CCAPCommentLineBuilder());
		commentBuilders.put(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES,
				new CCBioPhyChemCommentLineBuilder());
		commentBuilders.put(CommentType.DISEASE,
				new CCDiseaseCommentLineBuilder());
		commentBuilders.put(CommentType.INTERACTION,
				new CCInteractionCommentLineBuilder());
		commentBuilders.put(CommentType.MASS_SPECTROMETRY,
				new CCMassSpecCommentLineBuilder());
		commentBuilders.put(CommentType.RNA_EDITING,
				new CCRnaEditingCommentLineBuilder());
		commentBuilders.put(CommentType.SEQUENCE_CAUTION,
				new CCSequenceCautionCommentLineBuilder());
		commentBuilders.put(CommentType.SUBCELLULAR_LOCATION,
				new CCSubCellCommentLineBuilder());
		
		commentBuilders.put(CommentType.WEBRESOURCE,
				new CCWebResourceCommentLineBuilder());
		commentBuilders.put(CommentType.COFACTOR,
				new CCCofactorCommentLineBuilder());
		
	};
	private static final FFLineBuilder<FreeTextComment> defaultBuilder
	= new CCFreeTextCommentLineBuilder();
	@SuppressWarnings("unchecked")
	public static <T extends Comment> FFLineBuilder<T >  create(T f){
		FFLineBuilder<T> builder = 
				(FFLineBuilder<T >) commentBuilders.get(f.getCommentType());
		if(builder !=null)
			return builder;
		else
			return (FFLineBuilder<T >) defaultBuilder;
	}
}
