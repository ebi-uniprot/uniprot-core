package org.uniprot.core.flatfile.transformer;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.builder.WebResourceCommentBuilder;

public class WebResourceCommentTransformer implements CommentTransformer<WebResourceComment> {
    private static final CommentType COMMENT_TYPE = CommentType.WEBRESOURCE;

    @Override
    public WebResourceComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public WebResourceComment transform(CommentType type, String annotation) {
    	  WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
    	  annotation = updateMolecule(annotation, builder);  	  
        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");
        String[] tokens = annotation.split(";");
      

        for (String token : tokens) {
            token = token.trim();
            if (token.toLowerCase().startsWith("name")) {
                String name = token.substring(5, token.length());
                builder.resourceName(name);
                continue;
            }
            if (token.toLowerCase().startsWith("note")) {
                String note = token.substring(5, token.length());
                builder.note(note);
                continue;
            }

            if (token.toLowerCase().startsWith("url")) {
                String www = token.substring(4, token.length());
                if (www.startsWith("\"")) {
                    www = www.substring(1, www.length());
                }
                if (www.endsWith("\"")) {
                    www = www.substring(0, www.length() - 1);
                }
                builder.resourceUrl(www);
                builder.isFtp(false);
                continue;
            }

            if (token.toLowerCase().startsWith("ftp")) {
                String ftp = token.substring(4, token.length());
                if (ftp.startsWith("\"")) {
                    ftp = ftp.substring(1, ftp.length());
                }
                if (ftp.endsWith("\"")) {
                    ftp = ftp.substring(0, ftp.length() - 1);
                }
                builder.resourceUrl(ftp);
                builder.isFtp(true);
                continue;
            }

            throw new RuntimeException(token);
        }
        return builder.build();
    }
    private String updateMolecule(String annotation,  WebResourceCommentBuilder builder ) {
    	if(annotation.startsWith("[") && annotation.contains("]")){
    		int index =annotation.indexOf("]");
    		String molecule = annotation.substring(1, index);
    		molecule = molecule.replaceAll("\n", " ");
    		builder.molecule(molecule);
    		annotation = annotation.substring(index+2).trim();
    		  if (annotation.startsWith("\n"))
                  annotation = annotation.substring(1);
    		 return annotation;
    	}
    	return annotation;
    }
    
}
