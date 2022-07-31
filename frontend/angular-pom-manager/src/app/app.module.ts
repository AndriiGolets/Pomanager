import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PomTableComponent} from './components/pom-table/pom-table.component';
import {HttpClientModule} from "@angular/common/http";
import {PomTableService} from "./services/pom-table.service";

@NgModule({
  declarations: [
    AppComponent,
    PomTableComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [PomTableService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
