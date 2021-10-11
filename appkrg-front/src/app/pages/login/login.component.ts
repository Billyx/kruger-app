import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  loading = false;
  submitted = false;
  returnUrl?: string;
  error = '';
  errormsg?:string;
  
  constructor(private authenticationService: AuthService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router) {
      if (this.authenticationService.currentUserValue) { 
        this.router.navigate(['home']);
      }
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  get f() { return this.loginForm!.controls }

  login(){
    this.submitted = true;
    console.log("login ...!")
    console.log(this.loginForm.valid);
    // stop here if form is invalid
    if (this.loginForm!.invalid) {
      return;
    }
     
    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
        .pipe(first())
        .subscribe(
            data => {
                console.log(data, "ddddddddd")
                this.router.navigate(['home']);
            },
            error => {
                console.log(error," zzzzzz") 
                this.loading = false;
            });    
  }

  logout(){
    this.authenticationService.logout()
  }

}
