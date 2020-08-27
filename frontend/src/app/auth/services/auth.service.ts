import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080/oauth';

  constructor(private http: HttpClient) { }

  login(username, password) {
    var data = "username=" + username + "&password=" + password + "&grant_type=password";
    var reqHeader = new HttpHeaders({ 'Content-Type': 'application/x-www-urlencoded','Authorization': 'Basic YWRtaW46YWRtaW4='});
    return this.http.post(this.url+'/token?'+data, null, { headers: reqHeader });
  }

}
