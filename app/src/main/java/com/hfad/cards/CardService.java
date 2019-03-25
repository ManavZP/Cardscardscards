package com.hfad.cards;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardService {

    @GET("api/deck/new/draw/?count=1")
    Call<CardResponse> search();
}
