import axios from "axios/index";
import { APIURL } from "../config";

export const GET_USER_DATA = "GET_USER_DATA";
export const GET_USER_DATA_SUCCESS = "GET_USER_DATA_SUCCESS";
export const GET_USER_DATA_FAIL = "GET_USER_DATA_FAIL";
export const VERIFY_USER_EMAIL = "VERIFY_USER_EMAIL";
export const VERIFY_USER_EMAIL_SUCCESS = "VERIFY_USER_EMAIL_SUCCESS";
export const VERIFY_USER_EMAIL_FAIL = "VERIFY_USER_EMAIL_FAIL";
export const LOGOUT_USER_SUCCESS = "LOGOUT_USER_SUCCESS";

export const loginUser = (email, password) => dispatch => {
  // dispatch({
  //   type: GET_USER_DATA
  // });
  // const data = {
  //   email: email,
  //   password: password
  // };
  dispatch({
    type: GET_USER_DATA_SUCCESS,
    payload: { userName: 'Cristian', role: 'admin', userId: 3, token: 'basfjkasdjc27812ksad78jkdajks', verified: true }
  });
  // axios.post(`${APIURL}/login`, data)
  //   .then(res => {
  //     let token = '';
  //     if (res.data) {
  //       if (res.data.success === true) {
  //         token = res.data.session_id
  //       }
  //       dispatch({
  //         type: GET_USER_DATA_SUCCESS,
  //         payload: { userName: 'Marius', role: 'professor', token: 'asdadskjdaksjj123asdhjk123da2', verified: true }
  //       });
  //     }
  //   })
  //   .catch(err => {
  //     console.log(err);
  //     dispatch({ type: GET_USER_DATA_FAIL })
  //   })
};

export const registerUser = (name, email, password) => dispatch => {
  // dispatch({
  //   type: GET_USER_DATA
  // });
  // const data = {
  //   name: name,
  //   email: email,
  //   password: password
  // };
  dispatch({
    type: GET_USER_DATA_SUCCESS,
    payload: { userName: 'Cristian', role: 'professor', userId: 3, token: 'basfjkasdjc27812ksad78jkdajks', verified: false }
  });
  // axios.post(`${APIURL}/register`, data)
  //   .then(res => {
  //     let token = '';
  //     if (res.data) {
  //       if (res.data.success === true) {
  //         token = res.data.session_id
  //       }
  //       dispatch({
  //         type: GET_USER_DATA_SUCCESS,
  //         payload: { userName: 'Cristian', role: 'professor', token: 'basfjkasdjc27812ksad78jkdajks', verified: false }
  //       });
  //     }
  //   })
  //   .catch(err => {
  //     console.log(err);
  //     dispatch({ type: GET_USER_DATA_FAIL })
  //   })
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
          payload: { userName: 'Cristian', role: 'professor', token: 'basfjkasdjc27812ksad78jkdajks', verified: true }
        });
      }
    })
    .catch(err => {
      console.log(err);
      dispatch({ type: VERIFY_USER_EMAIL_FAIL })
    })
}

export const logoutUser = () => dispatch => {
  dispatch({
    type: LOGOUT_USER_SUCCESS,
    payload: {}
  });
}