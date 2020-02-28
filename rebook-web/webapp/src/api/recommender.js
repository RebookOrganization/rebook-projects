import {httpGet} from "./index";

const BASE_URL = "api/recommend";

export function recommendAPI(userId) {
  return httpGet( BASE_URL + "/list-news?userId=" + userId);
}