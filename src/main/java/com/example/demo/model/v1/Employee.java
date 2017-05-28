package com.example.demo.model.v1;

import com.example.demo.webservice.v1.view.Detail;
import com.example.demo.webservice.v1.view.OnlyId;
import com.example.demo.webservice.v1.view.Summary;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode(of = {"id"})
public class Employee {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView({ OnlyId.class, Summary.class })
    String id;

    @JsonView({ Summary.class, Detail.class })
    String name;

    @JsonView({ Summary.class, Detail.class })
    String firstname;

}
