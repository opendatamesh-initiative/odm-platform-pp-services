package org.opendatamesh.platform.pp.registry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.opendatamesh.platform.pp.registry.api.v1.resources.DefinitionResource;
import org.opendatamesh.platform.pp.registry.exceptions.OpenDataMeshAPIStandardError;
import org.opendatamesh.platform.pp.registry.resources.v1.ErrorRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TemplateDefinitionIT extends OpenDataMeshIT {

    @Before
    public void setup() {
        //objectMapper = DataProductDescriptor.buildObjectMapper();
    }

    // ======================================================================================
    // HAPPY PATH
    // ======================================================================================

    // ----------------------------------------
    // CREATE Template
    // ----------------------------------------

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateCreate() throws IOException {

        cleanState();

        // TEST 1: create a Template with all properties and verify the response
        DefinitionResource templateDefinitionRes = createTemplate(RESOURCE_TEMPLATE_1);
        assertThat(templateDefinitionRes.getId()).isEqualTo(1);
        verifyTemplate1(templateDefinitionRes);

        // TEST 1: create a Template without name and version
        templateDefinitionRes = resourceBuilder.readResourceFromFile(RESOURCE_TEMPLATE_1, DefinitionResource.class);
        templateDefinitionRes.setName(null);
        templateDefinitionRes.setVersion(null);
        templateDefinitionRes = createTemplate(templateDefinitionRes);
        assertThat(templateDefinitionRes.getVersion()).isEqualTo("1.0.0");



        // TEST 2: create a Template without version property and verify the response
        // ERROR SCENARIO

        // TEST 3: create a Template without name property and verify the response
        // ERROR SCENARIO

        // TEST 4: create a Template without name and version properties and verify the response
        // ERROR SCENARIO

    }

    

    // ----------------------------------------
    // READ Template
    // ----------------------------------------
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateReadAll() throws IOException {

        cleanState();

        createTemplate(RESOURCE_TEMPLATE_1);
        createTemplate(RESOURCE_TEMPLATE_2);

        ResponseEntity<DefinitionResource[]> getTemplateResponse = registryClient.readAllTemplateDefinitions();
        DefinitionResource[] templateResources = getTemplateResponse.getBody();
        verifyResponseEntity(getTemplateResponse, HttpStatus.OK, true);

        assertThat(getTemplateResponse.getBody().length).isEqualTo(2);
        assertThat(templateResources[0].getId()).isEqualTo(1);
        verifyTemplate1(templateResources[0]);
        verifyTemplate2(templateResources[1]);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateSearch() throws IOException {

        cleanState();

        createTemplate(RESOURCE_TEMPLATE_1);
        createTemplate(RESOURCE_TEMPLATE_2);

        // TEST 1: search by Media Type

        
        ResponseEntity<DefinitionResource[]> getTemplateResponse = registryClient.searchTemplateDefinitions(
                Optional.empty(), 
                Optional.empty(),
                Optional.empty(), 
                Optional.of("spec"), 
                Optional.empty()
        );
        DefinitionResource[] templateResources = getTemplateResponse.getBody();
        verifyResponseEntity(getTemplateResponse, HttpStatus.OK, true);

        for(int i = 0; i < templateResources.length; i++) {
            assertThat(templateResources[i].getSpecification()).isEqualTo("spec");
        }
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateReadOne() throws IOException {

        cleanState();

        DefinitionResource templateResource = createTemplate(RESOURCE_TEMPLATE_1);

        ResponseEntity<DefinitionResource> getTemplateResponse = registryClient.readOneTemplateDefinition(templateResource.getId());
        DefinitionResource templateRes = getTemplateResponse.getBody();

        verifyResponseEntity(getTemplateResponse, HttpStatus.OK, true);
        assertThat(templateRes.getDescription()).isEqualTo("test template-1");
        assertThat(templateRes.getContentMediaType()).isEqualTo("plain/text");
        //assertThat(templateRes.getRef()).isEqualTo("https://github.com/Giandom/tf-data-product-example.git");

    }

    // ----------------------------------------
    // UPDATE Template
    // ----------------------------------------

    // Templates are immutable

    // ----------------------------------------
    // DELETE Template
    // ----------------------------------------

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateDelete() throws IOException {

        cleanState();

        DefinitionResource templateResource = createTemplate(RESOURCE_TEMPLATE_1);

        ResponseEntity<Void> getTemplateResponse = registryClient.deleteTemplateDefinition(templateResource.getId(), Void.class);
        verifyResponseEntity(getTemplateResponse, HttpStatus.OK, false);

    }

    // ======================================================================================
    // ERROR PATH
    // ======================================================================================

    // ----------------------------------------
    // CREATE Template
    // ----------------------------------------

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateCreationError400Errors() throws IOException {

        cleanState();

        ResponseEntity<ErrorRes> errorResponse = null;

        // Test error SC400_12_TEMPLATE_IS_EMPTY
        String payload = null;
        errorResponse = registryClient.postTemplateDefinition(payload, ErrorRes.class);
        verifyResponseError(errorResponse,
                HttpStatus.BAD_REQUEST, OpenDataMeshAPIStandardError.SC400_14_TEMPLATE_IS_EMPTY);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateCreateError422Errors() throws IOException {

        cleanState();

        ResponseEntity<ErrorRes> errorResponse = null;

        createTemplate(RESOURCE_TEMPLATE_1);

        // TEST 1: try to register the same template again

        String payload = resourceBuilder.readResourceFromFile(RESOURCE_TEMPLATE_1);
        errorResponse = registryClient.postTemplateDefinition(payload, ErrorRes.class);
        verifyResponseError(errorResponse,
                HttpStatus.UNPROCESSABLE_ENTITY,
                OpenDataMeshAPIStandardError.SC422_13_TEMPLATE_ALREADY_EXISTS);

       // TEST 2: try to register a definition without setting the content
        DefinitionResource definitionRes = resourceBuilder.buildDefinition("template-1", "1.0.0", "application/json", null);
        errorResponse = registryClient.postApiDefinition(definitionRes, ErrorRes.class);
        verifyResponseError(errorResponse,
                HttpStatus.UNPROCESSABLE_ENTITY,
                OpenDataMeshAPIStandardError.SC422_08_DEFINITION_DOC_SYNTAX_IS_INVALID);

    }


    // ----------------------------------------
    // READ Template
    // ----------------------------------------

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateReadOne404Error() throws IOException {

        cleanState();

        ResponseEntity<ErrorRes> errorResponse = null;

        errorResponse = registryClient.readOneTemplateDefinition(1L, ErrorRes.class);
       
        verifyResponseError(
                errorResponse,
                HttpStatus.NOT_FOUND,
                OpenDataMeshAPIStandardError.SC404_05_TEMPLATE_NOT_FOUND
        );

    }


    // ----------------------------------------
    // DELETE Template
    // ----------------------------------------

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testTemplateDeleteError404Errors() {

        cleanState();

        ResponseEntity<ErrorRes> errorResponse = null;

        errorResponse = registryClient.deleteTemplateDefinition(1L,  ErrorRes.class);
        verifyResponseError(
                errorResponse,
                HttpStatus.NOT_FOUND,
                OpenDataMeshAPIStandardError.SC404_05_TEMPLATE_NOT_FOUND
        );

    }

    // ======================================================================================
    // PRIVATE METHODS
    // ======================================================================================

    // ----------------------------------------
    // Create test resources
    // ----------------------------------------

    // TODO ...as needed

    // ----------------------------------------
    // Verify test resources
    // ----------------------------------------

    private void verifyTemplate1(DefinitionResource templateDefinition) {
        assertThat(templateDefinition.getName()).isEqualTo("template-1");
        assertThat(templateDefinition.getVersion()).isEqualTo("1.0.1");
        assertThat(templateDefinition.getStatus()).isEqualTo("ACTIVE");
        assertThat(templateDefinition.getDisplayName()).isEqualTo("Github Template 1");
        assertThat(templateDefinition.getDescription()).isEqualTo("test template-1");
        assertThat(templateDefinition.getType()).isEqualTo("TEMPLATE");
        assertThat(templateDefinition.getContentMediaType()).isEqualTo("plain/text");
        assertThat(templateDefinition.getContent()).isEqualTo("https://github.com/Giandom/tf-data-product-example.git");
    }

    private void verifyTemplate2(DefinitionResource templateDefinition) {
        assertThat(templateDefinition.getName()).isEqualTo("template-2");
        assertThat(templateDefinition.getVersion()).isEqualTo("1.0.2");
        assertThat(templateDefinition.getStatus()).isEqualTo("ACTIVE");
        assertThat(templateDefinition.getDisplayName()).isEqualTo("Github Template 2");
        assertThat(templateDefinition.getDescription()).isEqualTo("test template-2");
        assertThat(templateDefinition.getType()).isEqualTo("TEMPLATE");
        assertThat(templateDefinition.getContentMediaType()).isEqualTo("application/json");
        
        try {
            ObjectNode contentNode = (ObjectNode) mapper.readTree(templateDefinition.getContent());
            assertThat(contentNode.get("repository").asText()).isEqualTo("https://github.com/Giandom/tf-data-product-example.git");
            assertThat(contentNode.get("tag").asText()).isEqualTo("v1.0.2");
        } catch (JsonProcessingException e) {
            fail("Impossible to parse response");
        }
    }

    // ----------------------------------------
    // UTILS
    // ----------------------------------------

    private void cleanState() {

        ResponseEntity<DefinitionResource[]> getTemplateResponse = registryClient.readAllTemplateDefinitions();
        DefinitionResource[] templateResources = getTemplateResponse.getBody();
        for (DefinitionResource templateResource : templateResources) {
            registryClient.deleteTemplateDefinition(templateResource.getId(), Void.class);
        }

    }
    
}
