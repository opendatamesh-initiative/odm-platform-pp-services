package org.opendatamesh.platform.pp.registry.resources.v1.dataproduct;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class ComponentContainerResource {

    public void setRawContent(List<? extends ComponentResource> components, ArrayNode componentNodes) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < componentNodes.size(); i++) {
            JsonNode componentNode = componentNodes.get(i);
            String rawContent = objectMapper.writeValueAsString(componentNode);
            components.get(i).setRawContent(rawContent);
        }
    }

    @JsonIgnore
    public ArrayNode getRawContent(List<? extends ComponentResource> components) throws JsonProcessingException  {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode componentNodes = objectMapper.createArrayNode();
        for (ComponentResource component : components) {
            String componentRawContent = component.getRawContent();
            ObjectNode componetNode = (ObjectNode)objectMapper.readTree(componentRawContent);
            componentNodes.add(componetNode);
        }

        return componentNodes;
    }

}
