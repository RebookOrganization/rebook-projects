import {httpGet} from "./index";

const BASE_URL = "api/recommend";

export function recommendAPI(prefix, id, include) {
  return httpGet( BASE_URL + "/list-news?prefix="+prefix+"&id="+id+"&include="+include)
}