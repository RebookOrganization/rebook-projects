import React, {Component} from "react";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";
import {
  Modal,
  ModalBody,
  ModalHeader, Row, Col, NavLink
} from "reactstrap";
import Select from 'react-select';
import {
  elasticSearchNews,
} from "../../api/userCallApi";
import shallowCompare from 'react-addons-shallow-compare';
import Alert from 'react-s-alert';
import InputRange from 'react-input-range';
import 'react-input-range/lib/css/index.css';
import {withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow } from 'react-google-maps';

const defaultZoom = 15;
const defaultCenter = {lat: 12.23893, lng: 109.1906241};
const locations = [
  {
    lat: 12.2406224,
    lng: 109.1956035,
    label: 'S',
    draggable: false,
    title: 'Tháp Trầm Hương Nha Trang',
    www: 'https://www.vntrip.vn/cam-nang/thap-tram-huong-diem-den-thu-vi-trong-tour-du-lich-nha-trang-1043'
  },
];

class MarkerList extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return locations.map((location, index) => {
          return (
              <MarkerWithInfoWindow key={index.toString()} location={location}/>
          )
        }
    );
  }
}

class MarkerWithInfoWindow extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false
    };
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }
  render() {
    const {location} = this.props;

    return (
        <Marker onClick={this.toggle} position={location} title={location.title} label={location.label}>
          {this.state.isOpen &&
          <InfoWindow onCloseClick={this.toggle}>
            <NavLink href={location.www} target="_blank">{location.title}</NavLink>
          </InfoWindow>}
        </Marker>
    )
  }
}

const GoogleMapsComponent = withScriptjs(withGoogleMap((props) => {
      return (
          <GoogleMap defaultZoom={defaultZoom} defaultCenter={defaultCenter}>
            {<MarkerList locations={locations}/>}
          </GoogleMap>
      );
    }
));

