import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models/post';
import { SearchResult } from 'src/app/models/searchResult';
import { ContentService } from 'src/app/services/content.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor(private route : ActivatedRoute,
              private contentService : ContentService,
              private router : Router) { }

  posts : Post[] = [];
  type : String = '';
  value : String = '';

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.type = params['type'];
      this.value = params['value'];
      const value = this.value.replace('#', '%23');
      this.contentService.getPostsBySearchCriteria(new SearchResult(value, this.type)).subscribe(
        data => { this.posts = data; this.constructSliderObjectsForPosts(); },
        error => console.log(error.error.message)
      );
    });
  }

  constructSliderObjectsForPosts() {
    for(const post of this.posts) {
      const storyObject = new Array<Object>();
      for(const url of post.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: environment.baseUrlContent + url + '/', thumbImage: environment.baseUrlContent + url + '/'});
        } else {
          storyObject.push({video: environment.baseUrlContent + url + '/', alt: 'video unavailable'});
        }
      }
      post['slider'] = storyObject;
    }
  }

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }
}
