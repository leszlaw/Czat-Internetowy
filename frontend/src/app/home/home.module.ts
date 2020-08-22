import { ModuleWithProviders, NgModule } from '@angular/core';

import { HomeComponent } from './home.component';
import { AboutComponent } from './about/about.component';
import { HomeRoutingModule } from './home-routing.module';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

@NgModule({
  imports: [
    HomeRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule
  ],
  declarations: [
    HomeComponent,
    AboutComponent,
    LoginComponent,
    SignupComponent
  ]
})
export class HomeModule {}
