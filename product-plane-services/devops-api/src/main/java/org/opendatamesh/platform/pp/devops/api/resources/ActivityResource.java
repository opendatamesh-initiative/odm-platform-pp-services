package org.opendatamesh.platform.pp.devops.api.resources;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityResource {
    @JsonProperty("id")
    Long id;

    @JsonProperty("dataProductId")
    private String dataProductId;

    @JsonProperty("dataProductVersion")
    private String dataProductVersion;

    @JsonProperty("status")
    ActivityType type;

    @JsonProperty("status")
    ActivityStatus status;

    @JsonProperty("results")
    String results;

    @JsonProperty("errors")
    String errors;

    @JsonProperty("startedAt")
    private Date startedAt;

    @JsonProperty("finishedAt")
    private Date finishedAt;
}