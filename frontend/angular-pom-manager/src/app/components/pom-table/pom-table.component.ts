import {Component, OnInit} from '@angular/core';
import {PomTableService} from "../../services/pom-table.service";
import {PomTable} from "../../common/pom-table";
import {PomPropertyValue} from "../../common/pom-property-value";

@Component({
  selector: 'app-pom-table',
  templateUrl: './pom-table.component.html',
  styleUrls: ['./pom-table.component.css']
})
export class PomTableComponent implements OnInit {

  pomTable: PomTable = new PomTable();

  constructor(private pomTableService: PomTableService) {
  }

  ngOnInit(): void {
    this.getPomTable();
  }

  selectRow(event, property) {

    this.pomTable.pomPropertyNameMap.forEach((val, key) => {
      if (key != property.key) {
        this.pomTable.pomPropertyNameMap.delete(key);
      }
    })
    this.removeEmptyRows(property);
  }

  private removeEmptyRows(property) {
    this.pomTable.pomTableMap.forEach((val, key, map) => {
      if(!val.get(property.key)){
        map.delete(key);
      }
    })
  }

  private getPomTable() {
    this.pomTableService.getPomTable().subscribe(
      data => {
        this.pomTable = data;
        console.log(this.pomTable)
      }
    )
  }
}
