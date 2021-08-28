export class Post {

    constructor(
        public id : Number,
        public username : String,
        public urls : String[],
        public location : String,
        public description : String,
        public hashtags : String[],
        //public comments : Comment[],
        //public ratings : Rating[]
        public created : Date
    ){}
    
}
