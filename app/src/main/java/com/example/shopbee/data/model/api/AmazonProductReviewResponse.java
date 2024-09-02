package com.example.shopbee.data.model.api;

import java.util.List;

public class AmazonProductReviewResponse {
    Data data;
    static public class Data {
        int total_reviews;
        int total_ratings;
        List<Review> reviews;
        static public class Review {
            String review_id;
            String review_title;
            String review_comment;
            String review_star_rating;
            String review_link;
            String review_author;
            String review_author_avatar;
            List<String> review_images;
            static public class ReviewVideo {
                String stream_url;
                String closed_captions_url;
                String thumbnail_url;
            }
            ReviewVideo review_video;
            String review_date;
            String helpful_vote_statement;

            public String getReview_id() {
                return review_id;
            }

            public void setReview_id(String review_id) {
                this.review_id = review_id;
            }

            public String getReview_title() {
                return review_title;
            }

            public void setReview_title(String review_title) {
                this.review_title = review_title;
            }

            public String getReview_comment() {
                return review_comment;
            }

            public void setReview_comment(String review_comment) {
                this.review_comment = review_comment;
            }

            public String getReview_star_rating() {
                return review_star_rating;
            }

            public void setReview_star_rating(String review_star_rating) {
                this.review_star_rating = review_star_rating;
            }

            public String getReview_link() {
                return review_link;
            }

            public void setReview_link(String review_link) {
                this.review_link = review_link;
            }

            public String getReview_author() {
                return review_author;
            }

            public void setReview_author(String review_author) {
                this.review_author = review_author;
            }

            public String getReview_author_avatar() {
                return review_author_avatar;
            }

            public void setReview_author_avatar(String review_author_avatar) {
                this.review_author_avatar = review_author_avatar;
            }

            public List<String> getReview_images() {
                return review_images;
            }

            public void setReview_images(List<String> review_images) {
                this.review_images = review_images;
            }

            public ReviewVideo getReview_video() {
                return review_video;
            }

            public void setReview_video(ReviewVideo review_video) {
                this.review_video = review_video;
            }

            public String getReview_date() {
                return review_date;
            }

            public void setReview_date(String review_date) {
                this.review_date = review_date;
            }

            public String getHelpful_vote_statement() {
                return helpful_vote_statement;
            }

            public void setHelpful_vote_statement(String helpful_vote_statement) {
                this.helpful_vote_statement = helpful_vote_statement;
            }
        }

        public int getTotal_reviews() {
            return total_reviews;
        }

        public void setTotal_reviews(int total_reviews) {
            this.total_reviews = total_reviews;
        }

        public int getTotal_ratings() {
            return total_ratings;
        }

        public void setTotal_ratings(int total_ratings) {
            this.total_ratings = total_ratings;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
