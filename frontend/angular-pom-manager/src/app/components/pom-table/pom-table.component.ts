import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PomTable} from "../../common/pom-table";
import {Filter} from "../../common/filter";

@Component({
  selector: 'app-pom-table',
  templateUrl: './pom-table.component.html',
  styleUrls: ['./pom-table.component.css']
})
export class PomTableComponent implements OnInit {

  @Input()
  pomTable: PomTable;

  @Input()
  filter: Filter;

  @Output()
  filterUpdate: EventEmitter<Filter> = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  selectProperty(event, propertyName) {
    this.filterUpdate.emit(<Filter>{
      ...this.filter,
      propertyFilter: propertyName.key,
    });
  }

  selectPackage(event, packageName) {
    this.filterUpdate.emit(<Filter>{
      ...this.filter,
      packageFilter: packageName,
    });
  }

}
