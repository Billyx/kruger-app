import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UtilService } from './util/util.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url!:string;
  constructor(private http: HttpClient, private utilService:UtilService) {
    this.url = utilService.getUrl();
 }

  public getAllUsersPaginate(search: string,page: any,qrecords: any):Observable<any>{
        
    let params = new HttpParams().set("search",search)
                                .set("page", page)
                                .set("qrecords",qrecords)
    return this.http.get(this.url+"/getAllUsersPaginate",{params})
  }

  public getAllUsersByFilters(arg1:string,arg2:string,arg3:string,arg4:string,arg5:string,arg6:string):Observable<any>{
    let params = new HttpParams().set("evac",arg1)
                                .set("tvac",arg2)
                                .set("dateini",arg3)
                                .set("datefin",arg4)
                                .set("page", arg5)
                                .set("qrecords",arg6)
    return this.http.get(this.url+"/getEmployeesByFilters",{params})
  }

}
