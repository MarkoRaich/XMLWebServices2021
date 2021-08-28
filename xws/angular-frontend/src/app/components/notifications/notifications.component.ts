import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Notification } from 'src/app/models/notification';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  constructor(private notificationService : NotificationService,
              private router : Router) { }

  notifications : Notification[] = []

  ngOnInit(): void {
    this.notificationService.getAll().subscribe(
      data => this.notifications = data
    )
  }

  public seeDetails(notification : Notification) {
    const postTypes = ['COMMENT', 'RATING', 'POST']
    const profileTypes = ['NEW_FOLLOW', 'NEW_FOLLOW_REQUEST', 'FOLLOW_REQUEST_ACCEPTED', 'STORY']
    if(postTypes.includes(notification.type)) {
      this.router.navigate(['./post/' + notification.resource])
    } else if(profileTypes.includes(notification.type)) {
      this.router.navigate(['./profile/' + notification.resource])
    }
  }
}
