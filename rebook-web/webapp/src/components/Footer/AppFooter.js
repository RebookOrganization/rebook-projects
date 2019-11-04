import React, {Component} from 'react';
import './AppFooter.css'

class AppFooter extends Component{
  render() {
    return (
        <footer className="page-footer font-small pt-4">
          <div className="container">
            <div className="row">
              <div className="col-md-6 mb-4">
                <form className="form-inline">
                  <input className="form-control form-control-sm mr-3 w-75"
                         type="text" placeholder="Search"
                         aria-label="Search"/>
                    <i className="fas fa-search" aria-hidden="true"></i>
                </form>
              </div>
              <div className="col-md-6 mb-4">
                <form className="input-group">
                  <input type="text" className="form-control form-control-sm"
                         placeholder="Your email"
                         aria-label="Your email"
                         aria-describedby="basic-addon2"/>
                  <button className="btn btn-outline-dark btn-sign text-light"
                          type="button">Sign up
                  </button>
                </form>
              </div>
            </div>
          </div>
          <div className="footer-copyright text-center py-3">© 2018 Copyright:
            <a href="https://mdbootstrap.com/education/bootstrap/"
               className="corp"> RealEstateSocial.com</a>
          </div>
        </footer>
    )
  }
}

export default AppFooter;
