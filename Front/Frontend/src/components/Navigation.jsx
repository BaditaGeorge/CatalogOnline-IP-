import React, {Component, Fragment} from 'react';
import {Navbar, Nav, NavDropdown, Image, Modal, Button} from 'react-bootstrap';
import {CSVLink, CSVDownload} from "react-csv";
import ReactFileReader from 'react-file-reader';
import NavItem from "react-bootstrap/NavItem";
import axios from "axios";
import {APIURL} from "../config";
import {EXPORT_CATALOG_FAIL} from "../actions/adminActions";

export default class Navigation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      csvData: [],
      show: false,
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
    id_professor = this.props.user.role === 'admin' ? this.props.currentProfessor.id_professor : this.props.user.userId;
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

  handleClose = () => {
    this.setState({show: false});
  }

  handleShow = () => {
    this.setState({show: true});
  }

  handleFiles = files => {
    var reader = new FileReader();
    reader.onload = function (e) {
      axios
        .post(`${APIURL}/importCatalog?id_Materie=${this.props.currentDiscipline.id_materie}&id_session=1`, reader.result)
        .then(res => {
          if (res.data !== 'Added!')
            throw res.data
        }).catch(err => {
        alert(err)
      });
      alert(reader.result)
    }
    reader.readAsText(files[0]);
  }

  onExport = async () => {
    const id_profesor = this.props.user.role === 'admin' ? this.props.currentProfessor.id_professor : this.props.user.userId;
    const id_materie = this.props.currentDiscipline.id_materie
    let csvData = [];
    await axios
      .get(`${APIURL}/export?id_profesor=${id_profesor}&id_Materie=${id_materie}&id_session=1`)
      .then(res => {
        let rows = res.data.split('\n');
        rows.map((row, index) => {
          if (!index && row.length) {
            csvData[0] = row.split(',')
          } else if (row.length) {
            csvData[index] = [];
            const split = row.split(',');
            csvData[index].push(split[0]);
            csvData[index].push(split[1]);
            csvData[index].push(split[2]);
            csvData[index].push('')
            split.slice(3, split.length).map((d, i) => {
              if (d.length) {
                csvData[index][3] += d
                if (i < split.length - 4) {
                  csvData[index][3] += ', '
                }
              }
            })
          }
        });
      }).catch(err => {
        alert(err)
      });
    this.setState({csvData: csvData})
    this.handleShow()
  }

  render() {
    return (
      <Fragment>
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
                {(this.props.user.role !== 'student') &&
                <Fragment>
                  <NavDropdown.Divider/>
                  <NavDropdown.Item onClick={() => this.onAddDiscipline()}>+ Add a
                    new</NavDropdown.Item>
                </Fragment>
                }
              </NavDropdown>
              {(this.props.user.role !== 'student') &&
              <Fragment>
                <NavItem>
                  <ReactFileReader fileTypes={[".csv"]} handleFiles={this.handleFiles}>
                    <Button variant="light" className={"ml-1"}>
                      <i className="fas fa-file-upload"></i>
                    </Button>
                  </ReactFileReader>
                </NavItem>
                <NavItem>
                  <Button
                    variant="light"
                    className={"ml-1"}
                    onClick={() => this.onExport()}
                  >
                    <i className="fas fa-file-download"></i>
                  </Button>
                </NavItem>
              </Fragment>
              }
            </Nav>
            <Nav>
              <NavDropdown title={`${this.props.user.name} (${this.props.user.role})`} id="collasible-nav-dropdown">
                <NavDropdown.Item onClick={() => this.onUserLogout()}>Logout</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
        <Modal show={this.state.show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>CSV Exported</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <CSVLink data={this.state.csvData}>
              <i className="fas fa-download"/>
              <span className={"ml-1"}>Download</span>
            </CSVLink>
          </Modal.Body>
        </Modal>
      </Fragment>
    )
  }
}