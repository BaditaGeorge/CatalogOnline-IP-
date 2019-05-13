import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
    getStudentCatalog,
    getStudentDisciplines,
} from "../actions/studentActions";
import NavProf from "./NavProf";

class StudentDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
    }

    render() {
        console.log(this.props.global, 'asdasdas')
        const user = {name: "Cornel", prenume: "Ivan", role: "student"}
        return (
            <div>
                <NavProf disciplines={this.props.disciplines}/>
                <Catalog user={user} rows={this.props.rows} columns={this.props.columns}/>
            </div>
        );
    }
}

export const StudentDashboardRedux = connect((state) => ({
    global: state.studentReducer.global,
    columns: state.studentReducer.columns,
    disciplines: state.studentReducer.disciplines,
    rows: state.studentReducer.rows,
    loading: state.studentReducer.loading
}), {
    getStudentCatalog,
    getStudentDisciplines,
})(StudentDashboard)


