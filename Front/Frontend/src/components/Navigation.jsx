import React, {Component, Fragment} from 'react';
import {Navbar, Nav, NavDropdown, Image, Button} from 'react-bootstrap';
import NavItem from "react-bootstrap/NavItem";

export default class Navigation extends Component {
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

  onDisciplineChange = (newDiscipline) => {
    this.props.onDisciplineChange(newDiscipline)
  }

  onAddProfessor = () => {
    const professorEmail = this.askUserInput("Specify professor Email")
    const professorName = this.askUserInput("Specify professor Name")
    const professorFirstname = this.askUserInput("Specify professor Firstname")
    const newProfessor = {
      email: professorEmail,
      nume: professorName,
      prenume: professorFirstname,
      materii: [{
        id_materie: '1',
        den_materie: 'POO',
        formula: 'L=sum(L1:L3)',
        antet: 'L L1 L2 L3'
      }]
    }
    this.props.onAddProfessor(newProfessor)
  }

  onProfessorChange = (item) => {
    this.props.onProfessorChange(item)
  }

  onUserLogout = () => {
    console.log(this.props.onUserLogout)
    this.props.onUserLogout()
  }

  render() {
    return (
      <Navbar collapseOnSelect expand="sm">
        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            {this.props.user.role === 'admin' &&
            <NavDropdown title={`${this.props.currentProfessor.nume} ${this.props.currentProfessor.prenume}`}
                         id="collasible-nav-dropdown">
              {this.props.professors && this.props.professors.map((item, id) => {
                return (
                  <NavDropdown.Item key={id} value={item.nume}
                                    onClick={() => this.onProfessorChange(item)}>
                    {`${item.nume} ${item.prenume}`}
                  </NavDropdown.Item>
                )
              })}
              <Fragment>
                <NavDropdown.Divider/>
                <NavDropdown.Item onClick={() => this.onAddProfessor()}>+ Add a
                  new</NavDropdown.Item>
              </Fragment>
            </NavDropdown>}
            <NavDropdown title={this.props.currentDiscipline.denumire_materie} id="collasible-nav-dropdown">
              {this.props.disciplines && this.props.disciplines.map((item, id) => {
                return (
                  <NavDropdown.Item key={id} value={item.denumire_materie}
                                    onClick={() => this.onDisciplineChange(item)}>
                    {item.denumire_materie}
                  </NavDropdown.Item>
                )
              })}
              {(this.props.user.role === 'professor' || this.props.user.role === 'admin') &&
              <Fragment>
                <NavDropdown.Divider/>
                <NavDropdown.Item onClick={() => this.onAddDiscipline(this.props.user.userId)}>+ Add a
                  new</NavDropdown.Item>
              </Fragment>
              }
            </NavDropdown>
          </Nav>
          <Nav>
            <NavDropdown title={`${this.props.user.name} (${this.props.user.role})`} id="collasible-nav-dropdown">
              <NavDropdown.Item onClick={() => this.onUserLogout()}>Logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    )
  }
}