package org.opendatamesh.platform.core.dpds;

import java.util.Set;

import org.opendatamesh.platform.core.dpds.exceptions.BuildException;
import org.opendatamesh.platform.core.dpds.exceptions.FetchException;
import org.opendatamesh.platform.core.dpds.exceptions.ParseException;
import org.opendatamesh.platform.core.dpds.exceptions.UnresolvableReferenceException;
import org.opendatamesh.platform.core.dpds.exceptions.ValidationException;
import org.opendatamesh.platform.core.dpds.model.DataProductVersionDPDS;
import org.opendatamesh.platform.core.dpds.resolvers.ExternalReferencesResolver;
import org.opendatamesh.platform.core.dpds.resolvers.InternalReferencesResolver;
import org.opendatamesh.platform.core.dpds.resolvers.ReadOnlyPropertiesResolver;
import org.opendatamesh.platform.core.dpds.resolvers.StandardDefinitionsResolver;
import org.opendatamesh.platform.core.dpds.resolvers.TemplatesResolver;
import org.opendatamesh.platform.core.dpds.serde.DataProductVersionSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

import lombok.Data;

@Data
public class DataProductVersionBuilder {

    DataProductVersionSource source;
    DataProductVersionDPDS dataProductVersion;
    
    private String targetURL;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProductVersionBuilder.class);
    

    public DataProductVersionBuilder(DataProductVersionSource source, String serverUrl) {
        this.source = source;
        this.targetURL = serverUrl;
    }

    public DataProductVersionBuilder validateSchema() throws ParseException, ValidationException {
        Set<ValidationMessage> errors;

        // TODO validate against the right schema version
        DataProductVersionValidator schemaValidator = new DataProductVersionValidator();

        DataProductVersionSerializer serializer = new DataProductVersionSerializer();
        String serailizedContent = null;

        try {
            serailizedContent = serializer.serialize(dataProductVersion, "canonical", "json", false);
        } catch (JsonProcessingException e) {
           throw new ParseException("Impossible to serialize data product version raw content", e);
        }
        errors = schemaValidator.validateSchema(serailizedContent);
        
        if (!errors.isEmpty()) {
            throw new ValidationException("Descriptor document does not comply with DPDS. The following validation errors has been found during validation [" + errors.toString() + "]", errors);
        }
        
        return this;
    }

    public DataProductVersionBuilder buildRootDoc(boolean validate) throws BuildException {
        try {
            String rawContent = source.fetchRootDoc();
            dataProductVersion = ObjectMapperFactory.getRightMapper(rawContent).readValue(rawContent, DataProductVersionDPDS.class);
            dataProductVersion.setRawContent(rawContent);
            if(validate) {
                validateSchema();
            }
        } catch (FetchException | ParseException | ValidationException | JsonProcessingException e) {
            throw new BuildException("Impossible to build root descriptor document",
                BuildException.Stage.LOAD_ROOT_DOC, e);
        }
        
        return this;
    }

    public DataProductVersionBuilder buildExternalReferences(boolean validate) throws BuildException {
        try {
            ExternalReferencesResolver.resolve(dataProductVersion, source);
            if(validate) {
                validateSchema();
            }
        } catch (UnresolvableReferenceException | ParseException | ValidationException e) {
            throw new BuildException("Impossible to build external reference of root descriptor document",
                BuildException.Stage.RESOLVE_EXTERNAL_REFERENCES, e);
        } 
        return this;
    }

    public DataProductVersionBuilder buildReadOnlyProperties() throws BuildException {
        
        try {
            ReadOnlyPropertiesResolver.resolve(dataProductVersion, source);
        } catch (ParseException e) {
            throw new BuildException("Impossible to build read only properties",
                BuildException.Stage.RESOLVE_READ_ONLY_PROPERTIES, e);
        }
       
        return this;
    }

    public DataProductVersionBuilder buildTemplates() throws BuildException {
        
        try {
            TemplatesResolver.resolve(dataProductVersion, source, targetURL);
        } catch (UnresolvableReferenceException | ParseException e) {
            throw new BuildException("Impossible to build template properties",
                BuildException.Stage.RESOLVE_TEMPLATE_PROPERTIES, e);
        }
       
        return this;
    }

    

    public DataProductVersionBuilder buildStandardDefinition() throws BuildException {
              
        try {
            StandardDefinitionsResolver.resolve(dataProductVersion, source, targetURL);
        } catch (UnresolvableReferenceException | ParseException e) {
            throw new BuildException("Impossible to build standard definitions",
                BuildException.Stage.RESOLVE_STANDARD_DEFINITIONS, e);
        }
       
       
        return this;
    }
   
    public DataProductVersionBuilder buildInternalReferences(boolean validate) throws BuildException {
        try {
            InternalReferencesResolver.resolve(dataProductVersion, source);
            if(validate) {
                validateSchema();
            }
        } catch (UnresolvableReferenceException | ParseException | ValidationException e) {
            throw new BuildException("Impossible to build internal reference of root descriptor document",
                BuildException.Stage.RESOLVE_INTERNAL_REFERENCES, e);
        } 
        return this;
    }

    public DataProductVersionDPDS build(boolean validate) throws BuildException  {
        buildRootDoc(true)
            .buildExternalReferences(validate)
            .buildInternalReferences(validate)
            .buildReadOnlyProperties()
            .buildStandardDefinition()
            .buildTemplates();
        return dataProductVersion;
    } 


    
}