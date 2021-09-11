package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "city")
    @NotEmpty(message = "*Please provide your city.")
    private String city;
    @Column(name = "postal_code")
    @NotEmpty(message = "*Please provide postal code.")
    private String postalCode;
    @Column(name = "block")
    @NotEmpty(message = "*Please provide your block number.")
    private String block;
    @Column(name = "road")
    @NotEmpty(message = "*Please provide your road number.")
    private String road;
    @Column(name = "house")
    @NotEmpty(message = "*Please provide your house number.")
    private String house;
    @Column(name = "area")
    @NotEmpty(message = "*Please provide your area name.")
    private String area;

    //RelationTable
    @OneToOne
    private PurchaseReceipt purchaseReceipt;

}
