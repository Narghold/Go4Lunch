package com.dubois.yann.go4lunch.model;

public class RestaurantChoice {
   private String placeId;
   private String userId;

   //Constructor
   public RestaurantChoice(String placeId, String userId) {
      this.placeId = placeId;
      this.userId = userId;
   }

   //Getter & Setter
   public String getPlaceId() {
      return placeId;
   }

   public void setPlaceId(String placeId) {
      this.placeId = placeId;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }
}
