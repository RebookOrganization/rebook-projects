import {httpGet} from "./index";
import axios from "axios";

export function getLatLngFromAddress(address) {
  const method = 'GET';
  const headers = {'Content-Type': 'application/json; charset=utf-8'};
  const meta = {
    method,
    headers,
  };
  return axios("https://maps.googleapis.com/maps/api/geocode/json?address="
      + address + "&key=AIzaSyBkoOTnG-hF9GLjQj7TUIlKJjQtCUfswDc", meta) // eslint-disable-line
  .then(response => {
    return response;
  }).catch((error) => {
    console.log("fail!", error);
    return false;
  });

}