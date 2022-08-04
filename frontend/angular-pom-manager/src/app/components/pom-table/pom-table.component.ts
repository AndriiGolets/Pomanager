import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PomTable} from "../../common/pom-table";
import {Filter} from "../../common/filter";
import {Router} from "@angular/router";

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

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  selectProperty(event, propertyName) {
    this.router.navigate([], {queryParams: {
        ...this.filter,
        propertyFilter: propertyName,
      }});
  }

  selectPackage(event, packageName) {
    this.router.navigate([], {queryParams: {
        ...this.filter,
        packageFilter: packageName,
      }});
  }

}
