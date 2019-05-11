import React, {Component} from 'react';
import { Navbar, Nav, NavDropdown, Image} from 'react-bootstrap';

export default class NavProf extends Component {
    constructor(props) {
        super(props);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({disciplines: nextProps.disciplines})
    }

    render() {
        return (
            <Navbar collapseOnSelect expand="sm">
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Button variant="secondary">Logo</Button>
                        <NavDropdown title="Courses" id="collasible-nav-dropdown">
                            <NavDropdown.Item
                                href="#action/1">Materie
                            </NavDropdown.Item>
                            <NavDropdown.Divider/>
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
                        <Image className={"avatar"} src="https://cdn.onlinewebfonts.com/svg/img_24787.png"
                               roundedCircle/>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}