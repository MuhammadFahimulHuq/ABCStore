package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.Review;
import com.ecommerce.abcStore.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review){
        return reviewRepository.save(review);
    }

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }
    public Review getReviewById(int id){
        return reviewRepository.findById(id).orElse(null);
    }

    public Review updateReview(int id,Review review){
        Review updateReview = getReviewById(id);
        updateReview.setRating(review.getRating());
        updateReview.setComment(review.getComment());
        return updateReview;
    }
    public String deleteCategory(int id){
        Review findReview = reviewRepository.findById(id).orElse(null);
        reviewRepository.delete(findReview);
        return "category id: "+ findReview.getId()+ " deleted" ;
    }


    public List<Review> getReviewByProductId(long productId) {
        return reviewRepository.findReviewByProductId(productId);
    }
}
