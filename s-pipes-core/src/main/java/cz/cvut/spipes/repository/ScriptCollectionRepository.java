package cz.cvut.spipes.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * Unified interface for collection of scripts.
 *
 */
public interface ScriptCollectionRepository extends Repository {


    @NotNull List<Resource> getModules(@Nullable Collection<String> contexts);

    @NotNull List<Resource> getModuleTypes(@Nullable Collection<String> contexts);

    @NotNull List<Resource> getFunctions(@Nullable Collection<String> contexts);

    Resource getResource(@NotNull String resourceUri, String context);



    /**
     * For each entity resource uri such as module, module-type, function it returns alternative names that
     * could be localName, prefixed-name, uris referenced by sameAs relation, some property defining id etc...
     * @return
     */
    Map<String, Set<String>> getAlternativeEntityIds();

    OntModel getContextClosure(String context);
}
