package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "product_Specification")
public class ProductSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "weight")
    private String weight;
    @Column(name = "description",length = 3000)
    @NotEmpty(message = "*Please provide description")
    private String description;
    @Column(name ="brand")
    @NotEmpty(message = "*Please provide brand")
    private String brand;
    @Column(name="gender")
    @NotEmpty(message = "*Please provide gender")
    private String gender;
    @Column(name="madeIn")
    @NotEmpty(message = "*Please provide made in from")
    private String madeIn;
}
