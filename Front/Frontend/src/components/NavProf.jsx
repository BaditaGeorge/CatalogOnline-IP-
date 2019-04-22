import React, {Component} from 'react';
import { Button, Navbar, Nav, NavItem, NavDropdown, MenuItem, Image, Col } from 'react-bootstrap';

export default class NavProf extends Component {
  constructor(props) {
      super(props);
  }
  render() {
    return(
    <Navbar collapseOnSelect expand="sm" bg="light" variant="light">
  <Navbar.Toggle aria-controls="responsive-navbar-nav" />
  <Navbar.Collapse id="responsive-navbar-nav">
    <Nav className="mr-auto">
      <Button variant="secondary">Logo</Button>
      <NavDropdown title="Courses" id="collasible-nav-dropdown">
        <NavDropdown.Item href="#action/1">Advanced Programming</NavDropdown.Item>
        <NavDropdown.Item href="#action/2">Graph Algorithms</NavDropdown.Item>
        <NavDropdown.Item href="#action/3">Java Technologies</NavDropdown.Item>
        <NavDropdown.Divider />
        <NavDropdown.Item href="#action/4">+ Add a new class</NavDropdown.Item>
      </NavDropdown>
    </Nav>
    <Nav>
      <NavDropdown title="Mark Otto" id="collasible-nav-dropdown">
      <NavDropdown.Item href="#action/myProfile">My profile</NavDropdown.Item>
      <NavDropdown.Item href="#action/Logout">Logout</NavDropdown.Item>
      </NavDropdown>
    </Nav>
    <Nav>
      <Image width={50} height={50} src="https://cdn.onlinewebfonts.com/svg/img_24787.png" roundedCircle />
    </Nav>
  </Navbar.Collapse>
  </Navbar>
    )
  }
}