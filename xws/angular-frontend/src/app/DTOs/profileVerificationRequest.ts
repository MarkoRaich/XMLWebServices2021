export class ProfileVerificationRequest {
    constructor(
        public id : Number,
        public name : String,
        public surname: String,
        public category : String,
        public url : String
    ){}
}