import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PostDTO } from 'src/app/DTOs/postDTO';
import { ContentService } from 'src/app/services/content.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  public description : string;
  public location : string;
  public tag : string;
  public dataSource: string[] = [];
  displayedColumns: string[] = ['tag'];
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";

  constructor(private contentService : ContentService, 
              private toastr : ToastrService, 
              private router : Router) { }

  ngOnInit(): void {
  }

  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);
      var post =  new PostDTO(this.dataSource,this.location, this.description);
      fd.append('post', JSON.stringify(post));

      this.contentService.postData(fd).subscribe(
         (data) => {
          this.toastr.success("Post je kreiran");
      },
      error => {
        this.toastr.error("Došlo je do greške");
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


  OnClick(){
    if(this.tag == ""){
      this.toastr.info("Popunite polja..")
    }
    else {
      this.dataSource.push(this.tag);
    
      var dataSource2 = this.dataSource;
      this.dataSource= [];
      for (let i=0; i<dataSource2.length; i++){
        this.dataSource.push(dataSource2[i]);
      }
    }
  }

}
