package com.mogh.cashyeah.services;

import com.mogh.cashyeah.models.BranchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public interface BranchService {

    @GET("/templates/pnbtheme/js/db.branch.js")
    Call<List<BranchResponse>> fetchBranchJSON();
}
