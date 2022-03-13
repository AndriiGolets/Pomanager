import { Component, OnInit } from '@angular/core';
import {PomTableService} from "../../services/pom-table.service";
import {PomTable} from "../../common/pom-table";

@Component({
  selector: 'app-pom-table',
  templateUrl: './pom-table.component.html',
  styleUrls: ['./pom-table.component.css']
})
export class PomTableComponent implements OnInit {

  pomTable: PomTable = new PomTable();

  constructor(private pomTableService: PomTableService) { }

  ngOnInit(): void {
    this.getPomTable();
  }

  asIs() { return 0; }

  private getPomTable() {
    this.pomTableService.getPomTable().subscribe(
      data =>  {
        this.pomTable = data;
        console.log(this.pomTable)
      }
    )
  }
}
