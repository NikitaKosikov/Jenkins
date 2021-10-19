package com.epam.esm.entity;

import com.epam.esm.entity.audit.GiftCertificateAudit;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gift_certificates")
@EntityListeners(GiftCertificateAudit.class)
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(name = "m2m_gift_certificates_tags", joinColumns = @JoinColumn(name = "gift_certificate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    public GiftCertificate() {
    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;

        if (id==null){
            if (that.id!=null){
                return false;
            }
        }else if (!id.equals(that.id)){
            return false;
        }
        if (name==null){
            if (that.name!=null){
                return false;
            }
        }else if (!name.equals(that.name)){
            return false;
        }
        if (description==null){
            if (that.description!=null){
                return false;
            }
        }else if (!description.equals(that.description)){
            return false;
        }
        if (price==null){
            if (that.price!=null){
                return false;
            }
        }else if (!price.equals(that.price)){
            return false;
        }
        if (createDate==null){
            if (that.createDate!=null){
                return false;
            }
        }else if (!createDate.equals(that.createDate)){
            return false;
        }
        if (lastUpdateDate==null){
            if (that.lastUpdateDate!=null){
                return false;
            }
        }else if (!lastUpdateDate.equals(that.lastUpdateDate)){
            return false;
        }
        if (tags==null){
            if (that.tags!=null){
                return false;
            }
        }else if (!tags.equals(that.tags)){
            return false;
        }

        return duration == that.duration;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        return result;
    }
}
