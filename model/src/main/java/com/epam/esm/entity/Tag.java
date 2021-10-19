package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag extends RepresentationModel<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        if (id==null){
            if (tag.id!=null){
                return false;
            }
        }else if (!id.equals(tag.id)){
            return false;
        }
        if (name==null){
            if (tag.name!=null){
                return false;
            }
        }else if (!name.equals(tag.name)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
