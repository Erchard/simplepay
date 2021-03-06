import {Injectable} from '@angular/core';
import {ApplicationUser} from "./shared/applicationuser.model";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {Observable, of, Subscription} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private signupUrl = '/users/sign-up';
  private loginUrl = '/login';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private authorization: string;


  constructor(private http: HttpClient,
              private router: Router) {
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

  private getResponse(applicationuser: ApplicationUser): Observable<HttpResponse<ApplicationUser>> {
    return this.http.post<ApplicationUser>(
      this.loginUrl, applicationuser, {observe: 'response'});
  }

  logIn(applicationuser: ApplicationUser): Observable<HttpResponse<ApplicationUser>> {
    console.log(applicationuser);
    return this.getResponse(applicationuser).pipe(
      map(resp => {
        console.log(resp);
        this.authorization = resp.headers.get('authorization');
        console.log(this.authorization);
        return resp;
      })
    )
  }

  requireLogedIn():void{
    if(!this.authorization || this.authorization.length == 0){
      this.router.navigate(['appuser/login']);
    }
  }

}
