import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  url = 'http://localhost:8080/messages'

  constructor(private http: HttpClient) {}

  getMessages(): Observable<any> {

    return this.http.get(this.url);

  }

  sendMessage(username:string,msg:string): void{
    this.http.post(this.url,{message: msg ,receiverUsername: username }).toPromise().then(data=>{});
  }

}
