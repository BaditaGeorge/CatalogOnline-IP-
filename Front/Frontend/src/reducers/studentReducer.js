import {
    GET_GLOBAL,
    GET_GLOBAL_SUCCESS,
    GET_GLOBAL_FAIL,
    GET_STUDENT_CATALOG,
    GET_STUDENT_CATALOG_SUCCESS,
    GET_STUDENT_CATALOG_FAIL,
    GET_STUDENT_DISCIPLINES,
    GET_STUDENT_DISCIPLINES_SUCCESS,
    GET_STUDENT_DISCIPLINES_FAIL,
} from "../actions/studentActions";

const INITIAL_STATE = {
    global: undefined,
    disciplines: [],
    loading: false,
    columns: [
        {
            "key": "student",
            "type": "text"
        },
        {
            "key": "id",
            "type": "text"
        },
        {
            "key": "group",
            "type": "text"
        },
        {
            "key": "L1",
            "type": "number"
        },
        {
            "key": "L2",
            "type": "number"
        },
        {
            "key": "L3",
            "type": "number"
        }],
    rows: [
        {
            "id": "1",
            "student": "Victor Paval",
            "group": "B1",
            "L1": "5",
            "L2": "10",
            "L3": "15"
        }],
};

export default function studentReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case GET_GLOBAL: {
            return {
                ...state,
                loading: true
            };
        }
        case GET_GLOBAL_SUCCESS: {
            return {
                ...state,
                global: action.payload.global,
                loading: false
            };
        }
        case GET_GLOBAL_FAIL: {
            return {
                ...state,
                loading: false
            };
        }
        case GET_STUDENT_CATALOG: {
            return {
                ...state,
                loading: true
            };
        }
        case GET_STUDENT_CATALOG_SUCCESS: {
            return {
                ...state,
                rows: action.payload.rows,
                columns: action.payload.columns,
                loading: false
            };
        }
        case GET_STUDENT_CATALOG_FAIL: {
            return {
                ...state,
                loading: false
            };
        }
        case GET_STUDENT_DISCIPLINES: {
            return {
                ...state,
                loading: true
            };
        }
        case GET_STUDENT_DISCIPLINES_SUCCESS: {
            return {
                ...state,
                disciplines: action.payload.disciplines,
                loading: false
            };
        }
        case GET_STUDENT_DISCIPLINES_FAIL: {
            return {
                ...state,
                loading: false
            };
        }
        default:
            return state;
    }
}
