package hcl.graphql.services.gqlfederation.tracktransfer;

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
    public GraphQLSchema customSchema(SchemaParser schemaParser, OrderService moneySentService,
                                      MoneyTransferService moneyTransferService){
        return Federation.transform(schemaParser.makeExecutableSchema())
                .fetchEntities(env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                        .stream()
                        .map(values -> {
                            if ("Order".equals(values.get("__typename"))) {
                                final Object id = values.get("id");
                                if (id instanceof String) {
                                    return moneySentService.lookupMoneySent((String) id);
                                }
                            }
                            if ("MoneyTransfer".equals(values.get("__typename"))) {
                                final Object mtcn = values.get("mtcn");
                                if (mtcn instanceof String) {
                                    return moneyTransferService.lookupTransfer((String) mtcn);
                                }
                            }
                            return null;
                        })
                        .collect(Collectors.toList())
                )
                .resolveEntityType(env -> {
                    final Object src = env.getObject();
                    if (src instanceof Order) {
                        return env.getSchema().getObjectType("Order");
                    }
                    if (src instanceof MoneyTransfer) {
                        return env.getSchema().getObjectType("MoneyTransfer");
                    }
                    return null;
                })
                .build();
    }
}
