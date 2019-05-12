import React, {Component} from 'react';
import {Navbar, Nav, NavDropdown, Image, Button} from 'react-bootstrap';
import equal from 'fast-deep-equal';

export default class NavProf extends Component {
    constructor(props) {
        super(props);
        this.state = {
            disciplines: []
        }
    }

    componentDidMount() {
        this.setState(
            {
                disciplines: this.props.disciplines,
            })
    }

    componentDidUpdate(prevProps) {
        if (!equal(this.props.disciplines, prevProps.disciplines)) {
            this.setState({disciplines: this.props.disciplines})
        }
    }

    askUserInput = (message) => {
        const result = prompt(message);
        if (result === "") {
            this.askUserInput("You can't enter an empty string")
        } else if (result) {
            return result
        } else {
            return undefined
        }
    };

    onAddDiscipline = (id_professor) => {
        const disciplineName = this.askUserInput("Specify discipline name")
        if (disciplineName)
            this.props.onAddDiscipline(id_professor, disciplineName)

    }

    render() {
        return (
            <Navbar collapseOnSelect expand="sm">
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Button variant="secondary">Logo</Button>
                        <NavDropdown title="Courses" id="collasible-nav-dropdown">
                            {this.state.disciplines && this.state.disciplines.map((item, id) => {
                                return (
                                    <NavDropdown.Item key={id} value={item.materie}
                                                      onClick={() => console.log(item.materie)}>
                                        {item.materie}
                                    </NavDropdown.Item>
                                )
                            })}
                            <NavDropdown.Divider/>
                            <NavDropdown.Item onClick={() => this.onAddDiscipline(this.props.user.id_prof)}>+ Add a new
                                class</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>

                    <Nav>
                        <NavDropdown title={this.props.user.name} id="collasible-nav-dropdown">
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