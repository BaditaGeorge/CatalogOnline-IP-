import React, {Component} from 'react';
import ReactDataGrid from "react-data-grid";
import {Data, Menu, Toolbar} from "react-data-grid-addons";

const selectors = Data.Selectors;
const {ContextMenu, MenuItem, SubMenu, ContextMenuTrigger} = Menu;

export default class Catalog extends Component {
    constructor(props) {
        super(props)
        this.state = {
            rows: props.rows,
            columns: props.columns,
            filters: {}
        }
    }

    askColumnName = () => {
        let columnId = undefined;
        columnId = prompt("Column ID:");
        return columnId ? columnId : `New Column`
    }

    createEmptyRow = (columns, fill) => {
        const newRow = {}
        columns.map((item, index) => {
            newRow[item.key] = fill
        })
        return newRow
    }

    deleteRow = (initialRows, rowIdx) => {
        const nextRows = [...initialRows];
        nextRows.splice(rowIdx, 1);
        return nextRows;
    };

    insertRow = (initialRows, rowIdx) => {
        const newRow = this.createEmptyRow(this.state.columns, "-");
        const nextRows = [...initialRows];
        nextRows.splice(rowIdx, 0, newRow);
        return nextRows;
    };

    deleteColumn = (initialColumns, columnIdx) => {
        const nextColumns = [...initialColumns];
        nextColumns.splice(columnIdx, 1);
        return nextColumns;
    };

    deleteDataFromColumn = (initialRows, columnId) => {
        const nextRows = [...initialRows];
        nextRows.map((item, index) => {
            delete nextRows[index][columnId];
        })
        return nextRows;
    };

    insertDataToColumn = (initialRows, columnId) => {
        const nextRows = [...initialRows];
        nextRows.map((item, index) => {
            nextRows[index][columnId] = '-';
        })
        return nextRows;
    };

    insertColumn = (initialColumns, colIdx, newColIdx) => {
        const newColumn = {key: newColIdx, name: newColIdx}
        const nextColumns = [...initialColumns];
        nextColumns.splice(colIdx, 0, newColumn);
        return nextColumns;
    };

    renameColumnInRows = (initialRows, initialColumnId, columnId) => {
        const nextRows = [...initialRows];
        nextRows.map((item, index) => {
            nextRows[index][columnId] = nextRows[index][initialColumnId];
            delete nextRows[index][initialColumnId]
        })
        return nextRows;
    };

    renameColumn = (initialColumns, colIdx, newColIdx) => {
        const nextColumns = [...initialColumns];
        nextColumns[colIdx].key = newColIdx;
        nextColumns[colIdx].name = newColIdx;
        return nextColumns;
    };

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

    getValidFilterValues = (rows, columnId) => {
        return rows
            .map(r => r[columnId])
            .filter((item, i, a) => {
                return i === a.indexOf(item);
            });
    }

    changeFilters = (initialFilters, filter) => {
        const newFilters = {...initialFilters};
        if (filter.filterTerm) {
            newFilters[filter.column.key] = filter;
        } else {
            delete newFilters[filter.column.key];
        }
        return newFilters;
    };

    onRowDelete = (rowIdx) => {
        this.setState(state => {
            const rows = this.deleteRow(state.rows, rowIdx)
            return {rows};
        })
    };

    onRowInsert = (rowIdx) => {
        this.setState(state => {
            const rows = this.insertRow(state.rows, rowIdx)
            return {rows};
        })
    };

    onColumnDelete = (colIdx) => {
        this.setState(state => {
            const colId = state.columns[colIdx].key;
            const columns = this.deleteColumn(state.columns, colIdx)
            const rows = this.deleteDataFromColumn(state.rows, colId)
            return {columns, rows};
        })
    };

    onColumnRename = (colIdx, newColIdx) => {
        this.setState(state => {
            const initialColumnId = this.state.columns[colIdx].key
            const columns = this.renameColumn(state.columns, colIdx, newColIdx)
            const rows = this.renameColumnInRows(state.rows, initialColumnId, newColIdx)
            return {columns, rows};
        })
        this.forceUpdate()
    };

    onColumnInsert = (colIdx, newColIdx) => {
        this.setState(state => {
            const columns = this.insertColumn(state.columns, colIdx, newColIdx)
            const rows = this.insertDataToColumn(state.rows, newColIdx)
            return {columns, rows};
        })
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
            const filters = this.changeFilters(state.filters, filter)
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
                rowsCount={this.state.rows.length}
                onGridRowsUpdated={this.onGridRowsUpdated}
                onColumnResize={this.onColumnResize}
                onGridSort={this.onColumnSort}
                enableCellSelect={true}
                toolbar={<Toolbar enableFilter={true}/>}
                onAddFilter={this.onFilter}
                onClearFilters={() => this.setState({filters: {}})}
                getValidFilterValues={columnKey => this.getValidFilterValues(this.state.rows, columnKey)}
                contextMenu={
                    <CatalogContextMenu
                        onRowDelete={(e, {rowIdx}) => this.onRowDelete(rowIdx)}
                        onRowInsertAbove={(e, {rowIdx}) => this.onRowInsert(rowIdx)}
                        onRowInsertBelow={(e, {rowIdx}) => this.onRowInsert(rowIdx + 1)}
                        onColumnDelete={(e, {idx}) => this.onColumnDelete(idx)}
                        onColumnInsertLeft={(e, {idx}) => this.onColumnInsert(idx, this.askColumnName())}
                        onColumnInsertRight={(e, {idx}) => this.onColumnInsert(idx + 1, this.askColumnName())}
                        onColumnRename={(e, {idx}) => this.onColumnRename(idx, this.askColumnName())}
                    />
                }
                RowsContainer={ContextMenuTrigger}
                ColumnsContainer={ContextMenuTrigger}
            />
        )
    }
}

class CatalogContextMenu extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        const {
            rowIdx,
            idx,
            id,
            onColumnRename,
            onRowDelete,
            onRowInsertAbove,
            onRowInsertBelow,
            onColumnDelete,
            onColumnInsertLeft,
            onColumnInsertRight
        } = this.props
        return (
            <ContextMenu id={id}>
                <MenuItem data={{rowIdx, idx}} onClick={onColumnRename}>
                    Rename Column
                </MenuItem>
                <MenuItem data={{rowIdx, idx}} onClick={onRowDelete}>
                    Delete Row
                </MenuItem>
                <MenuItem data={{rowIdx, idx}} onClick={onColumnDelete}>
                    Delete Column
                </MenuItem>
                <SubMenu title="Insert Row">
                    <MenuItem data={{rowIdx, idx}} onClick={onRowInsertAbove}>
                        Above
                    </MenuItem>
                    <MenuItem data={{rowIdx, idx}} onClick={onRowInsertBelow}>
                        Below
                    </MenuItem>
                </SubMenu>
                <SubMenu title="Insert Column">
                    <MenuItem data={{rowIdx, idx}} onClick={onColumnInsertLeft}>
                        Left
                    </MenuItem>
                    <MenuItem data={{rowIdx, idx}} onClick={onColumnInsertRight}>
                        Right
                    </MenuItem>
                </SubMenu>
            </ContextMenu>
        );
    }
}