package com.web.service;

import com.web.bean.Response.NewsResponseDTO;
import com.web.model.Comment;
import com.web.model.ContactOwner;
import com.web.model.LikeNews;
import com.web.model.NewsItem;
import com.web.model.PropertyAddress;
import com.web.model.PropertyProject;
import com.web.model.ShareNews;
import com.web.repository.CommentRepository;
import com.web.repository.ContactOwnerRepository;
import com.web.repository.LikeRepository;
import com.web.repository.PropertyAdressRepository;
import com.web.repository.PropertyProjectRepository;
import com.web.repository.ShareRepository;
import com.web.utils.DateTimeUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperService {

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ShareRepository shareRepository;

  @Autowired
  private PropertyAdressRepository propertyAdressRepository;

  @Autowired
  private ContactOwnerRepository contactOwnerRepository;

  @Autowired
  private PropertyProjectRepository propertyProjectRepository;

  private static Integer currentPartition = DateTimeUtils.getPartition();

  public NewsResponseDTO mapNewsToNewsResponseDTO(NewsItem newsItem) {
    NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
    newsResponseDTO.setImageUser(newsItem.getUser().getImageUrl());
    newsResponseDTO.setUsername(newsItem.getUser().getName());
    newsResponseDTO.setTitleNews(newsItem.getTitle());

    if (newsItem.getSummary() != null) {
      newsResponseDTO.setSummaryNews(newsItem.getSummary());
    }

    if (newsItem.getDescription() != null) {
      newsResponseDTO.setDescriptionNews(newsItem.getDescription());
    }

    newsResponseDTO.setPubDate(newsItem.getPostedDate());
    newsResponseDTO.setPrice(newsItem.getPrice());
    newsResponseDTO.setArea(newsItem.getArea());

    if (newsItem.getPropertyAddressId() != null) {
      Optional<PropertyAddress> propertyAddress = propertyAdressRepository.findById(currentPartition,
          newsItem.getPropertyAddressId());
      propertyAddress.ifPresent(address -> newsResponseDTO.setAddress_prop(address.getSummary()));
    }

    newsResponseDTO.setImageUrlList(newsItem.getImages());
    newsResponseDTO.setNewsId(newsItem.getId());
    newsResponseDTO.setUserId(newsItem.getUser().getId());

    if (newsItem.getContactOwnerId() != null) {
      Optional<ContactOwner> contactOwner = contactOwnerRepository.findById(currentPartition,
          newsItem.getContactOwnerId());
      contactOwner.ifPresent(contact -> {
        newsResponseDTO.setContactEmail(contact.getEmail());
        newsResponseDTO.setContactName(contact.getContactName());
        newsResponseDTO.setContactPhone(contact.getPhoneNumber());
      });

    }

    if (newsItem.getPropertyProjectId() != null) {
      Optional<PropertyProject> propertyProject = propertyProjectRepository.findById(currentPartition,
          newsItem.getPropertyProjectId());
      propertyProject.ifPresent(project -> {
        newsResponseDTO.setProjectOwner(project.getProjectOwner());
        newsResponseDTO.setProjectSize(project.getProjectSize());
        newsResponseDTO.setProjectName(project.getProjectName());
      });
    }

    List<LikeNews> likeNewsList = likeRepository.findLikeNewsByNewsItemId(newsItem.getId());
    newsResponseDTO.setLikeNewsList(likeNewsList);

    List<Comment> commentList = commentRepository.findByNewItemId(newsItem.getId());
    newsResponseDTO.setCommentList(commentList);

    List<ShareNews> shareNewsList = shareRepository.findByNewItemId(newsItem.getId());
    newsResponseDTO.setShareNewsList(shareNewsList);

    return newsResponseDTO;
  }
}
