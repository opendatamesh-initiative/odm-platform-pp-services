package org.opendatamesh.platform.up.notification.api.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResource implements Cloneable{
    
    @JsonProperty("id")
    Long id;

    @JsonProperty("event")
    EventResource event;

    @JsonProperty("status")
    private NotificationStatus status;

    @JsonProperty("processingOutput")
    private String processingOutput;

    @JsonProperty("receivedAt")
    private Date receivedAt;

    @JsonProperty("processedAt")
    private Date processedAt;
}
