import React, {Component} from 'react';
import {Link, NavLink} from 'react-router-dom';
import './_appHeader.css';
import '../../containers/Home/_home.css';
import {
  loadEnumArea, loadEnumDirectHouse,
  loadEnumDistrict, loadEnumPrice,
  loadEnumProvince,
  loadEnumRentType, loadEnumSaleType
} from "../../api/requestFilterSearchApi";
import LoadingIndicator from "../Loading/LoadingIndicator";
import InfiniteScroll from "react-infinite-scroller";
import AppSearch from "../AppSearch/AppSearch";

class AppHeader extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: this.props.currentUser ? this.props.currentUser : null,
      isSearch: false,
      loading: false,
      // optionProvince: null,
      // optionDistrict: null,
      // optionRentType: null,
      // optionSaleType: null,
      // optionPrice: null,
      // optionArea: null,
      // optionDirectHouse: null,
      items: 15
    }
  }

  // handleLoadEnum = () => {
  //   this.setState({loading: true});
  //   let provinceCity = loadEnumProvince();
  //   let district = loadEnumDistrict();
  //   let rentType = loadEnumRentType();
  //   let saleType = loadEnumSaleType();
  //   let priceOption = loadEnumPrice();
  //   let areaOption = loadEnumArea();
  //   let directHouse = loadEnumDirectHouse();
  //
  //   Promise.all([provinceCity, district, rentType, saleType,
  //     priceOption, areaOption, directHouse]).then(res => {
  //     console.log("res: "+ JSON.stringify(res));
  //     this.setState({
  //       optionProvince: res[0].data,
  //       optionDistrict: res[1].data,
  //       optionRentType: res[2].data,
  //       optionSaleType: res[3].data,
  //       optionPrice: res[4].data,
  //       optionArea: res[5].data,
  //       optionDirectHouse: res[6].data,
  //     })
  //   }).catch(e => console.log(e))
  //   .finally(()=> {
  //     this.setState({loading: false})
  //   })
  // };

  toggleModalSearch = () => {
    if (typeof this.props.toggleModalSearch === 'function') {
      return this.props.toggleModalSearch()
    }
  };

  loadMore() {
    if (this.state.items === 100) {
      this.setState({ hasMoreItems: false});
    }
    else {
      setTimeout(() => {
        this.setState({ items: this.state.items + 20});
      }, 2000);
    }
  };

  showItems() {
    let items = [];
    const styleChat = {
      display: 'flex',
      alignItems: 'center',
      padding: '0',
      maxHeight:'56px'
    };

    const dot = {
      height: '8px',
      width: '8px',
      backgroundColor: '#4dbd74',
      borderRadius: '50%',
      display: 'inline-block',
      marginLeft: '300px'
    };

    for (let i = 0; i < this.state.items; i++) {
      items.push(
        <React.Fragment>
          <a className="dropdown-item">
            <div style={styleChat}>
              <div className="btn-user">
                <img src={'assets/img/avatars/4.jpg'}
                     className="rounded-circle icon-user"
                     alt="Username"/>
              </div>{' '}
              <p style={{fontSize: '15px', marginTop: '15px'}}>user chat</p>
              <span className={"pull-right"} style={dot}/>
            </div>
          </a>
          <hr/>
        </React.Fragment>
      );
    }
    return items;
  };

  render() {
    const {currentUser, optionProvince, optionDistrict, optionRentType,
      optionSaleType, optionPrice, optionArea, optionDirectHouse} = this.state;

    return (
        <header className="app-header">
          {
            this.state.loading ? <LoadingIndicator/> : null
          }
          <div className="container-fluid" style={{paddingLeft: "40px"}}>
            <div className="row">
              <div className="col-md-5 app-branding">
                <img src="/assets/brand/sygnet.svg" style={{
                  width: '40px',
                  height: '40px',
                  marginRight: '20px'
                }}/>
                <Link to="/profile" className="app-title">Rebook</Link>
                <div className="input-group input-group-sm"
                   style={{marginLeft:'20px', width:'350px'}}>
                <input type="text" className="form-control"
                       placeholder="Tìm kiếm"
                       style={{fontSize:'14px',height:'34px'}}
                       onClick={this.toggleModalSearch}
                       aria-label="Tìm kiếm"
                       aria-describedby="basic-addon2"/>
                  <div className="input-group-append">
                    <span className="input-group-text"
                          style={{width:'50px'}}
                          id="basic-addon2">
                      <span className="fa fa-search" style={{color:'gray',marginLeft:'10px'}}/>
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-md-5 app-options">
                <nav className="app-nav">
                  <ul>
                    <li className="li-profile">
                      <img
                          src={currentUser && currentUser.imageUrl
                              ? currentUser.imageUrl
                              : '/icon/default.jpg'}
                          className="rounded-circle icon-profile"
                          alt="Username"/>
                      <NavLink to="/profile" style={{paddingLeft: "10px"}}>
                        {currentUser ? currentUser.name : "username"}
                      </NavLink>
                    </li>
                    <li>
                      <NavLink to="/home">Trang chủ</NavLink>
                    </li>
                    <li>
                      {/*<NavLink to="/message">Tin Nhắn</NavLink>*/}
                      <div className="dropdown message">
                        <button className="btn border-none-outline"
                                type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true"
                                style={{fontSize:'16px', fontWeight:'500',
                                  color:'white', padding:'0 5px'}}
                                aria-expanded="false">
                          <img className={"responsive"}
                               src="/icon/iconfinder_messenger.svg"
                               style={{width:'34px'}} alt={""}/>
                          {/*Tin Nhắn*/}
                        </button>
                        <div className="dropdown-menu dropdown-menu-center">
                          <h6 style={{color:'#616770'}}>Bắt đầu trò chuyện</h6>
                          <hr/>
                          <div style={{height:'500px', overflow:'auto'}}>
                            <InfiniteScroll
                                loadMore={this.loadMore.bind(this)}
                                hasMore={this.state.hasMoreItems}
                                loader={<div className="loader" key={""}><strong> Loading... </strong></div>}
                                useWindow={false}>
                              {this.showItems()}{" "}
                            </InfiniteScroll>
                          </div>
                          <hr/>
                          <a className="dropdown-item">
                            <i className="far fa-flag"/> Gửi phản hồi
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <div className="dropdown message">
                        <button className="btn border-none-outline"
                                type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true"
                                style={{fontSize:'16px', fontWeight:'500',
                                  color:'white', padding:'0 5px'}}
                                aria-expanded="false">
                          <img className={"responsive"}
                               src="/icon/alarm.svg"
                               style={{width:'29px',height:'25px'}} alt={""}/>
                        </button>
                        <div className="dropdown-menu dropdown-menu-center"
                             style={{width:'400px'}}
                        >
                          <a className="dropdown-item">
                            <i className="far fa-save"/> Lưu bài viết
                          </a>
                          <hr/>
                          <a className="dropdown-item">
                            <i className="far fa-flag"/> Gửi phản hồi
                          </a>
                        </div>
                      </div>
                    </li>
                    <li>
                      <a href="/logout">Logout</a>
                    </li>
                  </ul>
                </nav>
              </div>
              <div className="col-md-2">
              </div>
            </div>
          </div>

          {/*<AppSearch toggleModalSearch={this.toggleModalSearch}*/}
          {/*           isSearch={this.state.isSearch}*/}
          {/*           optionProvince={optionProvince}*/}
          {/*           optionDistrict={optionDistrict}*/}
          {/*           optionRentType={optionRentType}*/}
          {/*           optionSaleType={optionSaleType}*/}
          {/*           optionPrice={optionPrice}*/}
          {/*           optionArea={optionArea}*/}
          {/*           optionDirectHouse={optionDirectHouse}*/}
          {/*/>*/}
        </header>
    )
  }
}

export default AppHeader;