/**
 * 
 */
package oracle.monitoring.annotation.processor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementScanner6;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import oracle.monitoring.annotation.graphql.VGraphQLQuery;
import oracle.monitoring.annotation.graphql.VGraphQLType;
import oracle.monitoring.annotation.processor.graphql.Param;
import oracle.monitoring.annotation.processor.graphql.QueryInfo;

/**
 * @author vipink
 *
 */
@SupportedAnnotationTypes({
		"oracle.monitoring.annotation.graphql.VGraphQLArgument",
		"oracle.monitoring.annotation.graphql.VGraphQLQuery",
		"oracle.monitoring.annotation.graphql.VGraphQLType" })
public class GraphQLProcessor extends AbstractProcessor {

	
	private Map<String, List<VGraphQLType>> processingResults;
	private Map<String, String> processingTypeName;
	private Map<String,String> types = new HashMap<>();
	private Map<String,QueryInfo> queries = new HashMap<>();

	

	private boolean resetResults = true;

	/**
	 * 
	 */
	public GraphQLProcessor() {
		System.out.println(" Launched the processor");
		types.put("java.lang.String", "String");
		types.put("java.lang.Boolean", "Boolean");
		types.put("java.lang.Long", "Int");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		if (resetResults) {
			processingResults = new HashMap<>();
			processingTypeName = new HashMap<>();
		}

		try {
			// store the scanned types 
			ElementScanner8<Void, Void> scanner = new ElementScanner8<Void, Void>() {
				@Override
				public Void visitType(TypeElement e, Void aVoid) {
					String className = e.toString();
					if (className.lastIndexOf(".") != -1) {
						String typeObject = className.substring(className
								.lastIndexOf("."));
						processingEnv.getMessager().printMessage(
								Diagnostic.Kind.NOTE,
								MessageFormat.format(
										"type {0} and name is {1}.",
										className,typeObject));

						types.put(className, typeObject);
					}
					return super.visitType(e, aVoid);
				}

				@Override
				public Void visitUnknown(Element e, Void aVoid) {
					return DEFAULT_VALUE;
				}
			};

			for (Element e : roundEnv.getRootElements()) {
				if (e.getKind() == ElementKind.PACKAGE) { 
					continue;
				}
				scanner.scan(e, null);
			}
		} catch (RuntimeException | Error e) {
			// javac sucks at reporting errors in annotation processors
			e.printStackTrace();
		}

		for (Element element : roundEnv
				.getElementsAnnotatedWith(VGraphQLQuery.class)) {
			if (element instanceof ExecutableElement) {
				ExecutableElement typeElement = (ExecutableElement) element;
				if (typeElement.getKind() == ElementKind.METHOD) {
					buildQuery(typeElement.toString(), typeElement);
				}
			}
		}
		
		for (Element element : roundEnv
				.getElementsAnnotatedWith(VGraphQLType.class)) {

			
			if (element instanceof TypeElement) {
				
				TypeElement typeElement = (TypeElement) element;
				processingResults.put(typeElement.asType().toString(),
						new ArrayList<>());
				
				if (typeElement.getKind() == ElementKind.CLASS) {
					String schema = buildType(typeElement.getSimpleName().toString(), typeElement);
					processingTypeName.put(typeElement.asType().toString(),schema);
				}
				// done processing type
				processingEnv.getMessager().printMessage(
						Diagnostic.Kind.NOTE,
						MessageFormat.format(
								"Wid Processor : processing class {0}.",
								typeElement.asType().toString()));



				if (typeElement.getInterfaces() != null
						&& typeElement.getInterfaces().size() > 0) {
					for (TypeMirror mirror : typeElement.getInterfaces()) {
						if (mirror.getAnnotation(VGraphQLType.class) != null) {
							processingResults.get(
									typeElement.asType().toString()).add(
									mirror.getAnnotation(VGraphQLType.class));
						}
					}
				}

				processingResults.get(typeElement.asType().toString()).add(
						typeElement.getAnnotation(VGraphQLType.class));
			}
		}
		// finished collecting the type.. now generate a file
		return postProcessWorkItemDefinition();
	}
	
