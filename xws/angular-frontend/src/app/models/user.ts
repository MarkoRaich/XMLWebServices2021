export class User {
    
    email: string;
    password: string;
    accountName: string;
    firstName: string;
    lastName: string;
    biography: string;
    phoneNumber: string;
    dateOfBirth: string;
    gender: string;


    constructor( email: string,
                password: string,
                accountName: string,
                firstName: string,
                lastName: string,
                biography: string,
                phoneNumber: string,
                dateOfBirth: string,
                gender: string      ) {

                this.email =email;
                this.password = password;
                this.accountName = accountName;
                this.firstName = firstName;
                this.lastName = lastName;
                this.biography = biography;
                this.phoneNumber = phoneNumber;
                this.dateOfBirth = dateOfBirth;
                this.gender = gender;
    }

}