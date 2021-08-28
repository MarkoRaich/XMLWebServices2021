import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ProfileVerificationRequest } from 'src/app/DTOs/profileVerificationRequest';
import { ProfileService } from 'src/app/services/profile.service';

interface Category{
  value : String;
}

@Component({
  selector: 'app-verify-account',
  templateUrl: './verify-account.component.html',
  styleUrls: ['./verify-account.component.css']
})
export class VerifyAccountComponent implements OnInit {


  constructor(private profileService : ProfileService,
              private toastr : ToastrService) { }

  name: String;
  lastname : String;
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";
  selectedValue  = "";

  categories: Category[] = [
    {value: 'Influencer'},
    {value: 'Sports'},
    {value: 'News/Media'},
    {value: 'Business'},
    {value: 'Brand'},
    {value: 'Organisation'}
  ];
  ngOnInit(): void {
  }

  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);
      var request =  new ProfileVerificationRequest(0,this.name, this.lastname, this.selectedValue, "");
      fd.append('request', JSON.stringify(request));

      this.profileService.sendProfileVerifactionRequest(fd).subscribe(
         data => {
          let message = data;
          this.toastr.success("OK");
      },
      error => {
        this.toastr.error("Došlo je do greške...");
      }
      
      );
    }else{
      this.toastr.info("Morate odabrati fajl.");
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
