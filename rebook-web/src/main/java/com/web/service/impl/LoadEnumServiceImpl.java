package com.web.service.impl;

import com.web.enumeration.Area;
import com.web.enumeration.DirectionOfHouse;
import com.web.enumeration.District;
import com.web.enumeration.ForRentType;
import com.web.enumeration.ForSaleType;
import com.web.enumeration.Price;
import com.web.enumeration.ProvinceCity;
import com.web.service.LoadEnumService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LoadEnumServiceImpl implements LoadEnumService {

  @Override
  public Map<Integer, String> loadProvinceEnum() { return ProvinceCity.toHashMap(); }

  @Override
  public Map<Integer, String> loadDistrict() { return District.toHashMap(); }

  @Override
  public Map<Integer, String> loadRentType() {
    return ForRentType.toHashMap();
  }

  @Override
  public Map<Integer, String> loadSaleType() {
    return ForSaleType.toHashMap();
  }

  @Override
  public Map<Integer, String> loadPrice() { return Price.toHashMap(); }

  @Override
  public Map<Integer, String> loadArea() { return Area.toHashMap(); }

  @Override
  public Map<Integer, String> loadDirectHouse() { return DirectionOfHouse.toHashMap(); }

}
