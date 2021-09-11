package com.ecommerce.abcStore.Model;

import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "product_name")
    private String productName;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "product_Image_Front")
    private byte[] productImageFront;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "product_Image_Back")
    private byte[] productImageBack;
    //Relation Table
    @ManyToOne(cascade=CascadeType.ALL)
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    private ProductSpec productSpec;
    @OneToOne(cascade = CascadeType.ALL)
    private ProductPrice productPrice;


    @OneToMany
    private Collection<Review> reviews;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Cart> carts;




    public String generateFrontImage()
    {
        return Base64.encodeBase64String(this.getProductImageFront());
    }
    public String generateBackImage()
    {
        return Base64.encodeBase64String(this.getProductImageBack());
    }


}