class AppSearch extends React.PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      isSearch: false,
      inputSearch: '',
      inputSearchType: 0,
      loading: false,
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
      resultSearch: null,
      areaDistance: {min: 0, max: 1000},
      priceDistance: {min: 0, max: 10000},
      isRenderDistrict: false
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
    const {selectedProvince, selectedDistrict, selectedRentType, inputSearch,
      selectedDirectHouse, areaDistance, priceDistance} = this.state;
    this.setState({loading: true});

    const request = {
      content: inputSearch ? inputSearch : "",
      priceFrom: priceDistance ? priceDistance.min : "",
      priceTo: priceDistance ? priceDistance.max : "",
      areaFrom: areaDistance ? areaDistance.min : "",
      areaTo: areaDistance ? areaDistance.max : "",
      district: selectedDistrict ? selectedDistrict.label : "",
      provinceCity: selectedProvince ? selectedProvince.label : "",
      transType: selectedRentType ? selectedRentType.label : "",
      directHouse: selectedDirectHouse ? selectedDirectHouse.label : "",
    };

    elasticSearchNews(request).then(res => {
      if (res.data && res.data.result.length) {
        this.setState({
          resultSearch: res.data.result,
          loading: false
        }, () => this.props.callBackFromAppSearch(this.state.resultSearch,
            this.state.loading))
      }
      else {
        Alert.warning("Tìm kiếm không có kết quả");
      }
    })
    .catch(()=>Alert.warning("Xảy ra lỗi khi tìm kiếm"))
    .finally(()=>{
      this.setState({loading: false});
      this.toggleModalSearch();
    })
  };

  render() {
    const {optionProvince, optionDistrict, selectedDistrict, optionDirectHouse, selectedDirectHouse} = this.state;

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
                 className={'modal-lg modal-lg-custom modal-info' + this.props.className}
                 style={{maxWidth: '85%'}}
          >
            <ModalHeader toggle={()=>this.toggleModalSearch()}>
              <img src="/icon/icons8-search-2.png" alt={""}/> Công cụ tìm kiếm
            </ModalHeader>
            <ModalBody style={{padding:'15px'}}>
              <Row>
                <Col xs={12} sm={6}>
                  <div className="search-box" style={{marginBottom:"5px"}}>
                    <span className="fa fa-search"/>
                    <input id="inputSearch" placeholder="Nhập địa điểm, vd: Sunrise City"
                           style={{textIdent:'32px',backgroundColor: '#f2f3f5',outline:'none'}}
                           value={this.state.inputSearch}
                           onChange={(e) => this.setState(
                               {inputSearch: e.target.value})}
                    />
                  </div>
                  <hr/>
                  <Row>
                    <Col md={6}>
                      <h5>Tỉnh/Thành phố</h5>
                      <Select value={this.state.selectedProvince}
                              onChange={(e)=> this.setState({selectedProvince: e},
                                  ()=>{
                                    if (this.state.selectedProvince) {
                                      if (parseInt(this.state.selectedProvince.value) === 1) {
                                        this.setState({isRenderDistrict: true})
                                      }
                                      else {
                                        this.setState({isRenderDistrict: false})
                                      }
                                    }
                                  })}
                              options={province}
                              isSearchable={true}
                              isClearable={true}
                              style={{fontSize:'16px'}}
                      />
                    </Col>
                    {
                      this.state.isRenderDistrict ?
                          <Col md={6}>
                            <h5>Quận/Huyện: </h5>
                            <Select value={selectedDistrict}
                                    onChange={(e)=> this.setState({selectedDistrict: e})}
                                    options={district}
                                    isSearchable={true}
                                    isClearable={true}
                            />
                          </Col>
                          : null
                    }
                  </Row>
                  <hr/>
                  <Row>
                    <Col md={6}>
                      <h5>Hướng nhà: </h5>
                      <Select value={selectedDirectHouse}
                              onChange={(e)=> this.setState({selectedDirectHouse: e})}
                              options={directHouse}
                              isSearchable={true}
                              isClearable={true}
                      />
                    </Col>
                  </Row>
                  <hr/>
                  <Row style={{paddingTop: "20px"}}>
                    <Col xs={12} sm={12}>
                      <h5>Diện tích đơn vị: m²</h5>
                      {/*<Slider marks={marksArea} step={null} onChange={this.onChangeArea} defaultValue={100} />*/}
                      <div style={{margin:'40px 10px'}}>
                        <InputRange
                            maxValue={5000}
                            minValue={0}
                            value={this.state.areaDistance}
                            onChange={value => this.setState({ areaDistance: value })} />
                      </div>
                    </Col>
                  </Row>
                  <Row style={{paddingTop: "20px"}}>
                    <Col xs={12} sm={12}>
                      <h5>Giá đơn vị: triệu </h5>
                      {/*<Slider marks={marksPrice} step={null} onChange={this.onChangePrice} defaultValue={100} />*/}
                      <div style={{margin:'40px 10px'}}>
                        <InputRange
                            maxValue={50000}
                            minValue={0}
                            value={this.state.priceDistance}
                            onChange={value => this.setState({ priceDistance: value })} />
                      </div>
                    </Col>
                  </Row>
                  <Row style={{padding:'115px 15px 0',justifyContent:'center'}}>
                    <LaddaButton
                        className="btn btn-info btn-ladda"
                        loading={this.state.loading}
                        onClick={() => this.handleSearchByFiler()}
                        data-style={EXPAND_LEFT}
                        style={{backgroundColor: '#008FE5', color: 'white',border:'none',height:'40px',lineHeight:'0'}}>
                      <i className="fas fa-search"/> Tìm kiếm
                    </LaddaButton>
                  </Row>
                </Col>
                <Col xs={12} sm={6}>
                  <GoogleMapsComponent
                      key="map"
                      googleMapURL="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyBGcJRwkhXS-DG7qKWPwURYc-kbvmL77uw"
                      loadingElement={<div style={{height: `100%`}}/>}
                      containerElement={<div style={{height: `650px`}}/>}
                      mapElement={<div style={{height: `100%`}}/>}
                  />
                </Col>
              </Row>
            </ModalBody>
          </Modal>
        </React.Fragment>
    );
  }

}

export default AppSearch;