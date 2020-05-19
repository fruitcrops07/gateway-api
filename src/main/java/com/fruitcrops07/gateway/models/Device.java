package com.fruitcrops07.gateway.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "devices")
@JsonPropertyOrder({ "id", "vendor", "created_date", "status" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "DEFAULT '1'")
    private Integer id;

    @JsonProperty("gateway_serial_number")
    @Column(name = "gateway_serial_number")
    private String gatewaySerialNumber;

    @JsonProperty("vendor")
    @Column(name="vendor")
    private String vendor;

    @JsonProperty("created_date")
    @Column(name = "created_date")
    private Date createdDate;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;
}
