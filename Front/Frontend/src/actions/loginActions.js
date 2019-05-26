import axios from "axios/index";
import {APIURL, PUB_KEY} from "../config";
import NodeRSA from 'node-rsa'

export const GET_USER_DATA = "GET_USER_DATA";
export const GET_USER_DATA_SUCCESS = "GET_USER_DATA_SUCCESS";
export const GET_USER_DATA_FAIL = "GET_USER_DATA_FAIL";
export const VERIFY_USER_EMAIL = "VERIFY_USER_EMAIL";
export const VERIFY_USER_EMAIL_SUCCESS = "VERIFY_USER_EMAIL_SUCCESS";
export const VERIFY_USER_EMAIL_FAIL = "VERIFY_USER_EMAIL_FAIL";
export const LOGOUT_USER_SUCCESS = "LOGOUT_USER_SUCCESS";


const key = new NodeRSA();
key.importKey(PUB_KEY);


const text = '123456';
const encrypted = key.encrypt(text, 'base64');
console.log('encrypted: ', encrypted);

export const loginUser = (username, password) => dispatch => {
  dispatch({
    type: GET_USER_DATA
  });
  axios.post(`${APIURL}/login?username=${username}&password=${password}`)
    .then(res => {
      if (res.data) {
        if (!res.data.success) {
          throw(res.data.message)
        } else {
          let role = '';
          switch (res.data.rol) {
            case 'Administrator':
              role = 'admin';
              break;
            case 'Profesor':
              role = 'professor';
              break
            case 'Student':
              role = 'student';
              break
            default:
              throw ('Invalid Role')
          }
          dispatch({
            type: GET_USER_DATA_SUCCESS,
            payload: {username: username, userId: res.data.user_id, role: role, token: res.data.session_id}
          });
        }
      } else
        throw(res.data.message)
    })
    .catch(err => {
      console.log(err);
      dispatch({type: GET_USER_DATA_FAIL})
    })
};

export const registerUser = (username, password, email) => dispatch => {
  dispatch({
    type: GET_USER_DATA
  });
  axios.post(`${APIURL}/register?username=${username}&password=${password}&mail=${email}`)
    .then(res => {
      if (res.data) {
        if (!res.data.success) {
          throw (res.data.message)
        } else {
          dispatch({
            type: GET_USER_DATA_SUCCESS,
            payload: {userName: username, role: res.data.rol, token: res.data.session_id}
          });
        }
      }
    })
    .catch(err => {
      console.log(err);
      dispatch({type: GET_USER_DATA_FAIL})
    })
};

export const verifyEmail = (code) => dispatch => {
  dispatch({
    type: VERIFY_USER_EMAIL
  });
  const data = {
    code: code
  };
  axios.post(`${APIURL}/verify`, data)
    .then(res => {
      let verified = false;
      if (res.data) {
        if (res.data.success === true) {
          verified = true
        }
        dispatch({
          type: VERIFY_USER_EMAIL_SUCCESS,
          payload: {userName: 'Cristian', role: 'professor', token: 'basfjkasdjc27812ksad78jkdajks', verified: true}
        });
      }
    })
    .catch(err => {
      console.log(err);
      dispatch({type: VERIFY_USER_EMAIL_FAIL})
    })
}

export const logoutUser = () => dispatch => {
  dispatch({
    type: LOGOUT_USER_SUCCESS,
    payload: {}
  });
}