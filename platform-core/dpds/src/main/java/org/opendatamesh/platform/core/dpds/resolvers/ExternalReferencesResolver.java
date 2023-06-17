package org.opendatamesh.platform.core.dpds.resolvers;

import java.net.URI;
import java.util.List;

import org.opendatamesh.platform.core.dpds.DataProductVersionSource;
import org.opendatamesh.platform.core.dpds.ObjectMapperFactory;
import org.opendatamesh.platform.core.dpds.exceptions.ParseException;
import org.opendatamesh.platform.core.dpds.exceptions.UnresolvableReferenceException;
import org.opendatamesh.platform.core.dpds.model.ComponentDPDS;
import org.opendatamesh.platform.core.dpds.model.DataProductVersionDPDS;
import org.opendatamesh.platform.core.dpds.model.EntityTypeDPDS;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalReferencesResolver implements PropertiesResolver{

    DataProductVersionDPDS dataProductVersion;
    DataProductVersionSource source;
    ObjectMapper mapper;

    public ExternalReferencesResolver(DataProductVersionDPDS dataProductVersionRes, DataProductVersionSource source) {
        this.dataProductVersion = dataProductVersionRes;
        this.source = source;
        this.mapper = ObjectMapperFactory.JSON_MAPPER;
    }

    @Override
    public void resolve() throws UnresolvableReferenceException, ParseException {
        DataProductVersionDPDS parsedContent =  dataProductVersion;

        if (parsedContent.getInterfaceComponents() != null) {
            resolveExternalReferences(parsedContent.getInterfaceComponents().getInputPorts(), 
                    EntityTypeDPDS.inputport);
            resolveExternalReferences(parsedContent.getInterfaceComponents().getOutputPorts(),
                    EntityTypeDPDS.outputport);
            resolveExternalReferences(parsedContent.getInterfaceComponents().getDiscoveryPorts(),
                    EntityTypeDPDS.discoveryport);
            resolveExternalReferences(parsedContent.getInterfaceComponents().getObservabilityPorts(),
                    EntityTypeDPDS.observabilityport);
            resolveExternalReferences(parsedContent.getInterfaceComponents().getControlPorts(),
                    EntityTypeDPDS.controlport);
        }
        if (parsedContent.getInternalComponents() != null) {
            resolveExternalReferences(parsedContent.getInternalComponents().getApplicationComponents(),
                    EntityTypeDPDS.application);
            resolveExternalReferences(parsedContent.getInternalComponents().getInfrastructuralComponents(),
                    EntityTypeDPDS.infrastructure);
        }
    }

    private <E extends ComponentDPDS> void resolveExternalReferences(List<E> components,
            EntityTypeDPDS compoEntityType) throws UnresolvableReferenceException {
        for (int i = 0; i < components.size(); i++) {
            E component = components.get(i);
            String ref = component.getRef();
            if (ref != null && !ref.trim().startsWith("#")) {
                components.set(i, resolveExternalReference(component));
            }
        }
    }

    private <E extends ComponentDPDS> E resolveExternalReference(E component)
            throws UnresolvableReferenceException {
        E resolvedComponent = null;
        String ref = component.getRef();
        if (ref == null || ref.startsWith("#")) {
            throw new UnresolvableReferenceException(
                    "Impossible to resolve external reference. Field [$ref] value [" + ref
                            + "] is not an URL to an external resource");
        }

        try {
            URI uri = new URI(ref).normalize();
            String content = source.fetchResource(uri);
            resolvedComponent = (E) mapper.readValue(content, component.getClass());
            resolvedComponent.setRawContent(content);
        } catch (Exception e) {
            throw new UnresolvableReferenceException(
                    "Impossible to resolve external reference [" + ref + "]",
                    e);
        }
        return resolvedComponent;
    }

    public static void resolve(DataProductVersionDPDS dataProductVersionRes, DataProductVersionSource source) throws UnresolvableReferenceException, ParseException {
        ExternalReferencesResolver resolver = new ExternalReferencesResolver(dataProductVersionRes, source);
        resolver.resolve();
    }
}