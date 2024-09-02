package com.example.shopbee.data;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.AmazonSearchResponse;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.SearchResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.data.model.api.UserVariationResponse;
import com.example.shopbee.data.remote.AmazonApiService;
import com.example.shopbee.ui.user_search.UserSearchViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class Repository {
    final AmazonApiService amazonApiService;
    private DatabaseReference databaseReference;
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();

    @Inject
    Repository(AmazonApiService amazonApiService) {
        this.amazonApiService = amazonApiService;
    }
    // query, page, country, sort_by, product_condition
    public Observable<AmazonSearchResponse> search(HashMap<String, String> query) {
        return amazonApiService.search(query);
    }

    public Observable<AmazonDealsResponse> getAmazonDealsResponse(HashMap<String, String> map) {
        return amazonApiService.getAmazonDeals(map);
    }
    public Observable<AmazonProductByCategoryResponse> getAmazonProductByCategory(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductByCategory(map);
    }

    public Observable<AmazonProductByCategoryResponse> getAmazonProductBySearching(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductBySearching(map);
    }
    public Observable<AmazonProductDetailsResponse> getAmazonProductDetails(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductDetails(map);
    }
    public Observable<List<String>> getUserSearchHistory() {
        return null;
    }
    
    public Observable<UserResponse> getUserInformation(String email) {
        return Observable.create(emitter -> {
            databaseReference = FirebaseDatabase.getInstance().getReference("user");
            Query query = databaseReference.orderByChild("email").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            UserResponse user = userSnapshot.getValue(UserResponse.class);
                            if (user != null) {
                                emitter.onNext(user);
                                userResponse.setValue(user);
                            }
                        }
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("No user found with the email: " + email));
                        userResponse.setValue(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(new Exception("The read failed: " + error.getMessage()));
                }
            });
        });
    }
    public Observable<ListOrderResponse> getListOrderInformation(String email){
        return Observable.create(emitter -> {
            databaseReference = FirebaseDatabase.getInstance().getReference("order");
            Query query = databaseReference.orderByChild("email").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                            ListOrderResponse listOrderResponseObj = new ListOrderResponse();
                            listOrderResponseObj.setEmail(orderSnapshot.child("email").getValue(String.class));

                            List<OrderResponse> orderList = new ArrayList<>();
                            for (DataSnapshot listOrderSnapshot : orderSnapshot.child("list_order").getChildren()) {
                                OrderResponse orderResponseObj = new OrderResponse();
                                orderResponseObj.setDate(listOrderSnapshot.child("date").getValue(String.class));
                                orderResponseObj.setQuantity(listOrderSnapshot.child("quantity").getValue(Integer.class));
                                orderResponseObj.setStatus(listOrderSnapshot.child("status").getValue(String.class));
                                orderResponseObj.setOrder_number(listOrderSnapshot.child("order_number").getValue(String.class));
                                orderResponseObj.setTracking_number(listOrderSnapshot.child("tracking_number").getValue(String.class));
                                orderResponseObj.setPayment(listOrderSnapshot.child("payment").getValue(String.class));
                                orderResponseObj.setDiscount(listOrderSnapshot.child("discount").getValue(String.class));

                                List<OrderDetailResponse> orderDetailList = new ArrayList<>();
                                for (DataSnapshot orderDetailSnapshot : listOrderSnapshot.child("order_detail").getChildren()) {
                                    OrderDetailResponse orderDetailResponseObj = new OrderDetailResponse();
                                    orderDetailResponseObj.setProduct_id(orderDetailSnapshot.child("product_id").getValue(String.class));
                                    orderDetailResponseObj.setProduct_name(orderDetailSnapshot.child("product_name").getValue(String.class));
                                    orderDetailResponseObj.setQuantity(orderDetailSnapshot.child("quantity").getValue(Integer.class));
                                    orderDetailResponseObj.setPrice(orderDetailSnapshot.child("price").getValue(String.class));
                                    orderDetailResponseObj.setUrlImage(orderDetailSnapshot.child("urlImage").getValue(String.class));
                                    Map<String, String> variationMap = (Map<String, String>) orderDetailSnapshot.child("variation").getValue();
                                    orderDetailResponseObj.setVariation(variationMap);
                                    orderDetailList.add(orderDetailResponseObj);
                                }
                                orderResponseObj.setOrder_detail(orderDetailList);
                                orderList.add(orderResponseObj);
                            }
                            listOrderResponseObj.setList_order(orderList);
                            listOrderResponse.setValue(listOrderResponseObj);
                            emitter.onNext(listOrderResponseObj);
                            emitter.onComplete();
                        }
                    } else {
                        listOrderResponse.setValue(null);
                        emitter.onError(new Exception("No user found with the email: " + email));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getMessage());
                }
            });
        });
    }
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public MutableLiveData<ListOrderResponse> getOrderResponse(){
        return listOrderResponse;
    }
    public void saveSearchHistory(String searchString) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("search_history");
        String userEmail = getUserResponse().getValue().getEmail();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // The user already exists, get the uniqueUserKey
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String uniqueUserKey = userSnapshot.getKey();
                        // Check if the search string already exists in history
                        boolean searchStringExists = false;
                        DataSnapshot historySnapshot = userSnapshot.child("history");

                        for (DataSnapshot searchSnapshot : historySnapshot.getChildren()) {
                            String existingSearchString = searchSnapshot.child("search").getValue(String.class);
                            if (searchString.equals(existingSearchString)) {
                                searchStringExists = true;

                                // Update the timestamp of the existing search string
                                searchSnapshot.getRef().child("timestamp").setValue(System.currentTimeMillis())
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("FirebaseDB", "Timestamp updated successfully.");
                                            } else {
                                                Log.e("FirebaseDB", "Failed to update timestamp.", task.getException());
                                            }
                                        });
                                break;
                            }
                        }

                        // If the search string does not exist, add it with a new key under "history"
                        if (!searchStringExists) {
//                            String newSearchKey = userSnapshot.child("history").getRef().push().getKey(); // Generate a unique key for the new search entry
//                            if (newSearchKey != null) {
                                Map<String, Object> newSearchData = new HashMap<>();
                                newSearchData.put("search", searchString);
                                newSearchData.put("timestamp", System.currentTimeMillis());

                                userSnapshot.child("history").getRef().push().setValue(newSearchData)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("FirebaseDB", "Search string added successfully.");
                                            } else {
                                                Log.e("FirebaseDB", "Failed to add search string.", task.getException());
                                            }
                                        });
