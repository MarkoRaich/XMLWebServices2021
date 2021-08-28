import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ContentService } from 'src/app/services/content.service';

@Component({
  selector: 'app-create-story',
  templateUrl: './create-story.component.html',
  styleUrls: ['./create-story.component.css']
})
export class CreateStoryComponent implements OnInit {

  public closeFriends;
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";
  public close : string;

  constructor(private contentService : ContentService, 
              private toastr : ToastrService,
              private router : Router) { 
    this.closeFriends = 0;
  }

  ngOnInit(): void {}


  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);

      this.close = this.closeFriends == 0 ? "true" : "false";
      fd.append('story', this.close);

      this.contentService.postStory(fd).subscribe(
         (data) => {
          this.toastr.success("Stori je postavljen");
      },
      error => {
       this.toastr.error("Došlo je do greške...");
      }
      
      );
    }
  }
  
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.selectedFileHide = false;
    this.fileName = "Data selected";
    this.fileExtension = this.selectedFile.name.split('?')[0].split('.').pop();
    console.log(this.selectedFile.name);
  }
 
  
}
