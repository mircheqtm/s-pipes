package cz.cvut.sempipes.modules;

import cz.cvut.sempipes.constants.KBSS_MODULE;
import cz.cvut.sempipes.engine.ExecutionContext;
import cz.cvut.sempipes.engine.ExecutionContextFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.deri.tarql.tarql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ModuleTarql extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleTarql.class);

    private static final String TYPE_URI = KBSS_MODULE.getURI() + "tarql" + "-XXX-2";

    private static Property getParameter(final String name) {
        return ResourceFactory.createProperty(TYPE_URI + name);
    }

    /**
     * File with the TARQL script
     */
    static final Property P_TARQL_STRING = getParameter("p-tarql-string");
    private String tarqlString;

    /**
     * Ontology IRI
     */
    static final Property P_ONTOLOGY_IRI = getParameter("p-ontology-iri");
    private String ontologyIRI;

    /**
     * Input File
     */
    static final Property P_INPUT_FILE = getParameter("p-input-file");
    private String inputFile;

//    /**
//     * No header
//     */
//    static final Property P_NO_HEADER = getParameter("p-no-header");
//    private boolean noHeader;

    @Override
    public ExecutionContext executeSelf() {
        LOG.info("Running TARQL on " + inputFile);
        Model model = ModelFactory.createDefaultModel();

        try {
            final File output = File.createTempFile("output", ".nt");

            try (PrintStream s = new PrintStream(new FileOutputStream(output))) {
                final File queryFile = File.createTempFile("query", ".tarql");
                final String queryString = tarqlString.replaceAll("\\?__FN__", "\"" + ontologyIRI + "\"");
                Files.write(Paths.get(queryFile.toURI()), queryString.getBytes());

                final PrintStream origStream = System.out;
                System.setOut(s);
                tarql.main(
                        "--ntriples",
//                        noHeader ? "-H" : "",
                        queryFile.getAbsolutePath(),
                        inputFile
                );
                System.setOut(origStream);

                model.read(new FileInputStream(output), "", "N-TRIPLE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExecutionContextFactory.createContext(model);
    }

    @Override
    public String getTypeURI() {
        return TYPE_URI;
    }

    @Override
    public void loadConfiguration() {
        inputFile = this.getStringPropertyValue(P_INPUT_FILE);
        tarqlString = this.getStringPropertyValue(P_TARQL_STRING);
        ontologyIRI = this.getStringPropertyValue(P_ONTOLOGY_IRI);
//        noHeader = this.getPropertyValue(P_NO_HEADER, false);
    }
}
