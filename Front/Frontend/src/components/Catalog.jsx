import React, { Component } from 'react';
import ReactDataGrid from "react-data-grid";
import equal from "fast-deep-equal"
import { Data, Filters, Menu, Toolbar } from "react-data-grid-addons";

const {
  NumericFilter,
  AutoCompleteFilter,
  MultiSelectFilter,
  SingleSelectFilter
} = Filters;
const { ContextMenu, MenuItem, SubMenu, ContextMenuTrigger } = Menu;
const selectors = Data.Selectors;
const filters = {
  number: NumericFilter,
  string: AutoCompleteFilter,
  list: SingleSelectFilter,
};
const defaultColumnProperties = {
  editable: true,
  resizable: true,
  filterable: true,
  sortable: true,
};

export default class Catalog extends Component {
  constructor (props) {
    super(props);
    this.state = {
      rows: props.rows,
      columns: props.columns,
      user: props.user,
      variables: [],
      filters: {}
    }
  }

  componentWillReceiveProps (nextProps, nextContext) {
    this.setState({ rows: nextProps.rows, columns: nextProps.columns })
    let variables = []
    this.props.formulas.map(formula => {
      console.log(formula)
      if (formula.id_materie === this.props.currentDiscipline.id_materie) {
        variables = formula.formula_calcul.match(/\w+/g)
      }
    })
    this.setState({ variables: variables })
  }

  askUserInput = (message, defaultInput) => {
    const result = prompt(message);
    if (result === "") {
      return defaultInput
    } else if (result) {
      return result
    } else {
      return defaultInput
    }
  };

  createEmptyRow = (columns) => {
    const newRow = {};
    columns.map((item) => {
      const result = prompt(item.key);
      newRow[item.key] = result
    });
    return newRow
  };

  deleteRow = (initialRows, rowIdx) => {
    const nextRows = [...initialRows];
    nextRows.splice(rowIdx, 1);
    if (nextRows.length)
      return nextRows;
    alert('You cant delete the last row')
    return initialRows
  };

  insertRow = (initialRows, rowIdx) => {
    const newRow = this.createEmptyRow(this.state.columns);
    const nextRows = [...initialRows];
    nextRows.splice(rowIdx, 0, newRow);
    return nextRows;
  };

  deleteColumn = (initialColumns, columnIdx) => {
    const nextColumns = [...initialColumns];
    nextColumns.splice(columnIdx, 1);
    return nextColumns
  };

  deleteDataFromColumn = (initialRows, columnId) => {
    const nextRows = [...initialRows];
    nextRows.map((item, index) => {
      delete nextRows[index][columnId];
    });
    return nextRows;
  };

  insertDataToColumn = (initialRows, columnId, columnType) => {
    const nextRows = [...initialRows];
    nextRows.map((item, index) => {
      nextRows[index][columnId] = columnType === 'string' ? '-' : 0;
    });
    return nextRows;
  };

  insertColumn = (initialColumns, colIdx, newColIdx, newColType) => {
    const newColumn = { key: newColIdx, name: newColIdx, type: newColType };
    const nextColumns = [...initialColumns];
    nextColumns.splice(colIdx, 0, newColumn);
    return nextColumns;
  };

  renameColumnInRows = (initialRows, initialColumnId, columnId) => {
    const nextRows = [...initialRows];
    nextRows.map((item, index) => {
      nextRows[index][columnId] = nextRows[index][initialColumnId];
      delete nextRows[index][initialColumnId]
    });
    return nextRows;
  };

  renameColumn = (initialColumns, colIdx, newColIdx) => {
    const nextColumns = [...initialColumns];
    nextColumns[colIdx].key = newColIdx;
    nextColumns[colIdx].name = newColIdx;
    return nextColumns;
  };

