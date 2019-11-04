package com.web.service;

import com.web.bean.Response.CommonResponse;

import java.io.IOException;

public interface NewsItemService {

    CommonResponse getAllNewsItem() throws IOException;

    CommonResponse getAllNewsByUser(Long userID) throws IOException;
}
