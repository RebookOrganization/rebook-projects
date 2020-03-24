import React, {Component} from 'react';
import shallowCompare from "react-addons-shallow-compare";
import ImageGallery from "react-image-gallery";
import {
  Button,
  Card,
  CardBody,
  CardTitle,
  Col, Input,
  Modal,
  ModalBody,
  Row
} from "reactstrap";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";

class ModalShareNews extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modalShare: false,
      newsItem: null,
      currentUser: this.props.currentUser,
      loadingShare: false
    }
  }

  componentWillMount() {
    const {modalShare, newsItem, loadingShare} = this.props;
    this.setState({
      modalShare,
      newsItem,
      loadingShare
    })
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({
        modalShare: nextProps.modalShare,
        newsItem: nextProps.newsItem,
        loadingShare: nextProps.loadingShare
      })
    }
  }

  toggleModalShare = () => {
    if (typeof this.props.toggleModalShare === 'function') {
      return this.props.toggleModalShare()
    }
  };

  handleSharePost = (newsId) => {
    if (typeof this.props.handleSharePost === 'function') {
      return this.props.handleSharePost(newsId)
    }
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
          <Card style={{maxWidth:'250px'}}>
            <ImageGallery items={images}/>
          </Card>
      )
    }
  };

  render() {
    const {currentUser, newsItem} = this.state;
    return (
        <Modal isOpen={this.state.modalShare}
               toggle={()=>this.toggleModalShare()}
               className={'modal-lg modal-lg-custom ' + this.props.className}
               style={{maxWidth: "55%"}}
               centered={true}>
          <ModalBody>
            {
              currentUser ?
                <CardTitle>
                  <div className="row"
                       style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                    <div className="col-md-9">
                      <a className="btn-circle btn-lg">
                        <img
                            src={currentUser.imageUrl ? currentUser.imageUrl
                                : '/icon/default.jpg'}
                            className="rounded-circle img-profile"
                            alt="Username"/>
                      </a>{' '}
                      <a href={currentUser.imageUrl ? currentUser.imageUrl
                          : '/icon/default.jpg'}
                         className="username"
                      >
                        <strong>{currentUser.name ? currentUser.name
                            : 'username'}</strong>
                      </a>
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
                </CardTitle> : null
            }
            <input style={{fontSize:'16px', border:'none', outline:'none',
                      width:'95%', marginLeft:'20px', height:"40px"}}
                   placeholder="Viết nội dung chia sẻ..."
            />
            <Card style={{margin:'20px'}}>
              <CardBody style={{padding:'10px'}}>
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
                          </div>
                        </CardTitle>
                        <Row>
                          {/*<Col xs={12} sm={12}>*/}
                            {
                              newsItem.imageUrlList && newsItem.imageUrlList.length ?
                                  <Col xs={12} sm={5}>
                                    <div style={{marginBottom: '10px'}}>
                                      {this.handleRenderImageSlide(newsItem.imageUrlList)}
                                    </div>
                                  </Col>
                              : null
                            }
                            <Col xs={12} sm={newsItem.imageUrlList && newsItem.imageUrlList.length ? 7 : 12}>
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
                            </Col>
                          {/*</Col>*/}
                        </Row>
                      </div>
                      : null
                }
              </CardBody>
            </Card>
            <LaddaButton
                className="btn btn-info btn-ladda"
                loading={this.state.loadingShare}
                onClick={() => this.handleSharePost(newsItem.newsId)}
                data-style={EXPAND_LEFT}
                style={{backgroundColor: '#008FE5', color: 'white',border:'none',height:'40px',lineHeight:'0',
                  float:'right', marginTop: "20px", marginLeft: '20px'}}>
              <i className="fas fa-search"/> Chia sẻ
            </LaddaButton>
            <Button className={"pull-right"}
                    style={{backgroundColor: '#aaa', color: 'white', border:'none',height:'40px',
                      lineHeight:'0', float:'right', marginTop: "20px"}}
                    onClick={()=>this.toggleModalShare()}>Đóng</Button>
          </ModalBody>
        </Modal>
    );
  }
}

export default ModalShareNews;