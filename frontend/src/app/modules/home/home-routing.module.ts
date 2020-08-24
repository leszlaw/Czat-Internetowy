import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { AboutComponent } from './components/about/about.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  { path: '',
    component: HomeComponent,
    children: [
          {
            path: '',
            component: AboutComponent
          },
          {
            path: 'login',
            component: LoginComponent
          },
          {
            path: 'signup',
            component: SignupComponent
          }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {}
