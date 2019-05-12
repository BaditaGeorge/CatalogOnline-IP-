import axios from "axios/index";
import {APIURL} from "../config";

export const GET_GLOBAL = "GET_GLOBAL";
export const GET_GLOBAL_SUCCESS = "GET_GLOBAL_SUCCESS";
export const GET_GLOBAL_FAIL = "GET_GLOBAL_FAIL";
export const GET_PROFESSOR_CATALOG = "GET_PROFESSOR_CATALOG";
export const GET_PROFESSOR_CATALOG_SUCCESS = "GET_PROFESSOR_CATALOG_SUCCESS";
export const GET_PROFESSOR_CATALOG_FAIL = "GET_PROFESSOR_CATALOG_FAIL";
export const GET_PROFESSOR_DISCIPLINES = "GET_PROFESSOR_DISCIPLINES";
export const GET_PROFESSOR_DISCIPLINES_SUCCESS = "GET_PROFESSOR_DISCIPLINES_SUCCESS";
export const GET_PROFESSOR_DISCIPLINES_FAIL = "GET_PROFESSOR_DISCIPLINES_FAIL";
export const GET_DISCIPLINE_FORMULAS = "GET_DISCIPLINE_FORMULAS";
export const GET_DISCIPLINE_FORMULAS_SUCCESS = "GET_DISCIPLINE_FORMULAS_SUCCESS";
export const GET_DISCIPLINE_FORMULAS_FAIL = "GET_DISCIPLINE_FORMULAS_FAIL"
export const POST_DISCIPLINE_FORMULAS = "POST_DISCIPLINE_FORMULAS";
export const POST_DISCIPLINE_FORMULAS_SUCCESS = "POST_DISCIPLINE_FORMULAS_SUCCESS";
export const POST_DISCIPLINE_FORMULAS_FAIL = "POST_DISCIPLINE_FORMULAS_FAIL"
export const POST_PROFESSOR_CATALOG = "POST_PROFESSOR_CATALOG";
export const POST_PROFESSOR_CATALOG_SUCCESS = "POST_PROFESSOR_CATALOG_SUCCESS";
export const POST_PROFESSOR_CATALOG_FAIL = "POST_PROFESSOR_CATALOG_FAIL"
export const POST_PROFESSOR_DISCIPLINES = "POST_PROFESSOR_DISCIPLINES";
export const POST_PROFESSOR_DISCIPLINES_SUCCESS = "POST_PROFESSOR_DISCIPLINES_SUCCESS";
export const POST_PROFESSOR_DISCIPLINES_FAIL = "POST_PROFESSOR_DISCIPLINES_FAIL"
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

export const getProfessorCatalog = (id_materie, id_profesor) => dispatch => {
    dispatch({
        type: GET_PROFESSOR_CATALOG
    });
    const data = {
        "id_Materie": id_materie,
        "id_prof": id_profesor
    };
    axios
        .get(`${APIURL}/catalog?id_Materie=${id_materie}&id_prof=${id_profesor}`)
        .then(res => {
            let rows, columns
            if (res.data) {
                rows = res.data.rows;
                columns = res.data.columns;
                dispatch({
                    type: GET_PROFESSOR_CATALOG_SUCCESS,
                    payload: {rows: rows, columns: columns}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_PROFESSOR_CATALOG_FAIL});
        });
};

export const getProfessorDisciplines = (id_professor) => dispatch => {
    dispatch({
        type: GET_PROFESSOR_DISCIPLINES
    });
    axios
        .get(`${APIURL}/materii?id_profesor=${id_professor}`)
        .then(res => {
            let disciplines
            if (res.data) {
                disciplines = res.data.materii.list;
                dispatch({
                    type: GET_PROFESSOR_DISCIPLINES_SUCCESS,
                    payload: {disciplines: disciplines}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_PROFESSOR_DISCIPLINES_FAIL});
        });
};

export const getDisciplineFormulas = (id_materie) => dispatch => {
    dispatch({
        type: GET_DISCIPLINE_FORMULAS
    });
    axios
        .get(`${APIURL}/formule?id_profesor=${id_materie}`)
        .then(res => {
            let formulas
            if (res.data) {
                formulas = res.data.formule.list;
                dispatch({
                    type: GET_DISCIPLINE_FORMULAS_SUCCESS,
                    payload: {formulas: formulas}
                });
            }
        })
        .catch(err => {
            dispatch({type: GET_DISCIPLINE_FORMULAS_FAIL});
        });
};

export const insertDisciplineFormulas = (id_materie, formule) => dispatch => {
    dispatch({
        type: POST_DISCIPLINE_FORMULAS
    });
    axios
        .post(`${APIURL}/formule`, {id_materie: id_materie, formule: formule})
        .then(res => {
            let formulas
            if (res.data) {
                formulas = [{formula: res.data.formule}];
                dispatch({
                    type: POST_DISCIPLINE_FORMULAS_SUCCESS,
                    payload: {formulas: formulas}
                });
            }
        })
        .catch(err => {
            dispatch({type: POST_DISCIPLINE_FORMULAS_FAIL});
        });
};
export const insertProfessorCatalog = (catalog) => dispatch => {
    dispatch({
        type: POST_PROFESSOR_CATALOG
    });
    axios
        .post(`${APIURL}/catalog`, catalog)
        .then(res => {
            console.log(res)
            let columns, rows
            if (res.data) {
                rows = res.data.rows;
                columns = res.data.columns;
                dispatch({
                    type: POST_PROFESSOR_CATALOG_SUCCESS,
                    payload: {rows: rows, columns: columns}
                });
            }
        })
        .catch(err => {
            dispatch({type: POST_PROFESSOR_CATALOG_FAIL});
        });
};

export const insertProfessorDisciplines = (id_profesor, den_materie) => (dispatch, getState) => {
    dispatch({
        type: POST_PROFESSOR_DISCIPLINES
    });
    axios
        .post(`${APIURL}/materii`, {id_materie: id_profesor, den_materie: den_materie})
        .then(res => {
            if (res.data) {
                let disciplines = getState().professorReducer.disciplines
                disciplines.push({materie: res.data.den_materie})
                dispatch({
                    type: POST_PROFESSOR_DISCIPLINES_SUCCESS,
                    payload: {disciplines: disciplines}
                });
            }
        })
        .catch(err => {
            dispatch({type: POST_PROFESSOR_DISCIPLINES_FAIL});
        });
};
