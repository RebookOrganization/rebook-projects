import React, {Component} from 'react';
import {
  Card,
  CardHeader,
  CardBody,
  Modal,
  Input,
  Collapse, ModalHeader, ModalBody
} from 'reactstrap';
import './_home.css';
import PageLeft from "../PageLeft/PageLeft";
import PageRight from "../PageRight/PageRight";
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import '../App/_app.css';
import {
  createNewsPostItem,
  getAllNewsItem, getNewsItemById, uploadMultiImages
} from "../../api/userCallApi";
import ImageUploader from 'react-images-upload';
import moment from "moment";
import ListCardItem from "./ListCardItem/ListCardItem";
import 'bootstrap-social/bootstrap-social.css';
import LaddaButton, {EXPAND_LEFT} from "react-ladda";
import 'ladda/dist/ladda-themeless.min.css';
import Aside from "../Aside/Aside";
import { withCookies } from 'react-cookie';
import AppHeader from "../../components/Header/AppHeader";
import {withRouter} from "react-router-dom";
import RecommendSlider from "./Slider/RecommendSlider";
import {
  loadEnumArea, loadEnumDirectHouse,
  loadEnumDistrict, loadEnumPrice,
  loadEnumProvince, loadEnumRentType, loadEnumSaleType
} from "../../api/requestFilterSearchApi";
import AppSearch from "../../components/AppSearch/AppSearch";
import SkeletonLoading from "../../components/Loading/SkeletonLoading";
import RecommendModal from "../RecommendModal/RecommendModal";
import {recommendAPI} from "../../api/recommender";

