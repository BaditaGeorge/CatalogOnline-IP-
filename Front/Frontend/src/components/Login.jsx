import React, { Component, Suspense } from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import '../App.css';
import { Button, Form } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import { connect } from 'react-redux';
import {
  loginUser,
  verifyEmail
} from '../actions/loginActions';
import { ProfessorDashboardRedux } from "./ProfessorDashboard";


class Login extends Component {
  constructor (props) {
    super(props)
    this.state = {
      username: '',
      password: '',
      sessionId: '',
      failToLogin: false,
      loggedIn: false
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillUpdate (nextProps, nextState, nextContext) {
    if (nextProps.token.length && !this.state.loggedIn)
      this.setState({ loggedIn: true })
  }

  onSubmit = (e) => {
    e.preventDefault();
    this.props.loginUser(this.state.username, this.state.password)
  }

  onChange (e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render () {
    return (
      // !this.state.loggedIn ?
        <Card style={{ width: '20rem', marginTop: '10%', marginLeft: 'auto', marginRight: 'auto' }}>
          <Card.Body>
            <Form>
              <Form.Group controlId='formBasicEmail'>
                <Form.Label>Username</Form.Label>
                <Form.Control type='text' name='username' placeholder='Enter username' onChange={this.onChange}
                              value={this.state.username}/>
              </Form.Group>

              <Form.Group controlId='formBasicPassword'>
                <Form.Label>Password</Form.Label>
                <Form.Control type='password' name='password' placeholder='Password' onChange={this.onChange}
                              value={this.state.password}/>
              </Form.Group>

              <Button variant='primary' type='button' onClick={this.onSubmit} block>
                Login
              </Button>
            </Form>
            {this.state.failToLogin && <p>Failed. Try again.</p>}
          </Card.Body>
        </Card>
    );
  }
}

export const LoginWithRedux = connect((state) => ({
  userName: state.loginReducer.userName,
  role: state.loginReducer.role,
  token: state.loginReducer.token,
  verified: state.loginReducer.verified,
  loading: state.loginReducer.loading
}), {
  loginUser,
  verifyEmail
})(Login)