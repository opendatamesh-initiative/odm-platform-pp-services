package org.opendatamesh.platform.pp.registry.database.entities.dataproduct;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.opendatamesh.platform.core.dpds.model.EntityTypeDPDS;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class InterfaceComponents {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    @Where(clause = "\"ENTITY_TYPE\" = 'inputport'")
    private List<Port> inputPorts = new ArrayList<Port>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    @Where(clause = "\"ENTITY_TYPE\" = 'outputport'")
    private List<Port> outputPorts = new ArrayList<Port>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    @Where(clause = "\"ENTITY_TYPE\" = 'discoveryport'")
    private List<Port> discoveryPorts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    @Where(clause = "\"ENTITY_TYPE\" = 'observabilityport'")
    private List<Port> observabilityPorts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumns( {
            @JoinColumn(name = "DATA_PRODUCT_ID"),
            @JoinColumn(name = "DATA_PRODUCT_VERSION")
    })
    @Where(clause = "\"ENTITY_TYPE\" = 'controlport'")
    private List<Port> controlPorts = new ArrayList<>();

   
    public List<Port> getPortListByEntityType(EntityTypeDPDS entityType){
        switch (entityType){
            case outputport:
                return outputPorts;
            case inputport:
                return inputPorts;
            case controlport:
                return controlPorts;
            case discoveryport:
                return discoveryPorts;
            case observabilityport:
                return observabilityPorts;
            default:
                return null;
        }
    }
}
