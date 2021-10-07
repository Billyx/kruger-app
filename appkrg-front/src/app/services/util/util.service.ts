import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  url:string  = environment.apiUrl;
  
  constructor(private http: HttpClient, private router: Router) { }

  public getUrl(): string {
    return this.url;
  }
}
