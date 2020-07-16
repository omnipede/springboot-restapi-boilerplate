package io.omnipede.springbootrestapiboilerplate.domain.purchase.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="member_product")
@Getter
@Setter
@NoArgsConstructor
public class MemberProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public MemberProduct(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updated_at;
}