//                            }
                        }
                    }
                } else {
                    // User does not exist, create a new user entry with search history
//                    String uniqueUserKey = databaseReference.push().getKey(); // Generate a unique key for the user

//                    if (uniqueUserKey != null) {
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("email", userEmail);

                        // Create a new search entry under "history"
//                        String newSearchKey = databaseReference.child(uniqueUserKey).child("history").push().getKey();
//                        Log.d("FirebaseDB", "New search key: " + newSearchKey);
//                        if (newSearchKey != null) {
                            DatabaseReference userReference = databaseReference.push();
                            userReference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("FirebaseDB", "User added successfully.");
                                    } else {
                                        Log.e("FirebaseDB", "Failed to add user.", task.getException());
                                    }
                                }
                            });
                            Map<String, Object> newSearchData = new HashMap<>();
                            newSearchData.put("search", searchString);
                            newSearchData.put("timestamp", System.currentTimeMillis());
                            userReference.child("history").push().setValue(newSearchData);
//                            // Set the user data and the initial search data
////                            Map<String, Object> initialData = new HashMap<>();
////                            initialData.put("email", userEmail);
////                            initialData.put("history/" + newSearchKey, newSearchData);
//                            userReference.push().setValue(newSearchData)
//                                    .addOnCompleteListener(task -> {
//                                        if (task.isSuccessful()) {
//                                            Log.d("FirebaseDB", "User and search string added successfully.");
//                                        } else {
//                                            Log.e("FirebaseDB", "Failed to add user and search string.", task.getException());
//                                        }
//                                    });
//                        }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDB", "Database error: " + error.getMessage());
            }
        });
    }


    public Observable<SearchResponse> getSearchHistory() {
        return Observable.create(emitter -> {
            SearchResponse searchResponse = new SearchResponse();
//            if (getUserResponse().getValue() == null) {
//                emitter.onError(new Throwable("User response is null"));
//                return;
//            }
            String email = getUserResponse().getValue().getEmail();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("search_history");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userEmail = userSnapshot.child("email").getValue(String.class);
                        if (userEmail.equals(email)) {
                            for (DataSnapshot historySnapshot : userSnapshot.child("history").getChildren()) {
                                String searchString = historySnapshot.child("search").getValue(String.class);
                                Long timestamp = historySnapshot.child("timestamp").getValue(Long.class);
                                searchResponse.addSearch(new SearchResponse.Search(searchString, timestamp));
                            }
                        }
                        // sort by timestamp, larger timestamp first
                        SearchResponse result = new SearchResponse();
                        for (int i = searchResponse.getSearches().size() - 1; i >=0; i--) {
                            result.addSearch(searchResponse.getSearches().get(i));
//                            Log.d("TAG", "onDataChange: " + searchResponse.getSearches().get(i).getSearch());
                            emitter.onNext(result);
                        }
                        emitter.onNext(result);
                        emitter.onComplete();
//                        return;
                        break;
                    }
//                    emitter.onNext(null);
//                    emitter.onComplete();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
    public Observable<String> deleteSearchHistory(String searchText) {
        return Observable.create(emitter -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("search_history");
            String userEmail = getUserResponse().getValue().getEmail();
            Query query = databaseReference.orderByChild("email").equalTo(userEmail);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Query subQuery = userSnapshot.child("history").getRef().orderByChild("search").equalTo(searchText);

                        subQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean isDeleted = false;  // To track if any deletions happened
                                for (DataSnapshot searchSnapshot : snapshot.getChildren()) {
                                    searchSnapshot.getRef().removeValue();  // Perform the delete
                                    isDeleted = true;  // Mark that we've deleted something
                                }

                                // Only emit after all deletions in this subQuery are complete
                                if (isDeleted) {
                                    emitter.onNext("complete");
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Throwable("No matching search history found to delete"));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                emitter.onError(new Throwable("Error deleting search history: " + error.getMessage()));
                            }
                        });
                    }

                    // If no matching user snapshot found
