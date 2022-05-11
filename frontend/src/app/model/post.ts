export class Post {

  private  postId:number;
  private  description:string;
  private  likes:number;
  private  dislikes:number;
  private  createdDate:Date;
  private  creator:string;


  constructor(postId: number, description: string, likes: number, dislikes: number, createdDate: Date, creator: string) {
    this.postId = postId;
    this.description = description;
    this.likes = likes;
    this.dislikes = dislikes;
    this.createdDate = createdDate;
    this.creator = creator;
  }
}
