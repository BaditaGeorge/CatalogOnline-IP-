import React, {Component} from 'react';
import '../App.css';
import ReactDataGrid from 'react-data-grid';
import {Toolbar, Data} from "react-data-grid-addons";
import {connect} from "react-redux";
import {getGlobal, getProfessorCatalog} from "../actions/actions";

const selectors = Data.Selectors;

class ProfessorDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
        this.props.getGlobal()
        this.props.getProfessorCatalog()
    }

    render() {
        console.log(this.props)
        return (
            <Catalog rows={this.props.rows} columns={this.props.columns}/>
        );
    }
}

export const ProfessorDashboardRedux = connect((state) => ({
    global: state.global,
    columns: state.columns,
    rows: state.rows,
    loading: state.loading
}), {
    getGlobal, getProfessorCatalog
})(ProfessorDashboard)

class Catalog extends Component {
    constructor(props) {
        super(props)
        this.state = {
            rows: props.rows,
            columns: props.columns,
            filters: {}
        }
    }

    handleFilterChange = (initialFilters, filter) => {
        const newFilters = {...initialFilters};
        if (filter.filterTerm) {
            newFilters[filter.column.key] = filter;
        } else {
            delete newFilters[filter.column.key];
        }
        return newFilters;
    };

    getValidFilterValues = (rows, columnId) => {
        console.log(rows
            .map(r => r[columnId])
            .filter((item, i, a) => {
                return i === a.indexOf(item);
            }))
        return rows
            .map(r => r[columnId])
            .filter((item, i, a) => {
                return i === a.indexOf(item);
            });
    }

    getRowsSelector = (rows, filters) => {
        return selectors.getRows({rows, filters});
    }

    sortRows = (initialRows, sortColumn, sortDirection) => {
        const comparer = (a, b) => {
            if (sortDirection === "ASC") {
                return a[sortColumn] > b[sortColumn] ? 1 : -1;
            } else if (sortDirection === "DESC") {
                return a[sortColumn] < b[sortColumn] ? 1 : -1;
            }
        };
        return sortDirection === "NONE" ? initialRows : [...initialRows].sort(comparer);
    };

    onColumnSort = (sortColumn, sortDirection) => {
        this.setState(state => {
            const rows = this.sortRows(state.rows, sortColumn, sortDirection)
            return {rows};
        })
    }

    onGridRowsUpdated = ({fromRow, toRow, updated}) => {
        this.setState(state => {
            const rows = state.rows.slice();
            for (let i = fromRow; i <= toRow; i++) {
                rows[i] = {...rows[i], ...updated};
            }
            return {rows};
        });
    };


    onFilter = (filter) => {
        this.setState(state => {
            const filters = this.handleFilterChange(state.filters, filter)
            return {filters};
        })
    }

    onColumnResize = (idx, width) => {
        console.log(`Column ${idx} has been resized to ${width}`)
    }

    render() {
        const filteredRows = this.getRowsSelector(this.state.rows, this.state.filters);
        return (
            <ReactDataGrid
                columns={this.state.columns}
                rowGetter={i => filteredRows[i]}
                rowsCount={6}
                minHeight={300}
                onGridRowsUpdated={this.onGridRowsUpdated}
                onColumnResize={this.onColumnResize}
                onGridSort={this.onColumnSort}
                enableCellSelect={true}
                toolbar={<Toolbar enableFilter={true}/>}
                onAddFilter={this.onFilter}
                onClearFilters={() => this.setState({filters: {}})}
                getValidFilterValues={columnKey => this.getValidFilterValues(this.state.rows, columnKey)}
            />
        )
    }
}