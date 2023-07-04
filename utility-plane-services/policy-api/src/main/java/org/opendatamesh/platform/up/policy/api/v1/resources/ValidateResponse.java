package org.opendatamesh.platform.up.policy.api.v1.resources;

import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class ValidateResponse {

  @ElementCollection
  private List<ValidatedPolicyResource> validatedPolicies;

  @ElementCollection
  private List<ValidatedSuiteResource> validatedSuites;
  
}