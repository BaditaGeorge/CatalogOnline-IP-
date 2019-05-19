import axios from "axios/index";
import {APIURL} from "../config";

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
export const SET_CURRENT_DISCIPLINE = "SET_CURRENT_DISCIPLINE"


export const getProfessorCatalog = (id_materie, id_profesor) => dispatch => {
  dispatch({
    type: GET_PROFESSOR_CATALOG
  });
  axios
    .get(`${APIURL}/catalog?id_Materie=${id_materie}&id_prof=${id_profesor}`)
    .then(res => {
      if (res.data) {
        dispatch({
          type: GET_PROFESSOR_CATALOG_SUCCESS,
          payload: {rows: res.data.rows, columns: res.data.columns, didUpdate: false}
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
        disciplines = res.data.disciplines;
        dispatch({
          type: GET_PROFESSOR_DISCIPLINES_SUCCESS,
          payload: {
            disciplines: disciplines,
            currentDiscipline: disciplines[0] ? {
              denumire_materie: disciplines[0].denumire_materie,
              id_materie: parseInt(disciplines[0].id_materie.replace(" ", ""), 10)
            } : {}
          }
        });
      }
    })
    .catch(err => {
      dispatch({type: GET_PROFESSOR_DISCIPLINES_FAIL});
    });
};

export const getDisciplineFormulas = (id_professor) => dispatch => {
  dispatch({
    type: GET_DISCIPLINE_FORMULAS
  });
  axios
    .get(`${APIURL}/formule?id_profesor=${id_professor}`)
    .then(res => {
      if (res.data) {
        let formulas = res.data
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

export const setDefaultDiscipline = (newCurrentDiscipline) => dispatch => {
  dispatch({
    type: SET_CURRENT_DISCIPLINE,
    payload: {currentDiscipline: newCurrentDiscipline}
  });
}

export const insertDisciplineFormulas = (id_materie, formule) => (dispatch, getState) => {
  const newFormula = {id_materie: id_materie, formule: formule}
  dispatch({
    type: POST_DISCIPLINE_FORMULAS
  });
  axios
    .post(`${APIURL}/formule`, newFormula)
    .then(res => {
      let formulas
      if (res.data === 'Formula este valida') {
        formulas = [...getState().professorReducer.formulas.map(
          f => f.id_materie === newFormula.id_materie ? {
            id_materie: newFormula.id_materie,
            formula_calcul: newFormula.formule
          } : f
        )]
        dispatch({
          type: POST_DISCIPLINE_FORMULAS_SUCCESS,
          payload: {formulas: formulas}
        });
      }
      throw (res.data)
    })
    .catch(err => {
      dispatch({type: POST_DISCIPLINE_FORMULAS_FAIL})
      alert(err)
    });
};
export const insertProfessorCatalog = (catalog) => dispatch => {

  dispatch({
    type: POST_PROFESSOR_CATALOG
  });
  axios
    .post(`${APIURL}/catalog`, catalog)
    .then(res => {
      let columns, rows
      console.log(res.data)
      if (res.data.includes('Antetul este valid')) {
        let didUpdate = false
        if (res.data.includes('Update efectuat'))
          didUpdate = true
        dispatch({
          type: POST_PROFESSOR_CATALOG_SUCCESS,
          payload: {didUpdate: didUpdate}
        });
      } else {
        throw res.data
      }
    })
    .catch(err => {
      dispatch({type: POST_PROFESSOR_CATALOG_FAIL});
      alert(err)
    });
};

export const insertProfessorDisciplines = (id_profesor, den_materie, catalog) => (dispatch, getState) => {
  dispatch({
    type: POST_PROFESSOR_DISCIPLINES
  });
  axios
    .post(`${APIURL}/materii`, {id_Materie: id_profesor, den_materie: den_materie})
    .then(res => {
      if (res.data) {
        let newDiscipline = {denumire_materie: res.data.den_materie, id_materie: 1}
        axios
          .get(`${APIURL}/materii?id_profesor=${id_profesor}`)
          .then(res => {
            if (!res.data)
              throw 'Cant get disciplines'
            else {
              const lastDiscipline = res.data.disciplines[res.data.disciplines.length - 1]
              catalog.disciplina = lastDiscipline.id_materie
              newDiscipline.id_materie = lastDiscipline.id_materie
              axios
                .post(`${APIURL}/catalog`, catalog)
                .then(res => {
                    if (!res.data || !res.data.includes('Antetul este valid')) {
                      throw res.data
                    }
                  }
                )
              let disciplines = getState().professorReducer.disciplines
              disciplines.push(newDiscipline)
              dispatch({
                type: POST_PROFESSOR_DISCIPLINES_SUCCESS,
                payload: {disciplines: disciplines}
              });
            }
          })
      }
    })
    .catch(err => {
      dispatch({type: POST_PROFESSOR_DISCIPLINES_FAIL});
    });
};
