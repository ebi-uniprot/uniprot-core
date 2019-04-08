package uk.ac.ebi.uniprot.json.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;

public class CustomAnnotationIntrospector extends SimpleAnnotationIntrospector {
	private static final long serialVersionUID = 3724944589060382231L;

	@Override
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        if (baseType.isTypeOrSubTypeOf(Comment.class)
				//|| (baseType.isTypeOrSubTypeOf(DatabaseType.class) && !baseType.hasRawClass(EvidenceType.class))
                || baseType.isTypeOrSubTypeOf(Citation.class)){
            StdTypeResolverBuilder typeResolverBuilder = new StdTypeResolverBuilder();
            typeResolverBuilder.init(JsonTypeInfo.Id.NAME, null);
            typeResolverBuilder.typeProperty("type");
            typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
            return typeResolverBuilder;
        }
        return super.findTypeResolver(config, ac, baseType);
    }

}

