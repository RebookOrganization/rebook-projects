package com.web.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "contact_owner")
public class ContactOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_name")
    private String contactName;
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;

//    @OneToMany(mappedBy = "contactOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<NewsItem> newsItems;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getContactName() { return contactName; }

    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

//    public Set<NewsItem> getNewsItems() {
//        return newsItems;
//    }
//
//    public void setNewsItems(Set<NewsItem> newsItems) {
//        this.newsItems = newsItems;
//    }
}
