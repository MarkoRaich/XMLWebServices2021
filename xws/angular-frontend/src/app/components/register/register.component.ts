import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Admin } from 'src/app/models/admin';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  admin : Admin;

  submitted = false;

  constructor(   private toastr : ToastrService, 
                 private formBuilder : FormBuilder,
                 private userService: UserService,
                 private router: Router) { }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      email : new FormControl(null,  [Validators.required, Validators.email]),
      password : new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] ),
      repeatedPassword :  new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] ),
      firstName : new FormControl(null, [Validators.required]),
      lastName : new FormControl(null, [Validators.required]),
    })


  }

   // convenience getter for easy access to form fields
   get f() { return this.registerForm.controls; }

   onSubmit() {

      if(this.registerForm.value.password != this.registerForm.value.repeatedPassword){
        this.toastr.error("Lozinka i ponovljena lozinka se ne smeju razlikovati. Pokušajte ponovo.");
      }

      this.admin = new Admin( this.registerForm.value.email,
                              this.registerForm.value.password,
                              this.registerForm.value.firstName,
                              this.registerForm.value.lastName )

      this.userService.registerAdmin(this.admin).subscribe(
        () => {
          this.toastr.success("Uspešno Ste registrovali novog administratora");
          this.router.navigate(['certificates/all-certificates']);
        },
        (error) => {
          this.toastr.error("Došlo je do greške...");
        }
      )


   }

}
