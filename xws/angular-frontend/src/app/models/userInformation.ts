export class UserInformation {

    username:string;
    accountName:string;
    firstName:string;
    lastName:string;
    biography:string;
    phoneNumber:string;
    dateOfBirth:string;
    gender:string;
    website:string;
    userIsPublic:boolean;
    canReceiveMessages:boolean;
    canBeTagged:boolean;
    canReceiveNotifications:boolean;

    constructor(    username:string,
                    accountName:string,
                    firstName:string,
                    lastName:string,
                    biography:string,
                    phoneNumber:string,
                    dateOfBirth:string,
                    gender:string,
                    website:string,
                    userIsPublic:boolean,
                    canReceiveMessages:boolean,
                    canBeTagged:boolean,
                    canReceiveNotifications:boolean ){
                   
        this.username=username;
        this.accountName =accountName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.website = website;
        this.userIsPublic = userIsPublic;
        this.canReceiveMessages = canReceiveMessages;
        this.canBeTagged = canBeTagged;
        this.canReceiveNotifications = canReceiveNotifications;
        
    }
} 