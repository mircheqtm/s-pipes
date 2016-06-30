package cz.cvut.sempipes.engine;

import cz.cvut.sempipes.JenaTestUtils;
import cz.cvut.sempipes.modules.Module;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.FileUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Miroslav Blasko on 7.6.16.
 */
public class PipelineFactoryTest {

    @Test
    public void loadPipelines() throws Exception {

        OntModel ontModel = JenaTestUtils.loadOntologyResource("/pipeline/config.ttl");

        List<Module> moduleList = PipelineFactory.loadPipelines(ontModel);
        assertEquals("Number of output modules of pipeline does not match", 2, moduleList.size());

//        Module module = moduleList.get(0);
//        System.out.println("Root module of pipeline is " + module);
//        ExecutionContext newContext = module.execute(ExecutionContextFactory.createContext(ontModel));
//        newContext.getDefaultModel().write(System.out, FileUtils.langTurtle);
    }

}