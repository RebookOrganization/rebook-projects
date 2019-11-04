import React from 'react';
import {Spinner} from "reactstrap";

export default function LoadingIndicator(props) {
    return (
        <div className="loading-indicator" style={{display: 'block', textAlign: 'center', marginTop: '300px'}}>
          <Spinner type="grow" color="info" />
          <Spinner type="grow" color="info" />
          <Spinner type="grow" color="info" />
          <Spinner type="grow" color="info" />
        </div>
    );
}