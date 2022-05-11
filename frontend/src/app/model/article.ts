export class Article {

  private articleId:number;
  private articleName:string;
  private url1:string='some url';
  private description: string;
  private voteCount:number;
  private creator: string;
  private createdDate: Date;
  private category: string;


  constructor(articleId: number, articleName: string, url1: string, description: string, voteCount: number, creator: string, createdDate: Date, category: string) {
    this.articleId = articleId;
    this.articleName = articleName;
    this.url1 = url1;
    this.description = description;
    this.voteCount = voteCount;
    this.creator = creator;
    this.createdDate = createdDate;
    this.category = category;
  }
}
