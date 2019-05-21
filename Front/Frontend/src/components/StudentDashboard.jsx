import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
  getStudentCatalog,
  getStudentDisciplines,
  setStudentCurrentDiscipline
} from "../actions/studentActions";
import Navigation from "./Navigation";
import equal from "fast-deep-equal";
import {logoutUser} from "../actions/loginActions";

// const user = {name: "Alex Ivan", role: "student", id_prof: 1}


class StudentDashboard extends Component {
  constructor(props) {
    super(props)
    this.state = {
      currentDiscipline: {}
    }
  }


  componentWillMount() {
    if (this.props.token && this.props.token.length) {
      this.props.getStudentDisciplines(this.props.userId)
    }
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (nextProps.token !== this.props.token || this.props.token.length) {
      if (nextProps.currentDiscipline.id_materie !== this.props.currentDiscipline.id_materie) {
        this.props.getStudentCatalog(nextProps.userId, nextProps.currentDiscipline.id_materie)
      }
    }
  }

  render() {
    let catalogIndex = 0
    this.props.catalogs.map((catalog, index) => {
      if (catalog.denumire_materie === this.props.currentDiscipline.denumire_materie)
        catalogIndex = index
    })

    return (
      <div>
        <Navigation
          user={{name: this.props.userName, role: this.props.role, userId: this.props.userId}}
          disciplines={this.props.disciplines}
          currentDiscipline={this.props.currentDiscipline}
          onDisciplineChange={this.props.setStudentCurrentDiscipline}
          onUserLogout={this.props.logoutUser}

        />
        <Catalog user={{name: this.props.userName, role: this.props.role, userId: this.props.userId}}
                 rows={this.props.catalogs.length ? this.props.catalogs[catalogIndex].rows : []}
                 columns={this.props.catalogs.length ? this.props.catalogs[catalogIndex].columns : []}
                 formulas={[]}
        />
      </div>
    );
  }
}

export const StudentDashboardRedux = connect((state) => ({
  catalogs: state.studentReducer.catalogs,
  disciplines: state.studentReducer.disciplines,
  currentDiscipline: state.studentReducer.currentDiscipline,
  loading: state.studentReducer.loading,
  userName: state.loginReducer.userName,
  userId: state.loginReducer.userId,
  role: state.loginReducer.role,
  token: state.loginReducer.token,
  verified: state.loginReducer.verified,
}), {
  getStudentCatalog,
  getStudentDisciplines,
  setStudentCurrentDiscipline,
  logoutUser,
})(StudentDashboard)


