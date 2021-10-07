import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    constructor(private router: Router,
                private auth: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        
        const currentUser = this.auth.currentUserValue
        console.log(currentUser);
        const refToken = this.auth.getTokenRefresh();
        const helper = new JwtHelperService();
        const isExpired = helper.isTokenExpired(refToken);
        
        if(currentUser ){    
            return true;
        }

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login']); // , { queryParams: { returnUrl: state.url }}
        return false;
    }
}