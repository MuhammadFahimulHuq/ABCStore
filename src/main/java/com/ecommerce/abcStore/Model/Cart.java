package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private int id;
//    @Column(name = "total_price")
//    private float totalPrice;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    //Relation Table
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private PurchaseReceipt purchaseReceipt;

    @Transient
    public float getSubTotal() {
        if (this.product.getProductPrice().getDiscountPrice() == 0.0) {
            return this.product.getProductPrice().getPrice() * quantity;
        }
        else {
          return this.product.getProductPrice().getDiscountPrice()*quantity;
        }
    }
}
