import React, {Component} from 'react';
import '../Home/_home.css';
import { Card, CardBody, CardImg, CardText, Collapse } from "reactstrap";
import Row from "reactstrap/es/Row";
import Col from "reactstrap/es/Col";
import Alert from "react-s-alert";
import {searchNewsByAddress, searchNewsByUser} from "../../api/userCallApi";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";
import 'ladda/dist/ladda-themeless.min.css';
import "./_pageRight.css";



class PageRight extends Component {
  constructor(props) {
    super(props);

    this.state = {
      inputSearch: '',
      inputSearchType: 1,
      loading: false,
      resultSearchAddress: null,
      resultSearchUser: null,
      allNewsItem: null,

      collapseSearch: false,
    };

  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
  }

  handleScroll = () => {
    let pageRight = document.getElementById("page-right");
    let copyright = document.getElementById("Copyright");
    if (window.pageYOffset > copyright.offsetTop) {
      pageRight.classList.add("sticky-page-right")
    }
    else {
      pageRight.classList.remove("sticky-page-right")
    }
  };

  toggleCollapse = () => {
    this.setState({collapseSearch: !this.state.collapseSearch})
  };

  handleSearchByFiler = () => {
    const {inputSearch, inputSearchType} = this.state;
    this.setState({loading: true});

    console.log("input search type: " + inputSearchType);
    if (parseInt(inputSearchType) === 0) {
      Alert.error("Vui lòng chọn loại tìm kiếm.");
      this.setState({loading: false})
    } else if (parseInt(inputSearchType) === 1) {
      let address = inputSearch ? inputSearch : Alert.error(
          "Vui lòng nhập thông tin.");

      console.log("address: " + address);
      if (address !== null || address !== '') {

        //Api SearchByAddress
        searchNewsByAddress(address).then(res => {
          this.setState({
            resultSearchAddress: res.result,
            allNewsItem: res.result,
            loading: false
          }, () => {
            this.props.callBackFromPageRight(this.state.allNewsItem,
                this.state.loading);
          })
        }).catch((e) => {
          console.log(e);
          this.setState({loading: false});
          Alert.warning("Không có kết quả trả về.")
        });
      }
    } else {
      const requestParams = {
        username: inputSearch ? inputSearch : Alert.error(
            "Vui lòng nhập thông tin.")
      };
      console.log("requestParam: " + JSON.stringify(requestParams));

      //Api SearchByUser
      searchNewsByUser(requestParams).then(res => {
        this.setState({
          resultSearchUser: res.result,
        })
      }).catch(err => {
        console.log(err);
        Alert.warning("Không có kết quả trả về.")
      }).finally(() => {
        this.setState({loading: false})
      });
    }
  };

  render() {
    // className="sticky-top" style={{top: '60px', zIndex:'1'}}
    return (
        <div id={"page-right"}>
          <Card>
            <div style={{padding: '15px'}}>
              <div className="search-box">
                <span className="fa fa-search"/>
                <input id="inputSearch"
                       placeholder="Tìm kiếm nhanh địa chỉ"
                       onClick={this.toggleCollapse}
                       style={{
                         textIdent: '32px',
                         backgroundColor: '#f2f3f5',
                         outline: 'none'
                       }}
                       value={this.state.inputSearch}
                       onChange={(e) => this.setState(
                           {inputSearch: e.target.value})}
                />
              </div>
              <Collapse isOpen={this.state.collapseSearch}>
                <hr style={{marginTop: "5px"}}/>
                <Row>
                  <Col md={12} style={{paddingLeft: '5px'}}>
                    <select className="form-control"
                            style={{
                              height: '40px',
                              fontSize: '16px',
                              backgroundColor: '#f2f3f5',
                              marginBottom: "5px"
                            }}
                            onChange={(e) => this.setState(
                                {inputSearchType: e.target.value})}
                    >
                      <option value={0}>Chọn loại tìm kiếm</option>
                      <option value={1}>Địa điểm bất động sản</option>
                      <option value={2}>Loại giao dịch</option>
                      <option value={3}>Người dùng rebook</option>
                    </select>
                  </Col>
                </Row>
                <hr/>
                <Row style={{padding: '0 15px', justifyContent: 'flex-end'}}>
                  <LaddaButton
                      className="btn btn-info btn-ladda"
                      loading={this.state.loading}
                      onClick={() => this.handleSearchByFiler()}
                      data-style={EXPAND_LEFT}
                      style={{
                        backgroundColor: '#008FE5',
                        color: 'white',
                        border: 'none',
                        height: '40px',
                        lineHeight: '0'
                      }}>
                    <i className="fas fa-search"/> Search
                  </LaddaButton>
                </Row>
              </Collapse>
            </div>
          </Card>
          <Card>
            <CardBody>
              <strong style={{color: '#4b4f56'}}>Bất động sản được gợi
                ý</strong>
            </CardBody>
            <CardImg top width="100%"
                     src="https://www.ngoisaoso.vn/uploads/news/2014/02/19/thiet-ke-web-bat-dong-san-2.jpg"
                     alt="Card image cap"/>
            <CardBody>
              <CardText>With supporting text below as a natural lead-in to
                additional content.</CardText>
              <button className={"pull-right"}>
                <i className="fa fa-plus"/> Chi tiết
              </button>
            </CardBody>
          </Card>
          <Card>
            <CardImg top width="100%"
                     src="https://www.worldatlas.com/r/w728-h425-c728x425/upload/31/4c/3d/beautiful-new-zealand.jpg"
                     alt="Card image cap"/>
            <CardBody>
              <CardText>With supporting text below as a natural lead-in to
                additional content.</CardText>
              <button className={"pull-right"}>
                <i className="fa fa-plus"/> Chi tiết
              </button>
            </CardBody>
          </Card>
          <Card>
            <CardImg top width="100%"
                     src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
                     alt="Card image cap"/>
            <CardBody>
              <CardText>With supporting text below as a natural lead-in to
                additional content.</CardText>
              <button className={"pull-right"}>
                <i className="fa fa-plus"/> Chi tiết
              </button>
            </CardBody>
          </Card>
          <Card
              style={{display: 'flex', flexDirection: 'row', padding: '10px'}}>
            <CardText style={{marginRight: '20px'}}>Tiếng Việt.</CardText>
            <CardText style={{marginRight: '20px'}}>English.</CardText>
            <button className={"pull-right"}>
              <i className="fa fa-plus"/> Thêm
            </button>
          </Card>
          <div style={{display: 'flex'}}>
            <a href="https://mdbootstrap.com/education/bootstrap/"
               style={{color: '#616770', marginRight: '10px'}}>Điều khoản.</a>
            <a href="https://mdbootstrap.com/education/bootstrap/"
               style={{color: '#616770'}}>Quảng cáo.</a>
          </div>
          <span id={"Copyright"}
              style={{color: '#616770'}}>© 2019 Copyright: Rebook.com.vn</span>
        </div>
    )
  }
}

export default PageRight;