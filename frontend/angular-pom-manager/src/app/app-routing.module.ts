import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {APP_BASE_HREF} from '@angular/common';
import {PomManagerComponent} from "./components/pom-manager/pom-manager.component";

const appRoutes: Routes = [
  {
    path: '',
    component: PomManagerComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'}
  ]
})
export class AppRoutingModule {
}
