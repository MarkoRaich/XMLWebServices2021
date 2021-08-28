import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  registerForm: FormGroup;

  user : User;

  submitted = false;

  constructor(   private toastr : ToastrService, 
                 private formBuilder : FormBuilder,
                 private userService: UserService,
                 private router: Router) { }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      email : new FormControl(null, [Validators.required, Validators.email]),
      password : new FormControl(null, [Validators.required ] ),
      repeatedPassword :  new FormControl(null, [Validators.required] ),
      accountName : new FormControl(null, [Validators.required]),
      firstName : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      lastName : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      biography : new FormControl(null, []),
      phoneNumber : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z0-9-]*')]),
      dateOfBirth : new FormControl(null, [Validators.required]),
      gender : new FormControl(null, [Validators.required])
    })


  }

   // convenience getter for easy access to form fields
   get f() { return this.registerForm.controls; }

   onSubmit() {

      this.submitted = true;

      if(this.registerForm.invalid){
        return;
      }

      if(this.registerForm.value.password != this.registerForm.value.repeatedPassword ){
        this.toastr.error("Lozinka i ponovljena lozinka se ne poklapaju!");
        return;
      }

      this.user = new User(this.registerForm.value.email,
                              this.registerForm.value.password,
                              this.registerForm.value.accountName,
                              this.registerForm.value.firstName,
                              this.registerForm.value.lastName,
                              this.registerForm.value.biography,
                              this.registerForm.value.phoneNumber,
                              this.registerForm.value.dateOfBirth,
                              this.registerForm.value.gender
                              )

      this.userService.registerUser(this.user).subscribe(
        () => {
          this.toastr.success("Uspešno Ste se registrovali");
          this.router.navigate(['login']);
        },
        (error) => {
          this.toastr.error("Došlo je do greške...");
        }
      )


   }

    onReset() {
        this.submitted = false;
        this.registerForm.reset();
    }

}
