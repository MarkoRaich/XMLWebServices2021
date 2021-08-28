import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Notification } from 'src/app/models/notification';
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class NotificationService {
  
    constructor(private http : HttpClient) { }
  
  
    public getAll() : Observable<Notification[]> {
      return this.http.get<Notification[]>(environment.baseUrlNotification + 'notification');
    }
  }