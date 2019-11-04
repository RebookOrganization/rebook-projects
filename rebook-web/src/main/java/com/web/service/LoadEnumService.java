package com.web.service;

import java.util.Map;

public interface LoadEnumService {

  Map<Integer, String> loadProvinceEnum();

  Map<Integer, String> loadDistrict();

  Map<Integer, String> loadRentType();

  Map<Integer, String> loadSaleType();

  Map<Integer, String> loadPrice();

  Map<Integer, String> loadArea();

  Map<Integer, String> loadDirectHouse();

}
