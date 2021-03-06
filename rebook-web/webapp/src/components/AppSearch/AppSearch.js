import React, {Component} from "react";
import LaddaButton, {EXPAND_LEFT} from "react-ladda";
import {Col, Modal, ModalBody, ModalHeader, NavLink, Row} from "reactstrap";
import Select from 'react-select';
import {elasticSearchNews, searchNewsItem,} from "../../api/userCallApi";
import shallowCompare from 'react-addons-shallow-compare';
import Alert from 'react-s-alert';
import InputRange from 'react-input-range';
import 'react-input-range/lib/css/index.css';
import {
  GoogleMap,
  InfoWindow,
  Marker,
  withGoogleMap,
  withScriptjs
} from 'react-google-maps';
import Geocode from "react-geocode";

Geocode.setApiKey("AIzaSyBkoOTnG-hF9GLjQj7TUIlKJjQtCUfswDc");
Geocode.setLanguage("en");

const defaultZoom = 10;
const defaultCenter = {lat: 10.8230989, lng: 106.6296638};

class MarkerList extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    // console.log(
    //     "MarkerList locations: " + JSON.stringify(this.props.locations));
    const {locations} = this.props;
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
        <Marker onClick={this.toggle} position={location} title={location.title}
                label={location.label}>
          {this.state.isOpen &&
          <InfoWindow onCloseClick={this.toggle}>
            <NavLink href={location.www}
                     target="_blank">{location.title}</NavLink>
          </InfoWindow>}
        </Marker>
    )
  }
}

