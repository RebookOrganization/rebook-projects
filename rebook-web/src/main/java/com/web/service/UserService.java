package com.web.service;

import com.web.bean.Request.CommentRequest;
import com.web.bean.Request.LikeRequest;
import com.web.bean.Request.PostNewsRequest;
import com.web.bean.Request.ShareRequest;
import com.web.bean.Request.UpdateUserProfileRequest;
import com.web.bean.Response.CommonResponse;
import com.web.model.User;
import java.io.IOException;

public interface UserService {

  CommonResponse createNewsPost(PostNewsRequest request) throws IOException;

  CommonResponse likeNewsFeed(LikeRequest request) throws IOException;

  CommonResponse commentNewsFeed(CommentRequest request) throws IOException;

  CommonResponse shareNewsFeed(ShareRequest request) throws IOException;

  CommonResponse searchNewsByUser(String username) throws IOException;

  CommonResponse searchNewsByAddress(String address) throws IOException;

  CommonResponse getAllNewsByUser(Long userID) throws Exception;

  CommonResponse updateUserProfile(UpdateUserProfileRequest request) throws Exception;

}
