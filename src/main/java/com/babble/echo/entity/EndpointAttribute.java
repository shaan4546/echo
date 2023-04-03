package com.babble.echo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class EndpointAttribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    public EndpointAttribute(String verb, String path, EndpointAttributeResponse response) {
        this.verb = verb;
        this.path = path;
        this.response = response;
    }

    private String verb;
    private String path;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private EndpointAttributeResponse response;
}
