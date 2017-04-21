
package cz.cvut.sempipes.model;

import java.util.Map;
import java.util.Set;
import cz.cvut.kbss.jopa.CommonVocabulary;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLAnnotationProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraint;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.jopa.model.annotations.Properties;
import cz.cvut.kbss.jopa.model.annotations.Types;
import cz.cvut.sempipes.Vocabulary;


/**
 * This class was generated by the OWL2Java tool version $VERSION$
 * 
 */
@OWLClass(iri = Vocabulary.s_c_dataset_exploration)
public class dataset_exploration {

    @OWLAnnotationProperty(iri = CommonVocabulary.RDFS_LABEL)
    protected String name;
    @OWLAnnotationProperty(iri = CommonVocabulary.DC_DESCRIPTION)
    protected String description;
    @Types
    protected Set<String> types;
    @Id(generated = true)
    protected String id;
    @Properties
    protected Map<String, Set<String>> properties;
    @OWLObjectProperty(iri = Vocabulary.s_p_has_dataset_explorer)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset_exploring_journalist, min = 1, max = 1)
    })
    protected Set<Thing> has_dataset_explorer;
    @OWLObjectProperty(iri = Vocabulary.s_p_has_explored_dataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset, min = 1)
    })
    protected Set<Thing> has_explored_dataset;
    @OWLObjectProperty(iri = Vocabulary.s_p_uses)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_intent, min = 1)
    })
    protected Set<Thing> uses;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setProperties(Map<String, Set<String>> properties) {
        this.properties = properties;
    }

    public Map<String, Set<String>> getProperties() {
        return properties;
    }

    public void setHas_dataset_explorer(Set<Thing> has_dataset_explorer) {
        this.has_dataset_explorer = has_dataset_explorer;
    }

    public Set<Thing> getHas_dataset_explorer() {
        return has_dataset_explorer;
    }

    public void setHas_explored_dataset(Set<Thing> has_explored_dataset) {
        this.has_explored_dataset = has_explored_dataset;
    }

    public Set<Thing> getHas_explored_dataset() {
        return has_explored_dataset;
    }

    public void setUses(Set<Thing> uses) {
        this.uses = uses;
    }

    public Set<Thing> getUses() {
        return uses;
    }

}
