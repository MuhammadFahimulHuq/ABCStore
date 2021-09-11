package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "method_Type")
    private String methodType;
    @Column(name="method_amount")
    private float methodPricing;
    //RelationTable

}
