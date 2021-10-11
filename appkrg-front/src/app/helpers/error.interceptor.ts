import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchAll } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { DOCUMENT } from '@angular/common';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(@Inject(DOCUMENT) document: any,private authenticationService: AuthService, private router:Router) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401 || err.status === 403 ) { //403
                // auto logout if 401 response returned from api
                console.log(err.status," ddd ",request.url.search("/login"))
                
                // Si el jwt expira (Intercepta una url dentro del sistema)
                if(request.url.search("/login") != 26){
                    //alert("Tiempo de sesión expirado "+err.status)
                    
                    this.authenticationService.logout();
                    //location.reload();
                    //this.router.navigate(['/login'])
                }else{
                      console.log("Acceso Denegado2")
                      localStorage.setItem("msgerror", "Usuario/Clave incorrectos");
                 //   alert("Acceso denegado") 
                }
               
            }
                 
            const error = err.message || err.statusText;
            return throwError(error);
        }))
    }

    handleError(error: HttpErrorResponse){        
        return throwError(error);
       }
}