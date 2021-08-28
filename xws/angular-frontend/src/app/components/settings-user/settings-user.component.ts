import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { UserInformation } from 'src/app/models/userInformation';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-settings-user',
  templateUrl: './settings-user.component.html',
  styleUrls: ['./settings-user.component.css']
})
export class SettingsUserComponent implements OnInit {

  SettingsForm: FormGroup;

  loggedInUser : UserInformation = new UserInformation('','','','','','','','','',false,false,false,false);

  newUser : UserInformation;

  constructor(private formBuilder : FormBuilder,
              private toastr: ToastrService,
              private userService : UserService,
              private router: Router) { }

  ngOnInit(): void {

    this.SettingsForm = this.formBuilder.group({
      accountName : new FormControl(null, [Validators.required]),
      firstName : new FormControl(null, [Validators.required]),
      lastName : new FormControl(null, [Validators.required]),
      biography : new FormControl(null, []),
      phoneNumber  : new FormControl(null, [Validators.required]),
      dateOfBirth : new FormControl(null, [Validators.required]),
      gender : new FormControl(null, [Validators.required]),
      website : new FormControl(null, []),
      userIsPublic : new FormControl(null, [Validators.required]),
      canReceiveMessages : new FormControl(null, [Validators.required]),
      canBeTagged : new FormControl(null, [Validators.required]),
      canReceiveNotifications : new FormControl(null, [Validators.required])
    });

    this.userService.getMyInfo().subscribe(
      (responseData: UserInformation) => {
        this.loggedInUser = responseData;
        this.SettingsForm.patchValue(
          {
            'accountName' : this.loggedInUser.accountName,
            'firstName' : this.loggedInUser.firstName,
            'lastName' : this.loggedInUser.lastName,
            'biography' : this.loggedInUser.biography,
            'phoneNumber' : this.loggedInUser.phoneNumber,
            'dateOfBirth' : this.loggedInUser.dateOfBirth,
            'gender' : this.loggedInUser.gender,
            'website' : this.loggedInUser.website,
            'userIsPublic' : this.loggedInUser.userIsPublic,
            'canReceiveMessages' : this.loggedInUser.canReceiveMessages,
            'canBeTagged' : this.loggedInUser.canBeTagged,
            'canReceiveNotifications' : this.loggedInUser.canReceiveNotifications,
          }
        );
      },
      () => {
        this.userService.logout();
      }
    );
  }

  saveChanges() {
    

    if(this.SettingsForm.invalid){
      return;
    }

    this.newUser = new UserInformation(this.loggedInUser.username,
                                  this.SettingsForm.value.accountName,
                                  this.SettingsForm.value.firstName,
                                  this.SettingsForm.value.lastName,
                                  this.SettingsForm.value.biography,
                                  this.SettingsForm.value.phoneNumber,
                                  this.SettingsForm.value.dateOfBirth,
                                  this.SettingsForm.value.gender,
                                  this.SettingsForm.value.website,
                                  this.SettingsForm.value.userIsPublic,
                                  this.SettingsForm.value.canReceiveMessages,
                                  this.SettingsForm.value.canBeTagged,
                                  this.SettingsForm.value.canReceiveNotifications
                       
                       )

    this.userService.changeInfo(this.newUser).subscribe(
      () => {
        this.toastr.success("Uspešno Ste promenili podatke");
        this.router.navigate(['settings/user']);
      },
      (error) => {
        this.toastr.error("Došlo je do greške...");
      }
    )

  }

}
