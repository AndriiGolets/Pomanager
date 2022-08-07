import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PomTable} from "../../common/pom-table";
import {Filter} from "../../common/filter";
import {Router} from "@angular/router";
import {PropertyUpdateEvent} from "../../common/property-update-event";

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

  showInputForPackage: string;

  showInputForProperty: string;

  constructor(private router: Router) {
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

  showEditField(packageName: string, propertyName: string) {
    if (!this.showInputForPackage && !this.showInputForProperty) {
      this.showInputForPackage = packageName;
      this.showInputForProperty = propertyName;
    }
  }

  edit(packageName: string, newValue:string) {
    if (!this.showInputForPackage && !this.showInputForProperty) {
      return;
    }

    this.propertyUpdate.emit(new PropertyUpdateEvent(this.showInputForPackage, this.showInputForProperty, newValue));

    this.showInputForProperty = undefined;
    this.showInputForPackage = undefined;
  }

  getValue(event: Event): string {
    return (event.target as HTMLInputElement).value;
  }

}
