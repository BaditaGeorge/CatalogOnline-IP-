import {
    GET_GLOBAL,
    GET_GLOBAL_SUCCESS,
    GET_GLOBAL_FAIL,
    GET_PROFESSOR_CATALOG,
    GET_PROFESSOR_CATALOG_SUCCESS,
    GET_PROFESSOR_CATALOG_FAIL,
} from "../actions/actions";

const INITIAL_STATE = {
    global: undefined,
    columns: [
        {key: 'id', type: 'string'},
        {key: 'student', type: 'string'},
        {key: 'group', type: 'string'},
        {key: 'l1', type: 'number'},
        {key: 'l2', type: 'number'},
        {key: 'l3', type: 'number'},
        {key: 'l4', type: 'number'},
        {key: 'l5', type: 'number'},
        {key: 'presences', type: 'number'},
        {key: 'exam', type: 'number'},
    ],
    rows: [
        {
            id: 0,
            student: 'James Doe 1',
            group: 'B5',
            l1: 1,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        },
        {
            id: 0,
            student: 'James Doe 2',
            group: 'B2',
            l1: 2,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        },
        {
            id: 0,
            student: 'James Doe 3',
            group: 'B3',
            l1: 3,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        },
        {
            id: 0,
            student: 'James Doe 4',
            group: 'B6',
            l1: 4,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        },
        {
            id: 0,
            student: 'James Doe 1',
            group: 'B5',
            l1: 5,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        },
        {
            id: 0,
            student: 'James Doe 6',
            group: 'B5',
            l1: 6,
            l2: 4,
            l3: 6,
            l4: 8,
            l5: 10,
            l6: 10,
            presences: 10,
            exam: 10
        }
    ],
    loading: false
};

export default function reducer(state = INITIAL_STATE, action) {
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
        case GET_PROFESSOR_CATALOG: {
            return {
                ...state,
                loading: true
            };
        }
        case GET_PROFESSOR_CATALOG_SUCCESS: {
            return {
                ...state,
                // rows: action.payload.rows,
                // columns: action.payload.columns,
                loading: false
            };
        }
        case GET_PROFESSOR_CATALOG_FAIL: {
            return {
                ...state,
                loading: false
            };
        }
        default:
            return state;
    }
}
