
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
@OWLClass(iri = Vocabulary.s_c_described_data_artifact)
public class DescribedDataArtifact
    extends Thing
{

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
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_describes)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_dataset_descriptor, min = 1)
    })
    protected Set<DatasetDescriptor> inv_dot_describes;
    @OWLObjectProperty(iri = Vocabulary.s_p_inv_dot_is_description_of)
    @ParticipationConstraints({
        @ParticipationConstraint(owlObjectIRI = Vocabulary.s_c_description, min = 1)
    })
    protected Set<Thing> inv_dot_is_description_of;

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

    public void setInv_dot_describes(Set<DatasetDescriptor> inv_dot_describes) {
        this.inv_dot_describes = inv_dot_describes;
    }

    public Set<DatasetDescriptor> getInv_dot_describes() {
        return inv_dot_describes;
    }

    public void setInv_dot_is_description_of(Set<Thing> inv_dot_is_description_of) {
        this.inv_dot_is_description_of = inv_dot_is_description_of;
    }

    public Set<Thing> getInv_dot_is_description_of() {
        return inv_dot_is_description_of;
    }

}
