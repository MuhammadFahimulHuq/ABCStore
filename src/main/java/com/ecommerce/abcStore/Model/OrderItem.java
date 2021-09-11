package com.ecommerce.abcStore.Model;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "orderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "quantity")
    private int quantity;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

}
