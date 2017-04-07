package cz.cvut.sempipes.modules;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import cz.cvut.sempipes.constants.KBSS_MODULE;
import cz.cvut.sempipes.engine.ExecutionContext;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanUser;
import eu.trentorise.opendata.jackan.model.CkanCatalog;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ckan2RDFModule extends AnnotatedAbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(Ckan2RDFModule.class);

    public static final String TYPE_URI = KBSS_MODULE.uri + "ckan2rdf-v1";
    public static final String NS_DDO = "http://onto.fel.cvut.cz/ontologies/dataset-descriptor/";

    /**
     * URL of the CKAN server
     */
    @Parameter(urlPrefix = TYPE_URI+"/", name = "p-ckan-url")
    private String pCkanApiURL;

    /**
     * URL of the RDF4J repository
     */
    @Parameter(urlPrefix = TYPE_URI+"/", name = "p-rdf4j-repository-url")
    private String pRdf4jRepositoryURL;

    /**
     * URL of the RDF4J repository
     */
    @Parameter(urlPrefix = TYPE_URI+"/", name = "p-max-datasets")
    private Integer maxDatasets = Integer.MAX_VALUE;

    private Resource createInstance(final String classIRI, final Model m) {
        final Resource instance = ResourceFactory.createResource(classIRI+"-"+ Instant.now().toString());
        final Resource clazz = ResourceFactory.createResource(classIRI);
        m.add(instance, RDF.type, clazz);
        return instance;
    }

    private CkanDataset processDataset(EntityManager em, final String dataset, CkanClient client, Resource iDescription, Resource iDatasetSnapshot) {
        final Resource iDatasetSnapshotSub = createInstance(NS_DDO + "dataset-snapshot", executionContext.getDefaultModel());
        executionContext.getDefaultModel().add(iDatasetSnapshot, ResourceFactory.createProperty(NS_DDO + "has-sub-dataset-snapshot"), iDatasetSnapshotSub);

        final Resource iDescriptionSub = createInstance(NS_DDO + "description", executionContext.getDefaultModel());
        executionContext.getDefaultModel().add(iDescription,  ResourceFactory.createProperty(NS_DDO + "has-partial-description"), iDescriptionSub);
        executionContext.getDefaultModel().add(iDescriptionSub,  ResourceFactory.createProperty(NS_DDO + "is-description-of"), iDatasetSnapshotSub);

        CkanDataset ckanDataset = null;
        try{
            ckanDataset = client.getDataset(dataset);
            executionContext.getDefaultModel().add(iDatasetSnapshotSub, DC.source, ResourceFactory.createResource(ckanDataset.getIri()));
            em.getTransaction().begin();
            em.merge(ckanDataset
                    , new EntityDescriptor(URI.create(client.getCatalogUrl()))
            );
            em.getTransaction().commit();

            return ckanDataset;
        } catch(CkanException e) {
            executionContext.getDefaultModel().add(iDescriptionSub, ResourceFactory.createProperty(NS_DDO + "has-error-result") ,e.getMessage());
            LOG.warn("{}: Problem during dataset fetch {}",dataset, e.getMessage(),e);
        }
        return null;
    }

    private void processUser(EntityManager em, final CkanUser user, CkanClient client,  Resource iDescription, Resource iDatasetSnapshot) {

        final Resource iDatasetSnapshotSub = createInstance(NS_DDO + "dataset-snapshot", executionContext.getDefaultModel());
        executionContext.getDefaultModel().add(iDatasetSnapshot, ResourceFactory.createProperty(NS_DDO + "has-sub-dataset-snapshot"), iDatasetSnapshotSub);
        executionContext.getDefaultModel().add(iDatasetSnapshotSub, DC.source, ResourceFactory.createResource(user.getIri()));

        final Resource iDescriptionSub = createInstance(NS_DDO + "description", executionContext.getDefaultModel());
        executionContext.getDefaultModel().add(iDescription,  ResourceFactory.createProperty(NS_DDO + "has-partial-description"), iDescriptionSub);
        executionContext.getDefaultModel().add(iDescriptionSub,  ResourceFactory.createProperty(NS_DDO + "is-description-of"), iDatasetSnapshotSub);

        em.getTransaction().begin();
        em.merge(user
                , new EntityDescriptor(URI.create(client.getCatalogUrl()))
        );
        em.getTransaction().commit();
    }

    @Override
    ExecutionContext executeSelf() {
        final Map<String,String> props = new HashMap<>();
        props.put(JOPAPersistenceProperties.SCAN_PACKAGE, "eu.trentorise.opendata.jackan.model");
        props.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, pRdf4jRepositoryURL);
        PersistenceFactory.init(props);

        final Resource iDescription = createInstance(NS_DDO + "description", executionContext.getDefaultModel());
        final Resource iDatasetSnapshot = createInstance(NS_DDO + "dataset-snapshot", executionContext.getDefaultModel());
        executionContext.getDefaultModel().add(iDescription,  ResourceFactory.createProperty(NS_DDO + "is-description-of"), iDatasetSnapshot);
        executionContext.getDefaultModel().add(iDescription,  DC.source, ResourceFactory.createResource(pCkanApiURL));
        executionContext.getDefaultModel().add(ResourceFactory.createResource(pCkanApiURL),  RDF.type, ResourceFactory.createResource("http://onto.fel.cvut.cz/ontologies/org/ckan/catalog"));


        final EntityManager em = PersistenceFactory.createEntityManager();
        try {
            final CkanClient cc = new CkanClient(pCkanApiURL);
            final AtomicInteger i = new AtomicInteger();
            i.set(0);

            CkanCatalog catalog = new CkanCatalog();
            catalog.setIri(cc.getCatalogUrl());
            catalog.setDatasets(new HashSet<>());

            List<String> datasets = new ArrayList<>();
            try{
                datasets = cc.getDatasetList();
            } catch(CkanException e) {
                LOG.warn("Problem during datasets fetch {}",e.getMessage(),e);
                executionContext.getDefaultModel().add(iDescription, ResourceFactory.createProperty(NS_DDO + "has-error-result") ,e.getMessage());
            }
            int max = datasets.size();
            for (final String dataset : datasets) {
                LOG.info("Processing Dataset {} / {} - {}", i.incrementAndGet(), max, dataset);
                CkanDataset ckanDataset = processDataset(em, dataset,cc, iDescription, iDatasetSnapshot);
                if ( ckanDataset != null ) {
                    catalog.getDatasets().add(ckanDataset);
                    if ( i.get() > maxDatasets) {
                        LOG.info("Breaking execution {} / {} ",i.get(), maxDatasets);
                        break;
                    }
                }
            }

            i.set(0);
            List<CkanUser> userList = new ArrayList<>();
            max = 0;
            try{
                userList=cc.getUserList();
            } catch(CkanException e) {
                executionContext.getDefaultModel().add(iDescription, ResourceFactory.createProperty(NS_DDO + "has-error-result") ,e.getMessage());
                LOG.warn("Problem during userlist fetch {}",e.getMessage(),e);
            }
            max = userList.size();
            for (final CkanUser user : userList) {
                LOG.info("Processing User {} / {} - {}", i.incrementAndGet(), max, user.getId());
                processUser(em, user, cc, iDescription, iDatasetSnapshot);
            }

            em.getTransaction().begin();
            em.merge(catalog
                    , new EntityDescriptor(URI.create(cc.getCatalogUrl()))
            );
            em.getTransaction().commit();
        } catch(final Exception e) {
            executionContext.getDefaultModel().add(iDescription, ResourceFactory.createProperty(NS_DDO + "has-error-result") ,e.getMessage());
        } finally {
            em.close();
        }
        return executionContext;
    }

    @Override
    public String getTypeURI() {
        return TYPE_URI;
    }
}