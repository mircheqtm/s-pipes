package cz.cvut.spipes.modules;

import cz.cvut.spipes.constants.SM;
import cz.cvut.spipes.constants.SML;
import cz.cvut.spipes.engine.ExecutionContext;
import cz.cvut.spipes.engine.ExecutionContextFactory;
import cz.cvut.spipes.engine.VariablesBinding;
import org.apache.jena.rdf.model.RDFNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Miroslav Blasko on 28.5.16.
 */
public class BindWithConstantModule extends AbstractModule  {

    private static final Logger LOG = LoggerFactory.getLogger(BindWithConstantModule.class);
    String outputVariable;
    RDFNode value;

    @Override
    public ExecutionContext executeSelf() {

        VariablesBinding bindings = new VariablesBinding(outputVariable, value);

        LOG.debug("\tBinding {} --> {}", outputVariable, value);

        return ExecutionContextFactory.createContext(
                executionContext.getDefaultModel(),
                bindings);
    }

    @Override
    public String getTypeURI() {
        return SML.BindWithConstant.getURI();
    }

    @Override
    public void loadConfiguration() {
        value = getEffectiveValue(SML.value);
        outputVariable = getStringPropertyValue(SM.outputVariable);
    }

    public String getOutputVariable() {
        return outputVariable;
    }

    public void setOutputVariable(String outputVariable) {
        this.outputVariable = outputVariable;
    }

    public RDFNode getValue() {
        return value;
    }

    public void setValue(RDFNode value) {
        this.value = value;
    }
}