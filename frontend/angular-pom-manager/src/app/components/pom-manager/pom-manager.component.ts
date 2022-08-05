import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {PomTableStateService} from "../../state/pom-table-state.service";
import {Filter} from "../../common/filter";
import {Observable} from "rxjs";
import {PomTable} from "../../common/pom-table";
import {PropertyUpdateEvent} from "../../common/property-update-event";

@Component({
  selector: 'pom-manager',
  templateUrl: './pom-manager.component.html',
  styleUrls: ['./pom-manager.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PomManagerComponent implements OnInit {

  pomTable$: Observable<PomTable> = this.pomTableState.pomTableFiltered$;
  filter$: Observable<Filter> = this.pomTableState.filter$;

  constructor(private pomTableState: PomTableStateService) {
  }

  ngOnInit(): void {
  }

  onFilterUpdate(filter: Filter) {
    this.pomTableState.updateFilter(filter);
  }

  onPropertyUpdate(propertyUpdateEvent: PropertyUpdateEvent) {
    this.pomTableState.updatePropertyForPackage(propertyUpdateEvent);
  }

  reload() {
    this.pomTableState.load();
    this.pomTableState.clearFilters();
  }

}
