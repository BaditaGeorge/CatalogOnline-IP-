import axios from "axios/index";

export const GET_GLOBAL = "GET_GLOBAL";
export const GET_GLOBAL_SUCCESS = "GET_GLOBAL_SUCCESS";
export const GET_GLOBAL_FAIL = "GET_GLOBAL_FAIL";
export const GET_PROFESSOR_CATALOG = "GET_PROFESSOR_CATALOG";
export const GET_PROFESSOR_CATALOG_SUCCESS = "GET_PROFESSOR_CATALOG_SUCCESS";
export const GET_PROFESSOR_CATALOG_FAIL = "GET_PROFESSOR_CATALOG_FAIL";


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

export const getProfessorCatalog = () => dispatch => {
    dispatch({
        type: GET_PROFESSOR_CATALOG
    });
    axios
        .get(`http://ip-api.com/json/24.48.0.1`, {
            withCredentials: false
        })
        .then(res => {
            let rows, columns
            if (res.data) {
                rows = res.data;
                columns = res.data;
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

