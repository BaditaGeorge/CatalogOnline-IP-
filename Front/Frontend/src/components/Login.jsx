import React, { Component } from 'react';
import '../App.css';
import { Button, Form } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import { connect } from 'react-redux';
import {
  loginUser,
} from '../actions/loginActions';


class Login extends Component {
  constructor (props) {
    super(props)
    this.state = {
      username: '',
      password: '',
      sessionId: '',
      success: false
    }
  }

  static redirectIfLogged = (props) => {
    props.token && window.location.pathname !== '/dashboard' && (window.location.href = '/dashboard')
    !props.token && window.location.pathname === '/dashboard' && (window.location.href = '/login')
  }

  onSubmit = (e) => {
    e.preventDefault();
    this.props.loginUser(this.state.username, this.state.password)
  }

  onChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  render () {
    return (
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
        </Card.Body>
      </Card>
    );
  }
}

export const LoginWithRedux = connect(() => ({}), {
  loginUser,
})(Login)