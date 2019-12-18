import React, {Component} from 'react';
import './_profile.css';
import Card from "reactstrap/es/Card";
import {
  Button,
  CardBody,
  CardText,
  CardTitle,
  Input, Modal, ModalBody, ModalHeader,
} from "reactstrap";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import '../Home/_home.css';
import Aside from "../Aside/Aside";
import {getAllNewsByUser} from "../../api/userCallApi";
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import '../PageLeft/_pageLeft.css';
import shallowCompare from 'react-addons-shallow-compare';
import ImageGallery from 'react-image-gallery';
import "react-image-gallery/styles/scss/image-gallery.scss";
import "react-image-gallery/styles/css/image-gallery.css";
import ReactAvatarEditor from 'react-avatar-editor';
import { withCookies } from 'react-cookie';
import LoadingIndicator from "../../components/Loading/LoadingIndicator";
import AppHeader from "../../components/Header/AppHeader";
import {NavLink, withRouter} from "react-router-dom";

class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      newsByUser: null,
      loading: false,
      currentUser: null,
      comment: "",
      newsDetail: false,
      textOfReadMore: "Chi tiết",
      indexNews: 0,
      renderComment: false,
      likePosted: null,
      activeLike: false,
      sharePosted: null,
      activeShare: false,
      isLike: false,
      isShare: false,

      modalEditProfile: false,

      image: 'assets/img/avatars/avatar.jpg',
      allowZoomOut: false,
      position: { x: 0.5, y: 0.5 },
      scale: 1,
      rotate: 0,
      borderRadius: 50,
      preview: null,
      width: 300,
      height: 300,
      hideNav: false
    }
  }

  componentWillMount() {
    const {currentUser} = this.props;
    this.setState({loading: true});
    if (currentUser) {
      this.setState({
        currentUser: currentUser,
        loading: false
      })
    }
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({
        currentUser: nextProps.currentUser,
      }, ()=>console.log("currentUser: "+JSON.stringify(this.state.currentUser)))
    }
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
    window.removeEventListener("resize", this.resize.bind(this));
  }

  componentDidMount() {
    this.handleGetAllNewByUser();

    window.addEventListener('scroll', this.handleScroll);
    //
    window.addEventListener("resize", this.resize.bind(this));
    this.resize();
  }

  handleScroll = () => {
    let navbar = document.getElementById("navbar");
    if (window.pageYOffset >= 350) {
      navbar.classList.add("sticky-navbar")
    } else {
      navbar.classList.remove("sticky-navbar");
    }
  };

  resize() {
    let currentHideNav = (window.innerWidth <= 790);
    if (currentHideNav !== this.state.hideNav) {
      this.setState({hideNav: currentHideNav});
    }
  }

  handleGetAllNewByUser = () => {
    this.setState({loading: true});
    const {currentUser} = this.state;
    if (currentUser) {
      getAllNewsByUser(currentUser.userId).then(res => {
        if (res && parseInt(res.returnCode) !== 0) {
          this.setState({
            newsByUser: res.data.result
          })
        }
        else {
          Alert.warning("   ");
        }
      }).catch((e)=>{
        console.log(e);
      }).finally(()=>{
        this.setState({loading: false})
      })
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

  toggleModalEditProfile = () => {
    this.setState({
      modalEditProfile: !this.state.modalEditProfile
    })
  };

  handleNewImage = e => {
    this.setState({ image: e.target.files[0] })
  };

  handleScale = e => {
    const scale = parseFloat(e.target.value);
    this.setState({ scale })
  };

  handlePositionChange = position => {
    this.setState({ position })
  };

  render() {
    const {newsDetail, textOfReadMore, currentUser, indexNews,
      renderComment, activeLike, activeShare, newsByUser} = this.state;

    const styleText = {
      fontSize: '16px',
      fontWeight: 'normal',
      lineHeight: '1.58',
      fontFamily: 'inherit',
      marginBottom: '10px',
      paddingRight: '5px',
    };

    const styleTitle = {
      fontSize: '16px',
      fontWeight: 'normal',
      lineHeight: '1.58',
      fontFamily: 'inherit',
      marginBottom: '10px'
    };

    const styleIcon = {
      width: '21px',
      height: '21px',
      marginRight: "2px"
    };

    if (this.state.loading) {
      return <LoadingIndicator/>;
    }

    return (
      <div className="app">
        <div className="app-top-box sticky-top">
          <AppHeader authenticated={this.state.authenticated}
                     currentUser={this.state.currentUser}/>
        </div>
        <div className="app-body">
          <div className="profile-container">
            <div className="container-fluid" style={{paddingLeft:"40px"}}>
              <div className="row">
                <div className="col col-md-2">
                  <div className="sticky-top profile-info" style={{marginBottom:'20px',
                    zIndex:'10'}}>
                    <div className="profile-avatar" style={{textAlign:'center'}}>
                      {
                        currentUser ? (
                            <img src={currentUser.imageUrl ? currentUser.imageUrl : '/icon/default.jpg'}
                                 onClick={()=>this.toggleModalEditProfile()}
                                 alt={currentUser.name}/>
                        ) : (
                            <div className="text-avatar">
                              <span>{currentUser && currentUser.name[0]}</span>
                            </div>
                        )
                      }
                    </div>
                    <button onClick={()=>this.toggleModalEditProfile()}
                            className={"pull-right"}
                            style={{margin:'10px'}}
                    >
                      <i className="fa fa-plus"/> Edit
                    </button>
                    <div className="profile-name" style={{marginBottom:'30px', textAlign:'center'}}>
                      <h2>{currentUser.name}</h2>
                      <p className="profile-email">{currentUser.email}</p>
                    </div>

                    <h6 style={{color:'#616770', paddingLeft:'10px'}}>Lối tắt</h6>
                    <div className="list-group list-group-mine" style={{marginBottom: '15px'}}>
                      <NavLink tag={"a"} className="list-group-item" to={"/home"}>
                        <img src="/icon/icons8-news.png" alt={""}/> Bảng tin
                        <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
                      </NavLink>
                      <a className="list-group-item" href="#">
                        <img src="/icon/icons8-group.png" alt={""}/> Nhóm
                        <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
                      </a>
                      <a className="list-group-item" href="#">
                        <img src="/icon/icons8-add_user_male.png" alt={""}/> Tạo
                        <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
                      </a>
                    </div>
                  </div>
                </div>
                <div className="col col-md-8" style={{paddingRight:'30px'}}>
                  <div className="row">
                    <div className="col">
                      <Card style={{border:'none', maxHeight:'370px', marginBottom:'0'}}>
                        <img className={"responsive"}
                             style={{maxWidth:'100%', minHeight:'100%', objectFit:'cover'}}
                             src="https://images.pexels.com/photos/237018/pexels-photo-237018.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" alt={""}/>
                      </Card>
                      {/*<div className={"row"}>*/}
                      <Card id={"navbar"} style={{marginTop:'-1px', borderRadius: "0"}}>
                        <CardBody style={{padding:'15px'}}>
                          <button style={{marginRight:'20px'}}
                                  onClick={()=>this.toggleModalEditProfile()}
                          >
                            <i className="fa fa-plus"/> Cập nhật hình nền
                          </button>
                          <button style={{marginRight:'20px'}}>
                            <i className="fa fa-plus"/> Bạn bè
                          </button>
                          <button style={{marginRight:'20px'}}>
                            <i className="fa fa-plus"/> Ảnh/Video
                          </button>
                          <button className={'pull-right'} style={{marginRight:'20px'}}>
                            <i className="fa fa-plus"/> Thêm
                          </button>
                        </CardBody>
                      </Card>
                      {/*</div>*/}
                    </div>
                  </div>
                  <div className="row">
                    <div className="col col-md-8">
                      {
                        newsByUser ? newsByUser.map((item, index) => {
                          return (
                              <Card className="card" key={index}>
                                <CardTitle>
                                  <div className="row"
                                       style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                                    <div className="col-md-9">
                                      <a className="btn-circle btn-lg">
                                        <img
                                            src={item.imageUser ? item.imageUser
                                                : '/icon/default.jpg'}
                                            className="rounded-circle img-profile"
                                            alt="Username"/>
                                      </a>{' '}
                                      <a href={item.imageUser ? item.imageUser
                                          : '/icon/default.jpg'}
                                         className="username"
                                      >
                                        <strong>{item.username ? item.username
                                            : 'username'}</strong>
                                      </a>

                                      {/*pub Date*/}
                                      <div style={{color: '#606770', margin: '0 70px'}}>
                                        {item.pubDate ? item.pubDate : ''}
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

                                <div className="row"
                                     style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
                                  <p style={styleTitle}>
                                    {item.titleNews ? item.titleNews : null}
                                  </p>
                                  <p style={styleText}>
                                    <strong>Giá: </strong>{item.price ? item.price : null}
                                  </p>
                                  <p style={styleText}>
                                    <strong>Diện tích: </strong>{item.area ? item.area : null}
                                  </p>
                                  <p style={styleText}>
                                    <strong>Địa chỉ: </strong>{item.address_prop ? item.address_prop
                                      : null}
                                  </p>
                                  <p style={styleTitle}>
                                    {item.summaryNews ? item.summaryNews : null}
                                  </p>

                                  <a style={{
                                    fontSize: '16px',
                                    fontWeight: 'normal',
                                    lineHeight: '1.58',
                                    fontFamily: 'inherit',
                                    marginBottom: '10px',
                                    paddingRight: '5px',
                                    color: '#20a8d8'
                                  }}
                                      // onClick={()=>this.handleRenderNewsDetail(item.newsId)}
                                  >
                                    {textOfReadMore}
                                  </a>
                                  {
                                    newsDetail && indexNews === item.newsId ?
                                        <p style={styleTitle}>
                                          {item.descriptionNews ? item.descriptionNews : null}
                                        </p> : null
                                  }

                                  <p style={styleTitle}>
                                    <strong>Liên hệ: </strong>{' '}
                                    {item.contactName ? item.contactName : null}
                                    {item.contactPhone ? item.contactPhone : null}
                                    {item.contactEmail ? item.contactEmail : null}
                                  </p>
                                  <p style={styleTitle}>
                                    {item.projectName ? item.projectName : null}
                                    {item.projectOwner ? item.projectOwner : null}
                                    {item.projectSize ? item.projectSize : null}
                                  </p>
                                </div>

                                <div style={{marginBottom: '10px'}}>
                                  {
                                    item.imageUrlList && item.imageUrlList.length ? this.handleRenderImageSlide(item.imageUrlList) : null
                                  }
                                </div>

                                {/*Luot like luot share o day*/}
                                <div style={{margin: '0 20px'}}>
                                  <a className="amount-like-share" style={{color: '#606770'}}>
                                    <img style={styleIcon} src="/icon/thumb-up.svg"/>
                                    <img style={styleIcon} src="/icon/heart.svg"/>
                                    {item.likeNewsList ? item.likeNewsList.length : 0}
                                  </a>
                                  <a className="float-right amount-like-share"
                                     style={{marginLeft: '10px',color: '#606770'}}>
                                    {item.shareList ? item.shareList.length : 0} lượt share
                                  </a>
                                  <a className="float-right amount-like-share"
                                     style={{color: '#606770'}}
                                  >
                                    {item.commentList ? item.commentList.length : 0} comment
                                  </a>
                                </div>

                                <hr style={{margin: '5px 20px'}}/>

                                <div>
                                  <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={activeLike && indexNews === item.newsId ?
                                            {backgroundColor:'#20a8d8', color:'white'} : {}}
                                        onClick={() => this.handleLikePost(item.newsId)}>
                                      <img style={styleIcon} src="/icon/thumb-up.svg"/> Thích
                                    </Button>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={renderComment && indexNews === item.newsId ?
                                            {backgroundColor:'#20a8d8', color:'white'} : {}}
                                        // onClick={() => this.handleRenderComment(item.newsId)}
                                    >
                                      <img style={styleIcon} src="/icon/a-chat.svg"/> Bình luận
                                    </Button>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={activeShare && indexNews === item.newsId ?
                                            {backgroundColor:'#20a8d8', color:'white'} : {}}
                                        onClick={() => this.handleSharePost(item.newsId)}>
                                      <img style={styleIcon} src="/icon/share-right.svg"/> Chia sẻ
                                    </Button>
                                  </ButtonGroup>
                                </div>

                                <hr/>
                                {
                                  renderComment && indexNews === item.newsId ?
                                      <React.Fragment>
                                        <div className="input-comment" style={{paddingBottom:'10px'}}>
                                          <a className="btn-user">
                                            <img
                                                src={'/icon/icons8-checked_user_male.png'}
                                                className="rounded-circle icon-user"
                                                alt="Username"/>
                                          </a>{' '}
                                          <p style={{borderRadius: '30px', width:'470px', padding: '10px',
                                            backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}}>
                                            <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2'}}>{"Other User "}</p>
                                            {"Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"
                                            + "Bai viet rat hay!!!Bai viet rat hay!!!"
                                            + "Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"}
                                          </p>
                                        </div>
                                        <div className="input-comment" style={{paddingBottom:'10px'}}>
                                          <a className="btn-user">
                                            <img
                                                src={'/icon/icons8-checked_user_male.png'}
                                                className="rounded-circle icon-user"
                                                alt="Username"/>
                                          </a>{' '}
                                          <p style={{borderRadius: '30px', width:'470px', padding: '10px',
                                            backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}}>
                                            <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2'}}>{"Other User "}</p>
                                            {"Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"}
                                          </p>
                                        </div>
                                      </React.Fragment>
                                      : null
                                }

                                <div className="input-comment">
                                  <a className="btn-user">
                                    <img
                                        src={currentUser && currentUser.imageUrl
                                            ? currentUser.imageUrl
                                            : '/icon/default.jpg'}
                                        className="rounded-circle icon-user"
                                        alt="Username"/>
                                  </a>{' '}
                                  <Input style={{borderRadius: '36px', height: '40px',
                                    border:'1px solid #bbc0c4',
                                    backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px'}}
                                         placeholder="Viết bình luận..."
                                         value={this.state.comment}
                                         onChange={(e) => this.setState(
                                             {comment: e.target.value})}/>
                                  <button style={{border:'none', outline:'none'}}
                                          onClick={()=>this.handleCommentPost(item.newsId)}>
                                    <img style={{width:'40px'}} src={'icon/icons8-circled_up.png'} alt={""}/>
                                  </button>
                                </div>
                              </Card>
                          )
                        }) :  null
                      }
                    </div>
                    <div className="col col-md-4">
                      <div className="sticky-top" style={{zIndex:'1',top:'120px'}}>
                        <Card>
                          <CardBody>
                            <h6>Giới thiệu</h6>

                          </CardBody>
                        </Card>
                        <Card style={{display:'flex',flexDirection:'row',padding:'10px'}}>
                          <CardText style={{marginRight:'20px'}}>Tiếng Việt.</CardText>
                          <CardText style={{marginRight:'20px'}}>English.</CardText>
                          <button className={'pull-right'} style={{marginRight:'20px'}}>
                            <i className="fa fa-plus"/> Thêm
                          </button>
                        </Card>
                        <div style={{display: 'flex'}}>
                          <a href="https://mdbootstrap.com/education/bootstrap/" style={{color: '#616770', marginRight: '10px'}}>Điều khoản.</a>
                          <a href="https://mdbootstrap.com/education/bootstrap/" style={{color: '#616770'}}>Quảng cáo.</a>
                        </div>
                        <span style={{color: '#616770'}}>© 2019 Copyright: Rebook.com.vn</span>
                      </div>
                    </div>
                  </div>
                </div>

                {
                  !this.state.hideNav ?
                      <div className="col col-md-2">
                        <Aside/>
                      </div> : null
                }
              </div>
            </div>

            <Modal isOpen={this.state.modalEditProfile}
                   toggle={()=>this.toggleModalEditProfile()}
                   className={'modal-lg modal-lg-custom' + this.props.className}
            >
              <ModalHeader toggle={()=>this.toggleModalEditProfile()}>
                Cập nhật ảnh đại diện
              </ModalHeader>
              <ModalBody style={{margin:'auto'}}>
                <div>
                  <ReactAvatarEditor
                      scale={parseFloat(this.state.scale)}
                      width={this.state.width}
                      height={this.state.height}
                      position={this.state.position}
                      onPositionChange={this.handlePositionChange}
                      rotate={parseFloat(this.state.rotate)}
                      borderRadius={this.state.width / (100 / this.state.borderRadius)}
                      image={this.state.image}
                      className="editor-canvas"
                  />
                </div>
                <br />
                New File:
                <input name="newImage" type="file" onChange={this.handleNewImage} />
                <br />
                Zoom:
                <input
                    name="scale"
                    type="range"
                    onChange={this.handleScale}
                    min={this.state.allowZoomOut ? '0.1' : '1'}
                    max="2"
                    step="0.01"
                    defaultValue="1"
                />
              </ModalBody>
            </Modal>
          </div>
        </div>
        <div className="app-footer"/>
        <Alert stack={{limit: 3}}
               timeout={3000}
               position='top-right' effect='slide' offset={65}/>
      </div>

    )
  }
}

export default withCookies(withRouter(Profile));