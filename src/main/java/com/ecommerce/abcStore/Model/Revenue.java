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
@Table(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "revenue_id")
    private int id;
    @Column(name="revenue")
    private long revenue;
    @Column(name="remain_Quantity")
    private int remainQuantity;


    //RelationTable

}
