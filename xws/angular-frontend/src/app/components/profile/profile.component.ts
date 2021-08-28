import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CollectionInfoDTO } from 'src/app/models/collectionInfoDTO';
import { Post } from 'src/app/models/post';
import { ProfileInfo } from 'src/app/models/profileInfo';
import { Story } from 'src/app/models/story';
import { ContentService } from 'src/app/services/content.service';
import { ProfileService } from 'src/app/services/profile.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';
import { CloseFriendsComponent } from '../close-friends/close-friends.component';
import { CollectionDialogComponent } from '../collection-dialog/collection-dialog.component';
import { FollowerRequestDialogComponent } from '../follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from '../followers-dialog/followers-dialog.component';
import { RatingsDialogComponent } from '../ratings-dialog/ratings-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  constructor(  private route : ActivatedRoute,
                private router : Router, 
                private matDialog : MatDialog, 
                private profileService : ProfileService, 
                private contentService : ContentService, 
                private toastr : ToastrService, 
                public userService : UserService
              ) { }

  profile : ProfileInfo = new ProfileInfo(0, '', '', '', new Date(1998, 11, 29), '', '', '', 0, 0, false, false, false,false);
  posts : Post[] = [];
  favourites : Post[] = [];
  stories : Story[] = [];
  allStories : Story[] = [];
  storyHighlights : Story[] = [];
  collections : CollectionInfoDTO[] = [];
  collections1 = [];


  // uzima AccountName iz url-a i salje zahtev na server za profil
  ngOnInit(): void {
    this.route.paramMap.subscribe(
      params => {const username = params.get('username');
                 this.getProfileInfo(username);
      }
    )
  }

  getProfileInfo(username : String) {
    this.profileService.getProfileInfo(username).subscribe(
      data => {
        this.profile = data;
        if(this.profile == null) {
          this.router.navigate(['../feed']);
        }
        if(this.profile.owned){
          this.contentService.getFavouritesForUser().subscribe(
            data => { this.favourites = data; this.constructSliderObjectsForFav(); },
            error => console.log(error.error.message)
          );
          this.contentService.getStoriesForUser().subscribe(
            data => { this.allStories = data; this.constructSliderObjectsForAllStories(); },
            error => console.log(error.error.message)
          );
          this.contentService.getCollections().subscribe(
            data => { this.collections = data; this.constructSliderObjectsForCollections(this.collections); },
            error => console.log(error.error.message)
          );
        }
        if(this.profile.owned || this.profile.following || !this.profile.privateProfile) {

          this.contentService.getPostsByUser(this.profile.username).subscribe(
            data => { this.posts = data; this.constructSliderObjectsForPosts(); },
            error => { this.toastr.error("Greška");
                      }
          )
          this.contentService.getStoriesByUser(this.profile.username).subscribe(
            data => { this.stories = data; this.constructSliderObjectsForStories(); },
            error => {this.toastr.error("Greška");}
          );

          this.contentService.getHighlights().subscribe(
            data => { this.storyHighlights = data; this.constructSliderObjectsForHighlights(); },
            error => {this.toastr.error("Greška");}
          );
        }
      }
    );
  }

  constructSliderObjectsForPosts() {
    for(const post of this.posts) {
      const storyObject = new Array<Object>();
      for(const url of post.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: environment.baseUrlContent + url + "/", thumbImage: environment.baseUrlContent + url + "/"});
        } else {
          storyObject.push({video: environment.baseUrlContent + url + '/', alt: 'video unavailable'});
        }
      }
      post['slider'] = storyObject;
    }
  }

  constructSliderObjectsForCollections(collections : CollectionInfoDTO[]){
    this.collections1 = [];
    const names : String[] = Array.from(new Set(collections.map(c => c.name)));
    for(const name of names) {
      const nameCollections = collections.filter(c => c.name === name)
      const storyObject = new Array<Object>();
      for(const collection of nameCollections) {
        for(const url of collection.urls){
          if(url.endsWith('.jpg') || url.endsWith('.png')) {
            storyObject.push( {image: environment.baseUrlContent + url + '/', thumbImage: environment.baseUrlContent + url + "/", title: collection.name});
          } else {
            storyObject.push({video: environment.baseUrlContent + url + '/', alt: 'video unavailable', title: collection.name});
          }
        }
      }
      this.collections1.push(storyObject);
    }
  }

  constructSliderObjectsForFav() {
    for(const fav of this.favourites) {
      const storyObject = new Array<Object>();
      for(const url of fav.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: environment.baseUrlContent + url + '/', thumbImage: environment.baseUrlContent + url + "/"});
        } else {
          storyObject.push({video: environment.baseUrlContent + url + '/', alt: 'video unavailable'});
        }
      }
      fav['slider'] = storyObject;
    }
  }

  constructSliderObjectsForStories() {
    const storyObject = new Array<Object>();
    for(const story of this.stories) {
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.baseUrlContent + story.url + '/', thumbImage: environment.baseUrlContent + story.url + "/"});
      } else {
        storyObject.push({video: environment.baseUrlContent + story.url + '/', alt: 'video unavailable'});
      }
    }
    this.stories['slider'] = storyObject;
  }

  constructSliderObjectsForHighlights() {
    const storyObject = new Array<Object>();
    for(const story of this.storyHighlights) {
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.baseUrlContent + story.url + '/', thumbImage: environment.baseUrlContent + story.url + "/"});
      } else {
        storyObject.push({video: environment.baseUrlContent + story.url + '/', alt: 'video unavailable'});
      }
    }
    this.storyHighlights['slider'] = storyObject;
  }

  constructSliderObjectsForAllStories() {
    for(const story of this.allStories) {
      const storyObject = new Array<Object>();
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.baseUrlContent + story.url + '/', thumbImage: environment.baseUrlContent + story.url + "/"});
      } else {
        storyObject.push({video: environment.baseUrlContent + story.url + '/', alt: 'video unavailable'});
      }
      story['slider'] = storyObject;
    }
  }

  follow(privateProfile : boolean, profileUsername : String){
    if(!privateProfile){
      this.profile.following = true;
      this.profileService.followProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
    }else{
      this.toastr.success("Uspešno zapraćivanje");
      this.profileService.followProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
    }
  }
  unfollow(profileUsername : String){
    this.profile.following = false;
    this.profileService.unfollowProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
  }

  block(username: String): void {
    const myUsername = this.userService.getLoggedInUser().username;
    this.profileService.changeBlocked(username, myUsername)
      .subscribe(data => this.router.navigate(['profile/' + myUsername]));
  }

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }


  followRequests(){
    this.profileService.getFollowRequests(this.profile.username).subscribe(data =>{
      this.matDialog.open(FollowerRequestDialogComponent, {data : data});
    });
  }

  getFollowers(){
    this.profileService.getFollowers(this.profile.username).subscribe(data =>{
      console.log(data);
      this.matDialog.open(FollowersDialogComponent, {data : data});
    });
  }
  getFollowing(){
    this.profileService.getFollowing(this.profile.username).subscribe(data =>{
      this.matDialog.open(FollowersDialogComponent, {data : data});
    });
  }

  closeFriends(){
    this.profileService.getFollowers(this.profile.username).subscribe(data =>{
      console.log(data);
      this.matDialog.open(CloseFriendsComponent, {data : data,  width: '70vw',
      maxWidth: '70vw'});
    });
  }

  ratings(){
    this.matDialog.open(RatingsDialogComponent, { width: '30vw',
      maxWidth: '30vw'});
  }

  addToHighlights(story : Story){
    this.contentService.saveToHighlights(story).subscribe(data => {
    this.contentService.getHighlights().subscribe(
      data => { this.storyHighlights = data; this.constructSliderObjectsForHighlights(); },
      error => {this.toastr.error("Greška");}
    );
  });

  }

  addToCollection(post : Post){
    this.matDialog.open(CollectionDialogComponent, {data : post.id,  width: '30vw',
      maxWidth: '30vw'}).afterClosed().subscribe(
        _ => this.contentService.getFavouritesForUser().subscribe(
          data => { this.favourites = data; this.constructSliderObjectsForFav();
          this.contentService.getCollections().subscribe(_data => {
            this.collections = _data;
            this.constructSliderObjectsForCollections(_data);
          }) },
          error => console.log(error.error.message)
        )
      );
  }

  public includes(id : Number) : boolean {
    return this.storyHighlights.filter(h => h.id === id).length > 0;
  }

//  shareStories = () => {
//    this.matDialog.open(ShareDialogComponent, { width: '30vw', maxWidth: '30vw'});
//  }



}
