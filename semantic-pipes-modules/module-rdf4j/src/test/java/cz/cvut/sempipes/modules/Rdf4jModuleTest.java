package cz.cvut.sempipes.modules;

import cz.cvut.sempipes.engine.ExecutionContext;
import cz.cvut.sempipes.engine.ExecutionContextFactory;
//import info.aduna.webapp.util.HttpServerUtil;
import org.apache.jena.rdf.model.*;
import org.junit.Ignore;

public class Rdf4jModuleTest {

    @org.junit.Test
    @Ignore
    public void testDeployEmpty() throws Exception {
        final Rdf4jModule moduleRdf4j = new Rdf4jModule();

        final Model deployModel = ModelFactory.createDefaultModel();
        final Property resource = ResourceFactory.createProperty("http://a");
        deployModel.add(resource, resource, resource);

        final ExecutionContext executionContext = ExecutionContextFactory.createContext(deployModel);

        final Model model = ModelFactory.createDefaultModel();
        final Resource root = model.createResource();
        model.add(root, Rdf4jModule.P_IS_REPLACE_CONTEXT_IRI, model.createTypedLiteral(true));
        model.add(root, Rdf4jModule.P_RDF4J_SERVER_URL, "http://localhost:18080/rdf4j-server");
        model.add(root, Rdf4jModule.P_RDF4J_REPOSITORY_NAME, "test-semantic-pipes");
        model.add(root, Rdf4jModule.P_RDF4J_CONTEXT_IRI, "");

        moduleRdf4j.setConfigurationResource(root);

        // TODO: currently running server is needed;
        moduleRdf4j.setInputContext(executionContext);
        moduleRdf4j.execute();
    }
}