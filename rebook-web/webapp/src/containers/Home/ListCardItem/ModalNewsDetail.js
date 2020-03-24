import React, {Component} from 'react';
import {CardTitle, Col, Modal, ModalBody, Row} from "reactstrap";
import shallowCompare from "react-addons-shallow-compare";
import ImageGallery from "react-image-gallery";

class ModalNewsDetail extends Component{
  constructor(props) {
    super(props);
    this.state = {
      modalNewsDetail: false,
      newsItem: null,
    }
  }

  componentWillMount() {
    const {modalNewsDetail, newsItem} = this.props;
    this.setState({
      modalNewsDetail,
      newsItem,
    })
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({
        modalNewsDetail: nextProps.modalNewsDetail,
        newsItem: nextProps.newsItem,
      })
    }
  }

  toggleModalNewsDetail = () => {
    this.setState({
      modalNewsDetail: !this.state.modalNewsDetail
    })
  };

  handleRenderImageSlide = (imageList) => {
    if (imageList) {
      let images = [];
      imageList.map(i => {
        if (i.imageUrl) {
          images.push({
            original: i.imageUrl.replace("/resize/200x200", ""),
            thumbnail: i.imageUrl,
          })
        }
      });
      return (
          <ImageGallery items={images}/>
      )
    }
  };

  render() {
    const {newsItem} = this.state;
    return (
        <Modal isOpen={this.state.modalNewsDetail}
               toggle={()=>this.toggleModalNewsDetail()}
               className={'modal-lg modal-lg-custom ' + this.props.className}
               style={{maxWidth: "80%"}}
               centered={true}>
          <ModalBody>
            {
              newsItem ?
                  <div>
                    <CardTitle>
                      <div className="row"
                           style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                        <div className="col-md-9">
                          <a className="btn-circle btn-lg">
                            <img
                                src={newsItem.imageUser ? newsItem.imageUser
                                    : '/icon/default.jpg'}
                                className="rounded-circle img-profile"
                                alt="Username"/>
                          </a>{' '}
                          <a href={newsItem.imageUser ? newsItem.imageUser
                              : '/icon/default.jpg'}
                             className="username"
                          >
                            <strong>{newsItem.username ? newsItem.username
                                : 'username'}</strong>
                          </a>

                          {/*pub Date*/}
                          <div style={{color: '#606770', margin: '0 70px'}}>
                            {newsItem.pubDate ? newsItem.pubDate : ''}
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
                      {
                        newsItem.imageUrlList && newsItem.imageUrlList.length ?
                            <Col xs={12} sm={7}>
                              <div style={{marginBottom: '10px'}}>
                                {this.handleRenderImageSlide(newsItem.imageUrlList)}
                              </div>
                            </Col> : null
                      }
                      <Col xs={12} sm={newsItem.imageUrlList && newsItem.imageUrlList.length ? 5 : 12}>
                        <div className="row"
                             style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
                          <p className={"styleTitle"}>
                            {newsItem.titleNews ? newsItem.titleNews : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Giá: </strong>{newsItem.price ? newsItem.price : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Diện tích: </strong>{newsItem.area ? newsItem.area : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Địa chỉ: </strong>{newsItem.address_prop ? newsItem.address_prop
                              : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsItem.summaryNews ? newsItem.summaryNews : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsItem.descriptionNews ? newsItem.descriptionNews : null}
                          </p>

                          <p className={"styleTitle"}>
                            <strong>Liên hệ: </strong>{' '}
                            {newsItem.contactName ? newsItem.contactName : null}
                            {newsItem.contactPhone ? newsItem.contactPhone : null}
                            {newsItem.contactEmail ? newsItem.contactEmail : null}
                          </p>
                          <p className={"styleTitle"}>
                            {newsItem.projectName ? newsItem.projectName : null}
                            {newsItem.projectOwner ? newsItem.projectOwner : null}
                            {newsItem.projectSize ? newsItem.projectSize : null}
                          </p>
                        </div>

                        {/*Luot like luot share o day*/}
                        <div style={{margin: '0 20px'}}>
                          <a className="amount-like-share" style={{color: '#606770'}}>
                            <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
                            <img className={"styleIcon"} src="/icon/heart.svg"/>
                            {newsItem.likeNewsList ? newsItem.likeNewsList.length : 0}
                          </a>
                          <a className="float-right amount-like-share"
                             style={{marginLeft: '10px',color: '#606770'}}>
                            {newsItem.shareList ? newsItem.shareList.length : 0} lượt share
                          </a>
                          <a className="float-right amount-like-share"
                              // onClick={()=>this.handleRenderComment(newsRecommend.newsId)}
                             style={{color: '#606770'}}
                          >
                            {newsItem.commentList ? newsItem.commentList.length : 0} comment
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

export default ModalNewsDetail;