package com.ecommerce.abcStore.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private int id;
    @Column(name = "rating")
    private String rating;
    @Column(name = "created_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modify_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyAt;
    @Column(name = "comment")
    @NotEmpty(message = "*Provide a comment")
    private String comment;
    //Relation Table
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
}
