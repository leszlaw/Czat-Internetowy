import { HttpInterceptor, HttpRequest, HttpHandler, HttpUserEvent, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (localStorage.getItem('token') == null
        || request.headers.get('Authorization') != null)
          return next.handle(request.clone());

        request = request.clone({
                headers: request.headers.set("Authorization", "Bearer " + localStorage.getItem('token'))
        });

        return next.handle(request).pipe(
          catchError(
            (err, caught) => {
              if (err.status === 401){
                this.handleAuthError();
                return of(err);
              }
              throw err;
            }
          )
        );
      }

    private handleAuthError() {
        localStorage.removeItem('token');
        this.router.navigateByUrl('login');
    }
}
