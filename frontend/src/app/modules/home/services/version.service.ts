import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VersionService {

  url = 'http://localhost:8080/version'

  constructor(private http: HttpClient) {}

  getVersion(): Observable<string> {
    const requestOptions: Object = {
      responseType: 'text'
    }
    return this.http.get<string>(this.url,requestOptions);
  }

}
