package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "product_price")
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "price",nullable = false)
    private float price;
    @Column(name = "discount_price")
    private float discountPrice;
    @Column(name = "discount_percentage")
    private float discountPercentage;
}
