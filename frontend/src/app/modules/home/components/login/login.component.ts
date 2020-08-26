import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  token:string;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.login('user','user').subscribe((data:any)=>{this.token=data.access_token;});

  }

}
