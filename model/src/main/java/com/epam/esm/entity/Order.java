package com.epam.esm.entity;

import com.epam.esm.entity.audit.OrderAudit;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@EntityListeners(OrderAudit.class)
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id", nullable = false)
    private GiftCertificate giftCertificate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Order() {
    }

    public Order(Long id, BigDecimal price, User user, GiftCertificate giftCertificate, LocalDateTime purchaseTime) {
        this.id = id;
        this.price = price;
        this.giftCertificate = giftCertificate;
        this.user = user;
        this.purchaseTime = purchaseTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (id==null){
            if (order.id!=null){
                return false;
            }
        }else if (!id.equals(order.id)){
            return false;
        }
        if (user==null){
            if (order.user!=null){
                return false;
            }
        }else if (!user.equals(order.user)){
            return false;
        }
        if (purchaseTime==null){
            if (order.purchaseTime!=null){
                return false;
            }
        }else if (!purchaseTime.equals(order.purchaseTime)){
            return false;
        }
        if (price==null){
            if (order.price!=null){
                return false;
            }
        }else if (!price.equals(order.price)){
            return false;
        }
        if (giftCertificate==null){
            if (order.giftCertificate!=null){
                return false;
            }
        }else if (!giftCertificate.equals(order.giftCertificate)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + purchaseTime.hashCode();
        return result;
    }
}