import React, { Component } from 'react';
import '../App.css';
import Card from 'react-bootstrap/Card';


export class NotFound extends Component {
  render () {
    return (
      <Card style={{ width: '32rem', marginTop: '10%', marginLeft: 'auto', marginRight: 'auto' }}>
        <Card.Header>
          <h1>404 Not found</h1>
        </Card.Header>
      </Card>
    );
  }
}