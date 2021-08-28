import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { FollowerDTO } from "../DTOs/followerDTO";
import { FollowRequestDTO } from "../DTOs/followRequestDTO";
import { ProfileVerificationRequest } from "../DTOs/profileVerificationRequest";
import { ProfileInfo } from "../models/profileInfo";

@Injectable({ providedIn: 'root' })
export class ProfileService {
    
    constructor(private http : HttpClient) {}


    getProfileInfo(username: String): Observable<ProfileInfo> {
        return this.http.get<ProfileInfo>(environment.baseUrlUser + 'user/getInfo/' + username);
    }
  
    public followProfile(username : String){
        return this.http.get(environment.baseUrlUser + 'user/follow/' + username, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
    }
    
      public unfollowProfile(username : String) {
        return this.http.get(environment.baseUrlUser + "user/unfollow/"+ username, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
    }


    //___________________________________________________________________________


    public getFollowRequests(username : String) : Observable<FollowRequestDTO[]> {
        return this.http.get<FollowRequestDTO[]>(environment.baseUrlUser + "user/followRequest/"+ username, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public acceptRequest(username : String) : Observable<FollowRequestDTO[]> {
        return this.http.get<FollowRequestDTO[]>(environment.baseUrlUser + "user/acceptRequest/"+ username, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public deleteRequest(username : String) : Observable<FollowRequestDTO[]> {
        return this.http.get<FollowRequestDTO[]>(environment.baseUrlUser + "user/deleteRequest/"+ username, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public getFollowers(username : String){
        return this.http.get<FollowerDTO[]>(environment.baseUrlUser + "user/followers/"+ username, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public getFollowing(username : String){
        return this.http.get<FollowerDTO[]>(environment.baseUrlUser + "user/following/"+ username, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
      public addCloseFriend(data : string) {
        return this.http.post(environment.baseUrlUser + "user/addCloseFriend", data, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
      public removeCloseFriend(data : string) {
        return this.http.post(environment.baseUrlUser + "user/removeCloseFriend", data, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
      public getCloseFriends() : Observable<String[]> {
        return this.http.get<String[]>(environment.baseUrlUser + "user/getCloseFriends", {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
      public sendProfileVerifactionRequest(fd : FormData){
          return this.http.post(environment.baseUrlUser + "user/verify-profile",fd, {responseType: 'text',headers : {
            Authorization: 'Bearer ' + localStorage.getItem('jwt')
          }});
    
      }
    
      public getVerificationRequests() : Observable<ProfileVerificationRequest[]> {
        return this.http.get<ProfileVerificationRequest[]>(environment.baseUrlUser + "user/verification-requests", {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public acceptVerificationRequest(id : Number) : Observable<ProfileVerificationRequest[]> {
        return this.http.get<ProfileVerificationRequest[]>(environment.baseUrlUser + "user/verify/"+ id, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }
    
      public deleteVerificationRequest(id : Number) : Observable<ProfileVerificationRequest[]> {
        return this.http.get<ProfileVerificationRequest[]>(environment.baseUrlUser + "user/delete-ver-request/"+ id, {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }



    //MUTE I BLOCK
    public changeBlocked(blocked: String, blockedBy: String): Observable<any> {
        return this.http.get(environment.baseUrlUser + 'interaction/block-unblock/' + blocked + '/' + blockedBy);
    }

    public changeMuted(muted: string, mutedBy: string): Observable<any> {
        return this.http.get(environment.baseUrlUser + 'interaction/mute-unmute/' + muted + '/' + mutedBy);
    }

    public getBlocked(username: string): Observable<string[]> {
        return this.http.get<string[]>(environment.baseUrlUser + 'interaction/blocked/' + username);
    }

    public getMuted(username: string): Observable<string[]> {
        return this.http.get<string[]>(environment.baseUrlUser + 'interaction/muted/' + username);
    }

    
}