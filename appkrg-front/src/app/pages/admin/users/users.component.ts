import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { UtilService } from 'src/app/services/util/util.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  public users:any;
  searchString:string = "";

  // Pagination attributes
  page:number = 0;
  showpage:number = this.page + 1;
  qrecords:number = 10;
  totalRecords!:number;
  start:number = 1;
  end:number = this.qrecords;
  totalPages!:number;

  // Form Attreibutes
  nuform!: FormGroup;
  uform!: FormGroup;
  duform!:FormGroup;
  nuformSubmited:boolean = false;
  uformSubmited:boolean = false;
  spinner:boolean = true;
  usernameItsOk:boolean = true;
  loading:boolean = false;
  loadingnu:boolean = false;
  userCreated:boolean = false;
  userEdited:boolean = false;
  userId?:number
  userDelete?:boolean
  userInfo:any
  nuFormInitialized:boolean = false;
  selectedUser:boolean = false;

  // Search attributes
  evac:string = "";
  tvac:string = "";
  fvacini:string = "";
  fvacfin:string = "";
  phone:string = "";
  email:string = "";
  name:string = "";
  lastname:string = "";
  mlastname:string = "";
  idcard:string = "";

  private phonePattern = Validators.pattern("[0-9 ()/SNsn-]*");
  private emailPattern = Validators.pattern("[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*");
  private characterPattern = Validators.pattern("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*");
  private passwordPattern = Validators.pattern("[a-zA-Z0-9]*");
  private numberPattern = Validators.pattern("[0-9]*");

  constructor(private router: Router,
    private userService: UserService,
    private utilService:UtilService,
    private auth : AuthService) { }

  ngOnInit(): void {

    this.listUsersByFilters();
    this.uform =new FormGroup({
      'name':  new FormControl('',this.characterPattern),
      'lastname':  new FormControl('',this.phonePattern),
      'mlastname':  new FormControl('',this.characterPattern),
      'email':  new FormControl('',this.emailPattern),
      'idcard': new FormControl('',[Validators.min(10),Validators.max(10),this.numberPattern,Validators.required]),
      'idPerfil': new FormControl('',[Validators.required,this.numberPattern,Validators.min(2)]),
    });

    this.duform = new FormGroup({
      'user': new FormControl('')
    })
      
  }

  public initNuForm(){
    this.nuFormInitialized = true
    this.nuformSubmited = false
    this.userCreated = false

    this.nuform = new FormGroup({
      'name':  new FormControl('',this.characterPattern),
      'lastname':  new FormControl('',this.phonePattern),
      'mlastname':  new FormControl('',this.characterPattern),
      'email':  new FormControl('',this.emailPattern),
      'idcard': new FormControl('',[Validators.min(10),Validators.max(10),this.numberPattern,Validators.required]),
      'idPerfil': new FormControl('',[Validators.required,this.numberPattern,Validators.min(2)])
    })
  }
  listUsersByFilters(){

          this.userService.getAllUsersByFilters(this.evac,
                                                this.tvac,
                                                this.fvacini,
                                                this.fvacfin,
                                                this.page.toString(),
                                                this.qrecords.toString()).subscribe(data => {
                                                      this.users = data['content'];
                                                      console.log(this.users);
                                                      this.totalRecords = data['total'];
                                                      this.totalPages = Math.ceil(this.totalRecords/this.qrecords);
                                                      this.start = data.pageable.size * this.page + 1;
                                                      let endaux =  ( data.pageable.size * this.page + 1 ) + data.pageable.size - 1;
                                                      if(endaux >= this.totalRecords)
                                                        this.end = this.totalRecords;
                                                      else
                                                        this.end = endaux;
                                                      this.spinner = false;
                                                })
  }

  newUser(){

  }

  updateUser(){
  
  }

  setUserToDelete(userdata:any){
    this.duform.get('user')?.setValue(userdata.id)
    this.userInfo = userdata
    this.userDelete = true
  }

  getUserData(userdata:any){
    this.uformSubmited = false;
    this.userEdited = false;
    this.selectedUser = userdata;
    this.uform.get('id')?.setValue(userdata.id);
    this.uform.get('name')?.setValue(userdata.name);
    this.uform.get('lastname')?.setValue(userdata.telefono);
    this.uform.get('mlastname')?.setValue(userdata.email);
    this.uform.get('email')?.setValue(userdata.username);
    this.uform.get('idcard')?.setValue(userdata.username);
    this.uform.get('idPerfil')?.setValue(userdata.profile.idPerfil);
  }

  /**
   * @see void Navigate to nextPage on table list 
   */

   public nextPage(){
                  
    if(this.page+1 < this.totalPages ){
      this.page++
      this.showpage++
      this.listUsersByFilters()
    }
             
}
/**
 * @see void Navigate to previous page on list table
 */
public previousPage(){
  
    if(this.page > 0){
      this.page--
      this.showpage--        
      this.listUsersByFilters()
    }
}
/**
 * @see void Navigate to last page table list
 */
public lastPage(){
    this.page = this.totalPages - 1
    this.showpage = this.page + 1
    this.listUsersByFilters()
}

/**
 * @see void Navigate to first page table list
 */
public firstPage(){
    this.page = 0
    this.showpage = 1
    this.listUsersByFilters()
}

/**
 * @see set qrecords
 */
public setRange(newValue:number){
  this.qrecords = newValue
  this.page = 0
  this.showpage = 1
  this.listUsersByFilters()
}

/**
 * @see void Search user
 * @param event searchstring
 */
public search(event:string){      
    this.page = 0
    this.showpage = 1
    this.listUsersByFilters()
}

public goToPage(page:string){
  
  if(page==null){
      page = '0';
  }
  let pattern = /^([0-9]*)$/
  if(pattern.test(page) && Number(page) <= this.totalPages && Number(page) > 0 ){
    this.page = Number(page) - 1
    this.listUsersByFilters()
  }
}

public numberOnly(event:any):boolean{

  var charCode = (event.which) ? event.which : event.keyCode
  if (charCode > 31 && (charCode < 48 || charCode > 57))
    return false;

  return true;
}

  


}
