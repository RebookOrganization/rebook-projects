import React from "react";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";
import {
  Modal,
  ModalBody,
  ModalHeader, Row, Col
} from "reactstrap";
import Select from 'react-select';
import {searchNewsByAddress, searchNewsByUser} from "../../api/userCallApi";
import shallowCompare from 'react-addons-shallow-compare';
import Alert from 'react-s-alert';

class AppSearch extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isSearch: false,
      inputSearch: '',
      inputSearchType: 0,
      loading: false,
      resultSearchAddress: null,
      resultSearchUser: null,
      allNewsItem: null,
      optionProvince: null,
      selectedProvince: 0,
      optionDistrict: null,
      selectedDistrict: 0,
      optionRentType: null,
      selectedRentType: 0,
      optionSaleType: null,
      selectedSaleType: 0,
      optionPrice: null,
      selectedPrice: 0,
      optionArea: null,
      selectedArea: 0,
      optionDirectHouse: null,
      selectedDirectHouse: 0,

    }
  }

  toggleModalSearch = () => {
    if (typeof this.props.toggleModalSearch === 'function') {
      return this.props.toggleModalSearch();
    }
  };

  handleChangeProvince = (optionProvince) => {
    this.setState({optionProvince: optionProvince})
  };

  componentWillMount() {
    let {isSearch, optionProvince, optionDistrict, optionRentType,
      optionSaleType, optionPrice, optionArea, optionDirectHouse} = this.props;
    this.setState({
      isSearch: isSearch,
      optionProvince: optionProvince,
      optionDistrict: optionDistrict,
      optionRentType: optionRentType,
      optionSaleType: optionSaleType,
      optionPrice: optionPrice,
      optionArea: optionArea,
      optionDirectHouse: optionDirectHouse,
    })
  }

  componentWillReceiveProps(nextProps) {
    if(shallowCompare(this, this.props, nextProps)) {
      this.setState({
        isSearch: nextProps.isSearch,
        optionProvince: nextProps.optionProvince,
        optionDistrict: nextProps.optionDistrict,
        optionRentType: nextProps.optionRentType,
        optionSaleType: nextProps.optionSaleType,
        optionPrice: nextProps.optionPrice,
        optionArea: nextProps.optionArea,
        optionDirectHouse: nextProps.optionDirectHouse,
      })
    }
  }

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
    const {optionProvince, optionDistrict, selectedDistrict, optionRentType,
      selectedRentType, optionSaleType, selectedSaleType, optionPrice, selectedPrice,
      optionArea, selectedArea, optionDirectHouse, selectedDirectHouse} = this.state;

    let province = [];
    optionProvince ? Object.keys(optionProvince).map(item => {
      province.push({
        value: item,
        label: optionProvince[item]
      })
    }) : null;

    let district = [];
    optionDistrict ? Object.keys(optionDistrict).map(item => {
      district.push({
        value: item,
        label: optionDistrict[item]
      })
    }) : null;

    let rentType = [];
    optionRentType ? Object.keys(optionRentType).map(item => {
      rentType.push({
        value: item,
        label: optionRentType[item]
      })
    }) : null;

    let saleType = [];
    optionSaleType ? Object.keys(optionSaleType).map(item => {
      saleType.push({
        value: item,
        label: optionSaleType[item]
      })
    }) : null;

    let prices = [];
    optionPrice ? Object.keys(optionPrice).map(item => {
      prices.push({
        value: item,
        label: optionPrice[item]
      })
    }) : null;

    let areas = [];
    optionArea ? Object.keys(optionArea).map(item => {
      areas.push({
        value: item,
        label: optionArea[item]
      })
    }) : null;

    let directHouse = [];
    optionDirectHouse ? Object.keys(optionDirectHouse).map(item => {
      directHouse.push({
        value: item,
        label: optionDirectHouse[item]
      })
    }) : null;

    return (
        <React.Fragment>
          <Modal isOpen={this.state.isSearch}
                 toggle={()=>this.toggleModalSearch()}
                 className={'modal-lg modal-lg-custom' + this.props.className}
                 style={{maxWidth: '90%'}}
          >
            <ModalHeader toggle={()=>this.toggleModalSearch()}>
              <img src="/icon/icons8-search-2.png" alt={""}/> Tìm kiếm thông tin bất động sản
            </ModalHeader>
            <ModalBody style={{padding:'15px'}}>
              <div className="search-box" style={{marginBottom:"5px"}}>
                <span className="fa fa-search"/>
                <input id="inputSearch" placeholder="Nội dung tìm kiếm"
                       style={{textIdent:'32px',backgroundColor: '#f2f3f5',outline:'none'}}
                       value={this.state.inputSearch}
                       onChange={(e) => this.setState(
                           {inputSearch: e.target.value})}
                />
              </div>
              <hr/>
              <Row>
                <Col md={4}>
                  <h5>Loại tìm kiếm</h5>
                  <select className="form-control"
                          style={{height: '40px',fontSize:'16px',backgroundColor: '#f2f3f5',marginBottom:"5px"}}
                          onChange={(e) => this.setState(
                              {inputSearchType: e.target.value})}
                  >
                    <option value={0}>Chọn loại tìm kiếm</option>
                    <option value={1}>Địa điểm bất động sản</option>
                    <option value={2}>Loại giao dịch</option>
                    <option value={3}>Người dùng rebook</option>
                  </select>
                </Col>
                <Col md={4}>
                  <h5>Loại bất động sản: </h5>
                  <select className="form-control"
                          style={{height: '40px',fontSize:'16px',backgroundColor: '#f2f3f5',marginBottom:"5px"}}
                  >
                    <option value={0}>Mua bán</option>
                    <option value={1}>Cho thuê</option>
                    <option value={2}>Kho bãi</option>
                  </select>
                </Col>
                <Col md={4}>
                  <h5>Giá: </h5>
                  <Select value={selectedPrice}
                          onChange={(e)=> this.setState({selectedPrice: e})}
                          options={prices}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
              </Row>
              <hr/>
              <Row>
                <Col md={4}>
                  <h5>Tỉnh/Thành phố</h5>
                  <Select value={this.state.selectedProvince}
                          onChange={(e)=> this.setState({selectedProvince: e})}
                          options={province}
                          isSearchable={true}
                          isClearable={true}
                          style={{fontSize:'16px'}}
                  />
                </Col>
                <Col md={4}>
                  <h5>Mua bán: </h5>
                  <Select value={selectedSaleType}
                          onChange={(e)=> this.setState({selectedSaleType: e})}
                          options={saleType}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
                <Col md={4}>
                  <h5>Cho thuê: </h5>
                  <Select value={selectedRentType}
                          onChange={(e)=> this.setState({selectedRentType: e})}
                          options={rentType}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
              </Row>
              <hr/>
              <Row>
                <Col md={4}>
                  <h5>Diện tích: </h5>
                  <Select value={selectedArea}
                          onChange={(e)=> this.setState({selectedArea: e})}
                          options={areas}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
                <Col md={4}>
                  <h5>Hướng nhà: </h5>
                  <Select value={selectedDirectHouse}
                          onChange={(e)=> this.setState({selectedDirectHouse: e})}
                          options={directHouse}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
                <Col md={4}>
                  <h5>Quận/Huyện: </h5>
                  <Select value={selectedDistrict}
                          onChange={(e)=> this.setState({selectedDistrict: e})}
                          options={district}
                          isSearchable={true}
                          isClearable={true}
                  />
                </Col>
              </Row>
              <hr/>
              <Row style={{padding:'0 15px',justifyContent:'flex-end'}}>
                <LaddaButton
                    className="btn btn-info btn-ladda"
                    loading={this.state.loading}
                    onClick={() => this.handleSearchByFiler()}
                    data-style={EXPAND_LEFT}
                    style={{backgroundColor: '#008FE5', color: 'white',border:'none',height:'40px',lineHeight:'0'}}>
                  <i className="fas fa-search"/> Tìm kiếm
                </LaddaButton>
              </Row>
            </ModalBody>
          </Modal>
        </React.Fragment>
    );
  }

}

export default AppSearch;