const GoogleMapsComponent = withScriptjs(withGoogleMap((props) => {
      // console.log("props GoogleMapsComponent: " + JSON.stringify(props));
      return (
          <GoogleMap zoom={props.zoom} center={props.center}>
            {<MarkerList locations={props.locations}/>}
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
      selectedPrice: {value: 0, label: "Thoả Thuận", from: 0, to: 0},
      optionArea: null,
      selectedArea: {value: 0, label: "Chưa xác định", from: 0, to: 0},
      optionDirectHouse: null,
      selectedDirectHouse: 0,
      resultSearch: null,
      areaDistance: {min: 0, max: 1000},
      priceDistance: {min: 0, max: 10000},
      isRenderDistrict: false,
      listMarker: [],
      center: {lat: 10.8230989, lng: 106.6296638},
      zoom: 10,
      enableArea: true,
      enablePrice: true,
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
    let {
      isSearch, optionProvince, optionDistrict, optionRentType,
      optionSaleType, optionPrice, optionArea, optionDirectHouse
    } = this.props;
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
    if (shallowCompare(this, this.props, nextProps)) {
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
    const {
      selectedProvince, selectedDistrict, selectedRentType, inputSearch,
      selectedDirectHouse, areaDistance, priceDistance, selectedArea, selectedPrice
    } = this.state;
    this.setState({loading: true});

    const request = {
      content: inputSearch ? inputSearch : "",
      priceFrom: selectedPrice ? selectedPrice.from : 0,
      priceTo: selectedPrice ? selectedPrice.to : 0,
      areaFrom: selectedArea ? selectedArea.from : 0,
      areaTo: selectedArea ? selectedArea.to : 0,
      district: selectedDistrict ? selectedDistrict.label : "",
      provinceCity: selectedProvince ? selectedProvince.label : "",
      transType: selectedRentType ? selectedRentType.label : "",
      directHouse: selectedDirectHouse ? selectedDirectHouse.label : "",
    };

    searchNewsItem(request).then(res => {
      if (res.data && res.data.result.length) {
        this.setState({
          resultSearch: res.data.result,
          loading: false
        }, () => this.props.callBackFromAppSearch(this.state.resultSearch,
            this.state.loading))
      } else {
        Alert.info("Không tìm thấy bất động sản phù hợp");
      }
    })
    .catch(() => Alert.warning("Xảy ra lỗi khi tìm kiếm"))
    .finally(() => {
      this.setState({loading: false});
      this.toggleModalSearch();
    })
  };

  handleAddMarker = (location) => {
    let center = this.state.center;
    Geocode.fromAddress(location).then(res => {
      const data = res.results;
      let listMarker = data ? data.map(item => {
        center = {
          lat: item.geometry.location.lat,
          lng: item.geometry.location.lng
        };
        return {
          lat: item.geometry.location.lat,
          lng: item.geometry.location.lng,
          label: 'R',
          draggable: false,
          title: item.formatted_address,
          www: ''
        };
      }) : [];
      this.setState({
        listMarker: listMarker,
        center,
        zoom: 16
      })
    });
  };

  render() {
    const {
      optionProvince, optionDistrict, selectedDistrict, optionDirectHouse, selectedDirectHouse,
      optionPrice, optionArea, selectedArea, selectedPrice
    } = this.state;

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

    let area = [];
    optionArea ? optionArea.map(item => {
      area.push({
        value: item.value,
        label: item.displayValue,
        from: item.from,
        to: item.to
      })
    }) : null;

    let price = [];
    optionPrice ? optionPrice.map(item => {
      price.push({
        value: item.value,
        label: item.displayValue,
        from: item.from,
        to: item.to
      })
    }) : null;

    return (
        <React.Fragment>
          <Modal isOpen={this.state.isSearch}
                 toggle={() => this.toggleModalSearch()}
                 className={'modal-lg modal-lg-custom modal-info'
                 + this.props.className}
                 style={{maxWidth: '80%'}}
          >
            <ModalHeader toggle={() => this.toggleModalSearch()}>
              <img src="/icon/icons8-search-2.png" alt={""}/> Công cụ tìm kiếm
            </ModalHeader>
            <ModalBody style={{padding: '0'}}>
              <Row>
                <Col xs={12} sm={5}>
                  <div style={{margin: '15px 0 15px 15px'}}>
                    <div className="search-box" style={{marginBottom: "5px"}}>
                      <span className="fa fa-search"/>
                      <input id="inputSearch"
                             placeholder="Nhập địa điểm, vd: Sunrise City"
                             style={{
                               textIdent: '32px',
                               backgroundColor: '#f2f3f5',
                               outline: 'none'
                             }}
                             value={this.state.inputSearch}
                             onBlur={() => this.handleAddMarker(
                                 this.state.inputSearch)}
                             onChange={(e) => this.setState(
                                 {inputSearch: e.target.value})}
                      />
                    </div>
                    <hr/>
                    <Row>
                      <Col md={12}>
                        <h5>Tỉnh/Thành phố</h5>
                        <Select value={this.state.selectedProvince}
                                onChange={(e) => this.setState(
                                    {selectedProvince: e},
                                    () => {
                                      if (this.state.selectedProvince) {
                                        if (parseInt(
                                            this.state.selectedProvince.value)
                                            === 1) {
                                          this.setState(
                                              {isRenderDistrict: true})
                                        } else {
                                          this.setState(
                                              {isRenderDistrict: false})
                                        }
                                      }
                                    })}
                                options={province}
                                isSearchable={true}
                                isClearable={true}
                                style={{fontSize: '16px'}}
                        />
                      </Col>
                    </Row>
                    <hr/>
                    <Row>
                      {
                        this.state.isRenderDistrict ?
                            <Col md={12}>
                              <h5>Quận/Huyện: </h5>
                              <Select value={selectedDistrict}
                                      onChange={(e) => this.setState(
                                          {selectedDistrict: e})}
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
                      <Col md={12}>
                        <h5>Hướng nhà: </h5>
                        <Select value={selectedDirectHouse}
                                onChange={(e) => this.setState(
                                    {selectedDirectHouse: e})}
                                options={directHouse}
                                isSearchable={true}
                                isClearable={true}
                        />
                      </Col>
                    </Row>
                    <hr/>
                    <Row>
                      {
                        this.state.enablePrice ?
                            <Col md={12}>
                              <h5>Giá cả: </h5>
                              <Select value={selectedPrice}
                                      onChange={(e) => this.setState(
                                          {selectedPrice: e},() => {
                                            if (this.state.selectedPrice) {
                                              if (parseInt(this.state.selectedPrice.value) !== 0)  {
                                                this.setState({enableArea: false})
                                              }
                                              else {
                                                this.setState({enableArea: true})
                                              }
                                            }
                                            else {
                                              this.setState({enableArea: true})
                                            }
                                          })}
                                      options={price}
                                      isSearchable={true}
                                      isClearable={true}
                              />
                            </Col> : null
                      }
                    </Row>
                    <hr/>
                    <Row>
                      {
                        this.state.enableArea ?
                            <Col md={12}>
                              <h5>Diện tích: </h5>
                              <Select value={selectedArea}
                                      onChange={(e) => this.setState(
                                          {selectedArea: e}, () => {
                                            if (this.state.selectedArea) {
                                              if (parseInt(this.state.selectedArea.value) !== 0) {
                                                this.setState({enablePrice: false})
                                              }
                                              else {
                                                this.setState({enablePrice: true})
                                              }
                                            }
                                            else {
                                              this.setState({enablePrice: true})
                                            }
                                          })}
                                      options={area}
                                      isSearchable={true}
                                      isClearable={true}
                              />
                            </Col> : null
                      }
                    </Row>
                    <Row style={{
                      padding: '130px 15px 0',
                      justifyContent: 'center'
                    }}>
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
                        <i className="fas fa-search"/> Tìm kiếm
                      </LaddaButton>
                    </Row>
                  </div>
                </Col>
                <Col xs={12} sm={7}>
                  <GoogleMapsComponent
                      key="map"
                      googleMapURL="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyBkoOTnG-hF9GLjQj7TUIlKJjQtCUfswDc"
                      loadingElement={<div style={{height: `100%`}}/>}
                      containerElement={<div style={{height: `650px`}}/>}
                      mapElement={<div style={{height: `100%`}}/>}
                      locations={this.state.listMarker}
                      center={this.state.center}
                      zoom={this.state.zoom}
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