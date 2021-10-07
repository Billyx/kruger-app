import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
    
  },
  { path: 'login',component: LoginComponent},
  { path: '',component: HomeComponent, canActivate: [AuthGuard],
        children: [] },
  { path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash:true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }