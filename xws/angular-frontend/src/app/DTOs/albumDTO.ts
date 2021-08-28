export class AlbumDTO {
    constructor(
        public postSelected : boolean,
        public closeFriends : boolean,
        public tags : string[],
        public location : string,
        public description : string
    ){}
}
