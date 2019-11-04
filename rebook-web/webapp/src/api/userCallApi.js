import { API_BASE_URL } from '../constants/constant';
import {httpGet, httpPost, httpUploadFile} from "./index";
import {post} from "axios";

const API_NEWS = "api/news";

export function getCurrentUser() {
  return httpGet("api/auth/me");
}

export function getAllNewsItem() {
  return httpGet(API_NEWS + "/all-news")
}

export function uploadMultiImages(formData) {
  return httpUploadFile("api/uploadMultipleFiles", formData);
}

 export function createNewsPostItem(requestParam) {
  return httpPost("api/news/create-post", requestParam);
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