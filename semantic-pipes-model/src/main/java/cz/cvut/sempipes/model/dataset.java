
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
@OWLClass(iri = Vocabulary.s_c_dataset)
public class dataset {

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
    @OWLObjectProperty(iri = Vocabulary.s_p_has_subdataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset, min = 2)
    })
    protected Set<Thing> has_subdataset;
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_offers_dataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset_source, min = 1)
    })
    protected Set<Thing> inv_dot_offers_dataset;
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_has_subdataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset, min = 1, max = 1)
    })
    protected Set<Thing> inv_dot_has_subdataset;
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_has_explored_dataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset_exploration, min = 1)
    })
    protected Set<Thing> inv_dot_has_explored_dataset;
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_has_dataset)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset_snapshot, min = 1)
    })
    protected Set<Thing> inv_dot_has_dataset;

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

    public void setHas_subdataset(Set<Thing> has_subdataset) {
        this.has_subdataset = has_subdataset;
    }

    public Set<Thing> getHas_subdataset() {
        return has_subdataset;
    }

    public void setInv_dot_offers_dataset(Set<Thing> inv_dot_offers_dataset) {
        this.inv_dot_offers_dataset = inv_dot_offers_dataset;
    }

    public Set<Thing> getInv_dot_offers_dataset() {
        return inv_dot_offers_dataset;
    }

    public void setInv_dot_has_subdataset(Set<Thing> inv_dot_has_subdataset) {
        this.inv_dot_has_subdataset = inv_dot_has_subdataset;
    }

    public Set<Thing> getInv_dot_has_subdataset() {
        return inv_dot_has_subdataset;
    }

    public void setInv_dot_has_explored_dataset(Set<Thing> inv_dot_has_explored_dataset) {
        this.inv_dot_has_explored_dataset = inv_dot_has_explored_dataset;
    }

    public Set<Thing> getInv_dot_has_explored_dataset() {
        return inv_dot_has_explored_dataset;
    }

    public void setInv_dot_has_dataset(Set<Thing> inv_dot_has_dataset) {
        this.inv_dot_has_dataset = inv_dot_has_dataset;
    }

    public Set<Thing> getInv_dot_has_dataset() {
        return inv_dot_has_dataset;
    }

}