  getRowsSelector = (rows, filters) => {
    return selectors.getRows({ rows, filters });
  };

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
  };

  changeFilters = (initialFilters, filter) => {
    const newFilters = { ...initialFilters };
    if (filter.filterTerm) {
      newFilters[filter.column.key] = filter;
    } else {
      delete newFilters[filter.column.key];
    }
    return newFilters;
  };

  onFilter = (filter) => {
    this.setState(state => {
      const filters = this.changeFilters(state.filters, filter);
      return { filters };
    })
  };

  onColumnResize = (idx, width) => {
    console.log(`Column ${idx} has been resized to ${width}`)
  };

  onRowDelete = (rowIdx) => {
    this.setState(state => {
      const rows = this.deleteRow(state.rows, rowIdx);
      return { rows };
    })
  };

  onRowInsert = (rowIdx) => {
    this.setState(state => {
      const rows = this.insertRow(state.rows, rowIdx);
      return { rows };
    })
  };

  onColumnDelete = (colIdx) => {
    try {
      if (colIdx < 3)
        throw 'You cant delete one of the mandatory columns (ex: student, id, group)'
      if (this.state.variables.includes(this.state.columns[colIdx].key)) {
        throw 'You cant remove a column from formula. Change formula and try again!'
      }
      if (this.state.columns.length < 5)
        throw 'You cant delete all variables'
      this.setState(state => {
        const colId = state.columns[colIdx].key;
        const columns = this.deleteColumn(state.columns, colIdx);
        const rows = this.deleteDataFromColumn(state.rows, colId);
        return { columns, rows };
      })
    } catch (e) {
      alert(e)
    }

  };

  onColumnRename = (colIdx) => {
    try {
      if (colIdx < 3)
        throw 'You cant rename one of the mandatory columns (ex: student, id, group)'
      if (this.state.variables.includes(this.state.columns[colIdx].key)) {
        throw 'You cant rename a column from formula. Change formula and try again!'
      }
      const newColIdx = this.askUserInput('Name:', 'undefined');
      this.state.columns.map(col => {
        if (col.key === newColIdx)
          throw `You already have a column named ${newColIdx}`
      })
      this.setState(state => {
        const initialColumnId = this.state.columns[colIdx].key;
        const columns = this.renameColumn(state.columns, colIdx, newColIdx);
        const rows = this.renameColumnInRows(state.rows, initialColumnId, newColIdx);
        return { columns, rows };
      });
      this.forceUpdate()
    } catch (e) {
      alert(e)
    }
  };

  onColumnInsert = (colIdx) => {
    try {
      const newColIdx = this.askUserInput('Name:', 'undefined');
      const newColType = 'number';
      this.state.columns.map(col => {
        if (col.key === newColIdx)
          throw `You already have a column named ${newColIdx}`
      })
      this.setState(state => {
        const columns = this.insertColumn(state.columns, colIdx, newColIdx, newColType);
        const rows = this.insertDataToColumn(state.rows, newColIdx, newColType);
        return { columns, rows };
      })
    } catch (e) {
      alert(e)
    }
  };

  onColumnSort = (sortColumn, sortDirection) => {
    this.setState(state => {
      const rows = this.sortRows(state.rows, sortColumn, sortDirection);
      return { rows };
    })
  };

  onGridRowsUpdated = ({ fromRow, toRow, updated }) => {
    this.setState(state => {
      const rows = state.rows.slice();
      for (let i = fromRow; i <= toRow; i++) {
        rows[i] = { ...rows[i], ...updated };
      }
      return { rows };
    });

  };

  onCatalogChange = (rows, columns) => {
    const catalog = {
      profesor: this.props.user.role === 'admin' ?  this.props.currentProfessor.id_professor : this.props.user.userId,
      disciplina: this.props.currentDiscipline.id_materie,
      columns: columns,
      rows: rows
    }
    this.props.onCatalogChange(catalog)
  }

  render () {

    if (!equal(this.props.rows, this.state.rows) || !equal(this.props.columns, this.state.columns)) {
      this.onCatalogChange(this.state.rows, this.state.columns)
    }
    const filteredRows = this.getRowsSelector(this.state.rows, this.state.filters);
    let columns = [...this.state.columns.map(c => ({ ...c, ...defaultColumnProperties }))]
    columns = [...columns.map(column => {
      column.name = column.key;
      column.filterable = column.type !== 'none';
      if (column.filterable)
        column.filterRenderer = filters[column.type];
      return column
    })];
    const writeRight = this.state.user.role !== 'student'
    return (
      <ReactDataGrid
        columns={columns}
        rowGetter={i => filteredRows[i]}
        rowsCount={this.state.rows.length}
        onGridRowsUpdated={writeRight ? this.onGridRowsUpdated : null}
        onColumnResize={this.onColumnResize}
        onGridSort={this.onColumnSort}
        enableCellSelect={writeRight ? true : false}
        toolbar={<Toolbar enableFilter={true}/>}
        onAddFilter={this.onFilter}
        onClearFilters={() => this.setState({ filters: {} })}
        getValidFilterValues={columnKey => this.getValidFilterValues(this.state.rows, columnKey)}
        contextMenu={writeRight ?
          <CatalogContextMenu
            onRowDelete={(e, { rowIdx }) => this.onRowDelete(rowIdx)}
            onRowInsertAbove={(e, { rowIdx }) => this.onRowInsert(rowIdx)}
            onRowInsertBelow={(e, { rowIdx }) => this.onRowInsert(rowIdx + 1)}
            onColumnDelete={(e, { idx }) => this.onColumnDelete(idx)}
            onColumnInsertLeft={(e, { idx }) => this.onColumnInsert(idx)}
            onColumnInsertRight={(e, { idx }) => this.onColumnInsert(idx + 1)}
            onColumnRename={(e, { idx }) => this.onColumnRename(idx)}
          />
          : null
        }
        RowsContainer={ContextMenuTrigger}
        ColumnsContainer={ContextMenuTrigger}
      />
    )
  }
}

class CatalogContextMenu extends Component {
  constructor (props) {
    super(props)
  }

  render () {
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
    } = this.props;
    return (
      <ContextMenu id={id}>
        <MenuItem data={{ rowIdx, idx }} onClick={onColumnRename}>
          Rename Column
        </MenuItem>
        <MenuItem data={{ rowIdx, idx }} onClick={onRowDelete}>
          Delete Row
        </MenuItem>
        <MenuItem data={{ rowIdx, idx }} onClick={onColumnDelete}>
          Delete Column
        </MenuItem>
        <SubMenu title="Insert Row">
          <MenuItem data={{ rowIdx, idx }} onClick={onRowInsertAbove}>
            Above
          </MenuItem>
          <MenuItem data={{ rowIdx, idx }} onClick={onRowInsertBelow}>
            Below
          </MenuItem>
        </SubMenu>
        <SubMenu title="Insert Column">
          <MenuItem data={{ rowIdx, idx }} onClick={onColumnInsertLeft}>
            Left
          </MenuItem>
          <MenuItem data={{ rowIdx, idx }} onClick={onColumnInsertRight}>
            Right
          </MenuItem>
        </SubMenu>
      </ContextMenu>
    );
  }
}
