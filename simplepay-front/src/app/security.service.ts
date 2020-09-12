import {Injectable} from '@angular/core';
import {ApplicationUser} from "./shared/applicationuser.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private signupUrl = '/users/sign-up';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };


  constructor(private http: HttpClient) {
  }

  signUp(applicationuser: ApplicationUser): void {
    console.log(applicationuser);
    this.http.post<ApplicationUser>(this.signupUrl, applicationuser, this.httpOptions).pipe(
      catchError(this.handleError<ApplicationUser>('signUp'))
    ).subscribe(res => console.log(res));

  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      console.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }
}
