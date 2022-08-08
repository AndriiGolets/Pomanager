import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PomTable} from "../../common/pom-table";
import {Filter} from "../../common/filter";
import {Router} from "@angular/router";
import {PropertyUpdateEvent} from "../../common/property-update-event";
import {GitManagementService} from "../../services/git-management.service";

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

  @Output()
  propertyUpdate: EventEmitter<PropertyUpdateEvent> = new EventEmitter();

  showInput: boolean;

  packageName: string;

  propertyName: string;

  constructor(private router: Router, private gitManagementService: GitManagementService) {
  }

  ngOnInit(): void {
  }

  selectProperty(event, propertyName) {
    console.log(propertyName);
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

  showEditFieldFor(packageName: string, propertyName: string) {
    this.packageName = packageName;
    this.propertyName = propertyName;
    this.showInput = true;
  }

  edit(newValue: string) {
    if (!this.showInput) {
      return;
    }

    this.propertyUpdate.emit(new PropertyUpdateEvent(this.packageName, this.propertyName, newValue));
    this.showInput = false;
  }

  checkoutAndPullByProperty(propertyName: string) {
    const packagesToUpdate = [];
    this.pomTable.pomTableMap.forEach((value, key) => {
      if (value.has(propertyName)) {
        packagesToUpdate.push(key);
      }
    });

    this.gitManagementService.checkoutAndPull("master", packagesToUpdate);
  }

  getEventValue(event: Event): string {
    return (event.target as HTMLInputElement).value;
  }

}
