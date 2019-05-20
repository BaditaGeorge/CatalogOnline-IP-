import React, { Component } from 'react';
import '../App.css';
import { Button, Form } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import { connect } from 'react-redux';
import {
  verifyEmail
} from '../actions/loginActions';


class Verify extends Component {
  constructor (props) {
    super(props)
    this.state = {
      code: ''
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit = (e) => {
    e.preventDefault();
    this.props.verifyEmail(this.state.code)
  }

  onChange (e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render () {
    return (
      <Card style={{ width: '20rem', marginTop: '10%', marginLeft: 'auto', marginRight: 'auto' }}>
        <Card.Body>
          <Form>
            <Form.Group controlId='formBasicEmail'>
              <Form.Label>Code</Form.Label>
              <Form.Control type='code' name='code' placeholder='Enter code' onChange={this.onChange}
                            value={this.state.code}/>
            </Form.Group>

            <Button variant='primary' type='button' onClick={this.onSubmit} block>
              Verify
            </Button>
          </Form>
        </Card.Body>
      </Card>
    );
  }
}

export const VerifyWithRedux = connect((state) => ({
  verified: state.loginReducer.verified,
}), {
  verifyEmail
})(Verify)