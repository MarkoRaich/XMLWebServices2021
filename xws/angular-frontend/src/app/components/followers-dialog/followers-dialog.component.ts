import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FollowerDTO } from 'src/app/DTOs/followerDTO';
import { ContentService } from 'src/app/services/content.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-followers-dialog',
  templateUrl: './followers-dialog.component.html',
  styleUrls: ['./followers-dialog.component.css']
})
export class FollowersDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : FollowerDTO[], 
              private route : ActivatedRoute, 
              private router : Router,
              private profileService : ProfileService, 
              private contentService : ContentService, 
              private matDialog : MatDialog) { }

  displayedColumns: string[] = ['username'];
  dataSource = this.data;
  
  ngOnInit(): void {
   
  }
  
}
