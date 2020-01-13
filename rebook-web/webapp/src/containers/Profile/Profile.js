import React, {Component} from 'react';
import './_profile.css';
import Card from "reactstrap/es/Card";
import {
  Button,
  CardBody,
  CardText,
  CardTitle, Col,
  Input, Modal, ModalBody, ModalHeader, Row,
} from "reactstrap";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import '../Home/_home.css';
import Aside from "../Aside/Aside";
import {getAllNewsByUser, updateUserProfile} from "../../api/userCallApi";
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
import ListCardItem from "../Home/ListCardItem/ListCardItem";
import SkeletonLoading from "../../components/Loading/SkeletonLoading";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";

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

      image: this.props.currentUser ? this.props.currentUser.imageUrl : '/icon/default.jpg',
      allowZoomOut: false,
      position: { x: 0.5, y: 0.5 },
      scale: 1,
      rotate: 0,
      borderRadius: 50,
      preview: null,
      width: 300,
      height: 300,
      hideNav: false,

      displayName: "",
      displayPhone: "",
      displayBirthDate: "",
      displayGender: "",
      loadingUpdate: false
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
    if (navbar != null) {
      if (window.pageYOffset >= 350) {
        navbar.classList.add("sticky-navbar")
      } else {
        if (navbar.classList.contains("sticky-navbar")) {
          navbar.classList.remove("sticky-navbar");
        }
      }
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

  handleUpdateUserProfile = () => {
    this.setState({loadingUpdate: true});
    const request = {
      userId: this.state.currentUser,
      imageUrl: this.state.image,
      name: this.state.displayName,
      phoneNumber: this.state.displayPhone,
      birthDate: this.state.displayBirthDate,
      gender: this.state.displayGender
    };

    updateUserProfile(request).then(res => {
      Alert.success("Cập nhật thành công. " + JSON.stringify(res))
    }).catch(err => console.log(err))
    .finally(()=>this.setState({loadingUpdate: false}))
  };

  render() {
    const {currentUser, newsByUser} = this.state;

    // if (this.state.loading) {
    //   return <LoadingIndicator/>;
    // }

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
                        this.state.loading ? <SkeletonLoading/> :
                            <ListCardItem allNewsItem={newsByUser}
                                          currentUser={currentUser}/>
                      }
                    </div>
                    <div className="col col-md-4">
                      <div className="sticky-top" style={{zIndex:'1',top:'120px'}}>
                        <Card>
                          <CardBody>
                            <h5 style={{marginBottom:"10px"}}>Giới thiệu</h5>
                            <Row style={{marginTop:"5px", marginBottom:"5px"}}>
                              <Col xs={12} sm={5}>
                                <h6>Họ tên: </h6>
                              </Col>
                              <Col xs={12} sm={7} style={{textAlign:"end"}}>
                                <p style={{color:"blue"}}>{currentUser.name}</p>
                              </Col>
                            </Row>
                            <Row style={{marginTop:"5px", marginBottom:"5px"}}>
                              <Col xs={12} sm={5}><h6>Ngày sinh: </h6></Col>
                              <Col xs={12} sm={7} style={{textAlign:"end"}}>
                                <p style={{color:"blue"}}>{currentUser.birthDate}</p>
                              </Col>
                            </Row>
                            <Row style={{marginTop:"5px", marginBottom:"5px"}}>
                              <Col xs={12} sm={5}><h6>Giới tính: </h6></Col>
                              <Col xs={12} sm={7} style={{textAlign:"end"}}>
                                <p style={{color:"blue"}}>{currentUser.gender}</p>
                              </Col>
                            </Row>
                            <Row style={{marginTop:"5px", marginBottom:"5px"}}>
                              <Col xs={12} sm={5}><h6>Số điện thoại: </h6></Col>
                              <Col xs={12} sm={7} style={{textAlign:"end"}}>
                                <p style={{color:"blue"}}>{currentUser.phone}</p>
                              </Col>
                            </Row>
                            <Row style={{marginTop:"5px", marginBottom:"5px"}}>
                              <Col xs={12} sm={5}><h6>Địa chỉ: </h6></Col>
                              <Col xs={12} sm={7} style={{textAlign:"end"}}>
                                <p style={{color:"blue"}}>{"null"}</p>
                              </Col>
                            </Row>
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
              <ModalBody>
                <Row>
                  <Col xs={12} sm={6}>
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
                    <h6>New File:</h6>
                    <Input name="newImage" type="file" onChange={this.handleNewImage} />
                    <br />
                    <h6>Zoom:</h6>
                    <Input
                        name="scale"
                        type="range"
                        onChange={this.handleScale}
                        min={this.state.allowZoomOut ? '0.1' : '1'}
                        max="2"
                        step="0.01"
                        defaultValue="1"
                    />
                  </Col>
                  <Col xs={12} sm={6}>
                    <br/>
                    <h6>Tên hiển thị:</h6>
                    <Input type={"text"} placeholder={"Tên hiển thị"}
                           style={{marginBottom: "10px"}}
                           value={currentUser.name ? currentUser.name : ""}
                           onChange={(e)=>this.setState({displayName: e.target.value})}
                    />
                    <br/>
                    <h6>Giới tính:</h6>
                    <Input type={"text"} placeholder={"Giới tính"}
                           style={{marginBottom: "10px"}}
                           value={currentUser.gender ? currentUser.gender : ""}
                           onChange={(e)=>this.setState({displayGender: e.target.value})}
                    />
                    <br/>
                    <h6>Ngày sinh:</h6>
                    <Input type={"text"} placeholder={"Ngày sinh"}
                           style={{marginBottom: "10px"}}
                           value={currentUser.birthDate ? currentUser.birthDate : ""}
                           onChange={(e)=>this.setState({displayBirthDate: e.target.value})}
                    />
                    <br/>
                    <h6>Số điện thoại:</h6>
                    <Input type={"text"} placeholder={"Số điện thoại"}
                           style={{marginBottom: "10px"}}
                           value={currentUser.phoneNumber ? currentUser.phoneNumber : ""}
                           onChange={(e)=>this.setState({displayPhone: e.target.value})}
                    />
                  </Col>
                </Row>
                <hr/>
                <Row style={{padding:'0 15px',justifyContent:'flex-end'}}>
                  <LaddaButton
                      className="btn btn-info btn-ladda"
                      loading={this.state.loadingUpdate}
                      onClick={() => this.handleUpdateUserProfile()}
                      data-style={EXPAND_LEFT}
                      style={{backgroundColor: '#008FE5', color: 'white',border:'none',height:'40px',lineHeight:'0'}}>
                    <i className="fas fa-search"/> Cập nhật
                  </LaddaButton>
                </Row>
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