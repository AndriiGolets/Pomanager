import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PomTable} from "../common/pom-table";
import {PomTableJson} from "../common/pom-table";

@Injectable({
  providedIn: 'root'
})
export class PomTableService {

  private baseUrl = 'http://localhost:8080/api/pomtable'

  constructor(private httpClient: HttpClient) {
  }

  getPomTable(): Observable<PomTable> {
    return this.httpClient.get<PomTableJson>(this.baseUrl).pipe(
      map(response => new PomTable(response))
    )
  }

}
