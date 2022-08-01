import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PomTableComponent} from './components/pom-table/pom-table.component';
import {HttpClientModule} from "@angular/common/http";
import {PomTableService} from "./services/pom-table.service";
import { SearchComponent } from './components/search/search.component';
import {PomManagerComponent} from "./components/pom-manager/pom-manager.componenet";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    PomManagerComponent,
    PomTableComponent,
    SearchComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [PomTableService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
