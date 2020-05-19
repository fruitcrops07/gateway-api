package com.fruitcrops07.gateway.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "gateways")
@JsonPropertyOrder({ "serial_number", "name", "address", "periphirals" })
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class Gateway {

    @Id
    @JsonProperty("serial_number")
    @Column(name = "serial_number")
    private String serialNumber;

    @Column
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "gatewaySerialNumber")
    private List<Device> periphirals;
}
