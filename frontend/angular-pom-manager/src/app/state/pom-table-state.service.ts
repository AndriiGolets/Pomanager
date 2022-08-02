import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {PomTable} from "../common/pom-table";
import {StateService} from "./state.service";
import {PomTableService} from "../services/pom-table.service";
import {Filter} from "../common/filter";

interface PomTableState {
  table: PomTable,
  filter: Filter
}

const filterInitialState = new Filter('', '')

const initialState: PomTableState = {
  table: new PomTable(),
  filter: filterInitialState
}

@Injectable({
  providedIn: 'root',
})
export class PomTableStateService extends StateService<PomTableState> {

  pomTableFiltered$: Observable<PomTable> = this.select((state) => {
    return getPomTableFiltered(state.table, state.filter);
  });

  filter$: Observable<Filter> = this.select((state) => state.filter);

  constructor(private apiService: PomTableService) {
    super(initialState);

    this.load();
  }

  updateFilter(filter: Filter) {
    this.setState({
      filter: {
        ...this.state.filter,
        ...filter,
      },
    });
  }

  clearFilters() {
    this.setState({
      filter: filterInitialState
    });
  }

  load() {
    this.apiService.getPomTable().subscribe((table) => this.setState({table}));
  }
}

function getPomTableFiltered(table: PomTable, filter: Filter): PomTable {
  const tableClone: PomTable = table.copy();
  tableClone.filterByProperty(filter.propertyFilter);
  tableClone.filterByPackage(filter.packageFilter);
  return tableClone;
}
