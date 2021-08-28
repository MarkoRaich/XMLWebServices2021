import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { UserService } from './services/user.service';
import { LoggedInUser } from './models/loggedInUser';
import { SearchResult } from './models/searchResult';
import { ContentService } from './services/content.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router,
              private breakpointObserver: BreakpointObserver,
              private userService: UserService,
              private contentService: ContentService,
              private toastr : ToastrService
             ) { }

  title = 'Ništagram';
  user: LoggedInUser;
  accountName: String;

  query: String = '';
  options: SearchResult[] = [];


 //radi otvaranje i zatvaranje side navigation u zavisnosti od velicine prozora itd...
 isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
 .pipe(
   map(result => result.matches),
   shareReplay()
 );


 ngOnInit() {
  this.router.events.subscribe((event) => {
    if (event instanceof NavigationEnd) {
      if (this.isLoggedIn()) {
        this.user = this.userService.getLoggedInUser();
      }
    }
  });
}

isLoggedIn() {
  return this.userService.isLoggedIn();
}

onLogout() {
  this.userService.logout();
  this.router.navigate(['/login']);
}


isAdmin(){
  return this.userService.isAdmin();
}

isUser(){
  return this.userService.isUser();
}


public updated() {
  this.options = [];
  if (this.query.length > 0) {
    this.contentService.getSearchResults(this.query).subscribe(
      data => { this.options = data; this.assignIds(); }
    );
  }
}

assignIds() {
  let i = 0;
  for(let option of this.options) {
    option['id'] = i++;
  }
}

public seeDetails(id) {
  const res : SearchResult = this.options.filter(o => o['id'] === id)[0];
  if(res.type == 'profile') {
    this.router.navigate(['../profile/' + res.name]);
  } else if(res.type == 'location' || res.type == 'hashtag') {
    this.router.navigate(['../post'], { queryParams: { type: res.type, value: res.name }});
  }
}

public goToProfile() {
    var username = this.userService.getLoggedInUser().username;
    this.userService.getAccountNameFromUsername(username).subscribe(
      ( data : any ) => { this.accountName = data;
                           
                            this.router.navigate(['../profile/' + this.accountName]);
      },
      (error) =>{
        this.toastr.error("Došlo je do greške...");
      }

    );
    

}

}
