import React from "react";
import { Form, FormControl, Button, Card } from "react-bootstrap";
import Axios from "axios";
class Test extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      stud: [],
      search: "",
      sugestii: [],
      name: "",
      name2: "",
      group: "",
      email: ""
    };
    this.onChange = this.onChange.bind(this); 
  }

  fetchData() {
    let url = "http://localhost:9991/studs?id_session=value";
    fetch(url)
      .then(resp => resp.json())
      .then(resp => {
        this.setState(() => ({
          studs: resp
        }));
      })
      .catch(err => {
        console.log("Error ar fetching the students:", err);
      });
  }

  sugestii(value) {
    this.setState(() => ({ search: value }));
  }
  onChange(e) {
    let auto = [];
    let search = e.targe.value;
    if (search.length > 0) {
      auto = this.state.stud.name.sort().filer(nume => search.test(nume));
    }
    this.setState(() => ({
      search: search,
      sugestii: auto
    }));
  }

  onChangeF(e) {
    this.setState(() => ({
      [e.target.name]: e.target.value
    }));
  }
  alegere(valoare) {
    this.setState(() => ({
      search: valoare
    }));
  }
  renderSugestion() {
    const sugestii = this.state.sugestii;
    if (sugestii.length === 0) {
      return null;
    }
    return (
      <div style={{ border: "2px solid block" }}>
        {sugestii.map(sugestie => (
          <p
            onClick={this.alegere(
              sugestie.nume + " " + sugestie.nume + " " + sugestie.grupa
            )}
          >
            {sugestie.nume + " " + sugestie.nume + " " + sugestie.grupa}
          </p>
        ))}
      </div>
    );
  }

  submit(e) {
    e.preventDefault();
    let body;
    body = {
      name: this.state.name,
      name2: this.state.name2,
      group: this.state.group,
      email: this.state.email
    };
    Axios.post("localhost:9991/addStud?id_session=valoare", body)
      .then(response => response.json())
      .then(response => {
        console.log(response);
        this.fetchData();
      })
      .catch(error => {
        console.log("Error message: ", error);
      });
  }
  submitCatalog(e) {
    e.preventDefault();
    let body;
    body = {
      id_materie: 1,
      id_student: 2,
      den_materie: "Mate", 
      note: "P 0 L 0 L 0"
    };
    Axios.post("localhost:9991/addStudCatalog?id_session=valoare", body)
      .then(response => response.json())
      .then(response => {
        console.log(response);
        this.fetchData();
      })
      .catch(error => {
        console.log("Error message: ", error);
      });
  }
  render() {
    return (
      <Card style={{ width: "400px" }}>
        <div className="search">
          <input
            type="text"
            onChange={this.onChange}
            name="search"
            value={this.state.search}
          />

          {this.sugestii()}

          <Button block onClick={this.submitCatalog()}>
            Add student to catalog{" "}
          </Button>
        </div>

        <div className="newStud">
          <Form>
            <FormControl
              value={this.state.name}
              onChange={this.onChangeF}
              type="text"
              name="Name"
              placeholder="Student name"
            />
            <FormControl
              value={this.state.name2}
              onChange={this.onChangeF}
              type="text"
              name="Name2"
              placeholder="Student name2"
            />
            <FormControl
              value={this.state.group}
              onChange={this.onChangeF}
              type="text"
              name="Group"
              placeholder="Student group"
            />
            <FormControl
              value={this.state.email}
              onChange={this.onChangeF}
              type="text"
              name="email"
              placeholder="Student group"
            />
            <Button block onClick={this.submit}>
              Add new student
            </Button>
          </Form>
        </div>
      </Card>
    );
  }
}

export default Test;