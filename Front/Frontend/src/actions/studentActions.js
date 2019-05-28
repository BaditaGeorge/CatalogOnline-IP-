import axios from "axios/index";
import {APIURL} from "../config";

export const GET_STUDENT_CATALOG = "GET_STUDENT_CATALOG";
export const GET_STUDENT_CATALOG_SUCCESS = "GET_STUDENT_CATALOG_SUCCESS";
export const GET_STUDENT_CATALOG_FAIL = "GET_STUDENT_CATALOG_FAIL";
export const GET_STUDENT_DISCIPLINES = "GET_STUDENT_DISCIPLINES";
export const GET_STUDENT_DISCIPLINES_SUCCESS = "GET_STUDENT_DISCIPLINES_SUCCESS";
export const GET_STUDENT_DISCIPLINES_FAIL = "GET_STUDENT_DISCIPLINES_FAIL";
export const SET_STUDENT_CURRENT_DISCIPLINE = "SET_STUDENT_CURRENT_DISCIPLINE";

export const getStudentCatalog = (id_student, id_materie) => (dispatch, getState) => {
  dispatch({
    type: GET_STUDENT_CATALOG
  });
  const id_session = getState().loginReducer.token;
  axios
    .get(`${APIURL}/note?id_student=${id_student}&id_materie=${id_materie}&id_session=${id_session}`)
    .then(res => {
      if (res.data) {
        let catalogs = [
          ...res.data.map(item => {
            const rows = [item.valori_note]
            const columns = [...Object.keys(item.valori_note).map((keyName) => {
              return {key: keyName, type: "number"}
            })]

            return {
              denumire_materie: item.denumire_materie,
              columns: columns,
              rows: rows
            }
          })
        ];
        dispatch({
          type: GET_STUDENT_CATALOG_SUCCESS,
          payload: {catalogs: catalogs}
        });
      }
    })
    .catch(err => {
      dispatch({type: GET_STUDENT_CATALOG_FAIL});
    });
};

export const getStudentDisciplines = (id_student) => (dispatch, getState) => {
  dispatch({
    type: GET_STUDENT_DISCIPLINES
  });
  const id_session = getState().loginReducer.token;
  axios
    .get(`${APIURL}/cursuri?id_student=${id_student}&id_session=${id_session}`)
    .then(res => {
      if (res.data) {
        dispatch({
          type: GET_STUDENT_DISCIPLINES_SUCCESS,
          payload: {
            disciplines: res.data.disciplines,
            currentDiscipline: res.data.disciplines.length ? res.data.disciplines[0] : {}
          }
        });
      }
    })
    .catch(err => {
      dispatch({type: GET_STUDENT_DISCIPLINES_FAIL});
    });
};

export const setDefaultDiscipline = (newCurrentDiscipline) => dispatch => {
  dispatch({
    type: SET_STUDENT_CURRENT_DISCIPLINE,
    payload: {currentDiscipline: newCurrentDiscipline}
  });
}