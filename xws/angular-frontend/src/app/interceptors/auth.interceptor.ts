import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from '@angular/common/http';

import { UserService } from '../services/user.service';
import { Observable } from 'rxjs';
import { LoggedInUser } from '../models/loggedInUser';
import { UserTokenState } from '../models/userTokenState';

//PRESRECE SVAKI ZAHTEV KOJI SE SALJE NA SERVER I DODAJE MU TOKEN U HEADER!
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    loggedInUser: LoggedInUser;
    userTokenState: UserTokenState;
    
    constructor(public userService: UserService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        this.loggedInUser = JSON.parse(localStorage.getItem("LoggedInUser"));

        const token = null;

        if (this.loggedInUser) {
            this.userTokenState = this.loggedInUser.userTokenState;
            if (this.userTokenState) {
                if (this.userTokenState.jwtAccessToken) {
                    request = request.clone({
                        setHeaders: {
                            Authorization: `Bearer ${this.userTokenState.jwtAccessToken}`
                        }
                    });
                }
            } 
        } else {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }

        return next.handle(request);
    }

  }