package com.crawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "property_address")
public class PropertyAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "propertyAddress", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<NewsItem> newsItems;

    private String street;
    private String district;
    private String province;
    private String summary;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

//    public Set<NewsItem> getNewsItems() {
//        return newsItems;
//    }
//
//    public void setNewsItems(Set<NewsItem> newsItems) {
//        this.newsItems = newsItems;
//    }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    public String getSummary() { return summary; }

    public void setSummary(String summary) { this.summary = summary; }
}
