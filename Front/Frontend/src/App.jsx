import React, {Component, Suspense, lazy} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {Provider} from "react-redux";
import store from './store';

import Login from './components/Login';
import Register from './components/Register';
import {StudentDashboardRedux} from './components/StudentDashboard';
import {ProfessorDashboardRedux} from './components/ProfessorDashboard';
import SecretaryDashboard from './components/SecretaryDashboard'
import AdminDashboard from './components/AdminDashboard';
import About from './components/About';
import AdminLayout from './components/Template/Admin.jsx';

import "bootstrap/dist/css/bootstrap.min.css";
import "./css/animate.min.css";
import "./css/sass/light-bootstrap-dashboard-react.scss?v=1.3.0";
import "./css/demo.css";
import "./css/pe-icon-7-stroke.css";

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <Router>
                    <Suspense fallback={<div>Loading...</div>}>
                        <Switch>
                            <Route exact path="/login" component={Login}/>
                            <Route path="/register" component={Register}/>
                            <Route path="/student" component={StudentDashboardRedux}/>
                            <Route path="/professor" component={ProfessorDashboardRedux}/>
                            <Route path="/secretary" component={SecretaryDashboard}/>
                            <Route path="/admin" component={AdminDashboard}/>
                            <Route path="/about" component={About}/>
                            <Route path="/test" render={props => <AdminLayout {...props} />}/>
                        </Switch>
                    </Suspense>
                </Router>
            </Provider>
        );
    }
}

export default App;
