export class PostDTO {
    constructor(
        public tags : string[],
        public location : string,
        public description : string
    ){}
}
