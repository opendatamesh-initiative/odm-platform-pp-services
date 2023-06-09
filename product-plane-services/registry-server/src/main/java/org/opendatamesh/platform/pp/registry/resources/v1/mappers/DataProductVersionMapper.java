package org.opendatamesh.platform.pp.registry.resources.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.opendatamesh.platform.core.dpds.model.*;
import org.opendatamesh.platform.core.dpds.model.definitions.ApiDefinitionReferenceDPDS;
import org.opendatamesh.platform.core.dpds.model.definitions.DefinitionReferenceDPDS;
import org.opendatamesh.platform.pp.registry.database.entities.dataproduct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DataProductVersionMapper {

    DataProductVersion toEntity(DataProductVersionDPDS resource);
    DataProductVersionDPDS toResource(DataProductVersion entity);

    List<DataProductVersionDPDS> toResources(List<DataProductVersion> entities);
    
    InfoDPDS infoToInfoResource(Info entity);
    Info infoResourceToInfo(InfoDPDS entity);
   
    Port portResourceToPort(PortDPDS portDPDS);    
    ApplicationComponent applicationComponentResourceToApplicationComponent(ApplicationComponentDPDS applicationComponentDPDS);
    InfrastructuralComponent infrastructuralComponentResourceToInfrastructuralComponent(InfrastructuralComponentDPDS infrastructuralComponentDPDS);

    StandardDefinition toEntity(StandardDefinitionDPDS resource);
    StandardDefinitionDPDS toResource(StandardDefinition entity);

    ReferenceObject toEntity(ReferenceObjectDPDS resource);
    ReferenceObjectDPDS toResource(ReferenceObject entity);

    @SubclassMapping (target = ApiDefinitionReference.class, source = ApiDefinitionReferenceDPDS.class)
    DefinitionReference toEntity(DefinitionReferenceDPDS resource);
    DefinitionReferenceDPDS toResource(DefinitionReference entity);  
    
    ApiDefinitionReference toEntity(ApiDefinitionReferenceDPDS resource);
    ApiDefinitionReferenceDPDS toResource(ApiDefinitionReference entity);  

    ExternalResource toEntity(ExternalResourceDPDS resource);
    ExternalResourceDPDS toResource(ExternalResource entity);

    /* 
    @Mapping(source = "rawContent", target = "href", qualifiedByName = "rawContentToHref")
    ExternalResource toEntityTemplate(ReferenceObjectDPDS resource);

    @Named("rawContentToHref")
    static String rawContentToHref(String rawContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (rawContent == null)
            return null;
        ExternalResourceDPDS externalResourceDPDS = objectMapper.readValue(rawContent, ExternalResourceDPDS.class);
        return externalResourceDPDS.getHref();
    }
    */
}
