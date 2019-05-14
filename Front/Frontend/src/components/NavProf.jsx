import React, {Component, Fragment} from 'react';
import {Navbar, Nav, NavDropdown, Image, Button} from 'react-bootstrap';

export default class NavProf extends Component {
  constructor(props) {
    super(props);
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

  onDisciplineChange = (id_materie, denumire_materie) => {
    const newCurrentDiscipline = {
      denumire_materie: denumire_materie,
      id_materie: parseInt(id_materie.replace(" ", ""), 10)
    }
    this.props.onDisciplineChange(newCurrentDiscipline)
  }

  render() {
    return (
      <Navbar collapseOnSelect expand="sm">
        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <NavDropdown title="Courses" id="collasible-nav-dropdown">
              {this.props.disciplines && this.props.disciplines.map((item, id) => {
                return (
                  <NavDropdown.Item key={id} value={item.denumire_materie}
                                    onClick={() => this.onDisciplineChange(item.id_materie, item.denumire_materie)}>
                    {item.denumire_materie}
                  </NavDropdown.Item>
                )
              })}
              {this.props.user.role === 'professor' &&
              <Fragment>
                <NavDropdown.Divider/>
                <NavDropdown.Item onClick={() => this.onAddDiscipline(this.props.user.id_prof)}>+ Add a
                  new</NavDropdown.Item>
              </Fragment>
              }
            </NavDropdown>
          </Nav>
          <Nav>
            <NavDropdown title={this.props.user.name} id="collasible-nav-dropdown">
              <NavDropdown.Item href="#action/Logout">Logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    )
  }
}