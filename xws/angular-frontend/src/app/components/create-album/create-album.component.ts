import { Component, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AlbumDTO } from 'src/app/DTOs/albumDTO';
import { ContentService } from 'src/app/services/content.service';

@Component({
  selector: 'app-create-album',
  templateUrl: './create-album.component.html',
  styleUrls: ['./create-album.component.css']
})
export class CreateAlbumComponent implements OnInit {

  public publishAs;

  public closeFriends;
  public close : boolean;

  public description : string = "";
  public location : string = "";
  public tag : string;
  public dataSource: string[] = [];
  displayedColumns: string[] = ['tag'];
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";
  public postSelected : boolean = true;
  selectedFiles : File[] = [];


  constructor(private contentService : ContentService, 
              private toastr : ToastrService, 
              private router : Router) { 
                      this.closeFriends = 0;
                      this.publishAs = 0;
            }

  

  ngOnInit(): void {
    this.postSelected = true;
    console.log('publish as:' + this.publishAs);
    console.log('close:' + this.closeFriends);
  }

  
  submit(){
    const fd = new FormData();
    if(this.selectedFiles){
      for(var i = 0; i< this.selectedFiles.length; i++) {
        fd.append('file'+i, this.selectedFiles[i], this.selectedFiles[i].name);
        console.log('NAMES:'+ this.selectedFiles[i].name);
      }
      this.close = this.closeFriends == 0 ? true : false;
      var album =  new AlbumDTO(this.postSelected, this.close, this.dataSource,this.location, this.description);
      console.log('ALBUM:' + JSON.stringify(album));
      fd.append('album', JSON.stringify(album));

      this.contentService.postAlbum(fd).subscribe(
         (data) => {
          this.toastr.success("Album je kreiran");
      },
      error => {
       this.toastr.error("Došlo je do greške");
      }
      
      );
    }
  }
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.selectedFiles = event.target.files;
    this.selectedFileHide = false;
    this.fileName = "Data selected";
    this.fileExtension = this.selectedFile.name.split('?')[0].split('.').pop();
  }

  OnClick(){
    if(this.tag == ""){
      this.toastr.info("Popunite polja...");
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


  radioChange($event: MatRadioChange) {
    console.log($event.source.name, $event.value);
    if ($event.value == 0) {
      this.postSelected = true;
    }else {
      this.postSelected = false;
    }
  }
}
