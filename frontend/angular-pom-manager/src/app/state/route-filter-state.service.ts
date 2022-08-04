import {Injectable, OnInit} from '@angular/core';
import {PomTableStateService} from "./pom-table-state.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Filter} from "../common/filter";

@Injectable({
  providedIn: 'root'
})
export class RouteFilterStateService implements OnInit {

  init() {
    console.log("Initialized");
  }

  constructor(private pomTableState: PomTableStateService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.pomTableState.clearFilters();
      this.pomTableState.updateFilter(<Filter>{
        ...params
      });
    });
  }

  ngOnInit(): void {
  }

}
