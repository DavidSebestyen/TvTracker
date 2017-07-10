package com.davids.android.tvtracker.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by krypt on 04/07/2017.
 */

public class SeasonList {

    @SerializedName("created_by")
    private List<Season> createdBy;

    @SerializedName("genres")
    private List<Season> genres;

    @SerializedName("networks")
    private List<Season> networks;

    @SerializedName("production_companies")
    private List<Season> productionCompanies;
    @SerializedName("seasons")
    private List<Season> seasons;



    public List<Season> getSeasons(){
        return seasons;
    }

    public void setSeasons(List<Season> seasons){
        this.seasons = seasons;
    }

    public List<Season> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<Season> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Season> getGenres() {
        return genres;
    }

    public void setGenres(List<Season> genres) {
        this.genres = genres;
    }

    public List<Season> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Season> networks) {
        this.networks = networks;
    }

    public List<Season> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Season> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
}
