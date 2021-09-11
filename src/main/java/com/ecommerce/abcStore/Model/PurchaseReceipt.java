package com.ecommerce.abcStore.Model;


import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "purchaseReceipt")
public class PurchaseReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @Column(name = "contact_number")
    @NotEmpty(message = "*Please provide your Contact Number")
    private String contactNumber;
    @Column(name = "total")
    @NotNull
    private float total;

//    @Column(name = "totalPriceWithShipmentCost",nullable = false)
//    private float totalPriceWithShipmentCost;

    //RelationTable
    @ManyToOne(cascade = CascadeType.ALL)
    private PaymentMethod paymentMethod;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;


}
