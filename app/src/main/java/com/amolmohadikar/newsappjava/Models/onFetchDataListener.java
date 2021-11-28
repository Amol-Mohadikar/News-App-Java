package com.amolmohadikar.newsappjava.Models;

import java.util.List;

public interface onFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadline> headline , String message);
    void getError(String message);
}
