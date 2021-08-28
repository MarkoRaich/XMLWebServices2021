import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Admin } from "../models/admin";
import { User } from "../models/user";
import { LoggedInUser } from "../models/loggedInUser";
import { UserLoginRequest } from "../models/userLoginRequest";
import { UserTokenState } from "../models/userTokenState";
import { UserInformation } from "../models/userInformation";

@Injectable({ providedIn: 'root' })
export class UserService {
   
   
    access_token = null;
    req: UserTokenState;
    loggedInUserSubject: BehaviorSubject<LoggedInUser>;
    loggedInUser: Observable<LoggedInUser>;
    loggedInSuccess: BehaviorSubject<LoggedInUser> = new BehaviorSubject<LoggedInUser>(null);

    question: BehaviorSubject<String> = new BehaviorSubject<String>("");

    constructor(private http: HttpClient, private router: Router) {
        this.loggedInUserSubject = new BehaviorSubject<LoggedInUser>(JSON.parse(localStorage.getItem('LoggedInUser')));
        this.loggedInUser = this.loggedInUserSubject.asObservable();
    }

  
    getLoggedInUser(): LoggedInUser {
      return this.loggedInUserSubject.value;
    }

    login(user: UserLoginRequest) {
        return this.http.post(environment.baseUrlUser + environment.login, user).pipe(map((res: LoggedInUser) => {
          this.access_token = res.userTokenState.jwtAccessToken;
          localStorage.setItem('LoggedInUser', JSON.stringify(res));
          localStorage.setItem('jwt', this.access_token );
          this.loggedInUserSubject.next(res);
        }));
    }

    getMyInfo() {
      return this.http.get(environment.baseUrlUser + environment.myInfo);
    }

    changeInfo(loggedInUser: UserInformation) {
      return this.http.post<User>(environment.baseUrlUser + environment.changeInfo, loggedInUser)
    }

    registerUser(user: User) {
      return this.http.post<User>(environment.baseUrlUser + environment.registerUser, user)
    }

    registerAdmin(admin: Admin) {
      return this.http.post<Admin>(environment.baseUrlUser + environment.registerAdmin, admin)
    }

    getAccountNameFromUsername(username: String):  Observable<any> {
      var headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
      return this.http.post<any>(environment.baseUrlUser + environment.getAccountName, {"username" : username},{ headers, responseType: 'text' as 'json' } );
    }


    logout() {
        this.access_token = null;
        localStorage.removeItem('LoggedInUser');
        localStorage.removeItem('jwt');
        this.router.navigate(['']);
    }

  

    getToken(){
      return this.access_token;
    }
  
    tokenIsPresent() {
      return this.access_token != undefined && this.access_token != null;
    }


    isLoggedIn() {
        return localStorage.getItem('LoggedInUser') !== null;
    }


    isAdmin() {
        if (this.isLoggedIn()) {
          return this.loggedInUserSubject.value.role === "ADMIN";
        }
    }

    isUser(){
        if (this.isLoggedIn()) {
          return this.loggedInUserSubject.value.role === "USER";
        }
    }

  

}