package org.opendatamesh.platform.pp.registry.database.entities.sharedres;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "TemplateDefinition")
@Table(name = "DEF_TEMPLATES", schema="PUBLIC")
public class TemplateDefinition {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name = "SPECIFICATION_VERSION")
    private String specificationVersion;

    @Column(name = "CONTENT_MEDIA_TYPE")
    private String contentMediaType;

    @Column(name = "CONTENT")
    private String content;
}