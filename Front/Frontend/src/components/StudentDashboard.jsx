import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
  getStudentCatalog,
  getStudentDisciplines,
  setStudentCurrentDiscipline
} from "../actions/studentActions";
import NavProf from "./NavProf";
import equal from "fast-deep-equal";

const user = {name: "Alex Ivan", role: "student", id_prof: 1}

class StudentDashboard extends Component {
  constructor(props) {
    super(props)
    this.state = {
      currentDiscipline: {}
    }
  }


  componentWillMount() {
    this.props.getStudentCatalog(user.id_prof, 3)
    this.props.getStudentDisciplines(user.id_prof)
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (!equal(nextProps.currentDiscipline, this.props.currentDiscipline)) {
      this.setState({currentDiscipline: nextProps.currentDiscipline})
      console.log(this.state)
    }
  }

  render() {
    let catalogIndex = 0
    this.props.catalogs.map((catalog, index) => {
      console.log(catalog, this.props.currentDiscipline.denumire_materie)
      if (catalog.denumire_materie === this.props.currentDiscipline.denumire_materie)
        catalogIndex = index
    })

    return (
      <div>
        <NavProf
          user={user}
          disciplines={this.props.disciplines}
          currentDiscipline={this.props.currentDiscipline}
          onDisciplineChange={this.props.setStudentCurrentDiscipline}
        />
        <Catalog user={user}
                 rows={this.props.catalogs.length ? this.props.catalogs[catalogIndex].rows : []}
                 columns={this.props.catalogs.length ? this.props.catalogs[catalogIndex].columns : []}
        />
      </div>
    );
  }
}

export const StudentDashboardRedux = connect((state) => ({
  global: state.studentReducer.global,
  catalogs: state.studentReducer.catalogs,
  disciplines: state.studentReducer.disciplines,
  currentDiscipline: state.studentReducer.currentDiscipline,
  loading: state.studentReducer.loading
}), {
  getStudentCatalog,
  getStudentDisciplines,
  setStudentCurrentDiscipline,
})(StudentDashboard)


