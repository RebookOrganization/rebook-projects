import React, {Component} from "react";
import "../../containers/App/_app.css";
import "../../containers/Home/_home.css";

class AppSearch extends Component{
  constructor(props) {
    super(props);
    this.state = {
      inputSearch: "",
      inputSearchType: 0
    }
  }

  render() {
    return (
        <div id="group-search" className="app-search">
          <div className="container">
            <div className="row">
              <div className="col-md-7">
                <div className="search-box">
                  <span className="fa fa-search"></span>
                  <input id="inputSearch"
                         placeholder="Tìm kiếm thông tin theo địa chỉ hoặc người dùng"
                         value={this.state.inputSearch}
                         onChange={(e) => this.setState(
                             {inputSearch: e.target.value})}
                  />
                </div>
              </div>
              <div className="col-md-3">
                <select className="form-control"
                        style={{height: '40px',border:'none',fontSize:'16px',color:'#aaa'}}
                        onChange={(e) => this.setState(
                            {inputSearchType: e.target.value})}
                >
                  <option value={0}>Chọn loại tìm kiếm</option>
                  <option value={1}>Địa điểm bất động sản</option>
                  <option value={2}>Loại giao dịch</option>
                  <option value={3}>Người dùng rebook</option>
                </select>
              </div>
              <div className="col-md-2">
                <button type="button" className="btn btn-offset"
                        style={{
                          width: '100%',
                          fontWeight: '500',
                          color: 'white',
                          height: '40px',
                          lineHeight: '0'
                        }}
                        // onClick={() => this.handleSearchByFiler()}
                >
                  <i className="fas fa-search"></i> Search
                </button>
              </div>
            </div>
          </div>
        </div>
    );
  }

}
export default AppSearch;