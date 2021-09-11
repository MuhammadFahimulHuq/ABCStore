package com.ecommerce.abcStore.Model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "shipment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shipmentDate;
    @Column(name = "total_unit")
    private int totalUnit;
    @Column(name = "remaining_unit")
    private int remainingUnit;
    @Column(name ="total_unit_price")
    private float totalUnitPrice;



    //Relation table
    @OneToOne
    private Revenue revenue;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;
}
