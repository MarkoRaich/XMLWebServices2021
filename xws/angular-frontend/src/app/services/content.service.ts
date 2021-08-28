import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { CollectionInfoDTO } from "../models/collectionInfoDTO";
import { Post } from "../models/post";
import { SearchResult } from "../models/searchResult";
import { Story } from "../models/story";

@Injectable({ providedIn: 'root' })
export class ContentService {
    
    constructor(private http : HttpClient) {}



    getSearchResults(query: any) {
        return this.http.get<SearchResult[]>( environment.baseUrlContent + 'post/search/' + query );
    }


    getFavouritesForUser() : Observable<Post[]> {
        return this.http.get<Post[]>(environment.baseUrlContent + 'post/favourites');
    }
  
    public getStoriesForUser() : Observable<Story[]> {
        return this.http.get<Story[]>(environment.baseUrlContent + 'story/allStories');
    }

    public getCollections(): Observable<CollectionInfoDTO[]>{
        return this.http.get<CollectionInfoDTO[]>(environment.baseUrlContent + 'post/collections', {headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
    }

    public getPostsByUser(username : String) : Observable<Post[]> {
        return this.http.get<Post[]>(environment.baseUrlContent + 'post/profile/' + username);
    }
    
      public getStoriesByUser(username : String) : Observable<Story[]> {
        return this.http.get<Story[]>(environment.baseUrlContent + 'story/profile/' + username);
    }

    public getHighlights() : Observable<Story[]> {
        return this.http.get<Story[]>(environment.baseUrlContent + 'story/storyHighlights');
    }


    public saveToHighlights(story : Story){
        return this.http.post(environment.baseUrlContent + 'story/saveToHighlights', story,{responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }

      postData(data : FormData) {
        return this.http.post(environment.baseUrlContent + "media/createPost", data, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }

      postStory(data : FormData) {
        return this.http.post(environment.baseUrlContent + "media/createStory", data, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }

      postAlbum(data : FormData) : Observable<any> {
        return this.http.post(environment.baseUrlContent + "media/createAlbum", data, {responseType: 'text',headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }});
      }

      public getPostsBySearchCriteria(input : SearchResult) : Observable<Post[]> {
        return this.http.get<Post[]>(environment.baseUrlContent + 'post/' + input.type + '/' + input.name);
      }










}