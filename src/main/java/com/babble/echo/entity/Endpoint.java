package com.babble.echo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Endpoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String type;

    public Endpoint(String type, EndpointAttribute attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private EndpointAttribute attributes;

    public boolean validate() {
        if(this.getType() == null || this.getAttributes() == null || this.getAttributes().getPath() == null ||
        this.getAttributes().getVerb() == null || this.getAttributes().getResponse() == null ||
                this.getAttributes().getResponse().getBody() == null ||
                this.getAttributes().getResponse().getCode() == null || this.getAttributes().getPath().startsWith("/endpoint")){
            return false;
        }
        return true;
    }
}
