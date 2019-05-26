import React, { Component } from 'react';
import '../App.css';
import { Button, Form } from "react-bootstrap";
import Card from 'react-bootstrap/Card';
import { connect } from "react-redux";
import { registerUser, verifyEmail } from "../actions/loginActions";

class Register extends Component {
  constructor (props) {
    super(props)
    this.state = {
      email: '',
      password: '',
      userName: '',
      failToregister: false,
    };
    this.onChange = this.onChange.bind(this)
    this.onSubmit = this.onSubmit.bind(this)
  }


  onSubmit (e) {
    e.preventDefault();
    this.props.registerUser(this.state.userName, this.state.password,  this.state.email)
  }

  onChange (e) {
    this.setState({ [e.target.name]: e.target.value });
  }


  render () {
    return (
      <Card style={{ width: '20rem', marginTop: '10%', marginLeft: 'auto', marginRight: 'auto' }}>
        <Card.Body>
          <Form>
            <Form.Group controlId="formBasicMatr">
              <Form.Label>Username</Form.Label>
              <Form.Control type="text" name="userName" placeholder="james.doe" onChange={this.onChange}
                            value={this.state.userName}/>
            </Form.Group>


            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" name="email" placeholder="james.doe@info.uaic.ro" onChange={this.onChange}
                            value={this.state.email}/>
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" name="password" placeholder="password" onChange={this.onChange}
                            value={this.state.password}/>
            </Form.Group>

            <Button variant="primary" type="button" onClick={this.onSubmit} block>
              Register
            </Button>
          </Form>
        </Card.Body>
      </Card>
    );
  }

}

export const RegisterWithRedux = connect((state) => ({
  userName: state.loginReducer.userName,
  role: state.loginReducer.role,
  token: state.loginReducer.token,
  verified: state.loginReducer.verified,
  loading: state.loginReducer.loading
}), {
  registerUser,
  verifyEmail
})(Register)