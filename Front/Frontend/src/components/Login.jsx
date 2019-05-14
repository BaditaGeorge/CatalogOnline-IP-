import React, {Component} from 'react';
import '../App.css';
import {Button,Form} from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import '../css/Login.css'

export default class Login extends Component {
    constructor(props) {
        super(props)
    }

    sendForm() {

    }

    updateUser() {

    }

    render() {
        return (

            <Card style={{ width: '20rem', marginTop: '10%',marginLeft:'auto' ,marginRight:'auto' }}>
                <Card.Body>
                    <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email" />
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" />
                        </Form.Group>

                        <Button  variant="primary" type="submit" block>
                            Login
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        );
    }
}