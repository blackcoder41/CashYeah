package com.raketlabs.uhack.services.connection.models;

/**
 * Created by vergilsantos on 02/12/2017.
 */

public class APIRequestWithToken extends APIRequest
{
    private String tokenId;

    public String getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }
}
