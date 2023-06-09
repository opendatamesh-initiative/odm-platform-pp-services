package org.opendatamesh.platform.core.dpds.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractsDPDS {

    @JsonProperty("termsAndConditions")
    protected SpecificationExtensionPointDPDS termsAndConditions;

    @JsonProperty("billingPolicy")
    protected SpecificationExtensionPointDPDS billingPolicy;
   
    @JsonProperty("sla")
    protected SpecificationExtensionPointDPDS sla;
}
