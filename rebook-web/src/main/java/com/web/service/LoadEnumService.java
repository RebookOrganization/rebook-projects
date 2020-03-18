package com.web.service;

import com.web.dto.AreaDTO;
import com.web.dto.PriceDTO;
import com.web.enumeration.Area;
import com.web.enumeration.Price;
import java.util.List;
import java.util.Map;

public interface LoadEnumService {

  Map<Integer, String> loadProvinceEnum();

  Map<Integer, String> loadDistrict();

  Map<Integer, String> loadRentType();

  Map<Integer, String> loadSaleType();

  List<PriceDTO> loadPrice();

  List<AreaDTO> loadArea();

  Map<Integer, String> loadDirectHouse();

}
