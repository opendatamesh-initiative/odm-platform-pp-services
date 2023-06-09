package org.opendatamesh.platform.pp.registry.resources.v1.mappers;

import org.mapstruct.Mapper;
import org.opendatamesh.platform.pp.registry.api.v1.resources.DataProductResource;
import org.opendatamesh.platform.pp.registry.database.entities.dataproduct.DataProduct;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DataProductMapper { 

    DataProduct toEntity(DataProductResource resource);
    DataProductResource toResource(DataProduct entity);

    List<DataProductResource> dataProductsToResources(List<DataProduct> entities);
}