const initOffset = 0;

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allNewsItem: null,
      summary: '',
      address: '',
      contactName: '',
      contactPhone: '',
      contactAddress: '',
      projectName: '',
      projectOwner: '',
      projectSize: '',
      furniture: '',
      floors: '',
      rooms: '',
      toilets: '',
      direct: '',
      area: '',
      price: '',
      renderMoreButton: false,
      renderInputAddress: false,
      renderInputContact: false,
      renderInputProject: false,
      renderFloorInput: false,
      renderRoomInput: false,
      renderToiletInput: false,
      renderDirectInput: false,
      renderFurnitureInput: false,
      renderInputAreaAndPrice: false,
      isLike: false,
      comment: '',
      isShare: false,
      isPost: false,

      createNewsPost: null,
      pictures: [],

      commentPosted: null,
      sharePosted: null,
      currentUser: this.props.currentUser,
      authenticated: this.props.authenticated,

      isSearching: false,
      inputSearch: '',
      inputSearchType: 0,
      onScroll: false,

      loading: false,
      loadingPost: false,
      collapsePost: false,

      modalCreatedPost: false,

      items: 20,
      hasMoreItems: true,
      hideNav: false,

      isSearch: false,
      optionProvince: null,
      optionDistrict: null,
      optionRentType: null,
      optionSaleType: null,
      optionPrice: null,
      optionArea: null,
      optionDirectHouse: null,

      recommendModal: false,
      loadingRecommend: false,
      newsRecommend: null,
      listRecommend: []
    };

  }

  toggleCollapse = () => {
    this.setState({collapsePost: !this.state.collapsePost})
  };

  toggleModalCreatedPost = () => {
    this.setState({
      modalCreatedPost: !this.state.modalCreatedPost
    })
  };

  toggleModalSearch = () => {
    this.setState({
      isSearch: !this.state.isSearch
    }, () => {
      this.state.isSearch ?
          this.handleLoadEnum() : null
    })
  };

  onKeyDownTextArea = (e) => {
    console.log("e textarea: ", e);
    if (e.key === "Enter") {
      let textarea = document.getElementById("news-summary");
      setTimeout(function(){
        textarea.style.cssText = 'height:auto; padding:0';
        // for box-sizing other than "content-box" use:
        // el.style.cssText = '-moz-box-sizing:content-box';
        textarea.style.cssText = 'height:' + textarea.scrollHeight + 'px';
      },0);
    }
  };

  toggleModalRecommendDetail = (item) => {
    this.setState({
      recommendModal: !this.state.recommendModal
    }, () => {
      if (this.state.recommendModal) {
        if (item) {
          this.setState({
            newsRecommend: item
          })
        }
        else {
          this.setState({loadingRecommend: true});
          //old
          getNewsItemById(0).then(res => {
            console.log("res get News by id:" + JSON.stringify(res));
            if (parseInt(res.data.returnCode) === 1) {
              this.setState({newsRecommend: res.data.result})
            }
            else {
              Alert.warning(res.data.returnMessage)
            }
          }).finally(()=>this.setState({loadingRecommend: false}))
        }
      }
    })
  };

  handleLoadListRecommend = () => {
    this.setState({loadingRecommend: true});
    const {currentUser} = this.state;
    recommendAPI(currentUser ? currentUser.userId : 2).then(res => {
      if (res && res.data.length) {
        this.setState({
          listRecommend: res.data
        })
      }
    }).finally(()=>this.setState({loadingRecommend: false}));
  };

  handleLoadEnum = () => {
    let provinceCity = loadEnumProvince();
    let district = loadEnumDistrict();
    let rentType = loadEnumRentType();
    let saleType = loadEnumSaleType();
    let priceOption = loadEnumPrice();
    let areaOption = loadEnumArea();
    let directHouse = loadEnumDirectHouse();

    Promise.all([provinceCity, district, rentType, saleType,
      priceOption, areaOption, directHouse]).then(res => {
      this.setState({
        optionProvince: res[0].data,
        optionDistrict: res[1].data,
        optionRentType: res[2].data,
        optionSaleType: res[3].data,
        optionPrice: res[4].data,
        optionArea: res[5].data,
        optionDirectHouse: res[6].data,
      })
    }).catch(e => console.log(e))
  };

  componentDidMount() {
    console.log("currentUser home: ", this.state.currentUser);
    console.log("this.props.location.state: ", this.props.location.search);

    this.setState({loading: true});
    getAllNewsItem(initOffset).then(res => {
      this.setState({
        allNewsItem: res.data.result
      })
    }).catch(() => {
      Alert.warning("Không thể lấy tất cả tin tức.");
    })
    .finally(() => this.setState({loading: false}));
    this.handleLoadListRecommend();

    window.addEventListener("resize", this.resize.bind(this));
    this.resize();
  }

  componentWillUnmount() {
    window.removeEventListener("resize", this.resize.bind(this));
  }

  resize() {
    let currentHideNav = (window.innerWidth <= 790);
    if (currentHideNav !== this.state.hideNav) {
      this.setState({hideNav: currentHideNav});
    }
  }

  handleRenderMoreButton = () => {
    this.setState({
      renderMoreButton: !this.state.renderMoreButton,
      collapsePost: true
    })
  };

  handleRenderInputAddress = () => {
    this.setState({
      renderInputAddress: !this.state.renderInputAddress,
      collapsePost: true
    })
  };

  handleRenderFloorInput = () => {
    this.setState({
      renderFloorInput: !this.state.renderFloorInput,
      collapsePost: true
    })
  };

  handleRenderToiletInput = () => {
    this.setState({
      renderToiletInput: !this.state.renderToiletInput,
      collapsePost: true
    })
  };

  handleRenderRoomInput = () => {
    this.setState({
      renderRoomInput: !this.state.renderRoomInput,
      collapsePost: true
    })
  };

  handleRenderDirectInput = () => {
    this.setState({
      renderDirectInput: !this.state.renderDirectInput,
      collapsePost: true
    })
  };

  handleRenderFurnitureInput = () => {
    this.setState({
      renderFurnitureInput: !this.state.renderFurnitureInput,
      collapsePost: true
    })
  };

  handleRenderInputContact = () => {
    this.setState({
      renderInputContact: !this.state.renderInputContact,
      collapsePost: true
    })
  };

  handleRenderInputProject = () => {
    this.setState({
      renderInputProject: !this.state.renderInputProject,
      collapsePost: true
    })
  };

  handleRenderInputAreaAndPrice = () => {
    this.setState({
      renderInputAreaAndPrice: !this.state.renderInputAreaAndPrice,
      collapsePost: true
    })
  };

  onDrop = (pictureFiles) => {
    this.setState({
      pictures: [].concat(pictureFiles)
    }, () => console.log(
        "pictureFiles: " + JSON.stringify(this.state.pictures)))
  };

  handleChangeInput = (event) => {
    this.setState({[event.target.name]: event.target.value})
  };

  handlePostNewsItem = () => {
    this.setState({loadingPost: true});
    const {
      summary, contactName, contactPhone, contactAddress, address, floors, rooms, toilets, furniture,
      direct, currentUser, projectName, projectOwner, projectSize, pictures, area, price
    } = this.state;

    console.log("pictures" + JSON.stringify(pictures));
    let formData = new FormData();
    for (var i = 0; i < pictures.length; i++) {
      formData.append('files', pictures[i]);
    }

    let listUpload = null;
    console.log("formData: " + JSON.stringify(formData));

    uploadMultiImages(formData).then(res => {
      listUpload = res.data;
      console.log("listUpload: " + JSON.stringify(listUpload));
      if (address && summary && area.length && price.length) {
        const requestParams = {
          user_id: currentUser ? currentUser.userId : '1',
          prop_address: address,
          desc: summary.length >= 50 ? summary : Alert.warning("Nội dung quá ngắn, vui lòng nhập >= 50 ký tự."),
          listUpload: listUpload ? listUpload : [],
          ownerName: contactName ? contactName : '',
          ownerPhone: contactPhone ? contactPhone : '',
          ownerAddress: contactAddress ? contactAddress : '',
          title: summary.substring(0, summary.length/3),
          price: price,
          area: area,
          direct_house: direct ? direct : '',
          floor_number: floors ? floors : '',
          room_number: rooms ? rooms : '',
          toilet_number: toilets ? toilets : '',
          interior: furniture ? furniture : '',
          pub_date: moment().format("DD/MM/YYYY"),
          project_name: projectName ? projectName : '',
          project_owner: projectOwner ? projectOwner : '',
          project_size: projectSize ? projectSize : ''
        };

        console.log("createNewsPostItem requestParams: "+JSON.stringify(requestParams));

        createNewsPostItem(requestParams).then(res => {
          if (res && parseInt(res.data.returnCode) === 1) {
            Alert.success('Đăng bài thành công.');
            this.setState({
              createNewsPost: res.data.result
            }, () => {
              const {createNewsPost} = this.state;
              console.log("new post response: " + JSON.stringify(createNewsPost));
              this.handleCloseAllInput();
              this.setState({
                // allNewsItem: this.state.allNewsItem.unshift(createNewsPost),
                summary: ""
              })
            });
          } else {
            Alert.warning("Không có phản hồi. Vui lòng thử lại.")
          }
        }).catch(err => {
          console.log(err);
          Alert.error("Không có phản hồi. Vui lòng thử lại.")
        }).finally(() =>
            this.setState({loadingPost: false})
        );
      }
      else {
        Alert.error("Bạn chưa nhập địa chỉ bất động sản hoặc thông tin chung bất động sản.");
        this.setState({loadingPost: false})
      }
    })
    .catch(err => {
      Alert.warning("Upload images fail.");
      console.log(err)
    })

  };

  callBackFromAppSearch = (allNewsItem, loading) => {
    if (allNewsItem) {
      this.setState({
        allNewsItem: allNewsItem,
        loading: loading
      })
    }
  };

  handleCloseAllInput = () => {
    this.setState({
      renderInputAddress: false,
      renderFloorInput: false,
      renderRoomInput: false,
      renderToiletInput: false,
      renderDirectInput: false,
      renderFurnitureInput: false,
      renderInputContact: false,
      renderInputProject: false,
      renderInputAreaAndPrice: false
    })
  };

  render() {
    const { renderMoreButton, renderInputAddress, renderFloorInput, renderRoomInput, renderToiletInput,
      renderDirectInput, renderFurnitureInput, renderInputContact, renderInputProject, summary, address,
      currentUser, renderInputAreaAndPrice } = this.state;
    let inputAddress;
    let inputFloor;
    let inputRoom;
    let inputToilet;
    let inputDirect;
    let inputfurniture;
    let inputContact;
    let inputProject;
    let inputAreaAndPrice;

    if (renderInputContact) {
      inputContact =
          <div>
            <hr/>
            <h6>Người liên hệ</h6>
            <div className={"row"} style={{padding:'0 10px'}}>
              <div className="col col-md-6">
                <Input name="contactName" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập tên người liên hệ..."/>
              </div>
              <div className="col col-md-6">
                <Input name="contactPhone" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập số điện thoại người liên hệ..."/>
              </div>
            </div>
            <div className={"row"} style={{padding:'0 10px'}}>
              <div className={"col"}>
                <Input name="contactAddress" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập địa chỉ người liên hệ..."/>
              </div>
            </div>
          </div>
    } else {inputContact = null}

    if (renderInputAreaAndPrice) {
      inputAreaAndPrice =
          <div>
            <hr/>
            <h6>Diện tích, Giá:</h6>
            <div className={"row"} style={{padding:'0 10px'}}>
              <div className="col col-md-6">
                <Input name="area" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập diện tích .."/>
              </div>
              <div className="col col-md-6">
                <Input name="price" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập giá .."/>
              </div>
            </div>
          </div>
    }
    else { inputAreaAndPrice = null }

    if (renderInputProject) {
      inputProject =
          <div>
            <hr/>
            <h6>Dự án</h6>
            <div className={"row"} style={{padding:'0 10px'}}>
              <div className={"col col-md-6"}>
                <Input name="projectName" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập tên dự án..."/>
              </div>
              <div className={"col col-md-6"}>
                <Input name="projectOwner" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập chủ dự án..."/>
              </div>
            </div>
            <div className={"row"} style={{padding:'0 10px'}}>
              <div className={"col"}>
                <Input name="projectSize" type="text"
                       className={"inputStyle"}
                       onChange={this.handleChangeInput}
                       placeholder="Vui lòng nhập quy mô dự án..."/>
              </div>
            </div>
          </div>
    } else {
      inputProject = null
    }

    if (renderInputAddress) {
      inputAddress =
          <div>
            <hr/>
            <h6>Địa chỉ</h6>
            <Input name="address" type="text"
                   className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập địa chỉ..."/>
          </div>
    } else {
      inputAddress = null
    }

    if (renderFurnitureInput) {
      inputfurniture =
          <div>
            <hr/>
            <h6>Nội thất</h6>
            <Input name="furniture" type="text" className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập nội dung nội thất..."/>
          </div>
    } else {
      inputfurniture = null
    }

    if (renderFloorInput) {
      inputFloor =
          <div>
            <hr/>
            <h6>Số tầng</h6>
            <Input name="floors" type="text" className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập số tầng nhà..."/>
          </div>
    } else {
      inputFloor = null
    }

    if (renderRoomInput) {
      inputRoom =
          <div>
            <hr/>
            <h6>Số phòng</h6>
            <Input name="rooms" type="text" className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập số phòng ngủ..."/>
          </div>
    } else {
      inputRoom = null
    }

    if (renderToiletInput) {
      inputToilet =
          <div>
            <hr/>
            <h6>Số toilet</h6>
            <Input name="toilets" type="text" className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập số toilet..."/>
          </div>

    } else {
      inputToilet = null
    }

    if (renderDirectInput) {
      inputDirect =
          <div>
            <hr/>
            <h6>Hướng nhà</h6>
            <Input name="direct" type="text"
                   className={"inputStyle"}
                   onChange={this.handleChangeInput}
                   placeholder="Vui lòng nhập hướng nhà..."/>
          </div>
    } else {
      inputDirect = null
    }

    let moreButton;
    if (renderMoreButton) {
      moreButton =
          <div>
            <button className="button-pill"
                    onClick={() => this.handleRenderInputProject()}>
              <img src="/icon/icons8-project.png" alt={""}/> Dự án
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderFurnitureInput()}>
              <img src={"/icon/icons8-interior.png"}/> Nội thất
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderDirectInput()}>
              <img src="/icon/icons8-north_direction.png" alt={""}/> Hướng
              nhà
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderFloorInput()}>
              <img src={"/icon/icons8-floods.png"}/> Số tầng
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderRoomInput()}>
              <img src={"/icon/bed.png"}/> Số phòng
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderToiletInput()}>
              <img src={"/icon/icons8-toilet.png"}/> Số toilet
            </button>
            {' '}
          </div>
    } else {
      moreButton = <div/>
    }

    return (
      <div className="app">
        <div className="app-top-box sticky-top">
          <AppHeader authenticated={this.state.authenticated}
                     currentUser={this.state.currentUser}
                     toggleModalSearch={this.toggleModalSearch}
          />
        </div>
        <div className="app-body" id="app-body">
          <div className="container-fluid"
               style={{width: '90%', marginTop: "15px", maxWidth: '1700px'}}>
            <div className="row" id="scroll">
              {/*<div className="col col-md-1"/>*/}
              <div className="col col-md-2" style={{paddingRight: '10px'}}>
                <PageLeft currentUser={currentUser}/>
              </div>
              <div className="col col-md-5">
                {!this.state.loading ? (
                  <Card className="card">
                    <CardHeader className="news-post"
                                onClick={this.toggleModalCreatedPost}>
                      <strong>
                        <img src="/icon/icons8-browser_window.png"/> Tạo bài
                        viết
                      </strong>
                    </CardHeader>
                      <CardBody style={{padding: '10px'}}>
                        <div style={{display: 'flex', alignItems: 'center', marginBottom: '20px'}}>
                          <a className="btn-user-in-create">
                            <img
                                src={currentUser && currentUser.imageUrl ?
                                    currentUser.imageUrl
                                    : '/icon/default.jpg'}
                                className="rounded-circle icon-user-in-create"
                                alt="Username"/>
                          </a>
                          <textarea rows="1" name="summary" id="news-summary"
                                    onClick={this.toggleCollapse}
                                    onChange={this.handleChangeInput}
                                    placeholder={currentUser ? (currentUser.name
                                        + ', thông tin bất động sản của bạn là gì?') : 'Thông tin bất động sản của bạn là gì?'}
                                    onKeyDown={(e)=> this.onKeyDownTextArea(e)}/>
                        </div>
                        <hr/>
                        <button className="button-pill"
                                onClick={() => this.handleRenderInputAddress()}>
                          <img src="/icon/icons8-order_delivered.png" alt={""}/> Địa
                          chỉ
                        </button>
                        {' '}
                        <button className="button-pill"
                                onClick={() => this.handleRenderInputContact()}>
                          <img src="/icon/contact.png" alt={""}/> Người liên hệ
                        </button>
                        {' '}
                        <button className="button-pill"
                                onClick={() => this.handleRenderInputAreaAndPrice()}>
                          <img src="/icon/icons8-price_tag_euro.png" alt={""}/> Diện tích, giá
                        </button>
                        {' '}
                        <button className="button-pill"
                                onClick={() => this.handleRenderMoreButton()}>
                          <img src={"/icon/menu-5.svg"} alt={""}/>
                        </button>
                        {moreButton}
                        <Collapse isOpen={this.state.collapsePost}
                                  id="collapseExample">
                          <ImageUploader
                              withIcon={false}
                              buttonText='Ảnh/Video'
                              onChange={this.onDrop}
                              imgExtension={['.jpg', '.gif', '.png', '.gif',
                                '.jpeg']}
                              maxFileSize={5242880}
                              withPreview={true}
                          />

                          <hr/>
                          {inputAddress}
                          {inputContact}
                          {inputAreaAndPrice}
                          {inputProject}
                          {inputfurniture}
                          {inputFloor}
                          {inputRoom}
                          {inputToilet}
                          {inputDirect}
                          <div style={{display: 'flex', alignItems: 'center'}}>
                            <button className="button-pill" style={{
                              borderRadius: '5px',
                              marginRight: '10px',
                              width: '30%'
                            }}
                                    onClick={() => this.handleCloseAllInput()}>
                              <i className="fas fa-caret-down"/> Close All
                            </button>
                            <LaddaButton
                                data-style={EXPAND_LEFT}
                                className="btn btn-info btn-ladda"
                                loading={this.state.loadingPost}
                                style={{
                                  width: '70%',
                                  fontWeight: '500',
                                  backgroundColor: '#008FE5',
                                  border: 'none',
                                  color: 'white',
                                  height: '40px',
                                  lineHeight: 0
                                }}
                                onClick={() => this.handlePostNewsItem()}>
                              <img src={"/icon/icons8-hand_cursor.png"} alt={""}/> Chia sẻ
                            </LaddaButton>
                          </div>
                        </Collapse>
                      </CardBody>
                  </Card>
                  ) : null
                }

                <RecommendSlider toggleModalRecommendDetail={this.toggleModalRecommendDetail}
                                 listRecommend={this.state.listRecommend}/>

                {
                  this.state.loading ? <SkeletonLoading/>
                      : <ListCardItem allNewsItem={this.state.allNewsItem}
                                      currentUser={currentUser}
                                      createNewsPost={this.state.createNewsPost}
                      />
                }

              </div>

              <div className="col col-md-3" style={{paddingRight: '20px'}}>
                <PageRight toggleModalRecommendDetail={this.toggleModalRecommendDetail}
                           listRecommend={this.state.listRecommend}/>
              </div>

            </div>
          </div>

          {
            !this.state.hideNav ?
                <div style={{padding:'0', position:'fixed', right:'0', width:'260px'}}>
                  <Aside currentUser={currentUser}/>
                </div>
                : null
          }

        </div>
        <div className="app-footer"/>

        <Modal isOpen={this.state.modalCreatedPost}
               toggle={() => this.toggleModalCreatedPost()}
               className={'modal-lg modal-lg-custom' + this.props.className}
        >
          <ModalHeader toggle={() => this.toggleModalCreatedPost()}>
            <strong>
              <img src="/icon/icons8-browser_window.png" alt={""}/> Tạo bài viết
            </strong>
          </ModalHeader>
          <ModalBody style={{padding: '10px'}}>
            <div style={{display: 'flex', alignItems: 'center'}}>
              <a className="btn-user-in-create">
                <img src={currentUser && currentUser.imageUrl ? currentUser.imageUrl : '/icon/default.jpg'}
                     className="rounded-circle icon-user-in-create"
                     alt="Username"/>
              </a>
              <textarea rows="1" name="summary" id="news-summary"
                        onClick={this.toggleCollapse}
                        onChange={this.handleChangeInput}
                        placeholder={currentUser ? (currentUser.name
                            + ', thông tin bất động sản của bạn là gì?') : 'Thông tin bất động sản của bạn là gì?'}
                        onKeyDown={(e)=> this.onKeyDownTextArea(e)}/>
            </div>

            <hr/>
            <ImageUploader
                withIcon={false}
                buttonText='Ảnh/Video'
                onChange={this.onDrop}
                imgExtension={['.jpg', '.gif', '.png', '.gif',
                  '.jpeg']}
                maxFileSize={5242880}
                withPreview={true}
            />
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderInputAddress()}>
              <img src="/icon/icons8-order_delivered.png"/> Địa chỉ
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderInputContact()}>
              <img src="/icon/contact.png"/> Người liên hệ
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderInputAreaAndPrice()}>
              <img src="/icon/icons8-price_tag_euro.png" alt={""}/> Diện tích, giá
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderInputProject()}>
              <img src="/icon/icons8-project.png"/> Dự án
            </button>
            {' '}
            <button className="button-pill"
                    onClick={() => this.handleRenderMoreButton()}>
              <img src={"/icon/menu-5.svg"}/>
            </button>
            {moreButton}

            <hr/>

            {inputAddress}
            {inputContact}
            {inputAreaAndPrice}
            {inputProject}
            {inputfurniture}
            {inputFloor}
            {inputRoom}
            {inputToilet}
            {inputDirect}
            <div style={{display: 'flex', alignItems: 'center'}}>
              <button className="button-pill" style={{
                borderRadius: '5px',
                marginRight: '10px',
                width: '30%'
              }}
                      onClick={() => this.handleCloseAllInput()}>
                <i className="fas fa-caret-down"/> Close All
              </button>
              <LaddaButton
                  data-style={EXPAND_LEFT}
                  className="btn btn-info btn-ladda"
                  loading={this.state.loadingPost}
                  style={{
                    width: '70%',
                    fontWeight: '500',
                    backgroundColor: '#008FE5',
                    border: 'none',
                    color: 'white',
                    height: '40px',
                    lineHeight: 0
                  }}
                  onClick={() => this.handlePostNewsItem()}>
                <img src={"/icon/icons8-hand_cursor.png"}/> Chia sẻ
              </LaddaButton>
            </div>
          </ModalBody>
        </Modal>

        <AppSearch toggleModalSearch={this.toggleModalSearch}
                   callBackFromAppSearch={this.callBackFromAppSearch}
                   isSearch={this.state.isSearch}
                   optionProvince={this.state.optionProvince}
                   optionDistrict={this.state.optionDistrict}
                   optionRentType={this.state.optionRentType}
                   optionSaleType={this.state.optionSaleType}
                   optionPrice={this.state.optionPrice}
                   optionArea={this.state.optionArea}
                   optionDirectHouse={this.state.optionDirectHouse}
        />

        <RecommendModal recommendModal={this.state.recommendModal}
                        newsRecommend={this.state.newsRecommend}
                        // listRecommend={this.state.listRecommend}
                        loadingRecommend={this.state.loadingRecommend}
                        toggleModalRecommendDetail={this.toggleModalRecommendDetail}/>

        <Alert stack={{limit: 3}}
               timeout={3000}
               style={{zIndex:'9999'}}
               position='top-right' effect='slide' offset={65}/>
      </div>

    )
  }
}

// export default Home;
export default withCookies(withRouter(Home));