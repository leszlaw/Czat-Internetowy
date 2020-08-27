import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../auth/services/auth.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoginError : boolean = false;

  constructor(private authService: AuthService,private router : Router) { }

  ngOnInit(): void {}

  onSubmit(ngForm: NgForm): void{
    this.authService.login(ngForm.value.Username,ngForm.value.Password).subscribe(
    (data:any)=>{localStorage.setItem('token',data.access_token);
                       this.router.navigate(['/chat']);},
    (err : HttpErrorResponse)=>{this.isLoginError = true;});
  }

}
