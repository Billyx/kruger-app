import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Router} from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { AuthService } from "../services/auth.service";
import { ErrorResponse } from "../models/errorResponse";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{
    errorResponse!: ErrorResponse;

    constructor(public auth: AuthService,
                private router: Router,
                private http: HttpClient){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        
        let currentUser =  this.auth.currentUserValue // = JSON.parse(localStorage.getItem('currentUser'));
        console.log("CurrentUser: ",currentUser)
            
            if(this.auth.getToken()){
                request = request.clone({
                    setHeaders:{ Authorization: this.auth.getToken() }
                })
                
            }
        return next.handle(request)
    }

    private catchError(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler) {
        if (error instanceof HttpErrorResponse) {
          return this.catchHttpError(error, req, next);
        } else {
          return Observable.throw(error);
        }
    }

    private catchHttpError(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler) {
    if (error.status === 401 || error.status === 403) {
        this.errorResponse = JSON.parse(JSON.stringify(error.error));
        console.log("expired token!.!")
        if(this.errorResponse!= null && this.errorResponse.errorCode != null && this.errorResponse.errorCode == 10){
            return Observable.throw(error);
        }else{
            return this.catchUnauthorized(error, req, next);
        }
    } else {
            return Observable.throw(error);
            }
    }
      
    private catchUnauthorized(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler) {
    if(!this.auth.isAuthenticated() ){
        this.navigateToNoAutorizado();
        return Observable.throw(error);
    }else{
        return Observable.throw(error);
        }
    }

    private navigateToNoAutorizado() {
        console.log("navigateTo No autorizado")
        //this.router.navigate(['/login']);
    }
    
}