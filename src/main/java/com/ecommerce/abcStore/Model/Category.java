package com.ecommerce.abcStore.Model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty(message = "*Please provide a category")
    @Column(name = "category")
    private String categoryName;

    //RelationTable
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Product> products;
}