	private String buildType (String typeName , TypeElement typeElement ) {
		StringBuilder builder = new StringBuilder();
		builder.append("type ")
		.append(typeName)
		.append(" {").append("\r\n");
		for (Element enclosedElement : typeElement
				.getEnclosedElements()) {
			if (enclosedElement.getKind() == ElementKind.FIELD) {
				// see if you want to process modifier
				Set<Modifier> modifiers = enclosedElement
						.getModifiers();
				
				builder.append(enclosedElement.getSimpleName()).append(": ").append(types.get(enclosedElement.asType().toString())).append("\r\n");
			}
		}
		builder.append("} \n\r \n\r");	
		return builder.toString();
	}
	
	private void buildQuery(String typeName , ExecutableElement typeElement ) {
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setMethodName(typeElement.getSimpleName().toString());
		if (typeElement.getReturnType() != null) {
			String returnType = typeElement.getReturnType().toString();
			returnType = returnType.substring(returnType.lastIndexOf(".")+1);
			queryInfo.setReturnType(returnType);
		}
		// build query method here
		for (VariableElement el : typeElement.getParameters()) {
			Param p = new Param();
			p.setName(el.toString());
			// set type
			p.setType(types.get(el.asType().toString()));
			// use annotation if there is any
			for (AnnotationMirror e2 : el.getAnnotationMirrors()) {
				Map<? extends ExecutableElement, ? extends AnnotationValue>  map =  e2.getElementValues();
				for (ExecutableElement e : map.keySet()) {
					if ("name".equalsIgnoreCase(e.getSimpleName().toString())) {
						p.setName(map.get(e).toString().replaceAll("\"", ""));
					} else if ("required".equalsIgnoreCase(e.getSimpleName().toString())) {
						String req = map.get(e).toString();
						if ("true".equalsIgnoreCase(req)) {
							p.setRequired(true);
						}
					}
				}
			}
			queryInfo.getParamsList().add(p);
		}
		// find the method name from annotation
		for (AnnotationMirror el : typeElement.getAnnotationMirrors()) {
			Map<? extends ExecutableElement, ? extends AnnotationValue>  map =  el.getElementValues();
			for (ExecutableElement e : map.keySet()) {
				if ("name".equalsIgnoreCase(e.getSimpleName().toString())) {
					queryInfo.setMethodName(map.get(e).toString().replaceAll("\"", ""));
				}
			}
		}
		queries.put(queryInfo.getMethodName(), queryInfo);
	}

	public boolean postProcessWorkItemDefinition() {
		if (processingTypeName == null || processingTypeName.size() < 1) {
			return false;
		}
		try {
			generateSchema(processingTypeName);
		} catch (Exception e) {
			processingEnv.getMessager().printMessage(
					Diagnostic.Kind.ERROR,
					MessageFormat.format(
							"Error post-processing graphql annotations: {0}.",
							e.getMessage()));
		}
		return true;
	}

	public void generateSchema(Map<String, String> processingTypeName)
			throws IOException {
		// generate "all" wid
		StringBuilder buildr = new StringBuilder();
		if (queries.size() > 0) {
			buildr.append("type Query {").append("\r\n");
			for (String key : queries.keySet()) {
				QueryInfo info = queries.get(key);
				buildr.append(info.getMethodName()).append(" ( ");
				int k = 0;
				for (Param p: info.getParamsList()) {
					if (k>0) {
						buildr.append(", ");
					}
					buildr.append(p.getName()).append(": ").append(p.getType());
					if (p.isRequired()) {
						buildr.append("!");
					}
					k++;
				}
				buildr.append(" )");
				buildr.append(": ").append(info.getReturnType()).append("\r\n");
			}
			buildr.append("}").append("\n\r");
		}
		for (String key : processingTypeName.keySet()) {
			buildr.append(processingTypeName.get(key));
		}
		write(buildr.toString());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	private void write(String schema) throws IOException {
		FileObject f = processingEnv.getFiler().createResource(
				StandardLocation.CLASS_OUTPUT, "", "graphql.schema");
		try (Writer w = new OutputStreamWriter(f.openOutputStream(),
				StandardCharsets.UTF_8)) {
			w.write(schema);
		}
	}

	private Element asElement(TypeMirror m) {
		return processingEnv.getTypeUtils().asElement(m);
	}
}
