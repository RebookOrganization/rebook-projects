import React, {Component} from "react";
import {
  Card,
  CardTitle,
  Col,
  Modal,
  ModalBody,
  ModalHeader,
  Row
} from "reactstrap";
import Skeleton from 'react-loading-skeleton';
import '../Home/ListCardItem/_listCardItem.css';
import ImageGallery from 'react-image-gallery';
import "react-image-gallery/styles/scss/image-gallery.scss";
import "react-image-gallery/styles/css/image-gallery.css";

class RecommendModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      recommendModal: false,
      loadingRecommend: false,
      newsRecommend: null
    }
  }

  componentWillMount() {
    this.setState({
      recommendModal: this.props.recommendModal,
      loadingRecommend: this.props.loadingRecommend,
      newsRecommend: this.props.newsRecommend
    })
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      recommendModal: nextProps.recommendModal,
      loadingRecommend: nextProps.loadingRecommend,
      newsRecommend: nextProps.newsRecommend
    }, ()=>console.log("loadingRecommend: "+ this.state.loadingRecommend))
  }

  toggleModalRecommendDetail = () =>  {
    if (typeof this.props.toggleModalRecommendDetail === 'function') {
      return this.props.toggleModalRecommendDetail();
    }
  };

  handleRenderImageSlide = (imageList) => {
    if (imageList) {
      let images = [];
      imageList.map(i => {
        images.push({
          original: i.imageUrl.replace("/resize/200x200", ""),
          thumbnail: i.imageUrl,
        })
      });
      return (
          <ImageGallery items={images}/>
      )
    }
  };

  render() {
    const {newsRecommend} = this.state;
    return (
        <Modal isOpen={this.state.recommendModal}
               toggle={()=>this.toggleModalRecommendDetail()}
               className={'modal-lg modal-lg-custom ' + this.props.className}
               style={{maxWidth: "85%"}}
               centered={true}
        >
          <ModalBody>
            {
              this.state.loadingRecommend ?
                  <div>
                    <div style={{marginBottom: '15px'}}>
                      <Skeleton circle={true} height={50} width={50}/>
                    </div>
                    <Skeleton count={7}/>
                  </div> : null
            }
            {
              newsRecommend ?
                  <div>
                    <CardTitle>
                      <div className="row"
                           style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                        <div className="col-md-9">
                          <a className="btn-circle btn-lg">
                            <img
                                src={newsRecommend.imageUser ? newsRecommend.imageUser
                                    : '/icon/default.jpg'}
                                className="rounded-circle img-profile"
                                alt="Username"/>
                          </a>{' '}
                          <a href={newsRecommend.imageUser ? newsRecommend.imageUser
                              : '/icon/default.jpg'}
                             className="username"
                          >
                            <strong>{newsRecommend.username ? newsRecommend.username
                                : 'username'}</strong>
                          </a>

                          {/*pub Date*/}
                          <div style={{color: '#606770', margin: '0 70px'}}>
                            {newsRecommend.pubDate ? newsRecommend.pubDate : ''}
                          </div>
                        </div>
                        <div className="col-md-3">
                          <div className="dropdown float-right">
                            <button className="btn border-none-outline"
                                    type="button" id="dropdownMenuButton"
                                    data-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">
                              <img src="/icon/menu-5.svg" style={{width:'23px',height:'23px'}}/>
                            </button>
                            <div className="dropdown-menu"
                                 aria-labelledby="dropdownMenuButton">
                              <a className="dropdown-item">
                                <i className="far fa-eye-slash"/> Ẩn bài viết
                              </a>
                              <a className="dropdown-item">
                                <i className="far fa-save"/> Lưu bài viết
                              </a>
                              <a className="dropdown-item">
                                <i className="far fa-flag"/> Gửi phản hồi
                              </a>
                            </div>
                          </div>
                        </div>
                      </div>
                    </CardTitle>

                    <Row>
                      <Col xs={12} sm={7}>
                        <div style={{marginBottom: '10px'}}>
                          {
                            newsRecommend.imageUrlList && newsRecommend.imageUrlList.length ? this.handleRenderImageSlide(newsRecommend.imageUrlList) : null
                          }
                        </div>
                      </Col>
                      <Col xs={12} sm={5}>
                        <div className="row"
                             style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
                          <p className={"styleTitle"}>
                            {newsRecommend.titleNews ? newsRecommend.titleNews : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Giá: </strong>{newsRecommend.price ? newsRecommend.price : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Diện tích: </strong>{newsRecommend.area ? newsRecommend.area : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Địa chỉ: </strong>{newsRecommend.address_prop ? newsRecommend.address_prop
                              : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsRecommend.summaryNews ? newsRecommend.summaryNews : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsRecommend.descriptionNews ? newsRecommend.descriptionNews : null}
                          </p> : null

                          <p className={"styleTitle"}>
                            <strong>Liên hệ: </strong>{' '}
                            {newsRecommend.contactName ? newsRecommend.contactName : null}
                            {newsRecommend.contactPhone ? newsRecommend.contactPhone : null}
                            {newsRecommend.contactEmail ? newsRecommend.contactEmail : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsRecommend.projectName ? newsRecommend.projectName : null}
                            {newsRecommend.projectOwner ? newsRecommend.projectOwner : null}
                            {newsRecommend.projectSize ? newsRecommend.projectSize : null}
                          </p>
                        </div>

                        {/*Luot like luot share o day*/}
                        <div style={{margin: '0 20px'}}>
                          <a className="amount-like-share" style={{color: '#606770'}}>
                            <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
                            <img className={"styleIcon"} src="/icon/heart.svg"/>
                            {newsRecommend.likeNewsList ? newsRecommend.likeNewsList.length : 0}
                          </a>
                          <a className="float-right amount-like-share"
                             style={{marginLeft: '10px',color: '#606770'}}>
                            {newsRecommend.shareList ? newsRecommend.shareList.length : 0} lượt share
                          </a>
                          <a className="float-right amount-like-share"
                             // onClick={()=>this.handleRenderComment(newsRecommend.newsId)}
                             style={{color: '#606770'}}
                          >
                            {newsRecommend.commentList ? newsRecommend.commentList.length : 0} comment
                          </a>
                        </div>
                      </Col>
                    </Row>

                  </div>
                  : null
            }
          </ModalBody>
        </Modal>
    );
  }
}

export default RecommendModal;