//                    if (!isDeleted) {
//                        emitter.onNext("complete"); // Or error handling if no data is found
//                        emitter.onComplete();
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(new Throwable("Error querying search history: " + error.getMessage()));
                }
            });
        });
    }
    public void updateUserFirebase(){
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Query query = databaseReference.orderByChild("email").equalTo(userResponse.getValue().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        UserResponse userResponseUpdated = userResponse.getValue();
                        userSnapshot.getRef().setValue(userResponseUpdated).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("TAG", "updateUserFirebase: Update user successfully");
                                }
                                else {
                                    Log.d("TAG", "updateUserFirebase: Update user failed");
                                }
                            }
                        });
                    }
                } else {
                    Log.d("TAG", "No user found with email: " + userResponse.getValue().getEmail());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public enum UserVariation {
        FAVORITE,
        BAG
    }
    public void saveUserVariation(UserVariation userVariation, String asin, List<Pair<String, String>> variation, Integer quantity) {
        isVariationInUserPick(userVariation, asin, variation)
                .subscribeOn(Schedulers.io())  // Run on IO thread
                .observeOn(AndroidSchedulers.mainThread())  // Observe on Main Thread
                .subscribe(isInUserPick -> {
                    if (isInUserPick) {
                        return;  // If variation is already in user pick, do nothing
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference("user_variations");
                    String userEmail = getUserResponse().getValue().getEmail();
                    Query query = databaseReference.orderByChild("email").equalTo(userEmail);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("email", userEmail);
                            DatabaseReference userReference;
                            HashMap<String, Object> variations = new HashMap<>();
                            variations.put("asin", asin);
                            if (variation == null || variation.isEmpty()) {
                                variations.put("variation", variation);
                            }
                            if (userVariation == UserVariation.BAG) {
                                variations.put("quantity", quantity);
                            }

                            if (!snapshot.exists()) {
                                userReference = databaseReference.push();
                                userReference.setValue(userMap);
                            } else {
                                userReference = snapshot.getChildren().iterator().next().getRef();
                            }

                            if (userVariation == UserVariation.FAVORITE) {
                                userReference.child("favorite").push().setValue(variations);
                            } else {
                                userReference.child("bag").push().setValue(variations);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }, throwable -> {
                    // Handle error
                });
    }

    public Observable<Boolean> isVariationInUserPick(UserVariation userVariation, String asin, List<Pair<String, String>> variation) {
        return Observable.create(emitter -> {
            String email = getUserResponse().getValue().getEmail();
            databaseReference = FirebaseDatabase.getInstance().getReference("user_variations");
            Query query = databaseReference.orderByChild("email").equalTo(email);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot variationTypeSnapshot = userVariation == UserVariation.FAVORITE
                                    ? userSnapshot.child("favorite")
                                    : userSnapshot.child("bag");

                            if (variationTypeSnapshot.exists()) {
                                for (DataSnapshot variationSnapshot : variationTypeSnapshot.getChildren()) {
                                    if (Objects.equals(variationSnapshot.child("asin").getValue(String.class), asin)) {
                                        if (!variationSnapshot.child("variation").exists()) {
                                            emitter.onNext(true);
                                            emitter.onComplete();
                                            return;
                                        }

                                        List<DataSnapshot> variationList = new ArrayList<>();
                                        for (DataSnapshot variationMap : variationSnapshot.child("variation").getChildren()) {
                                            variationList.add(variationMap);
                                        }

                                        if (variation.size() != variationList.size()) {
                                            continue;
                                        }

                                        boolean match = true;
                                        for (int i = 0; i < variation.size(); i++) {
                                            DataSnapshot variationMap = variationList.get(i);
                                            Map<String, String> map = (Map<String, String>) variationMap.getValue();
                                            Pair<String, String> pair = new Pair<>(map.get("first"), map.get("second"));
                                            if (!variation.get(i).first.equals(pair.first) || !variation.get(i).second.equals(pair.second)) {
                                                match = false;
                                                break;
                                            }
                                        }

                                        if (match) {
                                            emitter.onNext(true);
                                            emitter.onComplete();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    emitter.onNext(false);
                    emitter.onComplete();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }
    public Observable<UserVariationResponse> getUserVariation(UserVariation userVariation) {
        UserVariationResponse userVariationResponse = new UserVariationResponse();
        return Observable.create(emitter -> {
            String email = getUserResponse().getValue().getEmail();
            databaseReference = FirebaseDatabase.getInstance().getReference("user_variations");
            Query query = databaseReference.orderByChild("email").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            DatabaseReference variationReference;
                            if (userVariation == UserVariation.FAVORITE) {
                                variationReference = userSnapshot.getRef().child("favorite");
                            } else {
                                variationReference = userSnapshot.getRef().child("bag");
                            }
                            variationReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot variation : snapshot.getChildren()) {
                                            String asin = variation.child("asin").getValue(String.class);
                                            List<Pair<String, String>> variations = new ArrayList<>();
                                            if (variation.child("variation").exists()) {
                                                for (DataSnapshot variationReference : variation.child("variation").getChildren()) {
                                                    Map<String, String> map = (Map<String, String>) variationReference.getValue();
                                                    Pair<String, String> pair = new Pair<>(map.get("first"), map.get("second"));
                                                    variations.add(pair);
                                                }
                                            }
                                            Integer quantity = variation.child("quantity").getValue(Integer.class);
                                            UserVariationResponse.Variation userVariation = new UserVariationResponse.Variation(asin, variations, quantity);
                                            userVariationResponse.addVariation(userVariation);

                                        }
                                        UserVariationResponse result = new UserVariationResponse();
                                        for (int i = userVariationResponse.getVariations().size() - 1; i >= 0; i--) {
                                            result.addVariation(userVariationResponse.getVariations().get(i));
                                            emitter.onNext(result);
                                        }
                                        emitter.onComplete();
                                    } else {
                                        emitter.onNext(userVariationResponse);
                                        emitter.onComplete();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    else {
                        emitter.onNext(userVariationResponse);
                        emitter.onComplete();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
    public void deleteUserVariation(UserVariation userVariation, String asin, List<Pair<String, String>> variation) {
        String email = getUserResponse().getValue().getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference("user_variations");
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through user data
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot variationTypeSnapshot;
                        if (userVariation == UserVariation.FAVORITE) {
                            variationTypeSnapshot = userSnapshot.child("favorite");
                        } else {
                            variationTypeSnapshot = userSnapshot.child("bag");
                        }
                        for (DataSnapshot variationSnapshot : variationTypeSnapshot.getChildren()) {
                            if (!variationSnapshot.child("asin").getValue(String.class).equals(asin)) {
                                break;
                            }
                            else {
                                int index = 0;
                                for (DataSnapshot variationMap : variationSnapshot.child("variation").getChildren()) {
                                    Map<String, String> map = (Map<String, String>) variationMap.getValue();
                                    Pair<String, String> pair = new Pair<>(map.get("first"), map.get("second"));
                                    if (!variation.get(index).first.equals(pair.first) || !variation.get(index).second.equals(pair.second)) {
                                        break;
                                    }
                                    else {
                                        index++;
                                    }
                                }
                                variationSnapshot.getRef().removeValue();
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during query
            }
        });
    }
    public void updateUserBagVariation(String asin, List<Pair<String, String>> variation, boolean increase) {
        String email = getUserResponse().getValue().getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference("user_variations");
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through user data
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot variationTypeSnapshot;
                        variationTypeSnapshot = userSnapshot.child("bag");
                        for (DataSnapshot variationSnapshot : variationTypeSnapshot.getChildren()) {
                            if (!variationSnapshot.child("asin").getValue(String.class).equals(asin)) {
                                break;
                            }
                            else {
                                int index = 0;
                                for (DataSnapshot variationMap : variationSnapshot.child("variation").getChildren()) {
                                    Map<String, String> map = (Map<String, String>) variationMap.getValue();
                                    Pair<String, String> pair = new Pair<>(map.get("first"), map.get("second"));
                                    if (!variation.get(index).first.equals(pair.first) || !variation.get(index).second.equals(pair.second)) {
                                        break;
                                    }
                                    else {
                                        index++;
                                    }
                                }
                                Integer quantity = variationSnapshot.child("quantity").getValue(Integer.class);
                                if (increase) {
                                    quantity += 1;
                                    variationSnapshot.child("quantity").getRef().setValue(quantity);
                                }
                                else {
                                    quantity = Math.min(quantity - 1, 0);
                                    if (quantity == 0) {
                                        deleteUserVariation(UserVariation.BAG, asin, variation);
                                    }
                                    else {
                                        variationSnapshot.child("quantity").getRef().setValue(quantity);
                                    }
                                }
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during query
            }
        });
    }
}
