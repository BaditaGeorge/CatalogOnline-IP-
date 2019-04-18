import React, {Component, Suspense, lazy} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

const Login = lazy(() => import('./components/Login'));
const Register = lazy(() => import('./components/Register'));
const StudentDashboard = lazy(() => import('./components/StudentDashboard'));
const TeacherDashboard = lazy(() => import('./components/TeacherDashboard'));
const SecretaryDashboard = lazy(() => import('./components/SecretaryDashboard'));
const AdminDashboard = lazy(() => import('./components/AdminDashboard'));
const About = lazy(() => import('./components/About'));


class App extends Component {
    render() {
        return (
            <Router>
                <Suspense fallback={<div>Loading...</div>}>
                    <Switch>
                        <Route exact path="/login" component={Login}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/student" component={StudentDashboard}/>
                        <Route path="/teacher" component={TeacherDashboard}/>
                        <Route path="/secretary" component={SecretaryDashboard}/>
                        <Route path="/admin" component={AdminDashboard}/>
                        <Route path="/about" component={About}/>
                    </Switch>
                </Suspense>
            </Router>
        );
    }
}

export default App;
