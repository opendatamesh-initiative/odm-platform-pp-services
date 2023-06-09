package org.opendatamesh.platform.pp.registry.database.entities.dataproduct;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class InternalComponents {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    private List<ApplicationComponent> applicationComponents = new ArrayList<ApplicationComponent>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    private List<InfrastructuralComponent> infrastructuralComponents = new ArrayList<InfrastructuralComponent>();

    private static final Logger logger = LoggerFactory.getLogger(InternalComponents.class);

    public boolean hasApplicationComponents() {
        return applicationComponents != null && !applicationComponents.isEmpty();
    }

     public boolean hasInfrastructuralComponents() {
        return infrastructuralComponents != null && !infrastructuralComponents.isEmpty();
    }
    
}
