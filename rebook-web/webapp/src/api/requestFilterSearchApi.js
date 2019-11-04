import {httpGet} from "./index";

const API_LOAD_ENUM = "api/enumeration";

export function loadEnumProvince() {
  return httpGet(API_LOAD_ENUM + "/province-city");
}

export function loadEnumDistrict() {
  return httpGet(API_LOAD_ENUM + "/district");
}

export function loadEnumRentType() {
  return httpGet(API_LOAD_ENUM + "/rent-type");
}

export function loadEnumSaleType() {
  return httpGet(API_LOAD_ENUM + "/sale-type");
}

export function loadEnumPrice() {
  return httpGet(API_LOAD_ENUM + "/price-options");
}
export function loadEnumArea() {
  return httpGet(API_LOAD_ENUM + "/area-options");
}
export function loadEnumDirectHouse() {
  return httpGet(API_LOAD_ENUM + "/direct-house");
}