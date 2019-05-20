import React, { Component, Fragment } from 'react';
import { Navbar, Nav, NavDropdown, Image, Button } from 'react-bootstrap';
import NavItem from "react-bootstrap/NavItem";

export default class Navigation extends Component {
  constructor (props) {
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
    let catalog = {
      profesor: id_professor,
      disciplina: 100,
      columns: [
        {
          "key": "student",
          "type": "text"
        },
        {
          "key": "id",
          "type": "text"
        },
        {
          "key": "group",
          "type": "text"
        },
        {
          "key": "lab",
          "type": "number"
        }
      ],
      rows: [
        {
          "id": "1",
          "student": "James Doe",
          "group": "B1",
          "lab": "10",
        }
      ]
    }
    if (disciplineName)
      this.props.onAddDiscipline(id_professor, disciplineName, catalog)

  }

  onDisciplineChange = (id_materie, denumire_materie) => {
    console.log(id_materie, denumire_materie, 'asdasdasd')
    const newCurrentDiscipline = {
      denumire_materie: denumire_materie,
      id_materie: parseInt(id_materie.replace(" ", ""), 10)
    }
    this.props.onDisciplineChange(newCurrentDiscipline)
  }

  onUserLogout = () => {
    console.log(this.props.onUserLogout)
    this.props.onUserLogout()
  }

  render () {
    return (
      <Navbar collapseOnSelect expand="sm">
        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <NavDropdown title="Courses" id="collasible-nav-dropdown">
              {this.props.disciplines && this.props.disciplines.map((item, id) => {
                console.log(item)
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
                <NavDropdown.Item onClick={() => this.onAddDiscipline(this.props.user.userId)}>+ Add a
                  new</NavDropdown.Item>
              </Fragment>
              }
            </NavDropdown>
          </Nav>
          <Nav>
            <NavDropdown title={this.props.user.name} id="collasible-nav-dropdown">
              <NavDropdown.Item onClick={() => this.onUserLogout()}>Logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
          <NavItem style={{ color: 'white' }}>
            {`${this.props.user.role}`}
          </NavItem>
        </Navbar.Collapse>
      </Navbar>
    )
  }
}