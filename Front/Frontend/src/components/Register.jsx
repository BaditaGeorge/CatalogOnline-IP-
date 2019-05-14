import React, {Component} from 'react';
import '../App.css';
import { MDBContainer, MDBRow, MDBCol, MDBBtn, MDBInput } from 'mdbreact';
import '../css/Register.css'
import {Button, Form} from "react-bootstrap";
import Card from "react-bootstrap/Card";

export default class Register extends Component {
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

                        <Form.Group controlId="formBasicMatr">
                            <Form.Label>Matricol Number</Form.Label>
                            <Form.Control type="nr" placeholder="Enter matricol" />
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" />
                        </Form.Group>

                        <Button  variant="primary" type="submit" block>
                            Register
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        );
    }
}




