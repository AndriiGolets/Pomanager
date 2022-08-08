import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GitManagementService {

  private baseUrl = 'http://localhost:8080/api/git-management'

  constructor(private httpClient: HttpClient) {
  }

  checkoutAndPull(branch: string, packageNames: string[]) {
    this.httpClient.get(this.baseUrl + "/checkout-and-pull", {
      params: {
        branchName: branch,
        packageNames: packageNames
      }
    }).pipe(
      catchError(e => {
        return new Observable(() => console.log(e));
      })
    ).subscribe();
  }

}
