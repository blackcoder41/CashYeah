package com.raketlabs.uhack.services;

import com.raketlabs.uhack.models.BranchResponse;

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
