package com.web.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "property_project")
public class PropertyProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_owner")
    private String projectOwner;

    @Column(name = "project_size")
    private String projectSize;

//    @OneToMany(mappedBy = "propertyProject", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<NewsItem> newsItems;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectOwner() { return projectOwner; }

    public void setProjectOwner(String projectOwner) { this.projectOwner = projectOwner; }

    public String getProjectSize() { return projectSize; }

    public void setProjectSize(String projectSize) { this.projectSize = projectSize; }

//    public Set<NewsItem> getNewsItems() {
//        return newsItems;
//    }
//
//    public void setNewsItems(Set<NewsItem> newsItems) {
//        this.newsItems = newsItems;
//    }
}
