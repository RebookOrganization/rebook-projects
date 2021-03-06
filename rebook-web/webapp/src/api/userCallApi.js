import { API_BASE_URL } from '../constants/constant';
import {httpGet, httpPost, httpUploadFile} from "./index";
import {post} from "axios";

const API_NEWS = "api/news";

export function getCurrentUser() {
  return httpGet("api/auth/me");
}

export function getNewsItemById(id) {
  return httpGet(API_NEWS + "?id=" + id);
}

export function getAllNewsItem(offset) {
  return httpGet(API_NEWS + "/all-news?offset=" + offset)
}

export function uploadMultiImages(formData) {
  return httpUploadFile("api/uploadMultipleFiles", formData);
}

export function uploadImage(formData) {
  return httpUploadFile("api/uploadFile", formData);
}

export function createNewsPostItem(requestParam) {
  return httpPost(API_NEWS + "/create-post", requestParam);
}

export function likeNews(likeRequest) {
  return httpPost(API_NEWS + "/like", likeRequest);
}

export function commentNews(commentRequest) {
  return httpPost(API_NEWS + "/comment", commentRequest);
}

export function shareNews(shareRequest) {
  return httpPost(API_NEWS + "/share", shareRequest);
}

export function searchNewsByAddress(address) {
  return  httpGet(API_NEWS + "/search-by-address?address=" + address);
}

export function searchNewsByUser(username) {
  let formData  = new FormData();
  formData.append('username', username);
  const config = {headers: {'content-type': 'multipart/form-data'}};
  let url = API_NEWS + "/search-by-user";
  return post(`${API_BASE_URL}/${url}`, formData, config)
  .then(response => {
    return response;
  }).catch((error) => {
    console.log("fail!", error);
    return false;
  })
}

export function getAllNewsByUser(userID) {
  return httpGet(API_NEWS + "/user-news?userID=" + userID);
}

export function elasticSearchNews(request) {
  return httpPost(API_NEWS + "/es-search", request);
}

export function searchNewsItem(request) {
  return httpPost(API_NEWS + "/search-news", request);
}

export function updateUserProfile(request) {
  return httpPost("api/user/update-profile", request);
}

export function getNewsByIdAndPartition(createNewsPost) {
  return httpGet(API_NEWS + "/news-by-partition?id=" + createNewsPost.newsId + "&partition=" + createNewsPost.partition)
}

export function updateBackground(request) {
  return httpPost("api/user/update-background", request);
}