package com.babble.echo.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.io.Serializable;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private EndpointAttribute attributes;

    public boolean validate() {
        if(this.getType() == null || this.getAttributes() == null || this.getAttributes().getPath() == null ||
        this.getAttributes().getVerb() == null || this.getAttributes().getResponse() == null ||
                this.getAttributes().getResponse().getBody() == null ||
                this.getAttributes().getResponse().getCode() == null){
            return false;
        }
        return true;
    }
}
