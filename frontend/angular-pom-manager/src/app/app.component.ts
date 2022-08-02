import { Component } from '@angular/core';
import {RouteFilterStateService} from "./state/route-filter-state.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-pom-manager';

  constructor(private routeFilterState: RouteFilterStateService) {
    this.routeFilterState.init();
  }

}
