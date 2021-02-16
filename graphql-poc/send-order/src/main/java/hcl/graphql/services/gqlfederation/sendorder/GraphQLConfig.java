package hcl.graphql.services.gqlfederation.sendorder;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQLSchema customSchema(SchemaParser schemaParser, UserService userService,
    		OrderService orderService){
        return Federation.transform(schemaParser.makeExecutableSchema())
                .fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(values -> {
                            if ("User".equals(values.get("__typename"))) {
                                final Object id = values.get("id");
                                if (id instanceof String) {
                                    return userService.lookupUser((String) id);
                                }
                            }
                            if ("Order".equals(values.get("__typename"))) {
                                final Object id = values.get("id");
                                if (id instanceof String) {
                                    return orderService.lookupOrder((String) id);
                                }
                            }
                            return null;
                        })
                        .collect(Collectors.toList()))
                .resolveEntityType(env -> {
                    final Object src = env.getObject();
                    if (src instanceof User) {
                        return env.getSchema().getObjectType("User");
                    } else if (src instanceof Order) {
                        return env.getSchema().getObjectType("Order");
                    }
                    return null;
                })
                .build();
    }
}
