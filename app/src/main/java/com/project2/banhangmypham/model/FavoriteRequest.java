package com.project2.banhangmypham.model;

public class FavoriteRequest {
    private String productId ;
    private String userId ;
    private boolean isFavorite ;

    public FavoriteRequest(String productId, String userId, boolean isFavorite) {
        this.productId = productId;
        this.userId = userId;
        this.isFavorite = isFavorite;
    }

    public FavoriteRequest(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
