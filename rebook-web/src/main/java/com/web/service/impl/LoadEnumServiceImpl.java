package com.web.service.impl;

import com.web.dto.AreaDTO;
import com.web.dto.PriceDTO;
import com.web.enumeration.Area;
import com.web.enumeration.DirectionOfHouse;
import com.web.enumeration.District;
import com.web.enumeration.ForRentType;
import com.web.enumeration.ForSaleType;
import com.web.enumeration.Price;
import com.web.enumeration.ProvinceCity;
import com.web.service.LoadEnumService;
import java.util.ArrayList;
import java.util.List;
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
  public List<PriceDTO> loadPrice() {
    List<PriceDTO> priceDTOList = new ArrayList<>();
    for (Price price : Price.values()) {
      PriceDTO priceDTO = new PriceDTO();
      priceDTO.setValue(price.getValue());
      priceDTO.setDisplayValue(price.getDisplayValue());
      priceDTO.setFrom(price.getFrom());
      priceDTO.setTo(price.getTo());
      priceDTOList.add(priceDTO);
    }
    return priceDTOList;
  }

  @Override
  public List<AreaDTO> loadArea() {
    List<AreaDTO> areaDTOList = new ArrayList<>();
    for (Area area: Area.values()) {
      AreaDTO areaDTO = new AreaDTO();
      areaDTO.setValue(area.getValue());
      areaDTO.setDisplayValue(area.getDisplayValue());
      areaDTO.setFrom(area.getFrom());
      areaDTO.setTo(area.getTo());
      areaDTOList.add(areaDTO);
    }
    return areaDTOList;
  }

  @Override
  public Map<Integer, String> loadDirectHouse() { return DirectionOfHouse.toHashMap(); }

}
