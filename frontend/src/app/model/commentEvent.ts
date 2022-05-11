import {User} from "./User";
import {Event} from "./Event";

export class CommentEvent {
  idCommentE !: number;
  content !: string;
  createDate !: string;
  event !: Event;
  user !: User
}
