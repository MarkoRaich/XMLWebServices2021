export class ProfileInfo {

    constructor(
        public id : Number,
        public username : String,
        public fullName : String,
        public bio : String,
        public birthDate : Date,
        public email : String,
        public gender : String,
        public website : String,
        public followerCount : Number,
        public followingCount : Number,
        public owned : boolean,
        public privateProfile : boolean,
        public following : boolean,
        public verified : boolean
    ){}
    
}
