import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, catchError } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject,Observable, of  } from 'rxjs';


import { User } from '../models/User';
import { UtilService } from './util/util.service';

const helper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private url:string;
  private currentUserSubject : BehaviorSubject<User>;
  public currentUser: Observable<User>;  
  
  constructor(private http: HttpClient, utilService:UtilService, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')!));
    this.currentUser = this.currentUserSubject.asObservable();
    this.url = utilService.getUrl()
   }

   login(username: string, password: string){
    
    return this.http.post<any>(this.url + '/login', { username, password },{observe: 'response' as 'body'})
           .pipe(map((user: any) => {                 
               localStorage.setItem('currentUser', JSON.stringify(user.headers.get('authorization'))); 
               this.currentUserSubject.next(user);              
               return user;
           },(error: any) =>{ console.log("ERRORN: ",error)}));
     
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
  
  logout(){
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    return helper.isTokenExpired(token) //tokenNotExpired(null,token);
  }

  public getToken(): any {
    if(localStorage.getItem('currentUser') == null){
      return "";
    }
    return localStorage.getItem('currentUser');
  }

  public getTokenRefresh(): string {
    if(localStorage.getItem('refreshToken') == null){
      return "";
    }
    return JSON.parse(localStorage.getItem('refreshToken')!);
  }

  public getDecodeToken():any {
    let token = this.getToken()
    return helper.decodeToken(token)
  }
}
