import axios from "axios/index";
import {APIURL} from "../config";

export const GET_GLOBAL = "GET_GLOBAL";
export const GET_GLOBAL_SUCCESS = "GET_GLOBAL_SUCCESS";
export const GET_GLOBAL_FAIL = "GET_GLOBAL_FAIL";
export const GET_STUDENT_CATALOG = "GET_STUDENT_CATALOG";
export const GET_STUDENT_CATALOG_SUCCESS = "GET_STUDENT_CATALOG_SUCCESS";
export const GET_STUDENT_CATALOG_FAIL = "GET_STUDENT_CATALOG_FAIL";
export const GET_STUDENT_DISCIPLINES = "GET_STUDENT_DISCIPLINES";
export const GET_STUDENT_DISCIPLINES_SUCCESS = "GET_STUDENT_DISCIPLINES_SUCCESS";
export const GET_STUDENT_DISCIPLINES_FAIL = "GET_STUDENT_DISCIPLINES_FAIL";
export const getGlobal = () => dispatch => {
    dispatch({
        type: GET_GLOBAL
    });
    axios
        .get(`http://ip-api.com/json/24.48.0.1`, {
            withCredentials: false
        })
        .then(res => {
            let global
            if (res.data) {
                global = res.data;
                dispatch({
                    type: GET_GLOBAL_SUCCESS,
                    payload: {global: global}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_GLOBAL_FAIL});
        });
};

export const getStudentCatalog = (id_materie = 7, id_profesor = 1) => dispatch => {
    dispatch({
        type: GET_STUDENT_CATALOG
    });
    axios
        .get(`${APIURL}/catalog?id_materie=${id_materie}&id_profesor=${id_profesor}`)
        .then(res => {
            let rows, columns
            if (res.data) {
                rows = res.data.rows;
                columns = res.data.columns;
                dispatch({
                    type: GET_STUDENT_CATALOG_SUCCESS,
                    payload: {rows: rows, columns: columns}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_STUDENT_CATALOG_FAIL});
        });
};

export const getStudentDisciplines = (id_professor = 1) => dispatch => {
    dispatch({
        type: GET_STUDENT_DISCIPLINES
    });
    axios
        .get(`${APIURL}/materii?id_profesor=${id_professor}`)
        .then(res => {
            let disciplines
            if (res.data) {
                disciplines = res.data.materii.list;
                dispatch({
                    type: GET_STUDENT_DISCIPLINES_SUCCESS,
                    payload: {disciplines: disciplines}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_STUDENT_DISCIPLINES_FAIL});
        });
};