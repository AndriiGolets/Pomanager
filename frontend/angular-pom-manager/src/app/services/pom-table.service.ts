import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PomTable} from "../common/pom-table";
import {PomTableJson} from "../common/pom-table";

@Injectable({
  providedIn: 'root'
})
export class PomTableService {

  private baseUrl = 'https://4687b69c-93ae-45af-9e1c-604765ae5084.mock.pstmn.io/pomtable'

  constructor(private httpClient: HttpClient) {
  }

  getPomTable(): Observable<PomTable> {
    return this.httpClient.get<PomTableJson>(this.baseUrl).pipe(
      map(response => new PomTable(response))
    )
  }

}
