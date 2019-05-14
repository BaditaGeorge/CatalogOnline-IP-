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
                        </Switch>
                    </Suspense>
                </Router>
            </Provider>
        );
    }
}

export default App;
